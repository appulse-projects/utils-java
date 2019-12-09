/*
 * Copyright 2019 the original author or authors.
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

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.io.InputStream;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 * A {@code BytesInputStream} contains
 * an internal buffer that contains bytes that
 * may be read from the stream. An internal
 * counter keeps track of the next byte to
 * be supplied by the {@code read} method.
 * <p>
 * Closing a {@code BytesInputStream} has no effect. The methods in
 * this class can be called after the stream has been closed without
 * generating an {@code IOException}.
 *
 * @since 1.17.0
 * @author Artem Labazin
 */
@FieldDefaults(level = PRIVATE)
public class BytesInputStream extends InputStream {

  Bytes buffer;

  int mark;

  /**
   * Creates a <code>BytesInputStream</code>
   * so that it  uses <code>bytes</code> as its
   * buffer array.
   * The buffer array is not copied.
   *
   * @param bytes the input buffer.
   */
  public BytesInputStream (byte[] bytes) {
    super();
    buffer = Bytes.wrap(bytes);
  }

  /**
   * Creates a <code>BytesInputStream</code>
   * so that it  uses <code>bytes</code> as its
   * buffer array.
   * The buffer array is not copied.
   *
   * @param bytes the input buffer.
   */
  public BytesInputStream (Bytes bytes) {
    super();
    buffer = bytes;
  }

  /**
   * Creates <code>ByteArrayInputStream</code>
   * that uses <code>buf</code> as its
   * buffer array. The initial value of <code>pos</code>
   * is <code>offset</code> and the initial value
   * of <code>count</code> is the minimum of <code>offset+length</code>
   * and <code>buf.length</code>.
   * The buffer array is not copied. The buffer's mark is
   * set to the specified offset.
   *
   * @param bytes the input buffer.
   * @param offset the offset in the buffer of the first byte to read.
   * @param length the maximum number of bytes to read from the buffer.
   */
  public BytesInputStream (byte[] bytes, int offset, int length) {
    super();
    buffer = Bytes.wrap(bytes);
    buffer.readerIndex(offset);
    buffer.writerIndex(length);
  }

  @Override
  public synchronized int read () throws IOException {
    return buffer.isReadable()
           ? buffer.readUnsignedByte()
           : -1;
  }

  @Override
  public synchronized int read (@NonNull byte[] bytes, int offset, int length) {
    val readerIndex = buffer.readerIndex();
    buffer.readBytes(bytes, offset, length);
    return buffer.readerIndex() - readerIndex;
  }

  @Override
  public synchronized long skip (long skipBytes) {
    val readerIndex = buffer.readerIndex();
    val readableBytes = buffer.readableBytes();
    val skip = (int) Math.min(readableBytes, skipBytes);
    buffer.readerIndex(readerIndex + skip);
    return skip;
  }

  @Override
  public synchronized int available () {
    return buffer.readableBytes();
  }

  @Override
  public boolean markSupported () {
    return true;
  }

  @Override
  public synchronized void mark (int readAheadLimit) {
    mark = buffer.readerIndex();
  }

  @Override
  public synchronized void reset () {
    buffer.readerIndex(mark);
  }

  @Override
  public void close () {
    // no op
  }
}
