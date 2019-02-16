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

/**
 * Different exception helpers.
 *
 * @author Artem Labazin
 * @since 1.6.0
 */
public final class ExceptionUtils {

  /**
   * Removes 'checkedness' of the setted exception. It works like @{code @SneakyThrows} annotation in Project Lombok.
   * <p>
   * For more information, see this:
   * http://johannesbrodwall.com/2018/05/15/a-wicked-java-trick-to-make-the-jvm-forget-to-check-exceptions/
   *
   * @param ex checked exception.
   *
   * @return exception withoud checkedness.
   *
   * @since 1.6.0
   */
  public static RuntimeException softException (Exception ex) {
    return checkednessRemover(ex);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Exception> T checkednessRemover (Exception ex) throws T {
    throw (T) ex;
  }

  private ExceptionUtils () {
  }
}
