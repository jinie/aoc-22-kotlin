class Day04 {
    fun part1(input: List<String>): Int{
        return input.map {
            val pair = it.split(",")
            val a = pair[0].split("-").map { it.toInt() }
            val b = pair[1].split("-").map { it.toInt() }
            if (((b[0] >= a[0]).and(b[1] <= a[1])) or ((a[0] >= b[0]).and(a[1] <= b[1])))
                1
            else
                0
        }.sum()
    }

    fun part2(input: List<String>): Int{
        return input.map {
            val pair = it.split(",")
            val a = pair[0].split("-").map { it.toInt() }
            val b = pair[1].split("-").map { it.toInt() }
            if (a[0].coerceAtLeast(b[0]) <= a[1].coerceAtMost(b[1]))
                1
            else
                0
        }.sum()
    }
}

fun main() {
    val testInput = readInput("Day04_test")
    check(Day04().part1(testInput) == 2)

    measureTimeMillisPrint {
        val input = readInput("Day04")
        println(Day04().part1(input))
        println(Day04().part2(input))
    }
}