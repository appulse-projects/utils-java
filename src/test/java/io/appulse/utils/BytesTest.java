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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;

/**
 *
 * @author Artem Labazin
 * @since 1.0.0
 */
public class BytesTest {

  @Test
  public void wrap () {
    byte[] expected = new byte[] { 1, 2, 3 };

    Bytes bytes = Bytes.wrap(expected);

    assertThat(bytes.position())
        .isEqualTo(0);

    assertThat(bytes.limit())
        .isEqualTo(expected.length);

    assertThat(bytes.remaining())
        .isEqualTo(expected.length);

    assertThat(bytes.array())
        .isEqualTo(expected);
  }

  @Test
  public void position () {
    Bytes bytes = Bytes.allocate(4);

    assertThat(bytes.position()).isEqualTo(0);

    bytes.put4B(10);
    assertThat(bytes.position()).isEqualTo(4);

    bytes.position(2);
    assertThat(bytes.position()).isEqualTo(2);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> bytes.position(5));
  }

  @Test
  public void clear () {
    Bytes bytes = Bytes.allocate()
        .put1B(1);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1 });

    bytes.clear();

    assertThat(bytes.array())
        .isEqualTo(new byte[0]);
  }

  @Test
  public void array () {
    Bytes bytes = Bytes.allocate();

    assertThat(bytes.array())
        .isEqualTo(new byte[0]);

    bytes.put1B(1);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1 });

    bytes.put1B(2);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1, 2 });
  }

  @Test
  public void limit () {
    Bytes bytes = Bytes.allocate(2);
    assertThat(bytes.limit()).isEqualTo(0);

    bytes.put4B(4);
    assertThat(bytes.limit()).isEqualTo(4);

    Bytes wrapped = Bytes.wrap(new byte[] { 1 });
    assertThat(wrapped.limit()).isEqualTo(1);
  }

  @Test
  public void remaining () {
    Bytes bytes = Bytes.allocate(2);
    assertThat(bytes.remaining()).isEqualTo(0);

    bytes.put4B(4);
    assertThat(bytes.remaining()).isEqualTo(0);

    Bytes wrapped = Bytes.wrap(new byte[] { 1 });
    assertThat(wrapped.remaining()).isEqualTo(1);

    wrapped.flip();
    assertThat(wrapped.remaining()).isEqualTo(0);
  }

  @Test
  public void unsignedTest () {
    Bytes bytes = Bytes.allocate()
        .put1B(254)
        .put2B(62994)
        .put4B(4_100_000_000L);

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
}
