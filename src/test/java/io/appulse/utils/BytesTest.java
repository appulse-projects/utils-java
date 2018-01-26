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
    byte[] expected = new byte[] { 1, 2, 3};

    Bytes bytes = Bytes.wrap(expected);

    assertThat(bytes.position())
        .isEqualTo(expected.length);

    assertThat(bytes.array())
        .isEqualTo(expected);
  }

  @Test
  public void position () {
    Bytes bytes = Bytes.allocate(4);


    assertThat(bytes.position()).isEqualTo(0);

    bytes.put(10);
    assertThat(bytes.position()).isEqualTo(4);

    bytes.position(2);
    assertThat(bytes.position()).isEqualTo(2);

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> bytes.position(5));
  }

  @Test
  public void clear () {
    Bytes bytes = Bytes.allocate()
        .putAsByte(1);

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

    bytes.putAsByte(1);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1 });

    bytes.putAsByte(2);

    assertThat(bytes.array())
        .isEqualTo(new byte[] { 1, 2 });
  }
}
