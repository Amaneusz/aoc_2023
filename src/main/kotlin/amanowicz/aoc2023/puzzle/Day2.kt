package amanowicz.aoc2023.puzzle

import amanowicz.aoc2023.util.readInput

fun main() {
//    Day2.task_1(readInput("day_2_sample_1"))
//    Day2.task_1(readInput("day_2_puzzle"))
//    Day2.task_2(readInput("day_2_sample_1"))
    Day2.task_2(readInput("day_2_puzzle"))
}

object Day2 {

    fun task_1(lines: List<String>) {
        val limits = mapOf(
            'r' to 12,
            'g' to 13,
            'b' to 14
        )

        val games = lines.map { lineToGame(it) }
        val result = games.sumOf { (id, gameContent) ->
            if (gamePossible(gameContent, limits)) {
                id
            } else 0
        }
        println(result)
    }

    fun task_2(lines: List<String>) {
        val games = lines.map { lineToGame(it) }
        val result = games.sumOf { (_, gameContent) ->
            val gameMax = maxMapOfGame(gameContent)
            gameMax.values.reduce { a, b -> a * b }
        }
        println(result)
    }

    fun lineToGame(line: String): Pair<Int, List<Map<Char, Int>>> {
        val (prefix, content) = line.split(":")
        val id = prefix.split(" ").last().toInt()
        val cubeSets = content.split(";").map { rawCubeSetToMap(it) }
        return id to cubeSets
    }

    // 8 green, 6 blue, 20 red -> { 'g': 8, 'b': 6, 'r': 20 }
    fun rawCubeSetToMap(rawCubeSet: String): Map<Char, Int> {
        val cubeSet = mutableMapOf<Char, Int>()
        rawCubeSet.split(",")
            .map {
                val (count, color) = it.trim().split(" ")
                cubeSet[color.first()] = count.toInt()
            }
        return cubeSet
    }

    fun gamePossible(
        cubeSets: List<Map<Char, Int>>,
        limits: Map<Char, Int>
    ): Boolean {
        return cubeSets.all { setPossible(it, limits) }
    }

    fun setPossible(
        cubeSet: Map<Char, Int>,
        limits: Map<Char, Int>,
    ): Boolean {
        return cubeSet.all { (color, count) ->
            count <= limits[color]!!
        }
    }

    fun maxMapOfGame(cubeSets: List<Map<Char, Int>>): Map<Char, Int> {
        val maxMap = mutableMapOf<Char, Int>()
        cubeSets.forEach { cubeSet ->
            cubeSet.forEach { color, count ->
                maxMap[color] = maxOf(maxMap[color] ?: 0, count)
            }
        }
        return maxMap
    }
}
