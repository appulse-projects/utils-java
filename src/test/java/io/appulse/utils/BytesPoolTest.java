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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.val;
import org.junit.jupiter.api.Test;

class BytesPoolTest {

  @Test
  void acquire () {
    val pool = BytesPool.builder()
        .initialBufferSizeBytes(10)
        .initialBuffersCount(2)
        .maximumBuffersCount(3)
        .bufferCreateFunction(Bytes::resizableArray)
        .build();

    assertThat(pool.getAcquiredCount()).isEqualTo(0);
    assertThat(pool.getFreeCount()).isEqualTo(3);
    assertThat(pool.getTotalCount()).isEqualTo(2);

    val buffer1 = pool.acquire();
    assertThat(buffer1).isNotNull();
    assertThat(buffer1.capacity()).isEqualTo(10);
    assertThat(buffer1.isAutoResizable()).isTrue();

    buffer1.capacity(50);
    assertThat(buffer1.capacity()).isEqualTo(50);

    assertThat(pool.getAcquiredCount()).isEqualTo(1);
    assertThat(pool.getFreeCount()).isEqualTo(2);
    assertThat(pool.getTotalCount()).isEqualTo(2);

    val buffer2 = pool.acquire();
    assertThat(buffer2).isNotNull();
    assertThat(buffer2.capacity()).isEqualTo(10);

    assertThat(pool.getAcquiredCount()).isEqualTo(2);
    assertThat(pool.getFreeCount()).isEqualTo(1);
    assertThat(pool.getTotalCount()).isEqualTo(2);

    val buffer3 = pool.acquire();
    assertThat(buffer3).isNotNull();
    assertThat(buffer3.capacity()).isEqualTo(10);

    assertThat(pool.getAcquiredCount()).isEqualTo(3);
    assertThat(pool.getFreeCount()).isEqualTo(0);
    assertThat(pool.getTotalCount()).isEqualTo(3);

    buffer1.release();

    assertThat(pool.getAcquiredCount()).isEqualTo(2);
    assertThat(pool.getFreeCount()).isEqualTo(1);
    assertThat(pool.getTotalCount()).isEqualTo(3);

    val buffer4 = pool.acquire();
    assertThat(buffer4).isNotNull();
    assertThat(buffer4.capacity()).isEqualTo(50);

    assertThat(pool.getAcquiredCount()).isEqualTo(3);
    assertThat(pool.getFreeCount()).isEqualTo(0);
    assertThat(pool.getTotalCount()).isEqualTo(3);
  }

  @Test
  void twoPools () {
    val pool1 = BytesPool.builder()
        .initialBufferSizeBytes(10)
        .initialBuffersCount(2)
        .maximumBuffersCount(3)
        .bufferCreateFunction(Bytes::resizableArray)
        .build();

    val pool2 = BytesPool.builder()
        .initialBufferSizeBytes(10)
        .initialBuffersCount(2)
        .maximumBuffersCount(3)
        .bufferCreateFunction(Bytes::resizableArray)
        .build();

    val buffer = pool1.acquire();

    assertThatThrownBy(() -> pool2.release(buffer))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The buffer not from this pool");
  }
}
