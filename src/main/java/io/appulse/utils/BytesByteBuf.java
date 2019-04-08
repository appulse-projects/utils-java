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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;

@SuppressWarnings("PMD.LinguisticNaming")
@FieldDefaults(level = PRIVATE)
public final class BytesByteBuf extends BytesAbstract {

  public static BytesByteBuf allocate (int size) {
    val buffer = Unpooled.buffer(size);
    buffer.clear();
    return new BytesByteBuf(buffer);
  }

  public static BytesByteBuf wrap (@NonNull byte[] bytes) {
    val wrapped = Unpooled.wrappedBuffer(bytes);
    wrapped.clear();
    return new BytesByteBuf(wrapped);
  }

  public static BytesByteBuf wrap (@NonNull ByteBuffer buffer) {
    val wrapped = Unpooled.wrappedBuffer(buffer);
    return new BytesByteBuf(wrapped);
  }

  public static BytesByteBuf wrap (@NonNull ByteBuf buffer) {
    val wrapped = Unpooled.wrappedBuffer(buffer);
    return new BytesByteBuf(wrapped);
  }

  public static BytesByteBuf copy (@NonNull byte[] bytes) {
    val copy = Unpooled.copiedBuffer(bytes);
    return new BytesByteBuf(copy);
  }

  public static BytesByteBuf copy (@NonNull ByteBuffer buffer) {
    val copy = Unpooled.copiedBuffer(buffer);
    return new BytesByteBuf(copy);
  }

  public static BytesByteBuf copy (@NonNull ByteBuf buffer) {
    val copy = buffer.copy();
    return new BytesByteBuf(copy);
  }

  ByteBuf buffer;

  private BytesByteBuf (@NonNull ByteBuf buffer) {
    super();
    this.buffer = buffer;
  }

  @Override
  public boolean isResizable () {
    return false;
  }

  @Override
  public Bytes writeNB (byte[] bytes, int offset, int length) {
    buffer.writeBytes(bytes, offset, length);
    return this;
  }

  @Override
  public Bytes write1B (byte value) {
    buffer.writeByte(value);
    return this;
  }

  @Override
  public Bytes write2B (short value) {
    buffer.writeShort(value);
    return this;
  }

  @Override
  public Bytes write4B (int value) {
    buffer.writeInt(value);
    return this;
  }

  @Override
  public Bytes write8B (long value) {
    buffer.writeLong(value);
    return this;
  }

  @Override
  public Bytes setNB (int index, byte[] bytes, int offset, int length) {
    buffer.setBytes(index, bytes, offset, length);
    return this;
  }

  @Override
  public Bytes set1B (int index, byte value) {
    buffer.setByte(index, value);
    return this;
  }

  @Override
  public Bytes set2B (int index, short value) {
    buffer.setShort(index, value);
    return this;
  }

  @Override
  public Bytes set4B (int index, int value) {
    buffer.setInt(index, value);
    return this;
  }

  @Override
  public Bytes set8B (int index, long value) {
    buffer.setLong(index, value);
    return this;
  }

  @Override
  public byte readByte () {
    return buffer.readByte();
  }

  @Override
  public short readShort () {
    return buffer.readShort();
  }

  @Override
  public int readInt () {
    return buffer.readInt();
  }

  @Override
  public long readLong () {
    return buffer.readLong();
  }

  @Override
  public float readFloat () {
    return buffer.readFloat();
  }

  @Override
  public double readDouble () {
    return buffer.readDouble();
  }

  @Override
  public char readChar () {
    return buffer.readChar();
  }

  @Override
  public Bytes readBytes (byte[] destination, int offset, int length) {
    buffer.readBytes(destination, offset, length);
    return this;
  }

  @Override
  public byte getByte (int index) {
    return buffer.getByte(index);
  }

  @Override
  public short getShort (int index) {
    return buffer.getShort(index);
  }

  @Override
  public int getInt (int index) {
    return buffer.getInt(index);
  }

  @Override
  public long getLong (int index) {
    return buffer.getLong(index);
  }

  @Override
  public float getFloat (int index) {
    return buffer.getFloat(index);
  }

  @Override
  public double getDouble (int index) {
    return buffer.getDouble(index);
  }

  @Override
  public char getChar (int index) {
    return buffer.getChar(index);
  }

  @Override
  public byte[] getBytes (int index, int length) {
    val result = new byte[length];
    buffer.getBytes(index, result);
    return result;
  }

  @Override
  public String getString (int index, int length, Charset charset) {
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

    val oldReaderIndex = buffer.readerIndex();
    val oldWriterIndex = buffer.writerIndex();
    val newByteArray = Arrays.copyOf(buffer.array(), bytes);

    buffer = Unpooled.wrappedBuffer(newByteArray);
    buffer.readerIndex(Math.min(oldReaderIndex, bytes - 1));
    buffer.writerIndex(Math.min(oldWriterIndex, bytes - 1));
  }

  @Override
  public int writerIndex () {
    return buffer.writerIndex();
  }

  @Override
  public Bytes writerIndex (int newIndex) {
    buffer.writerIndex(newIndex);
    return this;
  }

  @Override
  public int readerIndex () {
    return buffer.readerIndex();
  }

  @Override
  public Bytes readerIndex (int newIndex) {
    buffer.readerIndex(newIndex);
    return this;
  }

  @Override
  public byte[] array () {
    return buffer.array();
  }
}
