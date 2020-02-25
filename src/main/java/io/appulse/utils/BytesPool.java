/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appulse.utils;

import static io.appulse.utils.SizeUnit.KILOBYTES;
import static java.util.Locale.ENGLISH;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;
import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * The customisable {@link Bytes} pool.
 *
 * @since 1.15.0
 * @author Artem Labazin
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BytesPool implements AutoCloseable {

  private static final int DEFAULT_INITIAL_BUFFERS_COUNT = 2;

  private static final int DEFAULT_MAXIMUM_BUFFERS_COUNT = Integer.MAX_VALUE;

  private static final int DEFAULT_INITIAL_BUFFER_SIZE = (int) KILOBYTES.toBytes(8);

  int maximumBuffersCount;

  int initialBufferSizeBytes;

  Function<Integer, Bytes> bufferCreateFunction;

  BlockingQueue<Bytes> buffers;

  LongAdder acquiredBuffersCount;

  LongAdder totalElements;

  Lock createNewLock;

  public BytesPool () {
    this(null, null, null, null);
  }

  @Builder
  BytesPool (Integer initialBuffersCount,
             Integer maximumBuffersCount,
             Integer initialBufferSizeBytes,
             Function<Integer, Bytes> bufferCreateFunction
  ) {
    int initialBuffers = ofNullable(initialBuffersCount)
        .filter(it -> it >= 0)
        .orElse(DEFAULT_INITIAL_BUFFERS_COUNT);

    this.maximumBuffersCount = ofNullable(maximumBuffersCount)
        .filter(it -> it >= 0)
        .filter(it -> it >= initialBuffers)
        .orElse(DEFAULT_MAXIMUM_BUFFERS_COUNT);

    this.initialBufferSizeBytes = ofNullable(initialBufferSizeBytes)
        .filter(it -> it > 0)
        .orElse(DEFAULT_INITIAL_BUFFER_SIZE);

    this.bufferCreateFunction = ofNullable(bufferCreateFunction)
        .orElse(Bytes::resizableArray);

    totalElements = new LongAdder();
    acquiredBuffersCount = new LongAdder();
    createNewLock = new ReentrantLock(true);

    buffers = IntStream.range(0, initialBuffers)
        .mapToObj(it -> this.bufferCreateFunction.apply(this.initialBufferSizeBytes))
        .peek(it -> totalElements.increment())
        .collect(toCollection(LinkedBlockingQueue::new));
  }

  /**
   * Return the pooled bytes buffer.
   *
   * @return the pooled {@link Bytes} instance
   */
  @SneakyThrows
  public PooledBytes acquire () {
    val buffer = getOrCreateBuffer();
    acquiredBuffersCount.increment();
    buffer.reset();
    return new PooledBytes(buffer);
  }

  /**
   * Return the pooled bytes buffer with guaranteed minimal size.
   *
   * @param minSize expected minimal buffer size in mytes
   *
   * @return the pooled {@link Bytes} instance
   */
  public PooledBytes acquire (int minSize) {
    val result = acquire();
    if (result.capacity() < minSize) {
      result.capacity(minSize);
    }
    return result;
  }

  /**
   * Release the acquired bytes buffer.
   *
   * @param buffer the buffer to release
   */
  @SuppressWarnings("PMD.AccessorMethodGeneration")
  public void release (@NonNull PooledBytes buffer) {
    if (buffer.parent != this) {
      throw new IllegalArgumentException("The buffer not from this pool");
    }
    val detached = buffer.detach();
    if (!buffers.offer(detached)) {
      throw new IllegalStateException("Unexpected behaviour");
    }
    acquiredBuffersCount.decrement();
  }

  /**
   * Returns the acquired buffers count.
   *
   * @return acquired count
   */
  public int getAcquiredCount () {
    return acquiredBuffersCount.intValue();
  }

  /**
   * Returns the free buffers count.
   *
   * @return free count
   */
  public int getFreeCount () {
    return maximumBuffersCount - acquiredBuffersCount.intValue();
  }

  /**
   * Returns the total buffers count.
   *
   * @return total count
   */
  public int getTotalCount () {
    return totalElements.intValue();
  }

  @Override
  public void close () {
    createNewLock.lock();
    try {
      buffers.clear();
    } finally {
      createNewLock.unlock();
    }
  }

  private Bytes getOrCreateBuffer () throws InterruptedException {
    val buffer = buffers.poll();
    if (buffer != null) {
      return buffer;
    }

    createNewLock.lock();
    try {
      if (totalElements.sum() >= maximumBuffersCount) {
        return buffers.take();
      }
      val newBuffer = bufferCreateFunction.apply(initialBufferSizeBytes);
      totalElements.increment();
      return newBuffer;
    } finally {
      createNewLock.unlock();
    }
  }

  /**
   * Specific {@link Bytes} implementation for the pools.
   */
  @FieldDefaults(level = PRIVATE)
  @SuppressWarnings("PMD.LinguisticNaming")
  public final class PooledBytes extends BytesAbstract implements AutoCloseable {

    Bytes delegate;

    BytesPool parent = BytesPool.this;

    PooledBytes (Bytes delegate) {
      super();
      this.delegate = delegate;
    }

    /**
     * Releases the {@code this} Bytes instance in its pool back.
     */
    public void release () {
      BytesPool.this.release(this);
    }

    @Override
    public void close () {
      release();
    }

    @Override
    public boolean isAutoResizable () {
      validate();
      return delegate.isAutoResizable();
    }

    @Override
    public Bytes writeNB (byte[] bytes, int offset, int length) {
      validate();
      return delegate.writeNB(bytes, offset, length);
    }

    @Override
    public Bytes write1B (byte value) {
      validate();
      return delegate.write1B(value);
    }

    @Override
    public Bytes write2B (short value) {
      validate();
      return delegate.write2B(value);
    }

    @Override
    public Bytes write4B (int value) {
      validate();
      return delegate.write4B(value);
    }

    @Override
    public Bytes write8B (long value) {
      validate();
      return delegate.write8B(value);
    }

    @Override
    public Bytes setNB (int index, byte[] bytes, int offset, int length) {
      validate();
      return delegate.setNB(index, bytes, offset, length);
    }

    @Override
    public Bytes set1B (int index, byte value) {
      validate();
      return delegate.set1B(index, value);
    }

    @Override
    public Bytes set2B (int index, short value) {
      validate();
      return delegate.set2B(index, value);
    }

    @Override
    public Bytes set4B (int index, int value) {
      validate();
      return delegate.set4B(index, value);
    }

    @Override
    public Bytes set8B (int index, long value) {
      validate();
      return delegate.set8B(index, value);
    }

    @Override
    public byte readByte () {
      validate();
      return delegate.readByte();
    }

    @Override
    public short readShort () {
      validate();
      return delegate.readShort();
    }

    @Override
    public int readInt () {
      validate();
      return delegate.readInt();
    }

    @Override
    public long readLong () {
      validate();
      return delegate.readLong();
    }

    @Override
    public float readFloat () {
      validate();
      return delegate.readFloat();
    }

    @Override
    public double readDouble () {
      validate();
      return delegate.readDouble();
    }

    @Override
    public char readChar () {
      validate();
      return delegate.readChar();
    }

    @Override
    public Bytes readBytes (byte[] destination, int offset, int length) {
      validate();
      return delegate.readBytes(destination, offset, length);
    }

    @Override
    public byte getByte (int index) {
      validate();
      return delegate.getByte(index);
    }

    @Override
    public short getShort (int index) {
      validate();
      return delegate.getShort(index);
    }

    @Override
    public int getInt (int index) {
      validate();
      return delegate.getInt(index);
    }

    @Override
    public long getLong (int index) {
      validate();
      return delegate.getLong(index);
    }

    @Override
    public float getFloat (int index) {
      validate();
      return delegate.getFloat(index);
    }

    @Override
    public double getDouble (int index) {
      validate();
      return delegate.getDouble(index);
    }

    @Override
    public char getChar (int index) {
      validate();
      return delegate.getChar(index);
    }

    @Override
    public byte[] getBytes (int index, int length) {
      validate();
      return delegate.getBytes(index, length);
    }

    @Override
    public String getString (int index, int length, Charset charset) {
      validate();
      return delegate.getString(index, length, charset);
    }

    @Override
    public int capacity () {
      validate();
      return delegate.capacity();
    }

    @Override
    public void capacity (int bytes) {
      validate();
      delegate.capacity(bytes);
    }

    @Override
    public int writerIndex () {
      validate();
      return delegate.writerIndex();
    }

    @Override
    public Bytes writerIndex (int newIndex) {
      validate();
      return delegate.writerIndex(newIndex);
    }

    @Override
    public int readerIndex () {
      validate();
      return delegate.readerIndex();
    }

    @Override
    public Bytes readerIndex (int newIndex) {
      validate();
      return delegate.readerIndex(newIndex);
    }

    @Override
    public byte[] array () {
      validate();
      return delegate.array();
    }

    private void validate () {
      if (delegate != null) {
        return;
      }
      val msg = String.format(ENGLISH, "Pooled bytes buffer already released");
      throw new IllegalStateException(msg);
    }

    @SuppressWarnings("PMD.NullAssignment")
    private Bytes detach () {
      val result = delegate;
      delegate = null;
      return result;
    }
  }
}
