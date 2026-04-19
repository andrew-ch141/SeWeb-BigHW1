// File: CuisineOptions.java — Allowed cuisine values for forms and safe XPath filtering.
package com.semweb.recipes;

import java.util.List;
import java.util.Set;

/**
 * Fixed vocabulary for cuisine fields. Keeps user input safe for XPath and matches sample data.
 */
public final class CuisineOptions {

    public static final List<String> ALL = List.of(
            "Italian", "French", "Indian", "British", "North African", "Mediterranean",
            "Thai", "Asian", "American", "European", "Japanese", "Mexican", "Greek",
            "Korean", "Spanish", "Moroccan", "Middle Eastern"
    );

    private static final Set<String> SET = Set.copyOf(ALL);

    private CuisineOptions() {
    }

    public static boolean isAllowed(String cuisine) {
        return cuisine != null && SET.contains(cuisine);
    }
}
