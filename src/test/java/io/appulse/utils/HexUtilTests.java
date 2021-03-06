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

import io.netty.util.internal.ThreadLocalRandom;
import lombok.val;
import org.junit.jupiter.api.Test;

class HexUtilTests {

  @Test
  void test1 () {
    val bytes = new byte[512];
    ThreadLocalRandom.current().nextBytes(bytes);

    val prettyPrint = HexUtil.prettyHexDump(bytes);
    System.out.println(prettyPrint);
  }

  @Test
  void test2 () {
    val bytes = new byte[60];
    ThreadLocalRandom.current().nextBytes(bytes);

    val prettyPrint = HexUtil.prettyHexDump(bytes);
    System.out.println(prettyPrint);
  }

  @Test
  void toHexToByteArray () {
    val bytes = new byte[] {
        (byte) 0xDB, (byte) 0x68, (byte) 0xFE, (byte) 0x74, (byte) 0x59,
        (byte) 0x7A, (byte) 0x9A, (byte) 0xC1, (byte) 0xCF, (byte) 0x27,
        (byte) 0xCE, (byte) 0x65, (byte) 0x64, (byte) 0x08, (byte) 0xC8
    };

    val string = HexUtil.toHexString(bytes);
    assertThat(string)
        .isEqualTo("DB68FE74597A9AC1CF27CE656408C8");

    assertThat(HexUtil.toByteArray(string))
        .isEqualTo(bytes);
  }
}
