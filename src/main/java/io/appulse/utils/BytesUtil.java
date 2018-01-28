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
    return new byte[] {
        (byte) (value >> 8),
        (byte) value
    };
  }

  public static byte[] asBytes (byte value) {
    return new byte[] { value };
  }

  public static byte[] asBytes (short value) {
    return new byte[] {
        (byte) (value >> 8),
        (byte) value
    };
  }

  public static byte[] asBytes (int value) {
    return new byte[] {
        (byte) (value >> 24),
        (byte) (value >> 16),
        (byte) (value >> 8),
        (byte) value
    };
  }

  public static byte[] asBytes (long value) {
    return new byte[] {
        (byte) (value >> 56),
        (byte) (value >> 48),
        (byte) (value >> 40),
        (byte) (value >> 32),
        (byte) (value >> 24),
        (byte) (value >> 16),
        (byte) (value >> 8),
        (byte) value
    };
  }

  public static byte[] asBytes (float value) {
    return asBytes(Float.floatToRawIntBits(value));
  }

  public static byte[] asBytes (double value) {
    return asBytes(Double.doubleToRawLongBits(value));
  }

  public static short asShort (@NonNull byte[] bytes) {
    val aligned = align(bytes, Short.BYTES);
    return (short) ((aligned[0] <<  8) | (aligned[1] & 0xff));
  }

  public static char asChar (@NonNull byte[] bytes) {
    val aligned = align(bytes, Short.BYTES);
    return (char) ((aligned[0] <<  8) | (aligned[1] & 0xff));
  }

  public static int asInteger (@NonNull byte[] bytes) {
    val aligned = align(bytes, Integer.BYTES);
    return (aligned[0] << 24)
           | ((aligned[1] & 0xff) << 16)
           | ((aligned[2] & 0xff) <<  8)
           | (aligned[3] & 0xff);
  }

  public static long asLong (@NonNull byte[] bytes) {
    val aligned = align(bytes, Long.BYTES);
    return ((long) aligned[0] << 56)
           | (((long) aligned[1] & 0xff) << 48)
           | (((long) aligned[2] & 0xff) << 40)
           | (((long) aligned[3] & 0xff) << 32)
           | (((long) aligned[4] & 0xff) << 24)
           | (((long) aligned[5] & 0xff) << 16)
           | (((long) aligned[6] & 0xff) <<  8)
           | ((long) aligned[7] & 0xff);
  }

  public static float asFloat (@NonNull byte[] bytes) {
    val aligned = align(bytes, Float.BYTES);
    return Float.intBitsToFloat(asInteger(aligned));
  }

  public static double asDouble (@NonNull byte[] bytes) {
    val aligned = align(bytes, Double.BYTES);
    return Double.longBitsToDouble(asLong(aligned));
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
    if (length <= 0 || bytes.length == length) {
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
