class Day25(private val input: List<String>) {

    fun solvePart1(): String =
        input.sumOf { it.fromSnafu() }.toSnafu()

    private fun String.fromSnafu(): Long =
        fold(0) { carry, char ->
            (carry * 5) + when (char) {
                '-' -> -1
                '=' -> -2
                else -> char.digitToInt()
            }
        }

    private fun Long.toSnafu(): String =
        generateSequence(this) { (it + 2) / 5 }
            .takeWhile { it != 0L }
            .map { "012=-"[(it % 5).toInt()] }
            .joinToString("")
            .reversed()

}

fun main() {
    val testInput = readInput("Day25_test")
    check(Day25(testInput).solvePart1() == "2=-1=0")

    measureTimeMillisPrint {
        val input = readInput("Day25")
        val d25 = Day25(input)
        println(d25.solvePart1())
    }
}