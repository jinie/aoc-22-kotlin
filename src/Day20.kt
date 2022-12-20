import kotlin.math.abs

class Day20 {
    private fun decrypt(input: List<Long>, decryptKey: Int = 1, mixTimes: Int = 1): Long {
        val original = input.mapIndexed { index, i ->  Pair(index, i *decryptKey)}
        val moved = original.toMutableList()
        for(i in 0 until mixTimes) {
            original.forEach { p ->
                val idx = moved.indexOf(p)
                moved.removeAt(idx)
                moved.add((idx + p.second).mod(moved.size), p)
            }
        }
        return moved.map { it.second }.let {
            val idx0 = it.indexOf(0)
            it[(1000 + idx0) % moved.size] + it[(2000 + idx0) % moved.size] + it[(3000 + idx0) % moved.size]
        }
    }

    fun part1(input: List<Long>): Long {
        return decrypt(input, 1)
    }

    fun part2(input: List<Long>): Long {
        return decrypt(input, 811589153, 10)
    }
}

fun main() {
    val testInput = readInput("Day20_test").map { it.toLong() }
    check(Day20().part1(testInput) == 3L)
    check(Day20().part2(testInput) == 1623178306L)

    measureTimeMillisPrint {
        val input = readInput("Day20").map {it.toLong()}
        println(Day20().part1(input))
        println(Day20().part2(input))
    }
}