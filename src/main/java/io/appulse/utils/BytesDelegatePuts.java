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

import static io.appulse.utils.BytesUtils.align;
import static io.appulse.utils.BytesUtils.toBytes;
import static java.nio.charset.StandardCharsets.UTF_8;
import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.Charset;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Artem Labazin
 * @since 1.0.0
 */
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
final class BytesDelegatePuts {

  Bytes bytes;

  public Bytes putNB (byte value, int length) {
    return bytes.put(align(toBytes(value), length));
  }

  public Bytes putNB (int index, byte value, int length) {
    return bytes.put(index, align(toBytes(value), length));
  }

  public Bytes putNB (short value, int length) {
    return bytes.put(align(toBytes(value), length));
  }

  public Bytes putNB (int index, short value, int length) {
    return bytes.put(index, align(toBytes(value), length));
  }

  public Bytes putNB (int value, int length) {
    return bytes.put(align(toBytes(value), length));
  }

  public Bytes putNB (int index, int value, int length) {
    return bytes.put(index, align(toBytes(value), length));
  }

  public Bytes putNB (long value, int length) {
    return bytes.put(align(toBytes(value), length));
  }

  public Bytes putNB (int index, long value, int length) {
    return bytes.put(index, align(toBytes(value), length));
  }

  public Bytes putNB (byte[] value, int length) {
    return bytes.put(align(value, length));
  }

  public Bytes putNB (int index, byte[] value, int length) {
    return bytes.put(index, align(value, length));
  }

  public Bytes put1B (byte value) {
    return bytes.put(value);
  }

  public Bytes put1B (int index, byte value) {
    return bytes.put(value);
  }

  public Bytes put1B (short value) {
    return bytes.put((byte) value);
  }

  public Bytes put1B (int index, short value) {
    return bytes.put(index, (byte) value);
  }

  public Bytes put1B (int value) {
    return bytes.put((byte) value);
  }

  public Bytes put1B (int index, int value) {
    return bytes.put(index, (byte) value);
  }

  public Bytes put1B (long value) {
    return bytes.put((byte) value);
  }

  public Bytes put1B (int index, long value) {
    return bytes.put(index, (byte) value);
  }

  public Bytes put2B (byte value) {
    return putNB(value, 2);
  }

  public Bytes put2B (int index, byte value) {
    return putNB(index, value, 2);
  }

  public Bytes put2B (short value) {
    return putNB(value, 2);
  }

  public Bytes put2B (int index, short value) {
    return putNB(index, value, 2);
  }

  public Bytes put2B (int value) {
    return putNB(value, 2);
  }

  public Bytes put2B (int index, int value) {
    return putNB(index, value, 2);
  }

  public Bytes put2B (long value) {
    return putNB(value, 2);
  }

  public Bytes put2B (int index, long value) {
    return putNB(index, value, 2);
  }

  public Bytes put4B (byte value) {
    return putNB(value, 4);
  }

  public Bytes put4B (int index, byte value) {
    return putNB(index, value, 4);
  }

  public Bytes put4B (short value) {
    return putNB(value, 4);
  }

  public Bytes put4B (int index, short value) {
    return putNB(index, value, 4);
  }

  public Bytes put4B (int value) {
    return putNB(value, 4);
  }

  public Bytes put4B (int index, int value) {
    return putNB(index, value, 4);
  }

  public Bytes put4B (long value) {
    return putNB(value, 4);
  }

  public Bytes put4B (int index, long value) {
    return putNB(index, value, 4);
  }

  public Bytes put8B (byte value) {
    return putNB(value, 8);
  }

  public Bytes put8B (int index, byte value) {
    return putNB(index, value, 8);
  }

  public Bytes put8B (short value) {
    return putNB(value, 8);
  }

  public Bytes put8B (int index, short value) {
    return putNB(index, value, 8);
  }

  public Bytes put8B (int value) {
    return putNB(value, 8);
  }

  public Bytes put8B (int index, int value) {
    return putNB(index, value, 8);
  }

  public Bytes put8B (long value) {
    return putNB(value, 8);
  }

  public Bytes put8B (int index, long value) {
    return putNB(index, value, 8);
  }

  public Bytes put (short value) {
    return bytes.put(toBytes(value));
  }

  public Bytes put (int index, short value) {
    return bytes.put(index, toBytes(value));
  }

  public Bytes put (int value) {
    return bytes.put(toBytes(value));
  }

  public Bytes put (int index, int value) {
    return bytes.put(index, toBytes(value));
  }

  public Bytes put (long value) {
    return bytes.put(toBytes(value));
  }

  public Bytes put (int index, long value) {
    return bytes.put(index, toBytes(value));
  }

  public Bytes put (float value) {
    return bytes.put(toBytes(value));
  }

  public Bytes put (int index, float value) {
    return bytes.put(index, toBytes(value));
  }

  public Bytes put (double value) {
    return bytes.put(toBytes(value));
  }

  public Bytes put (int index, double value) {
    return bytes.put(index, toBytes(value));
  }

  public Bytes put (char value) {
    return bytes.put(toBytes(value));
  }

  public Bytes put (int index, char value) {
    return bytes.put(index, toBytes(value));
  }

  public Bytes put (String value) {
    return bytes.put(value.getBytes(UTF_8));
  }

  public Bytes put (String value, Charset charset) {
    return bytes.put(value.getBytes(charset));
  }

  public Bytes put (int index, String value) {
    return bytes.put(index, value.getBytes(UTF_8));
  }

  public Bytes put (int index, String value, Charset charset) {
    return bytes.put(index, value.getBytes(charset));
  }

  public Bytes put (Bytes value) {
    return bytes.put(value.array());
  }

  public Bytes put (int index, Bytes value) {
    return bytes.put(index, value.array());
  }
}
