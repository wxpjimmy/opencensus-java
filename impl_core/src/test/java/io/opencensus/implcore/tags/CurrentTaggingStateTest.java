/*
 * Copyright 2017, OpenCensus Authors
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

package io.opencensus.implcore.tags;

import static com.google.common.truth.Truth.assertThat;

import io.opencensus.tags.TaggingState;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link CurrentTaggingState}. */
@RunWith(JUnit4.class)
public final class CurrentTaggingStateTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void defaultState() {
    assertThat(new CurrentTaggingState().get()).isEqualTo(TaggingState.ENABLED);
  }

  @Test
  public void setState() {
    CurrentTaggingState state = new CurrentTaggingState();
    state.set(TaggingState.DISABLED);
    assertThat(state.getInternal()).isEqualTo(TaggingState.DISABLED);
    state.set(TaggingState.ENABLED);
    assertThat(state.getInternal()).isEqualTo(TaggingState.ENABLED);
  }

  @Test
  public void preventNull() {
    CurrentTaggingState state = new CurrentTaggingState();
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("state");
    state.set(null);
  }

  @Test
  public void preventSettingStateAfterReadingState() {
    CurrentTaggingState state = new CurrentTaggingState();
    state.get();
    thrown.expect(IllegalStateException.class);
    thrown.expectMessage("State was already read, cannot set state.");
    state.set(TaggingState.DISABLED);
  }
}
