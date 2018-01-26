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

import static lombok.AccessLevel.PRIVATE;

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
final class BytesDelegatePutsAs {

  Bytes bytes;

  public Bytes putAsByte (byte value) {
    return bytes.put(value);
  }

  public Bytes putAsByte (short value) {
    return bytes.put((byte) value);
  }

  public Bytes putAsByte (int value) {
    return bytes.put((byte) value);
  }

  public Bytes putAsByte (long value) {
    return bytes.put((byte) value);
  }

  public Bytes putAsShort (byte value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Short.BYTES);
  }

  public Bytes putAsShort (short value) {
    return bytes.put(value);
  }

  public Bytes putAsShort (int value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Short.BYTES);
  }

  public Bytes putAsShort (long value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Short.BYTES);
  }

  public Bytes putAsInt (byte value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Integer.BYTES);
  }

  public Bytes putAsInt (short value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Integer.BYTES);
  }

  public Bytes putAsInt (int value) {
    return bytes.put(value);
  }

  public Bytes putAsInt (long value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Integer.BYTES);
  }

  public Bytes putAsLong (byte value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Long.BYTES);
  }

  public Bytes putAsLong (short value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Long.BYTES);
  }

  public Bytes putAsLong (int value) {
    val valueBytes = BytesUtil.asBytes(value);
    return putAligned(valueBytes, Long.BYTES);
  }

  public Bytes putAsLong (long value) {
    return bytes.put(value);
  }

  public Bytes putAligned (byte[] value, int length) {
    return bytes.put(BytesUtil.align(value, length));
  }
}
