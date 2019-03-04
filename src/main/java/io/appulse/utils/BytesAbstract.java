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

@SuppressWarnings("PMD.LinguisticNaming")
abstract class BytesAbstract implements Bytes {

  @Override
  public Bytes writeNB (@NonNull byte[] bytes) {
    return writeNB(bytes, 0);
  }

  @Override
  public Bytes writeNB (@NonNull byte[] bytes, int offset) {
    return writeNB(bytes, offset, bytes.length);
  }

  @Override
  public Bytes writeNB (String value) {
    return writeNB(value, ISO_8859_1);
  }

  @Override
  public Bytes writeNB (@NonNull String value, @NonNull Charset charset) {
    val bytes = value.getBytes(charset);
    return writeNB(bytes);
  }

  @Override
  public Bytes write1B (short value) {
    return write1B((byte) value);
  }

  @Override
  public Bytes write1B (int value) {
    return write1B((byte) value);
  }

  @Override
  public Bytes write1B (long value) {
    return write1B((byte) value);
  }

  @Override
  public Bytes write1B (float value) {
    val intValue = Float.floatToIntBits(value);
    return write1B((byte) intValue);
  }

  @Override
  public Bytes write1B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return write1B((byte) longValue);
  }

  @Override
  public Bytes write1B (char value) {
    return write1B((byte) value);
  }

  @Override
  public Bytes write2B (byte value) {
    return write2B((short) value);
  }

  @Override
  public Bytes write2B (int value) {
    return write2B((short) value);
  }

  @Override
  public Bytes write2B (long value) {
    return write2B((short) value);
  }

  @Override
  public Bytes write2B (float value) {
    val intValue = Float.floatToIntBits(value);
    return write2B((short) intValue);
  }

  @Override
  public Bytes write2B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return write2B((short) longValue);
  }

  @Override
  public Bytes write2B (char value) {
    return write2B((short) value);
  }

  @Override
  public Bytes write4B (byte value) {
    return write4B((int) value);
  }

  @Override
  public Bytes write4B (short value) {
    return write4B((int) value);
  }

  @Override
  public Bytes write4B (long value) {
    return write4B((int) value);
  }

  @Override
  public Bytes write4B (float value) {
    val intValue = Float.floatToIntBits(value);
    return write4B(intValue);
  }

  @Override
  public Bytes write4B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return write4B((int) longValue);
  }

  @Override
  public Bytes write4B (char value) {
    return write4B((int) value);
  }

  @Override
  public Bytes write8B (byte value) {
    return write8B((long) value);
  }

  @Override
  public Bytes write8B (short value) {
    return write8B((long) value);
  }

  @Override
  public Bytes write8B (int value) {
    return write8B((long) value);
  }

  @Override
  public Bytes write8B (float value) {
    val intValue = Float.floatToIntBits(value);
    return write8B((long) intValue);
  }

  @Override
  public Bytes write8B (double value) {
    val longValue = Double.doubleToLongBits(value);
    return write8B(longValue);
  }

  @Override
  public Bytes write8B (char value) {
    return write8B((long) value);
  }

  @Override
  public Bytes setNB (int index, @NonNull byte[] bytes) {
    return setNB(index, bytes, 0, bytes.length);
  }

  @Override
  public Bytes setNB (int index, @NonNull byte[] bytes, int offset) {
    return setNB(index, bytes, offset, bytes.length);
  }

  @Override
  public Bytes setNB (int index, String value) {
    return setNB(index, value, ISO_8859_1);
  }

  @Override
  public Bytes setNB (int index, @NonNull String value, @NonNull Charset charset) {
    val bytes = value.getBytes(charset);
    return setNB(index, bytes);
  }

  @Override
  public Bytes set1B (int index, short value) {
    return set1B(index, (byte) value);
  }

  @Override
  public Bytes set1B (int index, int value) {
    return set1B(index, (byte) value);
  }

  @Override
  public Bytes set1B (int index, long value) {
    return set1B(index, (byte) value);
  }

  @Override
  public Bytes set1B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return set1B(index, (byte) intValue);
  }

  @Override
  public Bytes set1B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return set1B(index, (byte) longValue);
  }

  @Override
  public Bytes set1B (int index, char value) {
    return set1B(index, (byte) value);
  }

  @Override
  public Bytes set2B (int index, byte value) {
    return set2B(index, (short) value);
  }

  @Override
  public Bytes set2B (int index, int value) {
    return set2B(index, (short) value);
  }

  @Override
  public Bytes set2B (int index, long value) {
    return set2B(index, (short) value);
  }

  @Override
  public Bytes set2B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return set2B(index, (short) intValue);
  }

  @Override
  public Bytes set2B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return set2B(index, (short) longValue);
  }

  @Override
  public Bytes set2B (int index, char value) {
    return set2B(index, (short) value);
  }

  @Override
  public Bytes set4B (int index, byte value) {
    return set4B(index, (int) value);
  }

  @Override
  public Bytes set4B (int index, short value) {
    return set4B(index, (int) value);
  }

  @Override
  public Bytes set4B (int index, long value) {
    return set4B(index, (int) value);
  }

  @Override
  public Bytes set4B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return set4B(index, intValue);
  }

  @Override
  public Bytes set4B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return set4B(index, (int) longValue);
  }

  @Override
  public Bytes set4B (int index, char value) {
    return set4B(index, (int) value);
  }

  @Override
  public Bytes set8B (int index, byte value) {
    return set8B(index, (long) value);
  }

  @Override
  public Bytes set8B (int index, short value) {
    return set8B(index, (long) value);
  }

  @Override
  public Bytes set8B (int index, int value) {
    return set8B(index, (long) value);
  }

  @Override
  public Bytes set8B (int index, float value) {
    val intValue = Float.floatToIntBits(value);
    return set8B(index, (long) intValue);
  }

  @Override
  public Bytes set8B (int index, double value) {
    val longValue = Double.doubleToLongBits(value);
    return set8B(index, longValue);
  }

  @Override
  public Bytes set8B (int index, char value) {
    return set8B(index, (long) value);
  }

  @Override
  public short readUnsignedByte () {
    val value = readByte();
    return BytesUtils.asUnsignedByte(value);
  }

  @Override
  public int readUnsignedShort () {
    val value = readShort();
    return BytesUtils.asUnsignedShort(value);
  }

  @Override
  public long readUnsignedInt () {
    val value = readInt();
    return BytesUtils.asUnsignedInteger(value);
  }

  @Override
  public BigInteger readUnsignedLong () {
    val value = readLong();
    return BytesUtils.asUnsignedLong(value);
  }

  @Override
  public byte[] readBytes () {
    val result = readBytes(readerIndex());
    readerIndex(capacity());
    return result;
  }

  @Override
  public byte[] readBytes (int length) {
    val result = new byte[length];
    readBytes(result);
    return result;
  }

  @Override
  public Bytes readBytes (@NonNull byte[] destination) {
    return readBytes(destination, 0, destination.length);
  }

  @Override
  public String readString () {
    return readString(ISO_8859_1);
  }

  @Override
  public String readString (int length) {
    return readString(length, ISO_8859_1);
  }

  @Override
  public String readString (Charset charset) {
    val result = getString(readerIndex(), charset);
    readerIndex(capacity());
    return result;
  }

  @Override
  public String readString (int length, Charset charset) {
    val result = getString(readerIndex(), length, charset);
    readerIndex(readerIndex() + length);
    return result;
  }

  @Override
  public short getUnsignedByte (int index) {
    val value = getByte(index);
    return BytesUtils.asUnsignedByte(value);
  }

  @Override
  public int getUnsignedShort (int index) {
    val value = getShort(index);
    return BytesUtils.asUnsignedShort(value);
  }

  @Override
  public long getUnsignedInt (int index) {
    val value = getInt(index);
    return BytesUtils.asUnsignedInteger(value);
  }

  @Override
  public BigInteger getUnsignedLong (int index) {
    val value = getLong(index);
    return BytesUtils.asUnsignedLong(value);
  }

  @Override
  public byte[] getBytes (int index) {
    return getBytes(index, capacity() - index);
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
    return writerIndex() - readerIndex();
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
  public Bytes reset () {
    readerIndex(0);
    writerIndex(0);
    return this;
  }

  protected void checkWriteBounds (int index, int length) {
    if (index < readerIndex() || index + length > capacity()) {
      val msg = String.format("Writer index error. index(%d) < readerIndex(%d) || index(%d)+length(%d) > capacity(%d)",
                              index, readerIndex(), index, length, capacity());
      throw new IndexOutOfBoundsException(msg);
    }
  }

  protected void checkReaderBounds (int index, int length) {
    if (index < 0 || index + length > writerIndex()) {
      val msg = String.format("Reader index error. index(%d) < 0 || index(%d)+length(%d) > writerIndex(%d)",
                              index, index, length, writerIndex());
      throw new IndexOutOfBoundsException(msg);
    }
  }
}
