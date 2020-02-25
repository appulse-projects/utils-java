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

package io.appulse.utils.test;

import java.util.LinkedList;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Logging catcher helper class.
 *
 * @since 1.5.0
 * @author Artem Labazin
 */
@SuppressFBWarnings("MS_MUTABLE_COLLECTION_PKGPROTECT")
public class TestAppender extends AppenderBase<ILoggingEvent> {

  /**
   * The collected logging events.
   */
  public static final List<ILoggingEvent> EVENTS = new LinkedList<>();

  @Override
  protected void append (ILoggingEvent eventObject) {
    EVENTS.add(eventObject);
  }
}
