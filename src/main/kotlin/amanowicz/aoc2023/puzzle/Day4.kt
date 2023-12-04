package amanowicz.aoc2023.puzzle

import amanowicz.aoc2023.util.readInput
import kotlin.math.pow

fun main() {
//    Day4.task_1(readInput("Day_4_sample_1"))
//    Day4.task_1(readInput("day_4_puzzle"))
//    Day4.task_2(readInput("Day_4_sample_1"))
    Day4.task_2(readInput("day_4_puzzle"))
}

data class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val myNumbers: List<Int>
)

object Day4 {

    fun task_1(lines: List<String>) {
        val cards = cards(lines)
        val result = cards.sumOf { countPoints(it) }.toInt()
        println(result)
    }

    fun task_2(lines: List<String>) {
        val cards = cards(lines)
        val cardsById = cards.associateBy { it.id }
        val cardCounts = cards(lines)
            .associate { it to 1 }
            .toMutableMap()

        var currentCard: Card? = cardCounts.keys.find { it.id == 1 }
        while (currentCard != null) {
            val cardsWon = countPoints(currentCard)
            (currentCard.id+ 1..currentCard.id + cardsWon).forEach { wonCardId ->
                cardsById[wonCardId]
                    ?.let { cardWon ->
                        cardCounts[cardWon] = cardCounts[cardWon]?.plus(cardCounts[currentCard!!]!!)
                            ?: throw IllegalArgumentException("No count for $wonCardId")
                    }
            }
            currentCard = cardsById[currentCard.id + 1]
        }

        val result = cardCounts.values.sum()
        println(result)
    }

    fun countPoints(card: Card): Int {
        val common = card.myNumbers.intersect(card.winningNumbers)
//        if (common.isEmpty()) {
//            return 0.0
//        }
//        return 2.0.pow(common.size - 1)
        return common.size
    }

    fun cards(lines: List<String>): List<Card> {
        return lines.map {
            val lineParts = it.split(":")
            val id = lineParts[0].split(" ").last().trim().toInt()
            val contentParts = lineParts[1].split("|")
            Card(
                id,
                contentParts[0].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() },
                contentParts[1].trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            )
        }
    }
}