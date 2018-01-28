/*
 * Copyright 2018 the original author or authors.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;

import org.junit.Test;

import lombok.val;

public class BytesUtilTest {

  @Test
  public void asBytes () {
    assertThat(BytesUtil.asBytes('a'))
        .isEqualTo(new byte[] { 0, 97 });

    assertThat(BytesUtil.asBytes((byte) 1))
        .isEqualTo(new byte[] { 1 });

    assertThat(BytesUtil.asBytes((short) 300))
        .isEqualTo(new byte[] { 1, 44 });

    assertThat(BytesUtil.asBytes(40_000))
        .isEqualTo(new byte[] { 0, 0, -100, 64 });

    assertThat(BytesUtil.asBytes(100L))
        .isEqualTo(new byte[] { 0, 0, 0, 0, 0, 0, 0, 100 });

    assertThat(BytesUtil.asBytes(.5F))
        .isEqualTo(new byte[] { 63, 0, 0, 0 });

    assertThat(BytesUtil.asBytes(.3D))
        .isEqualTo(new byte[] { 63, -45, 51, 51, 51, 51, 51, 51 });
  }

  @Test
  public void concatenate () {
    assertThat(BytesUtil.concatenate(new byte[] { 1 }, new byte[] { 2 }, new byte[] { 3 }))
        .isEqualTo(new byte[] { 1, 2, 3 });
  }

  @Test
  public void align () {
    assertThat(BytesUtil.align(new byte[] { 1 }, 4))
        .isEqualTo(new byte[] { 0, 0, 0, 1 });

    assertThat(BytesUtil.align(new byte[] { 1, 2, 3, 4 }, 2))
        .isEqualTo(new byte[] { 3, 4 });
  }

  @Test
  public void asShort () {
    val bytes1 = ByteBuffer.allocate(Short.BYTES)
        .putShort(Short.MAX_VALUE)
        .array();

    assertThat(BytesUtil.asShort(bytes1))
        .isEqualTo(Short.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Short.BYTES)
        .putShort(Short.MIN_VALUE)
        .array();

    assertThat(BytesUtil.asShort(bytes2))
        .isEqualTo(Short.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Short.BYTES)
        .putShort((short) 42)
        .array();

    assertThat(BytesUtil.asShort(bytes3))
        .isEqualTo((short) 42);
  }

  @Test
  public void asChar () {
    val bytes = ByteBuffer.allocate(Character.BYTES)
        .putChar('z')
        .array();

    assertThat(BytesUtil.asChar(bytes))
        .isEqualTo('z');
  }

  @Test
  public void asInteger () {
    val bytes1 = ByteBuffer.allocate(Integer.BYTES)
        .putInt(Integer.MAX_VALUE)
        .array();

    assertThat(BytesUtil.asInteger(bytes1))
        .isEqualTo(Integer.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Integer.BYTES)
        .putInt(Integer.MIN_VALUE)
        .array();

    assertThat(BytesUtil.asInteger(bytes2))
        .isEqualTo(Integer.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Integer.BYTES)
        .putInt(42)
        .array();

    assertThat(BytesUtil.asInteger(bytes3))
        .isEqualTo(42);
  }

  @Test
  public void asLong () {
    val bytes1 = ByteBuffer.allocate(Long.BYTES)
        .putLong(Long.MAX_VALUE)
        .array();

    assertThat(BytesUtil.asLong(bytes1))
        .isEqualTo(Long.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Long.BYTES)
        .putLong(Long.MIN_VALUE)
        .array();

    assertThat(BytesUtil.asLong(bytes2))
        .isEqualTo(Long.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Long.BYTES)
        .putLong(42L)
        .array();

    assertThat(BytesUtil.asLong(bytes3))
        .isEqualTo(42L);
  }

  @Test
  public void asFloat () {
    val bytes1 = ByteBuffer.allocate(Float.BYTES)
        .putFloat(Float.MAX_VALUE)
        .array();

    assertThat(BytesUtil.asFloat(bytes1))
        .isEqualTo(Float.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Float.BYTES)
        .putFloat(Float.MIN_VALUE)
        .array();

    assertThat(BytesUtil.asFloat(bytes2))
        .isEqualTo(Float.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Float.BYTES)
        .putFloat(.5F)
        .array();

    assertThat(BytesUtil.asFloat(bytes3))
        .isEqualTo(.5F);
  }

  @Test
  public void asDouble () {
    val bytes1 = ByteBuffer.allocate(Double.BYTES)
        .putDouble(Double.MAX_VALUE)
        .array();

    assertThat(BytesUtil.asDouble(bytes1))
        .isEqualTo(Double.MAX_VALUE);

    val bytes2 = ByteBuffer.allocate(Double.BYTES)
        .putDouble(Double.MIN_VALUE)
        .array();

    assertThat(BytesUtil.asDouble(bytes2))
        .isEqualTo(Double.MIN_VALUE);

    val bytes3 = ByteBuffer.allocate(Double.BYTES)
        .putDouble(.5D)
        .array();

    assertThat(BytesUtil.asDouble(bytes3))
        .isEqualTo(.5D);
  }
}
