class Day12 {

    fun part1(): Int = createMap()
        .let { (map, start, end) ->
            findShortestPath(mutableListOf(map[start]!!), map, end)
        }

    fun part2(): Int = createMap()
        .let { (map, _, end) ->
            map.values
                .filter { it.height == 'a' }
                .map { findShortestPath(mutableListOf(it), createMap().first, end) }
                .minOf { it }
        }

    private fun createMap(): Triple<MutableMap<Point2d, Point>, Point2d, Point2d> {
        val field = mutableMapOf<Point2d, Point>()

        var start = Point2d(-1, -1)
        var end = Point2d(-1, -1)

        readInput("Day12").forEachIndexed { y, line ->
            line.toCharArray().forEachIndexed { x, char ->
                when (char) {
                    'S' -> {
                        start = Point2d(x, y)
                        field[start] = Point(coordinates = start, height = 'a')
                    }

                    'E' -> {
                        end = Point2d(x, y)
                        field[end] = Point(coordinates = end, height = 'z')
                    }

                    else -> {
                        field[Point2d(x, y)] = Point(coordinates = Point2d(x, y), height = char)
                    }
                }
            }
        }
        return Triple(field, start, end)
    }

    private fun findShortestPath(
        toVisit: MutableList<Point>,
        allPoints: MutableMap<Point2d, Point>,
        end: Point2d
    ): Int {
        var moves = 0
        while (toVisit.isNotEmpty()) {
            moves++

            val nextToVisit = mutableSetOf<Point>()
            for (point in toVisit) {
                val left = allPoints.getOrElse(Point2d(point.coordinates.x - 1, point.coordinates.y)) { null }
                if ((left?.height ?: Char.MAX_VALUE).code <= point.height.code + 1 && left?.visited == false) {
                    nextToVisit.add(left)
                }

                val right = allPoints.getOrElse(Point2d(point.coordinates.x + 1, point.coordinates.y)) { null }
                if ((right?.height ?: Char.MAX_VALUE).code <= point.height.code + 1 && right?.visited == false) {
                    nextToVisit.add(right)
                }

                val up = allPoints.getOrElse(Point2d(point.coordinates.x, point.coordinates.y - 1)) { null }
                if ((up?.height ?: Char.MAX_VALUE).code <= point.height.code + 1 && up?.visited == false) {
                    nextToVisit.add(up)
                }

                val down = allPoints.getOrElse(Point2d(point.coordinates.x, point.coordinates.y + 1)) { null }
                if ((down?.height ?: Char.MAX_VALUE).code <= point.height.code + 1 && down?.visited == false) {
                    nextToVisit.add(down)
                }

                point.visited = true
            }

            if (nextToVisit.any { it.coordinates == end }) {
                return moves
            } else {
                toVisit.clear()
                toVisit.addAll(nextToVisit)
            }
        }

        return Int.MAX_VALUE
    }
}

data class Point(
    val coordinates: Point2d,
    val height: Char,
    var visited: Boolean = false
)

fun main() {
    measureTimeMillisPrint {
        println(Day12().part1())
        println(Day12().part2())
    }
}