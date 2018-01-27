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
final class BytesDelegateGets {

  Bytes bytes;

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

  public int getInt () {
    return bytes.getBuffer().getInt();
  }

  public int getInt (int index) {
    return bytes.getBuffer().getInt(index);
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
