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

import static java.nio.charset.StandardCharsets.UTF_8;
import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.Charset;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.0.0
 */
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class BytesDelegateGets {

  Bytes bytes;

  /**
   * Returns unsigned byte as short integer (2 bytes).
   *
   * @return unsigned byte
   *
   * @since 1.3.1
   */
  public short getUnsignedByte () {
    return BytesUtils.asUnsignedByte(bytes.getByte());
  }

  /**
   * Returns unsigned byte as short integer (2 bytes) from specific position.
   *
   * @param index index in buffer, where extract unsigned byte.
   *
   * @return unsigned byte
   *
   * @since 1.3.1
   */
  public short getUnsignedByte (int index) {
    return BytesUtils.asUnsignedByte(bytes.getByte(index));
  }

  public char getChar () {
    return bytes.getBuffer().getChar();
  }

  public char getChar (int index) {
    return bytes.getBuffer().getChar(index);
  }

  public short getShort () {
    return bytes.getBuffer().getShort();
  }

  public int getUnsignedShort () {
    return getShort() & 0xFFFF;
  }

  public short getShort (int index) {
    return bytes.getBuffer().getShort(index);
  }

  /**
   * Returns unsigned short (2 bytes) as integer (4 bytes) from specific position.
   *
   * @param index index in buffer, where extract unsigned short.
   *
   * @return unsigned short
   *
   * @since 1.3.1
   */
  public int getUnsignedShort (int index) {
    return getShort(index) & 0xFFFF;
  }

  public int getInt () {
    return bytes.getBuffer().getInt();
  }

  /**
   * Returns unsigned integer (4 bytes) as long (8 bytes).
   *
   * @return unsigned integer
   *
   * @since 1.3.1
   */
  public long getUnsignedInt () {
    val intBytes = bytes.getBytes(Integer.BYTES);
    return BytesUtils.asUnsignedInteger(intBytes);
  }

  public int getInt (int index) {
    return bytes.getBuffer().getInt(index);
  }

  /**
   * Returns unsigned integer (4 bytes) as long (8 bytes) from specific position.
   *
   * @param index index in buffer, where extract unsigned intger.
   *
   * @return unsigned integer
   *
   * @since 1.3.1
   */
  public long getUnsignedInt (int index) {
    val oldPosition = bytes.position();
    try {
      bytes.position(index);
      val intBytes = bytes.getBytes(Integer.BYTES);
      return BytesUtils.asUnsignedInteger(intBytes);
    } finally {
      bytes.position(oldPosition);
    }
  }

  public long getLong () {
    return bytes.getBuffer().getLong();
  }

  public long getLong (int index) {
    return bytes.getBuffer().getLong(index);
  }

  public float getFloat () {
    return bytes.getBuffer().getFloat();
  }

  public float getFloat (int index) {
    return bytes.getBuffer().getFloat(index);
  }

  public double getDouble () {
    return bytes.getBuffer().getDouble();
  }

  public double getDouble (int index) {
    return bytes.getBuffer().getDouble(index);
  }

  public String getString () {
    return new String(bytes.getBytes(), UTF_8);
  }

  public String getString (Charset charset) {
    return new String(bytes.getBytes(), charset);
  }

  public String getString (int length) {
    return new String(bytes.getBytes(length), UTF_8);
  }

  public String getString (int length, Charset charset) {
    return new String(bytes.getBytes(length), charset);
  }
}
