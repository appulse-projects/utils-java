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

import lombok.Getter;
import lombok.NonNull;
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

  public static Bytes wrap (@NonNull byte[] bytes) {
    val result = allocate(bytes.length);
    result.put(bytes);
    result.flip();
    return result;
  }

  public static Bytes wrap (@NonNull ByteBuffer buffer) {
    return wrap(buffer.array());
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
  BytesDelegateGets gets;

  @NonFinal
  @Getter(value = PACKAGE)
  ByteBuffer buffer;

  @NonFinal
  int limit;

  private Bytes (ByteBuffer buffer) {
    puts = new BytesDelegatePuts(this);
    gets = new BytesDelegateGets(this);
    this.buffer = buffer;
    limit = buffer.position();
  }

  public Bytes put (byte value) {
    checkCapacity(1);
    buffer.put(value);
    limit++;
    return this;
  }

  public Bytes put (int index, byte value) {
    buffer.put(index, value);
    return this;
  }

  public Bytes put (@NonNull byte[] bytes) {
    checkCapacity(bytes.length);
    buffer.put(bytes);
    limit += bytes.length;
    return this;
  }

  public Bytes put (int index, @NonNull byte[] bytes) {
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
    return Arrays.copyOfRange(buffer.array(), 0, limit);
  }

  public int limit () {
    return limit;
  }

  public int remaining () {
    return limit - buffer.position();
  }

  public int position () {
    return buffer.position();
  }

  public Bytes position (int position) {
    if (position > limit || position < 0) {
      throw new IllegalArgumentException();
    }
    buffer.position(position);
    return this;
  }

  public Bytes clear () {
    buffer.clear();
    limit = 0;
    return this;
  }

  public Bytes flip () {
    limit = buffer.position();
    buffer.flip();
    return this;
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
