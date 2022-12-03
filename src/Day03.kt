class Day03(private val input: List<String>) {

    fun part1(): Int =
        input.sumOf { it.sharedItems().priority() }

    fun part2(): Int =
        input.chunked(3).sumOf { it.sharedItems().priority() }

    private fun Char.priority(): Int =
        when (this) {
            in 'a'..'z' -> (this - 'a') + 1
            in 'A'..'Z' -> (this - 'A') + 27
            else -> 0
        }

    private fun String.sharedItems(): Char =
        listOf(
            substring(0..length / 2),
            substring(length / 2)
        ).sharedItems()

    private fun List<String>.sharedItems(): Char =
        map { it.toSet() }.reduce { l, r -> l intersect r}.first()
}

fun main() {
    measureTimeMillisPrint {
        val input = readInput("Day03")
        println("Part 1: ${Day03(input).part1()}")
        println("Part 2: ${Day03(input).part2()}")
    }
}