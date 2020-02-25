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

import java.math.BigInteger;
import java.nio.charset.Charset;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class BytesReadOnly implements Bytes {

  Bytes delegate;

  @Override
  public boolean isAutoResizable () {
    return false;
  }

  @Override
  public Bytes writeNB (byte[] bytes) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes writeNB (byte[] bytes, int offset) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes writeNB (byte[] bytes, int offset, int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes writeNB (String value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes writeNB (String value, Charset charset) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write1B (char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write2B (char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write4B (char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes write8B (char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes setNB (int index, byte[] bytes) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes setNB (int index, byte[] bytes, int offset) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes setNB (int index, byte[] bytes, int offset, int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes setNB (int index, String value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes setNB (int index, String value, Charset charset) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set1B (int index, char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set2B (int index, char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set4B (int index, char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, byte value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, short value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, int value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, long value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, float value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, double value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes set8B (int index, char value) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public byte readByte () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public short readUnsignedByte () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public short readShort () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public int readUnsignedShort () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public int readInt () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public long readUnsignedInt () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public long readLong () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public BigInteger readUnsignedLong () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public float readFloat () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public double readDouble () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public char readChar () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public byte[] readBytes () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public byte[] readBytes (int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes readBytes (byte[] destination) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public Bytes readBytes (byte[] destination, int offset, int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public String readString () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public String readString (Charset charset) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public String readString (int length) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public String readString (int length, Charset charset) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public byte getByte (int index) {
    return delegate.getByte(index);
  }

  @Override
  public short getUnsignedByte (int index) {
    return delegate.getUnsignedByte(index);
  }

  @Override
  public short getShort (int index) {
    return delegate.getShort(index);
  }

  @Override
  public int getUnsignedShort (int index) {
    return delegate.getUnsignedShort(index);
  }

  @Override
  public int getInt (int index) {
    return delegate.getInt(index);
  }

  @Override
  public long getUnsignedInt (int index) {
    return delegate.getUnsignedInt(index);
  }

  @Override
  public long getLong (int index) {
    return delegate.getLong(index);
  }

  @Override
  public BigInteger getUnsignedLong (int index) {
    return delegate.getUnsignedLong(index);
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
  public byte[] getBytes (int index) {
    return delegate.getBytes(index);
  }

  @Override
  public byte[] getBytes (int index, int length) {
    return delegate.getBytes(index, length);
  }

  @Override
  public String getString (int index) {
    return delegate.getString(index);
  }

  @Override
  public String getString (int index, Charset charset) {
    return delegate.getString(index, charset);
  }

  @Override
  public String getString (int index, int length) {
    return delegate.getString(index, length);
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
  public int writableBytes () {
    return delegate.writableBytes();
  }

  @Override
  public boolean isWritable () {
    return delegate.isWritable();
  }

  @Override
  public boolean isWritable (int size) {
    return delegate.isWritable(size);
  }

  @Override
  public int readerIndex () {
    return delegate.readerIndex();
  }

  @Override
  public Bytes readerIndex (int newIndex) {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public int readableBytes () {
    return delegate.readableBytes();
  }

  @Override
  public boolean isReadable () {
    return delegate.isReadable();
  }

  @Override
  public boolean isReadable (int size) {
    return delegate.isReadable(size);
  }

  @Override
  public Bytes reset () {
    val msg = "The operation doesn't support in BytesReadOnly wrapper";
    throw new UnsupportedOperationException(msg);
  }

  @Override
  public byte[] array () {
    return delegate.array();
  }

  @Override
  public byte[] arrayCopy () {
    return delegate.arrayCopy();
  }
}
