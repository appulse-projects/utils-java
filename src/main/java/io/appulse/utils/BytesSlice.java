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

import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.Charset;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;

@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class BytesSlice extends BytesFixedArray {

  int from;

  int to;

  @Builder
  BytesSlice (Bytes delegate, @NonNull Integer from, @NonNull Integer to) {
    super();
    super.buffer = delegate.array();
    this.from = from;
    this.to = to;

    if (delegate.writerIndex() < from) {
      writerIndex = from;
    } else if (delegate.writerIndex() > to) {
      writerIndex = to;
    } else {
      writerIndex = delegate.writerIndex();
    }
    readerIndex = from;
  }

  @Override
  public Bytes writeNB (@NonNull byte[] bytes, int offset, int length) {
    super.setNB(writerIndex, bytes, offset, length);
    writerIndex += length;
    return this;
  }

  @Override
  public Bytes write1B (byte value) {
    super.set1B(writerIndex, value);
    writerIndex += Byte.BYTES;
    return this;
  }

  @Override
  public Bytes write2B (short value) {
    super.set2B(writerIndex, value);
    writerIndex += Short.BYTES;
    return this;
  }

  @Override
  public Bytes write4B (int value) {
    super.set4B(writerIndex, value);
    writerIndex += Integer.BYTES;
    return this;
  }

  @Override
  public Bytes write8B (long value) {
    super.set8B(writerIndex, value);
    writerIndex += Long.BYTES;
    return this;
  }

  @Override
  public Bytes setNB (int index, byte[] bytes, int offset, int length) {
    val newIndex = from + index;
    return super.setNB(newIndex, bytes, offset, length);
  }

  @Override
  public Bytes set1B (int index, byte value) {
    val newIndex = from + index;
    return super.set1B(newIndex, value);
  }

  @Override
  public Bytes set2B (int index, short value) {
    val newIndex = from + index;
    return super.set2B(newIndex, value);
  }

  @Override
  public Bytes set4B (int index, int value) {
    val newIndex = from + index;
    return super.set4B(newIndex, value);
  }

  @Override
  public Bytes set8B (int index, long value) {
    val newIndex = from + index;
    return super.set8B(newIndex, value);
  }

  @Override
  public byte readByte () {
    val result = super.getByte(readerIndex);
    readerIndex += Byte.BYTES;
    return result;
  }

  @Override
  public short readShort () {
    val result = super.getShort(readerIndex);
    readerIndex += Short.BYTES;
    return result;
  }

  @Override
  public int readInt () {
    val result = super.getInt(readerIndex);
    readerIndex += Integer.BYTES;
    return result;
  }

  @Override
  public long readLong () {
    val result = super.getLong(readerIndex);
    readerIndex += Long.BYTES;
    return result;
  }

  @Override
  public float readFloat () {
    val result = super.getFloat(readerIndex);
    readerIndex += Float.BYTES;
    return result;
  }

  @Override
  public double readDouble () {
    val result = super.getDouble(readerIndex);
    readerIndex += Double.BYTES;
    return result;
  }

  @Override
  public char readChar () {
    val result = super.getChar(readerIndex);
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
    val newIndex = from + index;
    return super.getByte(newIndex);
  }

  @Override
  public short getShort (int index) {
    val newIndex = from + index;
    return super.getShort(newIndex);
  }

  @Override
  public int getInt (int index) {
    val newIndex = from + index;
    return super.getInt(newIndex);
  }

  @Override
  public long getLong (int index) {
    val newIndex = from + index;
    return super.getLong(newIndex);
  }

  @Override
  public float getFloat (int index) {
    val newIndex = from + index;
    return super.getFloat(newIndex);
  }

  @Override
  public double getDouble (int index) {
    val newIndex = from + index;
    return super.getDouble(newIndex);
  }

  @Override
  public char getChar (int index) {
    val newIndex = from + index;
    return super.getChar(newIndex);
  }

  @Override
  public byte[] getBytes (int index, int length) {
    val newIndex = from + index;
    return super.getBytes(newIndex, length);
  }

  @Override
  public String getString (int index, int length, Charset charset) {
    val newIndex = from + index;
    return super.getString(newIndex, length, charset);
  }

  @Override
  public int capacity () {
    return to - from;
  }

  @Override
  public void capacity (int bytes) {
    val msg = "The operation doesn't support in BytesSlice wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public int writerIndex () {
    return super.writerIndex() - from;
  }

  @Override
  public Bytes writerIndex (int newIndex) {
    if (newIndex < readerIndex() || newIndex > capacity()) {
      val msg = String.format(
          "Writer index error: newIndex(%d) < readerIndex(%d) || newIndex(%d) > capacity(%d)",
          newIndex, readerIndex(), newIndex, capacity()
      );
      throw new IndexOutOfBoundsException(msg);
    }

    val index = from + newIndex;
    writerIndex = index;
    return this;
  }

  @Override
  public int readerIndex () {
    return super.readerIndex() - from;
  }

  @Override
  protected void checkWriteBounds (int index, int length) {
    if (index < super.readerIndex() || index + length > super.capacity()) {
      val msg = String.format(
          "Writer index error. index(%d) < readerIndex(%d) || index(%d)+length(%d) > capacity(%d)",
          index, super.readerIndex(), index, length, super.capacity()
      );
      throw new IndexOutOfBoundsException(msg);
    }
  }

  @Override
  protected void checkReaderBounds (int index, int length) {
    if (index < 0 || index + length > super.writerIndex()) {
      val msg = String.format(
          "Reader index error. index(%d) < 0 || index(%d)+length(%d) > writerIndex(%d)",
          index, index, length, super.writerIndex()
      );
      throw new IndexOutOfBoundsException(msg);
    }
  }

  @Override
  public Bytes readerIndex (int newIndex) {
    if (newIndex < 0 || newIndex > writerIndex()) {
      val msg = String.format(
          "Reader index error: newIndex(%d) < 0 || newIndex(%d) > writerIndex(%d)",
          newIndex, newIndex, writerIndex()
      );
      throw new IndexOutOfBoundsException(msg);
    }

    val index = from + newIndex;
    readerIndex = index;
    return this;
  }
}
