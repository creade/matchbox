/*
 * Copyright (C) 2012 Matchbox committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.matchbox.implementations;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class MismatchMessageMatcherTest {
    @Test
    public void shouldNotMatchIfSuppliedMatcherDoesNotMismatch() throws Exception {
        // Given
        Description targetDescription = mock(Description.class);
        Matcher<Integer> targetMatcher = is(5);
        MismatchMessageMatcher<Integer> mismatchMessageMatcher = new MismatchMessageMatcher<Integer>(5, "was <5>");
        
        // When
        Boolean matches = mismatchMessageMatcher.matchesSafely(targetMatcher, targetDescription);

        // Then
        assertThat(matches, is(false));
    }

    @Test
    public void shouldDescribeMatcherMatchingToDescriptionWhenSupplieMatcherDoesNotMismatch() throws Exception {
        // Given
        Description targetDescription = new StringDescription();
        Matcher<Integer> targetMatcher = is(5);
        MismatchMessageMatcher<Integer> mismatchMessageMatcher = new MismatchMessageMatcher<Integer>(5, "was <5>");

        Description expectedDescription = new StringDescription();
        expectedDescription.appendText("matcher matched");

        // When
        mismatchMessageMatcher.matchesSafely(targetMatcher, targetDescription);

        // Then
        assertThat(targetDescription.toString(), is(expectedDescription.toString()));
    }

    @Test
    public void shouldMatchIfMatcherMismatchesAndMismatchMessageMatches() throws Exception {
        Description targetDescription = new StringDescription();
        Matcher<Integer> targetMatcher = is(4);
        MismatchMessageMatcher<Integer> mismatchMessageMatcher = new MismatchMessageMatcher<Integer>(5, "was <5>");

        // When
        Boolean matches = mismatchMessageMatcher.matchesSafely(targetMatcher, targetDescription);

        // Then
        assertThat(matches, is(true));
    }

    @Test
    public void shouldNotMatchIfMatcherMismatchesButMismatchMessageDoesNotMatch() throws Exception {
        Description targetDescription = new StringDescription();
        Matcher<Integer> targetMatcher = is(4);
        MismatchMessageMatcher<Integer> mismatchMessageMatcher = new MismatchMessageMatcher<Integer>(5, "wrong mismatch message");

        // When
        Boolean matches = mismatchMessageMatcher.matchesSafely(targetMatcher, targetDescription);

        // Then
        assertThat(matches, is(false));
    }

    @Test
    public void shouldDescribeActualMismatchMessageIfMatcherMismatchesButMismatchMessageDoesNotMatch() throws Exception {
        Description targetDescription = new StringDescription();
        Matcher<Integer> targetMatcher = is(4);
        MismatchMessageMatcher<Integer> mismatchMessageMatcher = new MismatchMessageMatcher<Integer>(5, "wrong mismatch message");

        Description expectedDescription = new StringDescription();
        expectedDescription.appendText("got description <was <5>>");

        // When
        mismatchMessageMatcher.matchesSafely(targetMatcher, targetDescription);

        // Then
        assertThat(targetDescription.toString(), is(expectedDescription.toString()));
    }

    @Test
    public void shouldDescribeExpectedToSuppliedDescription() throws Exception {
        // Given
        Description actual = new StringDescription();
        MismatchMessageMatcher<Integer> mismatchMessageMatcher = new MismatchMessageMatcher<Integer>(5, "wrong mismatch message");

        Description expected = new StringDescription();
        expected.appendText("matcher to mismatch ")
                .appendValue(5)
                .appendText(" and give description containing ")
                .appendValue("wrong mismatch message");

        // When
        mismatchMessageMatcher.describeTo(actual);

        // Then
        assertThat(actual.toString(), is(expected.toString()));
    }
}
