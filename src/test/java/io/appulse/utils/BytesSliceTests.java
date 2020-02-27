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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;

import lombok.val;
import org.junit.jupiter.api.Test;

class BytesSliceTests {

  byte[] buffer = new byte[] {
      0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
      0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F
  };

  @Test
  void createFullSize () {
    val bytes = Bytes.wrap(buffer);
    val builder = bytes.slice();

    val slice1 = builder.toByteArray();
    assertThat(slice1.length)
        .isEqualTo(buffer.length);

    val slice2 = builder.toMutableBytes();
    assertThat(slice2.readerIndex())
        .isEqualTo(0);
    assertThat(slice2.capacity())
        .isEqualTo(buffer.length);

    val slice3 = builder.toReadOnlyBytes();
    assertThat(slice3.readerIndex())
        .isEqualTo(0);
    assertThat(slice3.capacity())
        .isEqualTo(buffer.length);

    val slice4 = builder.toNewBytes();
    assertThat(slice4.readerIndex())
        .isEqualTo(0);
    assertThat(slice4.capacity())
        .isEqualTo(buffer.length);
  }

  @Test
  void createFrom () {
    val bytes = Bytes.wrap(buffer);
    val builder = bytes.slice().from(8);
    val expectedSize = buffer.length - 8;

    val slice1 = builder.toByteArray();
    assertThat(slice1.length)
        .isEqualTo(expectedSize);

    val slice2 = builder.toMutableBytes();
    assertThat(slice2.readerIndex())
        .isEqualTo(0);
    assertThat(slice2.capacity())
        .isEqualTo(expectedSize);

    val slice3 = builder.toReadOnlyBytes();
    assertThat(slice3.readerIndex())
        .isEqualTo(0);
    assertThat(slice3.capacity())
        .isEqualTo(expectedSize);

    val slice4 = builder.toNewBytes();
    assertThat(slice4.readerIndex())
        .isEqualTo(0);
    assertThat(slice4.capacity())
        .isEqualTo(expectedSize);
  }

  @Test
  void createTo () {
    val bytes = Bytes.wrap(buffer);
    val builder = bytes.slice().to(buffer.length - 8);
    val expectedSize = buffer.length - 8;

    val slice1 = builder.toByteArray();
    assertThat(slice1.length)
        .isEqualTo(expectedSize);

    val slice2 = builder.toMutableBytes();
    assertThat(slice2.readerIndex())
        .isEqualTo(0);
    assertThat(slice2.capacity())
        .isEqualTo(expectedSize);

    val slice3 = builder.toReadOnlyBytes();
    assertThat(slice3.readerIndex())
        .isEqualTo(0);
    assertThat(slice3.capacity())
        .isEqualTo(expectedSize);

    val slice4 = builder.toNewBytes();
    assertThat(slice4.readerIndex())
        .isEqualTo(0);
    assertThat(slice4.capacity())
        .isEqualTo(expectedSize);
  }

  @Test
  void createFromTo () {
    val bytes = Bytes.wrap(buffer);
    val builder = bytes.slice()
        .from(8)
        .to(buffer.length - 8);
    val expectedSize = buffer.length - 16;

    val slice1 = builder.toByteArray();
    assertThat(slice1.length)
        .isEqualTo(expectedSize);

    val slice2 = builder.toMutableBytes();
    assertThat(slice2.readerIndex())
        .isEqualTo(0);
    assertThat(slice2.capacity())
        .isEqualTo(expectedSize);

    val slice3 = builder.toReadOnlyBytes();
    assertThat(slice3.readerIndex())
        .isEqualTo(0);
    assertThat(slice3.capacity())
        .isEqualTo(expectedSize);

    val slice4 = builder.toNewBytes();
    assertThat(slice4.readerIndex())
        .isEqualTo(0);
    assertThat(slice4.capacity())
        .isEqualTo(expectedSize);
  }

  @Test
  void checkSliceBounds () {
    val bytes = Bytes.wrap(buffer);
    val slice = bytes.slice()
        .from(8)
        .to(buffer.length - 8)
        .toMutableBytes();

    assertThatThrownBy(() -> slice.readerIndex(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage(
            "Reader index error: newIndex(%d) < 0 || newIndex(%d) > writerIndex(%d)",
            -1, -1, 16
        );

    assertThatThrownBy(() -> slice.readerIndex(17))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage(
            "Reader index error: newIndex(%d) < 0 || newIndex(%d) > writerIndex(%d)",
            17, 17, 16
        );

    assertThatThrownBy(() -> slice.writerIndex(-1))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage(
            "Writer index error: newIndex(%d) < readerIndex(%d) || newIndex(%d) > capacity(%d)",
            -1, 0, -1, 16
        );

    assertThatThrownBy(() -> slice.writerIndex(17))
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage(
            "Writer index error: newIndex(%d) < readerIndex(%d) || newIndex(%d) > capacity(%d)",
            17, 0, 17, 16
        );

    assertThatThrownBy(() -> {
            slice.readerIndex(8);
            slice.writerIndex(7);
        })
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage(
            "Writer index error: newIndex(%d) < readerIndex(%d) || newIndex(%d) > capacity(%d)",
            7, 8, 7, 16
        );
  }

  @Test
  void checkSliceMutability () {
    val bytes = Bytes.wrap(buffer);
    val slice = bytes.slice()
        .from(8)
        .to(buffer.length - 8)
        .toMutableBytes();

    val readed = slice.getBytes(0, 3);
    assertThat(readed)
        .containsExactly(Arrays.copyOfRange(buffer, 8, 11));
    assertThat(slice.readerIndex())
        .isEqualTo(0);
    assertThat(slice.writerIndex())
        .isEqualTo(16);

    slice.writerIndex(0);
    assertThat(slice.writerIndex())
        .isEqualTo(0);

    slice.write4B(0);
    assertThat(slice.readerIndex())
        .isEqualTo(0);
    assertThat(slice.writerIndex())
        .isEqualTo(4);

    assertThat(slice.getBytes(0, 3))
        .doesNotContain(readed);

    assertThat(slice.readBytes(3))
        .doesNotContain(readed);
  }

  @Test
  void checkSliceReadOnly () {
    val bytes = Bytes.wrap(buffer);
    val slice = bytes.slice()
        .from(8)
        .to(buffer.length - 8)
        .toReadOnlyBytes();

    assertThatThrownBy(() -> slice.writerIndex(0))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("The operation doesn't support in BytesReadOnly wrapper");

    assertThatThrownBy(() -> slice.write4B(500))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("The operation doesn't support in BytesReadOnly wrapper");

    assertThatThrownBy(() -> slice.set1B(0, 100))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessage("The operation doesn't support in BytesReadOnly wrapper");
  }
}
