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

import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.math.BigInteger;
import java.nio.charset.Charset;

import lombok.NonNull;
import lombok.val;

abstract class AbstractBytes implements Bytes {

  @Override
  public Bytes putNB (@NonNull byte[] bytes) {
    return putNB(bytes, 0);
  }

  @Override
  public Bytes putNB (int index, @NonNull byte[] bytes) {
    return putNB(index, bytes, 0, bytes.length);
  }

  @Override
  public Bytes putNB (@NonNull byte[] bytes, int offset) {
    return putNB(bytes, offset, bytes.length);
  }

  @Override
  public Bytes putNB (int index, @NonNull byte[] bytes, int offset) {
    return putNB(index, bytes, offset, bytes.length);
  }

  @Override
  public Bytes put1B (short value) {
    return put1B((byte) value);
  }

  @Override
  public Bytes put1B (int index, short value) {
    return put1B(index, (byte) value);
  }

  @Override
  public Bytes put1B (int value) {
    return put1B((byte) value);
  }

  @Override
  public Bytes put1B (int index, int value) {
    return put1B(index, (byte) value);
  }

  @Override
  public Bytes put1B (long value) {
    return put1B((byte) value);
  }

  @Override
  public Bytes put1B (int index, long value) {
    return put1B(index, (byte) value);
  }

  @Override
  public Bytes put1B (float value) {
    val intValue = Float.floatToIntBits(value);
    return put1B((byte) intValue);
  }

  @Override
  public Bytes put1B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return put1B(index, (byte) intValue);
  }

  @Override
  public Bytes put1B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return put1B((byte) longValue);
  }

  @Override
  public Bytes put1B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return put1B(index, (byte) longValue);
  }

  @Override
  public Bytes put1B (char value) {
    return put1B((byte) value);
  }

  @Override
  public Bytes put1B (int index, char value) {
    return put1B(index, (byte) value);
  }

  @Override
  public Bytes put2B (byte value) {
    return put2B((short) value);
  }

  @Override
  public Bytes put2B (int index, byte value) {
    return put2B(index, (short) value);
  }

  @Override
  public Bytes put2B (int value) {
    return put2B((short) value);
  }

  @Override
  public Bytes put2B (int index, int value) {
    return put2B(index, (short) value);
  }

  @Override
  public Bytes put2B (long value) {
    return put2B((short) value);
  }

  @Override
  public Bytes put2B (int index, long value) {
    return put2B(index, (short) value);
  }

  @Override
  public Bytes put2B (float value) {
    val intValue = Float.floatToIntBits(value);
    return put2B((short) intValue);
  }

  @Override
  public Bytes put2B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return put2B(index, (short) intValue);
  }

  @Override
  public Bytes put2B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return put2B((short) longValue);
  }

  @Override
  public Bytes put2B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return put2B(index, (short) longValue);
  }

  @Override
  public Bytes put2B (char value) {
    return put2B((short) value);
  }

  @Override
  public Bytes put2B (int index, char value) {
    return put2B(index, (short) value);
  }

  @Override
  public Bytes put4B (byte value) {
    return put4B((int) value);
  }

  @Override
  public Bytes put4B (int index, byte value) {
    return put4B(index, (int) value);
  }

  @Override
  public Bytes put4B (short value) {
    return put4B((int) value);
  }

  @Override
  public Bytes put4B (int index, short value) {
    return put4B(index, (int) value);
  }

  @Override
  public Bytes put4B (long value) {
    return put4B((int) value);
  }

  @Override
  public Bytes put4B (int index, long value) {
    return put4B(index, (int) value);
  }

  @Override
  public Bytes put4B (float value) {
    val intValue = Float.floatToIntBits(value);
    return put4B(intValue);
  }

  @Override
  public Bytes put4B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return put4B(index, intValue);
  }

  @Override
  public Bytes put4B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return put4B((int) longValue);
  }

  @Override
  public Bytes put4B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return put4B(index, (int) longValue);
  }

  @Override
  public Bytes put4B (char value) {
    return put4B((int) value);
  }

  @Override
  public Bytes put4B (int index, char value) {
    return put4B(index, (int) value);
  }

  @Override
  public Bytes put8B (byte value) {
    return put8B((long) value);
  }

  @Override
  public Bytes put8B (int index, byte value) {
    return put8B(index, (long) value);
  }

  @Override
  public Bytes put8B (short value) {
    return put8B((long) value);
  }

  @Override
  public Bytes put8B (int index, short value) {
    return put8B(index, (long) value);
  }

  @Override
  public Bytes put8B (int value) {
    return put8B((long) value);
  }

  @Override
  public Bytes put8B (int index, int value) {
    return put8B(index, (long) value);
  }

  @Override
  public Bytes put8B (float value) {
    val intValue = Float.floatToIntBits(value);
    return put8B((long) intValue);
  }

  @Override
  public Bytes put8B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return put8B(index, (long) intValue);
  }

  @Override
  public Bytes put8B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return put8B(longValue);
  }

  @Override
  public Bytes put8B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return put8B(index, longValue);
  }

  @Override
  public Bytes put8B (char value) {
    return put8B((long) value);
  }

  @Override
  public Bytes put8B (int index, char value) {
    return put8B(index, (long) value);
  }

  @Override
  public short getUnsignedByte () {
    val value = getByte();
    return BytesUtils.asUnsignedByte(value);
  }

  @Override
  public short getUnsignedByte (int index) {
    val value = getByte(index);
    return BytesUtils.asUnsignedByte(value);
  }

  @Override
  public int getUnsignedShort () {
    val value = getShort();
    return BytesUtils.asUnsignedShort(value);
  }

  @Override
  public int getUnsignedShort (int index) {
    val value = getShort(index);
    return BytesUtils.asUnsignedShort(value);
  }

  @Override
  public long getUnsignedInt () {
    val value = getInt();
    return BytesUtils.asUnsignedInteger(value);
  }

  @Override
  public long getUnsignedInt (int index) {
    val value = getInt(index);
    return BytesUtils.asUnsignedInteger(value);
  }

  @Override
  public BigInteger getUnsignedLong () {
    val value = getLong();
    return BytesUtils.asUnsignedLong(value);
  }

  @Override
  public BigInteger getUnsignedLong (int index) {
    val value = getLong(index);
    return BytesUtils.asUnsignedLong(value);
  }

  @Override
  public byte[] getBytes () {
    val result = getBytes(readerIndex());
    readerIndex(capacity());
    return result;
  }

  @Override
  public byte[] getBytes (int index) {
    return getBytes(index, capacity() - index);
  }

  @Override
  public Bytes getBytes (@NonNull byte[] destination) {
    return getBytes(destination, 0, destination.length);
  }

  @Override
  public String getString () {
    return getString(ISO_8859_1);
  }

  @Override
  public String getString (Charset charset) {
    val result = getString(readerIndex(), charset);
    readerIndex(capacity());
    return result;
  }

  @Override
  public String getString (int index) {
    return getString(index, ISO_8859_1);
  }

  @Override
  public String getString (int index, Charset charset) {
    return getString(index, capacity() - index, charset);
  }

  @Override
  public String getString (int index, int length) {
    return getString(index, length, ISO_8859_1);
  }

  @Override
  public int readableBytes () {
    return writerIndex() - readerIndex() + 1;
  }

  @Override
  public boolean isReadable () {
    return readableBytes() > 0;
  }

  @Override
  public boolean isReadable (int size) {
    return readableBytes() >= size;
  }

  @Override
  public int writableBytes () {
    return capacity() - writerIndex();
  }

  @Override
  public boolean isWritable () {
    return writableBytes() > 0;
  }

  @Override
  public boolean isWritable (int size) {
    return writableBytes() >= size;
  }

  @Override
  public Bytes clear () {
    readerIndex(0);
    writerIndex(0);
    return this;
  }

  protected void checkWriteBounds (int index, int length) {
    if (index < readerIndex() || index + length > capacity()) {
      throw new IndexOutOfBoundsException();
    }
  }

  protected void checkReaderBounds (int index, int length) {
    if (index < 0 || index + length > writerIndex()) {
      throw new IndexOutOfBoundsException();
    }
  }
}
