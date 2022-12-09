class Day08 {
    private fun linesOfSight(map: List<List<Int>>, x: Int, y: Int) = sequenceOf(
        sequenceOf((x - 1 downTo 0), (x + 1 until map[0].size)).map { it.map { x -> x to y } },
        sequenceOf((y - 1 downTo 0), (y + 1 until map.size)).map { it.map { y -> x to y } }
    ).flatten().map { it.map { (x, y) -> map[y][x] } }

    private fun List<Int>.countVisible(h: Int) = indexOfFirst { it >= h }.let { if (it == -1) size else it + 1 }

    fun part1(map: List<List<Int>>): Int{
        with(map.flatMapIndexed { y, l -> l.mapIndexed { x, h -> Triple(x, y, h) } }) {
           return count { (x, y, h) -> linesOfSight(map, x, y).any { it.all { oh -> oh < h } } }
        }
    }

    fun part2(map: List<List<Int>>): Int {
        with(map.flatMapIndexed { y, l -> l.mapIndexed { x, h -> Triple(x, y, h) } }) {
            return maxOf { (x, y, h) -> linesOfSight(map, x, y).map { it.countVisible(h) }.reduce(Int::times) }
        }
    }
}

fun main() {
    val d8 = Day08()

    val testMap = readInput("Day08_test").map { it.map(Char::digitToInt) }
    check(d8.part1(testMap) == 21)


    measureTimeMillisPrint {
        val map = readInput("Day08").map { it.map(Char::digitToInt)}
        println(d8.part1(map))
        println(d8.part2(map))
    }
}