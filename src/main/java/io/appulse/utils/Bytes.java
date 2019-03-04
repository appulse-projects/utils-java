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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;

public interface Bytes {

  static Bytes wrap (byte[] bytes) {
    return new BytesFixedArray(bytes);
  }

  static Bytes wrap (ByteBuffer buffer) {
    return new BytesByteBuffer(buffer);
  }

  static Bytes wrap (ByteBuf buffer) {
    return new BytesByteBuf(buffer);
  }

  static Bytes allocate (int size) {
    return new BytesFixedArray(size);
  }

  static Bytes allocate () {
    return new BytesExtendableArray();
  }

  boolean isExtendable ();

  Bytes writeNB (byte[] bytes);

  Bytes writeNB (byte[] bytes, int offset);

  Bytes writeNB (byte[] bytes, int offset, int length);

  Bytes writeNB (String value);

  Bytes writeNB (String value, Charset charset);

  Bytes write1B (byte value);

  Bytes write1B (short value);

  Bytes write1B (int value);

  Bytes write1B (long value);

  Bytes write1B (float value);

  Bytes write1B (double value);

  Bytes write1B (char value);

  Bytes write2B (byte value);

  Bytes write2B (short value);

  Bytes write2B (int value);

  Bytes write2B (long value);

  Bytes write2B (float value);

  Bytes write2B (double value);

  Bytes write2B (char value);

  Bytes write4B (byte value);

  Bytes write4B (short value);

  Bytes write4B (int value);

  Bytes write4B (long value);

  Bytes write4B (float value);

  Bytes write4B (double value);

  Bytes write4B (char value);

  Bytes write8B (byte value);

  Bytes write8B (short value);

  Bytes write8B (int value);

  Bytes write8B (long value);

  Bytes write8B (float value);

  Bytes write8B (double value);

  Bytes write8B (char value);

  Bytes setNB (int index, byte[] bytes);

  Bytes setNB (int index, byte[] bytes, int offset);

  Bytes setNB (int index, byte[] bytes, int offset, int length);

  Bytes setNB (int index, String value);

  Bytes setNB (int index, String value, Charset charset);

  Bytes set1B (int index, byte value);

  Bytes set1B (int index, short value);

  Bytes set1B (int index, int value);

  Bytes set1B (int index, long value);

  Bytes set1B (int index, float value);

  Bytes set1B (int index, double value);

  Bytes set1B (int index, char value);

  Bytes set2B (int index, byte value);

  Bytes set2B (int index, short value);

  Bytes set2B (int index, int value);

  Bytes set2B (int index, long value);

  Bytes set2B (int index, float value);

  Bytes set2B (int index, double value);

  Bytes set2B (int index, char value);

  Bytes set4B (int index, byte value);

  Bytes set4B (int index, short value);

  Bytes set4B (int index, int value);

  Bytes set4B (int index, long value);

  Bytes set4B (int index, float value);

  Bytes set4B (int index, double value);

  Bytes set4B (int index, char value);

  Bytes set8B (int index, byte value);

  Bytes set8B (int index, short value);

  Bytes set8B (int index, int value);

  Bytes set8B (int index, long value);

  Bytes set8B (int index, float value);

  Bytes set8B (int index, double value);

  Bytes set8B (int index, char value);

  byte readByte ();

  short readUnsignedByte ();

  short readShort ();

  int readUnsignedShort ();

  int readInt ();

  long readUnsignedInt ();

  long readLong ();

  BigInteger readUnsignedLong ();

  float readFloat ();

  double readDouble ();

  char readChar ();

  byte[] readBytes ();

  byte[] readBytes (int length);

  Bytes readBytes (byte[] destination);

  Bytes readBytes (byte[] destination, int offset, int length);

  String readString ();

  String readString (Charset charset);

  String readString (int length);

  String readString (int length, Charset charset);

  byte getByte (int index);

  short getUnsignedByte (int index);

  short getShort (int index);

  int getUnsignedShort (int index);

  int getInt (int index);

  long getUnsignedInt (int index);

  long getLong (int index);

  BigInteger getUnsignedLong (int index);

  float getFloat (int index);

  double getDouble (int index);

  char getChar (int index);

  byte[] getBytes (int index);

  byte[] getBytes (int index, int length);

  String getString (int index);

  String getString (int index, Charset charset);

  String getString (int index, int length);

  String getString (int index, int length, Charset charset);

  int capacity ();

  int writerIndex ();

  Bytes writerIndex (int newIndex);

  int writableBytes ();

  boolean isWritable ();

  boolean isWritable (int size);

  int readerIndex ();

  Bytes readerIndex (int newIndex);

  int readableBytes ();

  boolean isReadable ();

  boolean isReadable (int size);

  Bytes clear ();

  byte[] array ();
}
