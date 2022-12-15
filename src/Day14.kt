
class Day14 {
    private fun Point2d.next(map: Set<Point2d>, resting: Set<Point2d>) = listOf(
        Point2d(x, y + 1),
        Point2d(x - 1, y + 1),
        Point2d(x + 1, y + 1)
    ).firstOrNull { it !in map  && it !in resting }

    private fun parse(input: List<String>): Set<Point2d>{
        val ret = mutableSetOf<Point2d>()
        input.map {
            "(\\d+,\\d+)".toRegex().findAll(it).map {
                val v = it.value.split(",")
                Point2d(v.first().toInt(), v.last().toInt())
            }.windowed(2,1).forEach { ret.addAll(it.first().lineTo(it.last())) }
        }
        return ret
    }

    fun part1(input: List<String>): Int {
        val resting = mutableSetOf<Point2d>()
        val initial = Point2d(500,0)
        var current = initial
        val map = parse(input)
        val maxY = map.maxOf { it.y }

        while (current.y != maxY) {
            current = current.next(map, resting) ?: initial.also { resting.add(current) }
        }
        return resting.size
    }

    fun part2(input: List<String>): Int {
        val resting = mutableSetOf<Point2d>()
        val initial = Point2d(500,0)
        var current = initial
        val map = parse(input)
        val floor = map.maxOf { it.y } + 2

        while (initial !in resting) {
            val next = current.next(map, resting)
            current = when (next == null || next.y == floor) {
                true -> initial.also { resting.add(current) }
                else -> next
            }
        }
        return resting.size
    }
}

fun main(){
    val testInput = readInput("Day14_test")
    check(Day14().part1(testInput) == 24)

    measureTimeMillisPrint {
        val input = readInput("Day14")
        println(Day14().part1(input))
        println(Day14().part2(input))
    }
}