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

  Bytes putNB (byte[] bytes);

  Bytes putNB (int index, byte[] bytes);

  Bytes putNB (byte[] bytes, int offset);

  Bytes putNB (int index, byte[] bytes, int offset);

  Bytes putNB (byte[] bytes, int offset, int length);

  Bytes putNB (int index, byte[] bytes, int offset, int length);

  Bytes put1B (byte value);

  Bytes put1B (int index, byte value);

  Bytes put1B (short value);

  Bytes put1B (int index, short value);

  Bytes put1B (int value);

  Bytes put1B (int index, int value);

  Bytes put1B (long value);

  Bytes put1B (int index, long value);

  Bytes put1B (float value);

  Bytes put1B (int index, float value);

  Bytes put1B (double value);

  Bytes put1B (int index, double value);

  Bytes put1B (char value);

  Bytes put1B (int index, char value);

  Bytes put2B (byte value);

  Bytes put2B (int index, byte value);

  Bytes put2B (short value);

  Bytes put2B (int index, short value);

  Bytes put2B (int value);

  Bytes put2B (int index, int value);

  Bytes put2B (long value);

  Bytes put2B (int index, long value);

  Bytes put2B (float value);

  Bytes put2B (int index, float value);

  Bytes put2B (double value);

  Bytes put2B (int index, double value);

  Bytes put2B (char value);

  Bytes put2B (int index, char value);

  Bytes put4B (byte value);

  Bytes put4B (int index, byte value);

  Bytes put4B (short value);

  Bytes put4B (int index, short value);

  Bytes put4B (int value);

  Bytes put4B (int index, int value);

  Bytes put4B (long value);

  Bytes put4B (int index, long value);

  Bytes put4B (float value);

  Bytes put4B (int index, float value);

  Bytes put4B (double value);

  Bytes put4B (int index, double value);

  Bytes put4B (char value);

  Bytes put4B (int index, char value);

  Bytes put8B (byte value);

  Bytes put8B (int index, byte value);

  Bytes put8B (short value);

  Bytes put8B (int index, short value);

  Bytes put8B (int value);

  Bytes put8B (int index, int value);

  Bytes put8B (long value);

  Bytes put8B (int index, long value);

  Bytes put8B (float value);

  Bytes put8B (int index, float value);

  Bytes put8B (double value);

  Bytes put8B (int index, double value);

  Bytes put8B (char value);

  Bytes put8B (int index, char value);

  byte getByte ();

  byte getByte (int index);

  short getUnsignedByte ();

  short getUnsignedByte (int index);

  short getShort ();

  short getShort (int index);

  int getUnsignedShort ();

  int getUnsignedShort (int index);

  int getInt ();

  int getInt (int index);

  long getUnsignedInt ();

  long getUnsignedInt (int index);

  long getLong ();

  long getLong (int index);

  BigInteger getUnsignedLong ();

  BigInteger getUnsignedLong (int index);

  float getFloat ();

  float getFloat (int index);

  double getDouble ();

  double getDouble (int index);

  char getChar ();

  char getChar (int index);

  byte[] getBytes ();

  byte[] getBytes (int index);

  byte[] getBytes (int index, int length);

  Bytes getBytes (byte[] destination);

  Bytes getBytes (byte[] destination, int offset, int length);

  String getString ();

  String getString (Charset charset);

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
