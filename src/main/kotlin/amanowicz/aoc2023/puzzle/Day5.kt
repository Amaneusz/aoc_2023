package amanowicz.aoc2023.puzzle

import amanowicz.aoc2023.util.readInput

fun main() {
//    Day5.task_1(readInput("day_5_sample_1"))
//    Day5.task_1(readInput("day_5_puzzle"))
//    Day5.task_2(readInput("day_5_sample_1"))
    Day5.task_2(readInput("day_5_puzzle"))
}

data class Config(
    val seeds: List<Long>,
    val mappedProperties: List<MappedProperty>
)

data class Config2(
    val seedRanges: List<LongRange>,
    val mappedProperties: List<MappedProperty>
)

data class MappedProperty(
    val source: String,
    val dest: String,
    val destinationOffsets: List<DestinationOffset>
)

data class DestinationOffset(
    val range: LongRange,
    val offset: Long
) {

    fun destinationFor(value: Long): Long? {
        return if (value in range) {
            return value + offset
        } else null
    }
}

object Day5 {

    fun task_1(lines: List<String>) {
        val config = parse(lines)
        val destinations = config.seeds.map { seed ->
            seed to config.mappedProperties.fold(seed) { source, mappedProperty ->
                destination(source, mappedProperty)
            }
        }.toMap()
        val result = destinations.values.min()
        println(result)
    }

    // brute force
    fun task_2(lines: List<String>) {
        val config = parse2(lines)
        var min: Long = Long.MAX_VALUE
        config.seedRanges.forEach { seedRange ->
            seedRange.forEach { seed ->
                val dest = config.mappedProperties.fold(seed) { source, mappedProperty ->
                    destination(source, mappedProperty)
                }
                min = minOf(min, dest)
            }
        }
        println(min)
    }

    fun destination(value: Long, mappedProperty: MappedProperty): Long {
        return mappedProperty.destinationOffsets
            .firstNotNullOfOrNull { it.destinationFor(value) }
            ?: value
    }

    fun parse(lines: List<String>): Config {
        val seeds = lines[0].split(":")[1].trim().split(" ").map { it.trim().toLong() }
        return Config(seeds, parseProps(lines.takeLast(lines.size - 2)))
    }

    fun parse2(lines: List<String>): Config2 {
        val seedNumbers = lines[0].split(":")[1].trim().split(" ").map { it.trim().toLong() }
        val seedRanges = seedNumbers.mapIndexedNotNull { i, _ ->
            if(i % 2 == 0){
                LongRange(
                    seedNumbers[i],
                    seedNumbers[i] + seedNumbers[i + 1] - 1
                )
            } else null
        }
        return Config2(seedRanges, parseProps(lines.takeLast(lines.size - 2)))
    }

    fun parseProps(lines: List<String>): List<MappedProperty> {
        val props = mutableListOf<MappedProperty>()
        var source: String? = null
        var dest: String? = null
        var destinationOffsets: MutableList<DestinationOffset> = mutableListOf()
        lines.forEach { line ->
            if (line.isBlank()) {
                props.add(MappedProperty(source!!, dest!!, destinationOffsets))
                source = null
                dest = null
                destinationOffsets = mutableListOf()
            } else if (source == null) {
                val nameSplit = line.split(" ")[0].split("-to-")
                source = nameSplit[0]
                dest = nameSplit[1]
            } else {
                line.split(" ").let {
                    val start = it[1].toLong()
                    DestinationOffset(
                        LongRange(start, start + it[2].toLong() - 1),
                        it[0].toLong() - start
                    )
                }.let { destinationOffsets.add(it) }
            }
        }
        return props
    }

}