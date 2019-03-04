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

import static lombok.AccessLevel.PROTECTED;

import java.nio.charset.Charset;
import java.util.Arrays;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;

@FieldDefaults(level = PROTECTED)
class BytesFixedArray extends BytesAbstract {

  byte[] buffer;

  int writerIndex;

  int readerIndex;

  BytesFixedArray (int size) {
    super();
    buffer = new byte[size];
  }

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  BytesFixedArray (@NonNull byte[] bytes) {
    super();
    buffer = bytes;
    writerIndex(bytes.length);
  }

  @Override
  public boolean isExtendable () {
    return false;
  }

  @Override
  public Bytes writeNB (@NonNull byte[] bytes, int offset, int length) {
    setNB(writerIndex, bytes, offset, length);
    writerIndex += length;
    return this;
  }

  @Override
  public Bytes write1B (byte value) {
    set1B(writerIndex, value);
    writerIndex += Byte.BYTES;
    return this;
  }

  @Override
  public Bytes write2B (short value) {
    set2B(writerIndex, value);
    writerIndex += Short.BYTES;
    return this;
  }

  @Override
  public Bytes write4B (int value) {
    set4B(writerIndex, value);
    writerIndex += Integer.BYTES;
    return this;
  }

  @Override
  public Bytes write8B (long value) {
    set8B(writerIndex, value);
    writerIndex += Long.BYTES;
    return this;
  }

  @Override
  public Bytes setNB (int index, @NonNull byte[] bytes, int offset, int length) {
    checkWriteBounds(index, length);
    System.arraycopy(bytes, offset, buffer, index, length);
    return this;
  }

  @Override
  public Bytes set1B (int index, byte value) {
    checkWriteBounds(index, Byte.BYTES);
    buffer[index] = value;
    return this;
  }

  @Override
  public Bytes set2B (int index, short value) {
    checkWriteBounds(index, Short.BYTES);
    BytesUtils.unsafeWriteShort(value, buffer, index);
    return this;
  }

  @Override
  public Bytes set4B (int index, int value) {
    checkWriteBounds(index, Integer.BYTES);
    BytesUtils.unsafeWriteInteger(value, buffer, index);
    return this;
  }

  @Override
  public Bytes set8B (int index, long value) {
    checkWriteBounds(index, Long.BYTES);
    BytesUtils.unsafeWriteLong(value, buffer, index);
    return this;
  }

  @Override
  public byte readByte () {
    val result = getByte(readerIndex);
    readerIndex += Byte.BYTES;
    return result;
  }

  @Override
  public short readShort () {
    val result = getShort(readerIndex);
    readerIndex += Short.BYTES;
    return result;
  }

  @Override
  public int readInt () {
    val result = getInt(readerIndex);
    readerIndex += Integer.BYTES;
    return result;
  }

  @Override
  public long readLong () {
    val result = getLong(readerIndex);
    readerIndex += Long.BYTES;
    return result;
  }

  @Override
  public float readFloat () {
    val result = getFloat(readerIndex);
    readerIndex += Float.BYTES;
    return result;
  }

  @Override
  public double readDouble () {
    val result = getDouble(readerIndex);
    readerIndex += Double.BYTES;
    return result;
  }

  @Override
  public char readChar () {
    val result = getChar(readerIndex);
    readerIndex += Character.BYTES;
    return result;
  }

  @Override
  public Bytes readBytes (@NonNull byte[] destination, int offset, int length) {
    checkReaderBounds(readerIndex, length);
    System.arraycopy(buffer, readerIndex, destination, offset, length);
    readerIndex += length;
    return this;
  }

  @Override
  public byte getByte (int index) {
    checkReaderBounds(index, Byte.BYTES);
    return buffer[index];
  }

  @Override
  public short getShort (int index) {
    checkReaderBounds(index, Short.BYTES);
    return BytesUtils.unsafeReadShort(buffer, index);
  }

  @Override
  public int getInt (int index) {
    checkReaderBounds(index, Integer.BYTES);
    return BytesUtils.unsafeReadInteger(buffer, index);
  }

  @Override
  public long getLong (int index) {
    checkReaderBounds(index, Long.BYTES);
    return BytesUtils.unsafeReadLong(buffer, index);
  }

  @Override
  public float getFloat (int index) {
    checkReaderBounds(index, Float.BYTES);
    return BytesUtils.unsafeReadFloat(buffer, index);
  }

  @Override
  public double getDouble (int index) {
    checkReaderBounds(index, Double.BYTES);
    return BytesUtils.unsafeReadDouble(buffer, index);
  }

  @Override
  public char getChar (int index) {
    checkReaderBounds(index, Character.BYTES);
    return BytesUtils.unsafeReadCharacter(buffer, index);
  }

  @Override
  public byte[] getBytes (int index, int length) {
    checkReaderBounds(index, length);
    return Arrays.copyOfRange(buffer, index, index + length);
  }

  @Override
  public String getString (int index, int length, @NonNull Charset charset) {
    checkReaderBounds(index, length);
    return new String(buffer, index, length, charset);
  }

  @Override
  public int capacity () {
    return buffer.length;
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
  @SuppressWarnings("PMD.MethodReturnsInternalArray")
  public byte[] array () {
    return buffer;
  }
}
