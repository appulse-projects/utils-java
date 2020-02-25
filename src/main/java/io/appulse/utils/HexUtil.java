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

import static java.util.Locale.ENGLISH;

import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.IllegalFormatException;

import lombok.NonNull;
import lombok.val;

/**
 * The set of different HEX helper funtions.
 *
 * @since 1.16.0
 * @author Artem Labazin
 */
public final class HexUtil {

  private static final String NEW_LINE;

  static {
    String newLine;
    try (Formatter formatter = new Formatter()) {
      newLine = formatter.format("%n").toString();
    } catch (IllegalFormatException | FormatterClosedException ex) {
      newLine = "\n"; // Whaaaaaat?
    }
    NEW_LINE = newLine;
  }

  private static final char[] HEX_CODES = "0123456789ABCDEF".toCharArray();

  /**
   * Converts an array of bytes into a string.
   *
   * @param bytes an array of bytes
   *
   * @return a string containing a lexical representation of HEX binary
   *
   * @throws NullpointerException if {@code bytes} is null.
   *
   * @since 1.16.1
   */
  public static String toHexString (@NonNull byte[] bytes) {
    val result = new StringBuilder(bytes.length * 2);
    for (val value : bytes) {
      result.append(HEX_CODES[(value >> 4) & 0xF]);
      result.append(HEX_CODES[value & 0xF]);
    }
    return result.toString();
  }

  /**
   * Converts the string argument into an array of bytes.
   *
   * @param string a string containing lexical representation of HEX binary.
   *
   * @return an array of bytes represented by the string argument.
   *
   * @throws NullpointerException if {@code string} is null.
   *
   * @throws IllegalArgumentException if string parameter does not conform to lexical
   *                                  value space defined for HEX binary.
   *
   * @since 1.16.1
   */
  public static byte[] toByteArray (@NonNull String string) {
    val length = string.length();
    if (length % 2 != 0) {
      val msg = String.format(ENGLISH, "hexBinary needs to be even-length: %s", string);
      throw new IllegalArgumentException(msg);
    }

    val result = new byte[length / 2];
    for (int index = 0; index < length; index += 2) {
      int hight = hexToBin(string.charAt(index));
      int low = hexToBin(string.charAt(index + 1));
      if (hight == -1 || low == -1) {
        val msg = String.format(ENGLISH, "contains illegal character for hexBinary: %s", string);
        throw new IllegalArgumentException(msg);
      }
      result[index / 2] = (byte) (hight * 16 + low);
    }
    return result;
  }

  /**
   * Converts an integer value into two-chars string with leading 0.
   *
   * @param value the value to convert
   *
   * @return the result string
   */
  public static String byteToHex (int value) {
    return String.format(ENGLISH, "%02x", value);
  }

  /**
   * Converts an integer value into char.
   *
   * @param value the value to convert
   *
   * @return the value's {@code char} representation
   */
  public static char byteToChar (int value) {
    return value <= 0x1F || value >= 0x7F
           ? '.'
           : (char) value;
  }

  /**
   * Returns a multi-line hexadecimal dump of the specified byte array that is easy to read by humans.
   *
   * @param bytes the byte array to dump
   *
   * @return the human-readable multi-line hexadecimal dump
   */
  public static String prettyHexDump (@NonNull byte[] bytes) {
    val buffer = Bytes.wrap(bytes);
    return prettyHexDump(buffer);
  }

  /**
   * Returns a multi-line hexadecimal dump of the specified {@link Bytes} that is easy to read by humans.
   *
   * @param buffer the byte array buffer to dump
   *
   * @return the human-readable multi-line hexadecimal dump
   */
  public static String prettyHexDump (@NonNull Bytes buffer) {
    return prettyHexDump(buffer, buffer.readerIndex(), buffer.readableBytes());
  }

  /**
   * Returns a multi-line hexadecimal dump of the specified {@link Bytes} that is easy to read by humans,
   * starting at the given {@code offset} using the given {@code length}.
   *
   * @param buffer the byte array buffer to dump
   *
   * @param offset the buffer's offset
   *
   * @param length the dump's length
   *
   * @return the human-readable multi-line hexadecimal dump
   */
  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
  public static String prettyHexDump (@NonNull Bytes buffer, int offset, int length) {
    if (offset < 0 || length < 0 || offset + length > buffer.capacity()) {
      val msg = String.format(ENGLISH,
          "expected: 0 <= offset(%d) <= offset+length(%d) <= buffer.capacity(%d)",
          offset, length, buffer.capacity()
      );
      throw new IndexOutOfBoundsException(msg);
    }
    if (length == 0) {
      return "";
    }

    val result = new StringBuilder()
        .append("         +-------------------------------------------------+").append(NEW_LINE)
        .append("         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |").append(NEW_LINE)
        .append("+--------+-------------------------------------------------+----------------+");

    val elementsInRow = 16;
    val rows = (int) Math.ceil(length / (double) elementsInRow);
    for (int row = 0; row < rows; row++) {
      val hexDump = new StringBuilder("                                                 ");
      val asciiDump = new StringBuilder("                ");

      val rowStartIndex = row * elementsInRow + offset;
      val limit = Math.min(buffer.writerIndex() - rowStartIndex, elementsInRow);
      for (int index = 0; index < limit; index++) {
        val value = buffer.getUnsignedByte(rowStartIndex + index);

        val hexIndex = 1 + 3 * index;
        hexDump.setCharAt(hexIndex, HEX_CODES[(value >> 4) & 0xF]);
        hexDump.setCharAt(hexIndex + 1, HEX_CODES[value & 0xF]);

        asciiDump.setCharAt(index, byteToChar(value));
      }

      result.append(NEW_LINE)
          .append('|').append(String.format(ENGLISH, "%07x0", row))
          .append('|').append(hexDump)
          .append('|').append(asciiDump)
          .append('|');
    }

    return result.append(NEW_LINE)
        .append("+--------+-------------------------------------------------+----------------+")
        .toString();
  }

  private static int hexToBin (char character) {
    if (Character.isDigit(character)) {
      return character - '0';
    }

    val letter = Character.toUpperCase(character);
    return 'A' <= letter && letter <= 'F'
           ? letter - 'A' + 10
           : -1;
  }

  private HexUtil () {
    throw new UnsupportedOperationException();
  }
}
