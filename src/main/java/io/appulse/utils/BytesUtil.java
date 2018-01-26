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

import static java.lang.Math.min;

import java.nio.ByteBuffer;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.0.0
 */
public final class BytesUtil {

  public static byte[] asBytes (char value) {
    return ByteBuffer.allocate(Character.BYTES).putChar(value).array();
  }

  public static byte[] asBytes (byte value) {
    return new byte[] { value };
  }

  public static byte[] asBytes (short value) {
    return ByteBuffer.allocate(Short.BYTES).putShort(value).array();
  }

  public static byte[] asBytes (int value) {
    return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
  }

  public static byte[] asBytes (long value) {
    return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
  }

  public static byte[] asBytes (float value) {
    return ByteBuffer.allocate(Float.BYTES).putFloat(value).array();
  }

  public static byte[] asBytes (double value) {
    return ByteBuffer.allocate(Double.BYTES).putDouble(value).array();
  }

  public static int asInteger (@NonNull ByteBuffer buffer, int length) {
    val bytes = new byte[length];
    buffer.get(bytes, 0, length);
    return asInteger(bytes);
  }

  public static int asInteger (@NonNull byte[] bytes) {
    val aligned = align(bytes, Integer.BYTES);
    return ByteBuffer.wrap(aligned).getInt();
  }

  public static byte[] concatenate (@NonNull byte[]... arrays) {
    val size = Stream.of(arrays)
        .mapToInt(it -> it.length)
        .sum();

    val buffer = ByteBuffer.allocate(size);
    Stream.of(arrays).forEach(buffer::put);
    return buffer.array();
  }

  public static byte[] align (@NonNull byte[] bytes, int length) {
    if (length <= 0) {
      return bytes;
    }

    val result = new byte[length];
    int srcPos = 0;
    int destPos = 0;

    if (bytes.length > length) {
      srcPos = bytes.length - length;
    } else if (bytes.length < length) {
      destPos = length - bytes.length;
    }

    System.arraycopy(bytes, srcPos, result, destPos, min(bytes.length, length));
    return result;
  }

  private BytesUtil () {
  }
}
