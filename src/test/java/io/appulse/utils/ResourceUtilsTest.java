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

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Artem Labazin
 * @since 1.3.0
 */
class ResourceUtilsTest {

  @Test
  void getResource () {
    assertThat(ResourceUtils.getResource("/test.txt"))
        .isPresent()
        .hasValue("Hello world!\n");
  }

  @Test
  void notExist () {
    assertThat(ResourceUtils.getResource("/not-exist.txt"))
        .isNotPresent();
  }

  @Test
  void getResourceUrls () {
    List<String> list = ResourceUtils.getResourceUrls("folder", "file-?.*")
        .stream()
        .map(ResourceUtils::getTextContent)
        .map(Optional::get)
        .map(String::trim)
        .collect(toList());

    assertThat(list).contains("Artem", "Liza", "Milada", "Thais");
  }
}
