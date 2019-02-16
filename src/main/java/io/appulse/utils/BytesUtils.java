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

import static java.lang.Math.min;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.3.0
 */
public final class BytesUtils {

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

  /**
   * Converts byte to unsigned byte.
   *
   * @param value byte value
   *
   * @return unsigned byte value as short integer
   *
   * @since 1.3.1
   */
  public static short asUnsignedByte (byte value) {
    return (short) (value & 0xff);
  }

  /**
   * Transforms byte array to unsigned byte value as short integer.
   *
   * @param bytes byte array
   *
   * @return unsigned byte
   *
   * @since 1.3.1
   */
  public static short asUnsignedByte (@NonNull byte[] bytes) {
    return asShort(bytes);
  }

  public static short asShort (@NonNull byte[] bytes) {
    val aligned = align(bytes, Short.BYTES);
    return (short) ((aligned[0] << 8) | (aligned[1] & 0xff));
  }

  /**
   * Transforms byte array to unsigned short integer value as integer.
   *
   * @param value signed short value
   *
   * @return unsigned short as integer
   *
   * @since 1.5.2
   */
  public static int asUnsignedShort (short value) {
    return value & 0xFFFF;
  }

  /**
   * Transforms byte array to unsigned short integer value as integer.
   *
   * @param bytes byte array
   *
   * @return unsigned short as integer
   *
   * @since 1.3.1
   */
  public static int asUnsignedShort (@NonNull byte[] bytes) {
    return asShort(bytes) & 0xFFFF;
  }

  public static char asChar (@NonNull byte[] bytes) {
    val aligned = align(bytes, Short.BYTES);
    return (char) ((aligned[0] << 8) | (aligned[1] & 0xff));
  }

  public static int asInteger (@NonNull byte[] bytes) {
    val aligned = align(bytes, Integer.BYTES);
    return (aligned[0] << 24)
           | ((aligned[1] & 0xff) << 16)
           | ((aligned[2] & 0xff) << 8)
           | (aligned[3] & 0xff);
  }

  /**
   * Transforms byte array to unsigned integer value as long integer.
   *
   * @param value signed integer value
   *
   * @return unsigned integer as long
   *
   * @since 1.5.2
   */
  public static long asUnsignedInteger (int value) {
    return value & 0x00000000FFFFFFFFL;
  }

  /**
   * Transforms byte array to unsigned integer value as long integer.
   *
   * @param bytes byte array
   *
   * @return unsigned integer as long
   *
   * @since 1.3.1
   */
  public static long asUnsignedInteger (@NonNull byte[] bytes) {
    return asLong(bytes);
  }

  public static long asLong (@NonNull byte[] bytes) {
    val aligned = align(bytes, Long.BYTES);
    return ((long) aligned[0] << 56)
           | (((long) aligned[1] & 0xff) << 48)
           | (((long) aligned[2] & 0xff) << 40)
           | (((long) aligned[3] & 0xff) << 32)
           | (((long) aligned[4] & 0xff) << 24)
           | (((long) aligned[5] & 0xff) << 16)
           | (((long) aligned[6] & 0xff) << 8)
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

  /**
   * Reads all bytes from stream till the end of the stream.
   *
   * @param stream steam
   *
   * @return readed bytes array
   *
   * @since 1.3.0
   */
  @SneakyThrows
  public static byte[] read (@NonNull InputStream stream) {
    val outputStream = new ByteArrayOutputStream(32);
    val buffer = new byte[32];

    while (true) {
      val length = stream.read(buffer);
      if (length == -1) {
        break;
      }
      outputStream.write(buffer, 0, length);
    }

    return outputStream.toByteArray();
  }

  /**
   * Reads fixed length bytes from the stream.
   *
   * @param stream steam
   * @param length how many bytes to read from stream
   *
   * @return readed bytes array
   *
   * @since 1.3.0
   */
  @SneakyThrows
  public static byte[] read (@NonNull InputStream stream, int length) {
    if (length < 0) {
      throw new IndexOutOfBoundsException();
    }

    val result = new byte[length];
    int readed = 0;

    while (readed < length) {
      val count = stream.read(result, readed, length - readed);
      if (count < -1) {
        throw new EOFException();
      }
      readed += count;
    }

    return result;
  }

  /**
   * Reads all bytes from stream till the end of the stream.
   *
   * @param stream steam
   *
   * @return readed bytes array wrapped in {@link Bytes} instance
   *
   * @since 1.3.0
   */
  public static Bytes readBytes (@NonNull InputStream stream) {
    val result = read(stream);
    return Bytes.wrap(result);
  }

  /**
   * Reads fixed length bytes from the stream.
   *
   * @param stream steam
   * @param length how many bytes to read from stream
   *
   * @return readed bytes array wrapped in {@link Bytes} instance
   *
   * @since 1.3.0
   */
  public static Bytes readBytes (@NonNull InputStream stream, int length) {
    val result = read(stream, length);
    return Bytes.wrap(result);
  }

  private BytesUtils () {
  }
}
