package amanowicz.aoc2023.util

import kotlin.text.Charsets.UTF_8

fun readInput(fileName: String): List<String> {
    return ClassLoader
        .getSystemResource(fileName)
        ?.readText(UTF_8)
        ?.lines()
        ?: throw NullPointerException("No file found (fileName: $fileName)")
}
