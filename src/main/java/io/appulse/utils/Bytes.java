/*
 * Copyright 2018 the original author or authors.
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

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.0.0
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class Bytes {

  public static Bytes wrap (byte[] bytes) {
    val result = allocate(bytes.length);
    return result.put(bytes);
  }

  public static Bytes wrap (ByteBuffer buffer) {
    val clone = ByteBuffer.wrap(buffer.array());
    return new Bytes(clone);
  }

  public static Bytes wrap (ByteBuf buf) {
    ByteBuffer buffer;
    if (buf.isDirect()) {
      buffer = buf.nioBuffer();
    } else {
      val bytes = new byte[buf.readableBytes()];
      buf.getBytes(buf.readerIndex(), bytes);
      buffer = ByteBuffer.wrap(bytes);
    }
    return new Bytes(buffer);
  }

  public static Bytes allocate () {
    return allocate(32);
  }

  public static Bytes allocate (int capacity) {
    val buffer = ByteBuffer.allocate(capacity);
    return new Bytes(buffer);
  }

  @Delegate
  BytesDelegatePuts puts;

  @Delegate
  BytesDelegatePutsAs putsAs;

  @Delegate
  BytesDelegateGets gets;

  @NonFinal
  @Getter(value = PACKAGE)
  ByteBuffer buffer;

  private Bytes (ByteBuffer buffer) {
    puts = new BytesDelegatePuts(this);
    putsAs = new BytesDelegatePutsAs(this);
    gets = new BytesDelegateGets(this);

    this.buffer = buffer;
  }

  public Bytes put (byte value) {
    checkCapacity(1);
    buffer.put(value);
    return this;
  }

  public Bytes put (int index, byte value) {
    buffer.put(index, value);
    return this;
  }

  public Bytes put (byte[] bytes) {
    checkCapacity(bytes.length);
    buffer.put(bytes);
    return this;
  }

  public Bytes put (int index, byte[] bytes) {
    checkCapacity(index, bytes.length);
    IntStream.range(index, index + bytes.length).forEach(it -> {
      buffer.put(it, bytes[it - index]);
    });
    return this;
  }

  public byte getByte () {
    return buffer.get();
  }

  public byte getByte (int index) {
    return buffer.get(index);
  }

  public byte[] getBytes () {
    return getBytes(0, buffer.remaining());
  }

  public byte[] getBytes (int length) {
    return getBytes(0, length);
  }

  public byte[] getBytes (int offset, int length) {
    val result = new byte[length];
    buffer.get(result, offset, length);
    return result;
  }

  public byte[] array () {
    return Arrays.copyOfRange(buffer.array(), 0, buffer.position());
  }

  public int size () {
    return buffer.position();
  }

  public int position () {
    return buffer.position();
  }

  public Bytes position (int position) {
    buffer.position(position);
    return this;
  }

  public void clear () {
    buffer.clear();
  }

  public void flip () {
    buffer.flip();
  }

  private void checkCapacity (int size) {
    if (buffer.remaining() >= size) {
      return;
    }

    int newCapacity;
    for (newCapacity = buffer.limit() * 2;
         newCapacity - buffer.position() < size;
         newCapacity *= 2) {
      // empty body
    }

    buffer.flip();
    buffer = ByteBuffer.allocate(newCapacity)
        .put(buffer);
  }

  private void checkCapacity (int from, int size) {
    int difference = size - (buffer.position() - from);
    if (difference > 0) {
      checkCapacity(difference);
    }
  }
}
