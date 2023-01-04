class Day18 {
    fun part1(input: Set<Point3D>): Int {
        return input.sumOf {
            6 - it.neighbors().count { neighbor ->
                neighbor in input
            }
        }
    }

    private fun Set<Point3D>.rangeOf(function: (Point3D) -> Int): IntRange =
        this.minOf(function) - 1..this.maxOf(function) + 1

    fun part2(input: Set<Point3D>): Int {
        val xRange = input.rangeOf { it.x }
        val yRange = input.rangeOf { it.y }
        val zRange = input.rangeOf { it.z }

        val queue = ArrayDeque<Point3D>().apply { add(Point3D(xRange.first, yRange.first, zRange.first)) }
        val seen = mutableSetOf<Point3D>()
        var ret = 0
        queue.forEach { lookNext ->
            if (lookNext !in seen) {
                lookNext.neighbors()
                    .filter { it.x in xRange && it.y in yRange && it.z in zRange }
                    .forEach { neighbor ->
                        seen += lookNext
                        if (neighbor in input) ret++
                        else queue.add(neighbor)
                    }
            }
        }
        return ret
    }


}

fun main() {
    val testInput = readInput("Day18_test").map { it.split(",").toList() }
        .map { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }.toSet()
    check(Day18().part1(testInput) == 64)

    measureTimeMillisPrint {
        val input = readInput("Day18").map { it.split(",").toList() }
            .map { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }.toSet()
        println(Day18().part1(input))
        println(Day18().part2(input))
    }
}