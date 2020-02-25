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

import static io.appulse.utils.ExceptionUtils.softException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ExceptionUtilsTest {

  @Test
  void test () {
    assertThat(doSomething(true)).isTrue();

    assertThatThrownBy(() -> doSomething(false)).isExactlyInstanceOf(IOException.class);
  }

  private boolean doSomething (boolean value) {
    if (value) {
      return value;
    }
    throw softException(new IOException());
  }
}
