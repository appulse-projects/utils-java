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

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

import lombok.NonNull;
import lombok.val;

/**
 *
 * @author Artem Labazin
 * @since 1.3.0
 */
public final class ResourceUtils {

  public static Optional<String> getResource (@NonNull String name) {
    return getResource(name, UTF_8);
  }

  public static Optional<String> getResource (@NonNull String name, @NonNull Charset charset) {
    val classLoader = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(name);
    if (inputStream == null) {
      inputStream = ResourceUtils.class.getResourceAsStream(name);
      if (inputStream == null) {
        return empty();
      } else {
        byte[] bytes = BytesUtils.read(inputStream);
        String string = new String(bytes, charset);
        return of(string);
      }
    } else {
      return of("");
    }
  }

  private ResourceUtils () {
  }
}
