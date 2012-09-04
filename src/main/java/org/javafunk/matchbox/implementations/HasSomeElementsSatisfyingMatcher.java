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
import org.hamcrest.TypeSafeMatcher;
import org.javafunk.matchbox.SelfDescribingPredicate;

import static org.javafunk.funk.Eagerly.any;

public class HasSomeElementsSatisfyingMatcher<T> extends TypeSafeMatcher<Iterable<T>> {
    private final SelfDescribingPredicate<T> predicate;

    public HasSomeElementsSatisfyingMatcher(SelfDescribingPredicate<T> predicate) {
        this.predicate = predicate;
    }

    public static <T> Matcher<Iterable<T>> hasSomeElementsSatisfying(SelfDescribingPredicate<T> predicate) {
        return new HasSomeElementsSatisfyingMatcher<T>(predicate);
    }

    @Override
    protected boolean matchesSafely(Iterable<T> items) {
        return any(items, predicate);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Collection with any items matching predicate ").appendValue(predicate.describe());
    }
}
