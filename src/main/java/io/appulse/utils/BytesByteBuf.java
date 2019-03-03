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

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class BytesByteBuf extends AbstractBytes {

  @NonNull
  ByteBuf buffer;

  @Override
  public boolean isExtendable () {
    return false;
  }

  @Override
  public Bytes putNB (byte[] bytes, int offset, int length) {
    buffer.writeBytes(bytes, offset, length);
    return this;
  }

  @Override
  public Bytes putNB (int index, byte[] bytes, int offset, int length) {
    buffer.setBytes(index, bytes, offset, length);
    return this;
  }

  @Override
  public Bytes put1B (byte value) {
    buffer.writeByte(value);
    return this;
  }

  @Override
  public Bytes put1B (int index, byte value) {
    buffer.setByte(index, value);
    return this;
  }

  @Override
  public Bytes put2B (short value) {
    buffer.writeShort(value);
    return this;
  }

  @Override
  public Bytes put2B (int index, short value) {
    buffer.setShort(index, value);
    return this;
  }

  @Override
  public Bytes put4B (int value) {
    buffer.writeInt(value);
    return this;
  }

  @Override
  public Bytes put4B (int index, int value) {
    buffer.setInt(index, value);
    return this;
  }

  @Override
  public Bytes put8B (long value) {
    buffer.writeLong(value);
    return this;
  }

  @Override
  public Bytes put8B (int index, long value) {
    buffer.setLong(index, value);
    return this;
  }

  @Override
  public byte getByte () {
    return buffer.readByte();
  }

  @Override
  public byte getByte (int index) {
    return buffer.getByte(index);
  }

  @Override
  public short getShort () {
    return buffer.readShort();
  }

  @Override
  public short getShort (int index) {
    return buffer.getShort(index);
  }

  @Override
  public int getInt () {
    return buffer.readInt();
  }

  @Override
  public int getInt (int index) {
    return buffer.getInt(index);
  }

  @Override
  public long getLong () {
    return buffer.readLong();
  }

  @Override
  public long getLong (int index) {
    return buffer.getLong(index);
  }

  @Override
  public float getFloat () {
    return buffer.readFloat();
  }

  @Override
  public float getFloat (int index) {
    return buffer.getFloat(index);
  }

  @Override
  public double getDouble () {
    return buffer.readDouble();
  }

  @Override
  public double getDouble (int index) {
    return buffer.getDouble(index);
  }

  @Override
  public char getChar () {
    return buffer.readChar();
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
  public Bytes getBytes (byte[] destination, int offset, int length) {
    buffer.readBytes(destination, offset, length);
    return this;
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
