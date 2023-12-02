package amanowicz.aoc2023.puzzle

import amanowicz.aoc2023.util.readInput

object Day1 {

    private val numbersByName = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun task1(lines: List<String>): Int {
        val regex = Regex("\\d")

        return lines.fold(0) { acc, line ->
            val matches = regex.findAll(line)
            acc + matches.first().value.toInt() * 10 + matches.last().value.toInt()
        }
    }

    fun task2(lines: List<String>): Int {
        val regex = Regex("\\d|one|two|three|four|five|six|seven|eight|nine")

        return lines.fold(0) { acc, line ->
            val matches = regex.findAll(line)
            acc + parseNum(matches.first().value) * 10 + parseNum(matches.last().value)
        }
    }

    fun parseNum(numString: String): Int {
        if (numString.length == 1) return numString.toInt()
        return numbersByName[numString]
            ?: throw IllegalArgumentException("Not found entry for $numString")
    }
}

fun main() {
//    val result = Day1.task1(readInput("day_1_sample_1"))
//    val result = Day1.task1(readInput("day_1_puzzle"))
//    val result = Day1.task2(readInput("day_1_sample_2"))
//    val result = Day1.task2(readInput("day_1_puzzle"))
    val result = Day1.task2(readInput("input_ziomek.txt"))
    println(result)
}