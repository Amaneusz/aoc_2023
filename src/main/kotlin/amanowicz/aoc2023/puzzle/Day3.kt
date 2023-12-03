package amanowicz.aoc2023.puzzle

import amanowicz.aoc2023.util.readInput
import kotlin.math.abs

data class Position(val x: Int, val y: Int) {
    fun isAdjacentTo(other: Position): Boolean =
        abs(x - other.x) <= 1 && abs(y - other.y) <= 1
}

data class PositionedNumber(val value: Int, val positions: List<Position>) {
    fun isAdjacentTo(position: Position) =
        positions.any { it.isAdjacentTo(position) }
}

data class PositionedSymbol(val value: Char, val position: Position)

data class Board(val numbers: List<PositionedNumber>, val symbols: List<PositionedSymbol>)


fun main() {
//    Day3.task_1(readInput("day_3_sample_1"))
//    Day3.task_1(readInput("day_3_puzzle"))
//    Day3.task_2(readInput("day_3_sample_1"))
    Day3.task_2(readInput("day_3_puzzle"))
}

object Day3 {
    fun task_1(lines: List<String>) {
        val board = board(lines)
        val x = board.symbols
            .map { symbol ->
                board.numbers
                    .filter { it.isAdjacentTo(symbol.position) }
                    .map { it.value }
                    .sum()
            }
        println(x.sum())
    }

    fun task_2(lines: List<String>){
        val board = board(lines)
        val gearsWithNeighbours = board.symbols
            .filter { it.value == '*' }
            .map { star ->
                star to board.numbers
                    .filter { it.isAdjacentTo(star.position) }
            }
            .filter { (_, neighbours) -> neighbours.size == 2 }
        val result = gearsWithNeighbours
            .map { (_, partNumbers) -> partNumbers[0].value * partNumbers[1].value }
            .sum()
        println(result)
    }

    fun board(lines: List<String>): Board {
        var currentNum: Int? = null
        var currentNumPositions = mutableListOf<Position>()
        val numbers = mutableListOf<PositionedNumber>()
        val symbols = mutableListOf<PositionedSymbol>()
        lines.reversed()
            .forEachIndexed { yPos, line ->
                line.forEachIndexed { xPos, char ->
                    if (char.isDigit()) {
                        currentNum = if (currentNum != null) {
                            currentNum!! * 10 + char.digitToInt()
                        } else {
                            char.digitToInt()
                        }
                        currentNumPositions.add(Position(xPos, yPos))
                    } else {
                        if (currentNum != null) {
                            numbers.add(PositionedNumber(currentNum!!, currentNumPositions))
                        }
                        if (char != '.') {
                            symbols.add(PositionedSymbol(char, Position(xPos, yPos)))
                        }
                        currentNum = null
                        currentNumPositions = mutableListOf()
                    }
                }
            }
        return Board(numbers, symbols)
    }
}