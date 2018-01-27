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

import org.junit.Test;

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
}