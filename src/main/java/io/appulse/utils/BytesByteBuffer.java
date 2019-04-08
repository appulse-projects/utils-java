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

import static lombok.AccessLevel.PRIVATE;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;

@FieldDefaults(level = PRIVATE)
@SuppressWarnings("PMD.LinguisticNaming")
class BytesByteBuffer extends BytesAbstract {

  static BytesByteBuffer copy (@NonNull ByteBuffer buffer) {
    val copy = buffer;
    return new BytesByteBuffer(copy);
  }

  ByteBuffer buffer;

  int writerIndex;

  int readerIndex;

  BytesByteBuffer (@NonNull ByteBuffer buffer) {
    super();
    this.buffer = buffer;
  }

  @Override
  public boolean isResizable () {
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
    val position = buffer.position();
    buffer.position(index);
    buffer.put(bytes, offset, length);
    buffer.position(position);
    return this;
  }

  @Override
  public Bytes set1B (int index, byte value) {
    checkWriteBounds(index, Byte.BYTES);
    buffer.put(index, value);
    return this;
  }

  @Override
  public Bytes set2B (int index, short value) {
    checkWriteBounds(index, Short.BYTES);
    buffer.putShort(index, value);
    return this;
  }

  @Override
  public Bytes set4B (int index, int value) {
    checkWriteBounds(index, Integer.BYTES);
    buffer.putInt(index, value);
    return this;
  }

  @Override
  public Bytes set8B (int index, long value) {
    checkWriteBounds(index, Long.BYTES);
    buffer.putLong(index, value);
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
    buffer.get(destination, offset, length);
    readerIndex += length;
    return this;
  }

  @Override
  public byte getByte (int index) {
    checkReaderBounds(index, Byte.BYTES);
    return buffer.get(index);
  }

  @Override
  public short getShort (int index) {
    checkReaderBounds(index, Short.BYTES);
    return buffer.getShort(index);
  }

  @Override
  public int getInt (int index) {
    checkReaderBounds(index, Integer.BYTES);
    return buffer.getInt(index);
  }

  @Override
  public long getLong (int index) {
    checkReaderBounds(index, Long.BYTES);
    return buffer.getLong(index);
  }

  @Override
  public float getFloat (int index) {
    checkReaderBounds(index, Float.BYTES);
    return buffer.getFloat(index);
  }

  @Override
  public double getDouble (int index) {
    checkReaderBounds(index, Double.BYTES);
    return buffer.getDouble(index);
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
  public void capacity (int bytes) {
    if (capacity() == bytes) {
      return;
    }

    val oldPosition = buffer.position();
    val newByteArray = Arrays.copyOf(buffer.array(), bytes);

    buffer = ByteBuffer.wrap(newByteArray);

    buffer.position(Math.min(oldPosition, bytes - 1));
    writerIndex = Math.min(writerIndex, bytes - 1);
    readerIndex = Math.min(readerIndex, bytes - 1);
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
