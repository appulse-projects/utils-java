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
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import lombok.experimental.FieldDefaults;
import lombok.val;
import org.junit.jupiter.api.Test;

class BytesUtilsTest {

  @Test
  void toBytes () {
    assertThat(BytesUtils.toBytes('a'))
        .isEqualTo(new byte[] { 0, 97 });

    assertThat(BytesUtils.toBytes((byte) 1))
        .isEqualTo(new byte[] { 1 });

    assertThat(BytesUtils.toBytes((short) 300))
        .isEqualTo(new byte[] { 1, 44 });

    assertThat(BytesUtils.toBytes(40_000))
        .isEqualTo(new byte[] { 0, 0, -100, 64 });

    assertThat(BytesUtils.toBytes(100L))
        .isEqualTo(new byte[] { 0, 0, 0, 0, 0, 0, 0, 100 });

    assertThat(BytesUtils.toBytes(.5F))
        .isEqualTo(new byte[] { 63, 0, 0, 0 });

    assertThat(BytesUtils.toBytes(.3D))
        .isEqualTo(new byte[] { 63, -45, 51, 51, 51, 51, 51, 51 });
  }

  @Test
  void concatenate () {
    assertThat(BytesUtils.concatenate(new byte[] { 1 }, new byte[] { 2 }, new byte[] { 3 }))
        .isEqualTo(new byte[] { 1, 2, 3 });
  }

  @Test
  void align () {
    assertThat(BytesUtils.align(new byte[] { 1 }, 4))
        .isEqualTo(new byte[] { 0, 0, 0, 1 });

    assertThat(BytesUtils.align(new byte[] { 1, 2, 3, 4 }, 2))
        .isEqualTo(new byte[] { 3, 4 });
  }

  @Test
  void asUnsignedByte () {
    val bytes = ByteBuffer.allocate(Byte.BYTES)
        .put((byte) 254)
        .array();

    assertThat(bytes[0])
        .isNotEqualTo(254);

    assertThat(BytesUtils.asUnsignedByte(bytes))
        .isNotEqualTo(254);
  }

  @Test
  void asShort () {
    val bytes1 = ByteBuffer.allocate(Short.BYTES)
        .putShort(Short.MAX_VALUE)
        .array();

    assertThat(BytesUtils.asShort(bytes1))
        .isEqualTo(Short.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Short.BYTES)
        .putShort(Short.MIN_VALUE)
        .array();

    assertThat(BytesUtils.asShort(bytes2))
        .isEqualTo(Short.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Short.BYTES)
        .putShort((short) 42)
        .array();

    assertThat(BytesUtils.asShort(bytes3))
        .isEqualTo((short) 42);
  }

  @Test
  void asUnsignedShort () {
    val bytes = ByteBuffer.allocate(Short.BYTES)
        .putShort((short) 62994)
        .array();

    assertThat(BytesUtils.asShort(bytes))
        .isNotEqualTo(62994);

    assertThat(BytesUtils.asUnsignedShort(bytes))
        .isEqualTo(62994);

    assertThat(BytesUtils.asUnsignedShort((short) -1))
        .isEqualTo(65535);
  }

  @Test
  void asChar () {
    val bytes = ByteBuffer.allocate(Character.BYTES)
        .putChar('z')
        .array();

    assertThat(BytesUtils.asChar(bytes))
        .isEqualTo('z');
  }

  @Test
  void asInteger () {
    val bytes1 = ByteBuffer.allocate(Integer.BYTES)
        .putInt(Integer.MAX_VALUE)
        .array();

    assertThat(BytesUtils.asInteger(bytes1))
        .isEqualTo(Integer.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Integer.BYTES)
        .putInt(Integer.MIN_VALUE)
        .array();

    assertThat(BytesUtils.asInteger(bytes2))
        .isEqualTo(Integer.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Integer.BYTES)
        .putInt(42)
        .array();

    assertThat(BytesUtils.asInteger(bytes3))
        .isEqualTo(42);
  }

  @Test
  void asUnsignedInteger () {
    val bytes = ByteBuffer.allocate(Integer.BYTES)
        .putInt((int) 4_100_000_000L)
        .array();

    assertThat(BytesUtils.asInteger(bytes))
        .isNotEqualTo(4_100_000_000L);

    assertThat(BytesUtils.asUnsignedInteger(bytes))
        .isEqualTo(4_100_000_000L);

    assertThat(BytesUtils.asUnsignedInteger(-1))
        .isEqualTo(4_294_967_295L);
  }

  @Test
  void asLong () {
    val bytes1 = ByteBuffer.allocate(Long.BYTES)
        .putLong(Long.MAX_VALUE)
        .array();

    assertThat(BytesUtils.asLong(bytes1))
        .isEqualTo(Long.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Long.BYTES)
        .putLong(Long.MIN_VALUE)
        .array();

    assertThat(BytesUtils.asLong(bytes2))
        .isEqualTo(Long.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Long.BYTES)
        .putLong(42L)
        .array();

    assertThat(BytesUtils.asLong(bytes3))
        .isEqualTo(42L);
  }

  @Test
  void asFloat () {
    val bytes1 = ByteBuffer.allocate(Float.BYTES)
        .putFloat(Float.MAX_VALUE)
        .array();

    assertThat(BytesUtils.asFloat(bytes1))
        .isEqualTo(Float.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Float.BYTES)
        .putFloat(Float.MIN_VALUE)
        .array();

    assertThat(BytesUtils.asFloat(bytes2))
        .isEqualTo(Float.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Float.BYTES)
        .putFloat(.5F)
        .array();

    assertThat(BytesUtils.asFloat(bytes3))
        .isEqualTo(.5F);
  }

  @Test
  void asDouble () {
    val bytes1 = ByteBuffer.allocate(Double.BYTES)
        .putDouble(Double.MAX_VALUE)
        .array();

    assertThat(BytesUtils.asDouble(bytes1))
        .isEqualTo(Double.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Double.BYTES)
        .putDouble(Double.MIN_VALUE)
        .array();

    assertThat(BytesUtils.asDouble(bytes2))
        .isEqualTo(Double.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Double.BYTES)
        .putDouble(.5D)
        .array();

    assertThat(BytesUtils.asDouble(bytes3))
        .isEqualTo(.5D);
  }

  @Test
  void read () throws Exception {
    byte[] expected = "Hello world".getBytes();

    assertThat(ReadBytesUtils.read(new CustomInputStream(expected)).arrayCopy())
        .isEqualTo(expected);
  }

  @Test
  void readWithLength () throws Exception {
    byte[] bytes = "Hello world".getBytes();
    byte[] expected = Arrays.copyOfRange(bytes, 0, 2);

    assertThat(ReadBytesUtils.read(new CustomInputStream(bytes), 2).arrayCopy())
        .isEqualTo(expected);
  }

  @FieldDefaults(level = PRIVATE)
  private static class CustomInputStream extends InputStream {

    final byte[] bytes;

    int index;

    CustomInputStream (byte[] bytes) {
      this.bytes = new byte[bytes.length + 1];
      int middle = bytes.length / 2;
      System.arraycopy(bytes, 0, this.bytes, 0, middle);
      this.bytes[middle] = -1;
      System.arraycopy(bytes, middle, this.bytes, middle + 1, bytes.length - middle);
    }

    @Override
    public int read () throws IOException {
      if (index >= bytes.length) {
        return -1;
      }
      return bytes[index++];
    }
  }
}
