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

import static java.util.Arrays.copyOfRange;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.nio.ByteBuffer;

import lombok.val;
import org.junit.jupiter.api.Test;

class BytesTest {

  @Test
  void wrap () {
    byte[] expected = new byte[] { 1, 2, 3 };

    Bytes bytes = Bytes.wrap(expected);

    assertThat(bytes.readerIndex())
        .isEqualTo(0);

    assertThat(bytes.writerIndex())
        .isEqualTo(3);

    assertThat(bytes.capacity())
        .isEqualTo(expected.length);

    assertThat(bytes.readableBytes())
        .isEqualTo(expected.length);

    assertThat(bytes.array())
        .isEqualTo(expected);
  }

  @Test
  void set () {
    Bytes bytes = Bytes.allocate(8);
    bytes.write1B(1);

    val writerIndex = bytes.writerIndex();
    bytes.write4B(0);

    bytes.write1B(7);
    bytes.set4B(writerIndex, bytes.writerIndex() - writerIndex - Integer.BYTES);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1, 0, 0, 0, 1, 7, 0, 0 });

    assertThat(copyOfRange(bytes.array(), bytes.readerIndex(), bytes.readableBytes()))
        .isEqualTo(new byte[] { 1, 0, 0, 0, 1, 7 });
  }

  @Test
  void position () {
    Bytes bytes = Bytes.allocate(4);

    assertThat(bytes.readerIndex()).isEqualTo(0);
    assertThat(bytes.writerIndex()).isEqualTo(0);

    bytes.write4B(10);
    assertThat(bytes.writerIndex()).isEqualTo(4);

    bytes.readerIndex(2);
    assertThat(bytes.readerIndex()).isEqualTo(2);

    assertThatExceptionOfType(IndexOutOfBoundsException.class)
        .isThrownBy(() -> bytes.writerIndex(5));
  }

  @Test
  void clear () {
    Bytes bytes = Bytes.resizableArray(1)
        .write1B(1);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1 });

    bytes.reset();

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1 });
  }

  @Test
  void array () {
    Bytes bytes = Bytes.resizableArray(4);

    assertThat(bytes.array())
        .isEqualTo(new byte[4]);

    bytes.write1B(1);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1, 0, 0, 0 });

    bytes.write1B(2);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1, 2, 0, 0 });
  }

  @Test
  void arrayCopy () {
    Bytes bytes = Bytes.resizableArray(4);

    assertThat(bytes.arrayCopy())
        .isEqualTo(new byte[0]);

    bytes.write1B(1);

    assertThat(bytes.arrayCopy())
        .isEqualTo(new byte[] { 1 });

    bytes.write1B(2);

    assertThat(bytes.arrayCopy())
        .isEqualTo(new byte[] { 1, 2 });
  }

  @Test
  void limit () {
    Bytes bytes = Bytes.allocate(4);
    assertThat(bytes.writerIndex()).isEqualTo(0);

    bytes.write4B(4);
    assertThat(bytes.writerIndex()).isEqualTo(4);

    Bytes wrapped = Bytes.wrap(new byte[] { 1, 0, 0 });
    assertThat(wrapped.array()).isEqualTo(new byte[] { 1, 0, 0 });
    assertThat(wrapped.writerIndex()).isEqualTo(3);

    wrapped.setNB(1, new byte[] { 2, 3 });
    assertThat(wrapped.array()).isEqualTo(new byte[] { 1, 2, 3 });
    assertThat(wrapped.writerIndex()).isEqualTo(3);
    assertThat(wrapped.readerIndex()).isEqualTo(0);
  }

  @Test
  void unsignedTest () {
    Bytes bytes = Bytes.resizableArray()
        .write1B(254)
        .write2B(62994)
        .write4B(4_100_000_000L);

    assertThat(bytes.getByte(0))
        .isNotEqualTo(254);

    assertThat(bytes.getUnsignedByte(0))
        .isEqualTo((short) 254);

    assertThat(bytes.getShort(1))
        .isNotEqualTo(62994);

    assertThat(bytes.getUnsignedShort(1))
        .isEqualTo(62994);

    assertThat(bytes.getInt(3))
        .isNotEqualTo(4_100_000_000L);

    assertThat(bytes.getUnsignedInt(3))
        .isEqualTo(4_100_000_000L);
  }

  @Test
  void capacity () {
    assertCapacity(new BytesFixedArray(2));
    assertCapacity(new BytesByteBuffer(ByteBuffer.allocate(2)));
    assertCapacity(BytesByteBuf.allocate(2));
  }

  private void assertCapacity (Bytes buffer) {
    buffer.capacity(2);
    buffer.write2B(7);
    assertThat(buffer.capacity())
        .isEqualTo(2);
    assertThat(buffer.array())
        .isEqualTo(new byte[] { 0, 7 });

    buffer.capacity(4);
    assertThat(buffer.capacity())
        .isEqualTo(4);
    assertThat(buffer.array())
        .isEqualTo(new byte[] { 0, 7, 0, 0 });

    buffer.capacity(1);
    assertThat(buffer.capacity())
        .isEqualTo(1);
    assertThat(buffer.array())
        .isEqualTo(new byte[] { 0 });
  }
}
