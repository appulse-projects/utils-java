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

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.nio.charset.Charset;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@FieldDefaults(level = PRIVATE)
@RequiredArgsConstructor(access = PACKAGE)
class BytesReadOnly extends BytesAbstract {

  final Bytes delegate;

  int readerIndex;

  @Override
  public boolean isAutoResizable () {
    return false;
  }

  @Override
  public Bytes writeNB (byte[] bytes, int offset, int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes setNB (int index, byte[] bytes, int offset, int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
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
  public Bytes readBytes (byte[] destination, int offset, int length) {
    checkReaderBounds(readerIndex, length);
    System.arraycopy(array(), readerIndex, destination, offset, length);
    readerIndex += length;
    return this;
  }

  @Override
  public byte getByte (int index) {
    return delegate.getByte(index);
  }

  @Override
  public short getShort (int index) {
    return delegate.getShort(index);
  }

  @Override
  public int getInt (int index) {
    return delegate.getInt(index);
  }

  @Override
  public long getLong (int index) {
    return delegate.getLong(index);
  }

  @Override
  public float getFloat (int index) {
    return delegate.getFloat(index);
  }

  @Override
  public double getDouble (int index) {
    return delegate.getDouble(index);
  }

  @Override
  public char getChar (int index) {
    return delegate.getChar(index);
  }

  @Override
  public byte[] getBytes (int index, int length) {
    return delegate.getBytes(index, length);
  }

  @Override
  public String getString (int index, int length, Charset charset) {
    return delegate.getString(index, length, charset);
  }

  @Override
  public int capacity () {
    return delegate.capacity();
  }

  @Override
  public void capacity (int bytes) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public int writerIndex () {
    return delegate.writerIndex();
  }

  @Override
  public Bytes writerIndex (int newIndex) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public int readerIndex () {
    return readerIndex;
  }

  @Override
  public Bytes readerIndex (int newIndex) {
    readerIndex = newIndex;
    return this;
  }

  @Override
  public byte[] array () {
    return delegate.array();
  }
}
