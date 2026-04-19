// File: SkillLevel.java — Shared skill / difficulty literals aligned with the XSD.
package com.semweb.recipes;

import java.util.List;
import java.util.Set;

/**
 * Cooking skill for users and difficulty for recipes use the same three levels (assignment).
 */
public final class SkillLevel {

    public static final List<String> ALL = List.of("Beginner", "Intermediate", "Advanced");
    private static final Set<String> SET = Set.copyOf(ALL);

    private SkillLevel() {
    }

    public static boolean isValid(String v) {
        return v != null && SET.contains(v);
    }
}
