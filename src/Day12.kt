class Day12 {

    fun part1(input:List<String>): Int = parseMap(input)
        .let { (map, start, end) ->
            findShortestPath(mutableListOf(map[start]!!), map, end)
        }

    fun part2(input:List<String>): Int = parseMap(input)
        .let { (map, _, end) ->
            map.values
                .filter { it.height == 'a' }
                .map { findShortestPath(mutableListOf(it), parseMap(input).first, end) }
                .minOf { it }
        }

    private fun parseMap(input:List<String>): Triple<Map<Point2d,Point>,Point2d,Point2d>{
        val map = mutableMapOf<Point2d, Point>()
        var start = Point2d(-1,-1)
        var end = Point2d(-1, -1)
        
        input.forEachIndexed{ y, line ->
            line.toCharArray().forEachIndexed { x, c ->
                when(c) {
                    'S' -> { start = Point2d(x,y); map[start] = Point(coordinates = start, height = 'a')}
                    'E' -> { end = Point2d(x,y); map[end] = Point(coordinates = end, height = 'z')}
                    else -> {val pt = Point2d(x,y); map[pt] = Point(coordinates = pt, height = c)}
                }
            }
        }
        return Triple(map, start, end)
    }

    private fun findShortestPath(toVisit: MutableList<Point>, allNodes: Map<Point2d, Point>, end: Point2d): Int{
        var steps = 0
        while(toVisit.isNotEmpty()) {
            steps++

            val nextStop = mutableSetOf<Point>()
            toVisit.forEach{ point ->
                point.coordinates.neighbors().filter{ it in allNodes }.filter{ allNodes[it]!!.height.code <= point.height.code+1}.filter{ !allNodes[it]!!.visited }.forEach {
                    nextStop.add(allNodes[it]!!)
                }
                point.visited = true
            }
            if(nextStop.any { it.coordinates == end })
                return steps
            else{
                toVisit.clear()
                toVisit.addAll(nextStop)
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
        val input = readInput("Day12")
        println(Day12().part1(input))
        println(Day12().part2(input))
    }
}