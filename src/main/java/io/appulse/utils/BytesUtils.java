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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.stream.Stream;

import io.appulse.utils.exception.CantReadFromArrayException;
import io.appulse.utils.exception.CantWriteToArrayException;

import lombok.NonNull;
import lombok.val;

/**
 * Different methods for working with bytes.
 *
 * @since 1.3.0
 * @author Artem Labazin
 */
@SuppressWarnings({
    "PMD.ExcessiveClassLength",
    "PMD.GodClass",
    "PMD.CyclomaticComplexity"
})
public final class BytesUtils {

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteByte (int value, byte[] bytes, int index) {
    bytes[index] = (byte) value;
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteShort (int value, byte[] bytes, int index) {
    bytes[index] = (byte) (value >> 8);
    bytes[index + 1] = (byte) value;
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteInteger (int value, byte[] bytes, int index) {
    bytes[index] = (byte) (value >> 24);
    bytes[index + 1] = (byte) (value >> 16);
    bytes[index + 2] = (byte) (value >> 8);
    bytes[index + 3] = (byte) value;
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteLong (long value, byte[] bytes, int index) {
    bytes[index] = (byte) (value >> 56);
    bytes[index + 1] = (byte) (value >> 48);
    bytes[index + 2] = (byte) (value >> 40);
    bytes[index + 3] = (byte) (value >> 32);
    bytes[index + 4] = (byte) (value >> 24);
    bytes[index + 5] = (byte) (value >> 16);
    bytes[index + 6] = (byte) (value >> 8);
    bytes[index + 7] = (byte) value;
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteFloat (float value, byte[] bytes, int index) {
    val intValue = Float.floatToIntBits(value);
    unsafeWriteInteger(intValue, bytes, index);
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteDouble (double value, byte[] bytes, int index) {
    val longValue = Double.doubleToLongBits(value);
    unsafeWriteLong(longValue, bytes, index);
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteCharacter (char value, byte[] bytes, int index) {
    bytes[index] = (byte) (value >> 8);
    bytes[index + 1] = (byte) value;
  }

  /**
   * Unsafe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @since 1.11.2
   */
  public static void unsafeWriteBytes (byte[] value, byte[] bytes, int index) {
    for (int i = 0; i < value.length; i++) {
      bytes[index + i] = value[i];
    }
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeByte (int value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Byte.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Byte.BYTES);
    }
    unsafeWriteByte(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeShort (int value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Short.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Short.BYTES);
    }
    unsafeWriteShort(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeInteger (int value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Integer.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Integer.BYTES);
    }
    unsafeWriteInteger(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeLong (long value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Long.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Long.BYTES);
    }
    unsafeWriteLong(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeFloat (float value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Float.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Float.BYTES);
    }
    unsafeWriteFloat(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeDouble (double value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Double.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Double.BYTES);
    }
    unsafeWriteDouble(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeCharacter (char value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + Character.BYTES) {
      throw new CantWriteToArrayException(bytes, index, Character.BYTES);
    }
    unsafeWriteCharacter(value, bytes, index);
  }

  /**
   * Safe write to a byte array.
   *
   * @param value a value to write to the byte array
   *
   * @param bytes the byte array to write to
   *
   * @param index where to start write index
   *
   * @throws CantWriteToArrayException in case of discrepancy of the byte array's length and the write index
   *
   * @since 1.11.2
   */
  public static void writeBytes (byte[] value, @NonNull byte[] bytes, int index) {
    if (bytes.length < index + value.length) {
      throw new CantWriteToArrayException(bytes, index, value.length);
    }
    unsafeWriteBytes(value, bytes, index);
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (byte value) {
    val result = new byte[Byte.BYTES];
    writeByte(value, result, 0);
    return result;
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (short value) {
    val result = new byte[Short.BYTES];
    writeShort(value, result, 0);
    return result;
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (char value) {
    val result = new byte[Character.BYTES];
    writeCharacter(value, result, 0);
    return result;
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (int value) {
    val result = new byte[Integer.BYTES];
    writeInteger(value, result, 0);
    return result;
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (long value) {
    val result = new byte[Long.BYTES];
    writeLong(value, result, 0);
    return result;
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (float value) {
    val result = new byte[Float.BYTES];
    writeFloat(value, result, 0);
    return result;
  }

  /**
   * Converts a value to a byte array.
   *
   * @param value the value to convert
   *
   * @return the byte array result
   *
   * @since 1.11.2
   */
  public static byte[] toBytes (double value) {
    val result = new byte[Double.BYTES];
    writeDouble(value, result, 0);
    return result;
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static short unsafeReadUnsignedByte (byte[] bytes, int index) {
    return (short) (bytes[index] & 0xff);
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static short unsafeReadShort (byte[] bytes, int index) {
    return (short) ((bytes[index] << 8) | (bytes[index + 1] & 0xff));
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static int unsafeReadUnsignedShort (byte[] bytes, int index) {
    val value = unsafeReadShort(bytes, index);
    return asUnsignedShort(value);
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static char unsafeReadCharacter (byte[] bytes, int index) {
    return (char) ((bytes[index] << 8) | (bytes[index + 1] & 0xff));
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static int unsafeReadInteger (byte[] bytes, int index) {
    return (bytes[index] << 24)
           | ((bytes[index + 1] & 0xff) << 16)
           | ((bytes[index + 2] & 0xff) << 8)
           | (bytes[index + 3] & 0xff);
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static long unsafeReadUnsignedInteger (byte[] bytes, int index) {
    val value = unsafeReadInteger(bytes, index);
    return asUnsignedInteger(value);
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static long unsafeReadLong (byte[] bytes, int index) {
    return ((long) bytes[index] << 56)
           | (((long) bytes[index + 1] & 0xff) << 48)
           | (((long) bytes[index + 2] & 0xff) << 40)
           | (((long) bytes[index + 3] & 0xff) << 32)
           | (((long) bytes[index + 4] & 0xff) << 24)
           | (((long) bytes[index + 5] & 0xff) << 16)
           | (((long) bytes[index + 6] & 0xff) << 8)
           | ((long) bytes[index + 7] & 0xff);
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static float unsafeReadFloat (byte[] bytes, int index) {
    val value = unsafeReadInteger(bytes, index);
    return Float.intBitsToFloat(value);
  }

  /**
   * Unsafe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @since 1.11.2
   */
  public static double unsafeReadDouble (byte[] bytes, int index) {
    val value = unsafeReadLong(bytes, index);
    return Double.longBitsToDouble(value);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static short readUnsignedByte (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Byte.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Byte.BYTES);
    }
    return unsafeReadUnsignedByte(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static short readShort (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Short.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Short.BYTES);
    }
    return unsafeReadShort(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static int readUnsignedShort (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Short.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Short.BYTES);
    }
    return unsafeReadUnsignedShort(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static char readCharacter (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Character.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Character.BYTES);
    }
    return unsafeReadCharacter(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static int readInteger (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Integer.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Integer.BYTES);
    }
    return unsafeReadInteger(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static long readUnsignedInteger (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Integer.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Integer.BYTES);
    }
    return unsafeReadUnsignedInteger(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static long readLong (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Long.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Long.BYTES);
    }
    return unsafeReadLong(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static float readFloat (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Long.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Long.BYTES);
    }
    return unsafeReadFloat(bytes, index);
  }

  /**
   * Safe read a value from a byte array.
   *
   * @param bytes the byte array to read from
   *
   * @param index where to start read index
   *
   * @return a parsed value
   *
   * @throws CantReadFromArrayException in case of discrepancy of the byte array's length and the read index
   *
   * @since 1.11.2
   */
  public static double readDouble (@NonNull byte[] bytes, int index) {
    if (bytes.length < index + Double.BYTES) {
      throw new CantReadFromArrayException(bytes, index, Double.BYTES);
    }
    return unsafeReadDouble(bytes, index);
  }

  /**
   * Converts a value into an unsigned value.
   *
   * @param value the signed value
   *
   * @return the unsigned value
   *
   * @since 1.3.1
   */
  public static short asUnsignedByte (byte value) {
    return (short) (((int) value) & 0xff);
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   *
   * @since 1.3.1
   */
  public static short asUnsignedByte (@NonNull byte[] bytes) {
    return asShort(bytes);
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static short asShort (@NonNull byte[] bytes) {
    val aligned = align(bytes, Short.BYTES);
    return unsafeReadShort(aligned, 0);
  }

  /**
   * Converts a value into an unsigned value.
   *
   * @param value the signed value
   *
   * @return the unsigned value
   *
   * @since 1.5.2
   */
  public static int asUnsignedShort (short value) {
    return ((int) value) & 0xFFFF;
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   *
   * @since 1.3.1
   */
  public static int asUnsignedShort (@NonNull byte[] bytes) {
    return asShort(bytes) & 0xFFFF;
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static char asChar (@NonNull byte[] bytes) {
    val aligned = align(bytes, Short.BYTES);
    return unsafeReadCharacter(aligned, 0);
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static int asInteger (@NonNull byte[] bytes) {
    val aligned = align(bytes, Integer.BYTES);
    return unsafeReadInteger(aligned, 0);
  }

  /**
   * Converts a value into an unsigned value.
   *
   * @param value the signed value
   *
   * @return the unsigned value
   *
   * @since 1.5.2
   */
  public static long asUnsignedInteger (int value) {
    return ((long) value) & 0xFFFFFFFFL;
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static long asUnsignedInteger (@NonNull byte[] bytes) {
    return asLong(bytes);
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static long asLong (@NonNull byte[] bytes) {
    val aligned = align(bytes, Long.BYTES);
    return unsafeReadLong(aligned, 0);
  }

  public static BigInteger asUnsignedLong (long value) {
    val bytes = new byte[] {
        (byte) (value >> 56),
        (byte) (value >> 48),
        (byte) (value >> 40),
        (byte) (value >> 32),
        (byte) (value >> 24),
        (byte) (value >> 16),
        (byte) (value >> 8),
        (byte) (value >> 0)
    };
    return asUnsignedLong(bytes);
  }

  public static BigInteger asUnsignedLong (@NonNull byte[] bytes) {
    val aligned = align(bytes, Long.BYTES);
    return new BigInteger(1, aligned);
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static float asFloat (@NonNull byte[] bytes) {
    val aligned = align(bytes, Float.BYTES);
    return unsafeReadFloat(aligned, 0);
  }

  /**
   * Parses a byte array into a value.
   * <p>
   * The main difference from the {@code read*} methods -
   * before parsing the byte array the method aligns it an
   * expected size.
   *
   * @param bytes the byte array for parsing
   *
   * @return the parsed value
   */
  public static double asDouble (@NonNull byte[] bytes) {
    val aligned = align(bytes, Double.BYTES);
    return unsafeReadDouble(aligned, 0);
  }

  /**
   * Concatenates the several byte arrays into a single one.
   *
   * @param arrays the byte arrays for concatenation
   *
   * @return the byte array
   */
  public static byte[] concatenate (@NonNull byte[]... arrays) {
    val size = Stream.of(arrays)
        .mapToInt(it -> it.length)
        .sum();

    val buffer = ByteBuffer.allocate(size);
    Stream.of(arrays).forEach(buffer::put);
    return buffer.array();
  }

  /**
   * Aligns (expand or narrow) a byte array to a specified length.
   *
   * @param bytes the byte array for operation
   *
   * @param length the new array's length
   *
   * @return an aligned byte array
   */
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

    System.arraycopy(bytes, srcPos, result, destPos, Math.min(bytes.length, length));
    return result;
  }

  private BytesUtils () {
    throw new UnsupportedOperationException();
  }
}
