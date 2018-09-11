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

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * LRU cache implementation based on {@code LinkedHashMap}
 *
 * @author Artem Labazin
 * @since 1.9.0
 */
public class LruCache<K, V> extends LinkedHashMap<K, V> {

  private static final long serialVersionUID = -1100634446524987320L;

  private final int maxSize;

  public LruCache (int maxSize) {
    super(maxSize + 1, 1.0F, true);
    this.maxSize = maxSize;
  }

  @Override
  protected boolean removeEldestEntry (Entry<K, V> eldest) {
    return size() > maxSize;
  }
}
