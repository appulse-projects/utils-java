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

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE)
class BytesByteBuffer extends AbstractBytes {

  @NonNull
  final ByteBuffer buffer;

  int writerIndex;

  int readerIndex;

  @Override
  public boolean isExtendable () {
    return false;
  }

  @Override
  public Bytes putNB (@NonNull byte[] bytes, int offset, int length) {
    putNB(writerIndex, bytes, offset, length);
    writerIndex += length;
    return this;
  }

  @Override
  public Bytes putNB (int index, @NonNull byte[] bytes, int offset, int length) {
    val position = buffer.position();
    buffer.position(index);
    buffer.put(bytes, offset, length);
    buffer.position(position);
    return this;
  }

  @Override
  public Bytes put1B (byte value) {
    put1B(writerIndex, value);
    writerIndex += Byte.BYTES;
    return this;
  }

  @Override
  public Bytes put1B (int index, byte value) {
    checkWriteBounds(index, Byte.BYTES);
    buffer.put(index, value);
    return this;
  }

  @Override
  public Bytes put2B (short value) {
    put2B(writerIndex, value);
    writerIndex += Short.BYTES;
    return this;
  }

  @Override
  public Bytes put2B (int index, short value) {
    checkWriteBounds(index, Short.BYTES);
    buffer.putShort(index, value);
    return this;
  }

  @Override
  public Bytes put4B (int value) {
    put4B(writerIndex, value);
    writerIndex += Integer.BYTES;
    return this;
  }

  @Override
  public Bytes put4B (int index, int value) {
    checkWriteBounds(index, Integer.BYTES);
    buffer.putInt(index, value);
    return this;
  }

  @Override
  public Bytes put8B (long value) {
    put8B(writerIndex, value);
    writerIndex += Long.BYTES;
    return this;
  }

  @Override
  public Bytes put8B (int index, long value) {
    checkWriteBounds(index, Long.BYTES);
    buffer.putLong(index, value);
    return this;
  }

  @Override
  public byte getByte () {
    val result = getByte(readerIndex);
    readerIndex += Byte.BYTES;
    return result;
  }

  @Override
  public byte getByte (int index) {
    checkReaderBounds(index, Byte.BYTES);
    return buffer.get(index);
  }

  @Override
  public short getShort () {
    val result = getShort(readerIndex);
    readerIndex += Short.BYTES;
    return result;
  }

  @Override
  public short getShort (int index) {
    checkReaderBounds(index, Short.BYTES);
    return buffer.getShort(index);
  }

  @Override
  public int getInt () {
    val result = getInt(readerIndex);
    readerIndex += Integer.BYTES;
    return result;
  }

  @Override
  public int getInt (int index) {
    checkReaderBounds(index, Integer.BYTES);
    return buffer.getInt(index);
  }

  @Override
  public long getLong () {
    val result = getLong(readerIndex);
    readerIndex += Long.BYTES;
    return result;
  }

  @Override
  public long getLong (int index) {
    checkReaderBounds(index, Long.BYTES);
    return buffer.getLong(index);
  }

  @Override
  public float getFloat () {
    val result = getFloat(readerIndex);
    readerIndex += Float.BYTES;
    return result;
  }

  @Override
  public float getFloat (int index) {
    checkReaderBounds(index, Float.BYTES);
    return buffer.getFloat(index);
  }

  @Override
  public double getDouble () {
    val result = getDouble(readerIndex);
    readerIndex += Double.BYTES;
    return result;
  }

  @Override
  public double getDouble (int index) {
    checkReaderBounds(index, Double.BYTES);
    return buffer.getDouble(index);
  }

  @Override
  public char getChar () {
    val result = getChar(readerIndex);
    readerIndex += Character.BYTES;
    return result;
  }

  @Override
  public char getChar (int index) {
    checkReaderBounds(index, Character.BYTES);
    return buffer.getChar(index);
  }

  @Override
  public byte[] getBytes (int index, int length) {
    checkReaderBounds(index, length);

    val result = new byte[length];
    val position = buffer.position();
    buffer.position(index);
    buffer.put(result);
    buffer.position(position);
    return result;
  }

  @Override
  public Bytes getBytes (@NonNull byte[] destination, int offset, int length) {
    checkReaderBounds(readerIndex, length);
    buffer.get(destination, offset, length);
    readerIndex += length;
    return this;
  }

  @Override
  public String getString (int index, int length, @NonNull Charset charset) {
    checkReaderBounds(index, length);
    val bytes = getBytes(index, length);
    return new String(bytes, charset);
  }

  @Override
  public int capacity () {
    return buffer.capacity();
  }

  @Override
  public int writerIndex () {
    return writerIndex;
  }

  @Override
  public Bytes writerIndex (int newIndex) {
    if (newIndex < readerIndex() || newIndex > capacity()) {
      throw new IndexOutOfBoundsException();
    }
    writerIndex = newIndex;
    return this;
  }

  @Override
  public int readerIndex () {
    return readerIndex;
  }

  @Override
  public Bytes readerIndex (int newIndex) {
    if (newIndex < 0 || newIndex > writerIndex()) {
      throw new IndexOutOfBoundsException();
    }
    readerIndex = newIndex;
    return this;
  }

  @Override
  public byte[] array () {
    return buffer.array();
  }
}
