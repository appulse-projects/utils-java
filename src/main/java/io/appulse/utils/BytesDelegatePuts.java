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
final class BytesDelegatePuts {

  Bytes bytes;

  public Bytes put (short value) {
    return bytes.put(BytesUtil.asBytes(value));
  }

  public Bytes put (int index, short value) {
    return bytes.put(index, BytesUtil.asBytes(value));
  }

  public Bytes put (int value) {
    return bytes.put(BytesUtil.asBytes(value));
  }

  public Bytes put (int index, int value) {
    return bytes.put(index, BytesUtil.asBytes(value));
  }

  public Bytes put (long value) {
    return bytes.put(BytesUtil.asBytes(value));
  }

  public Bytes put (int index, long value) {
    return bytes.put(index, BytesUtil.asBytes(value));
  }

  public Bytes put (float value) {
    return bytes.put(BytesUtil.asBytes(value));
  }

  public Bytes put (int index, float value) {
    return bytes.put(index, BytesUtil.asBytes(value));
  }

  public Bytes put (double value) {
    return bytes.put(BytesUtil.asBytes(value));
  }

  public Bytes put (int index, double value) {
    return bytes.put(index, BytesUtil.asBytes(value));
  }

  public Bytes put (char value) {
    return bytes.put(BytesUtil.asBytes(value));
  }

  public Bytes put (int index, char value) {
    return bytes.put(index, BytesUtil.asBytes(value));
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
}
