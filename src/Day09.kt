import kotlin.math.sign

//Advent of Code 2022 Day 9, Rope Physics
class Day09 {
    private class Rope(knotAmt: Int){
        val knots = mutableListOf<Point2d>()
        val tailPositions = mutableSetOf<Pair<Int,Int>>()
        val maxKnotDistance = 1

        init {
            tailPositions.add(Pair(0,0))
            for(i in 1..knotAmt){
                val knot = Point2d(0,0)
                knots.add(knot)
                tailPositions.add(Pair(knot.x,knot.y))
            }
        }

        fun move(dir: String, amt: Int){
            for(j in 0 until amt){
                when(dir){
                    "U" -> knots[0].y++
                    "D" -> knots[0].y--
                    "R" -> knots[0].x++
                    "L" -> knots[0].x--
                }

                for(i in 1 until knots.size){
                    if(knots[i-1].chebyshevDistance(knots[i]) > maxKnotDistance){
                        val knot = knots[i]
                        val prevKnot = knots[i-1]
                        val xDelta = (prevKnot.x - knot.x).sign
                        val yDelta = (prevKnot.y - knot.y).sign
                        knot.x += xDelta
                        knot.y += yDelta
                    }
                }
                tailPositions.add(Pair(knots.last().x,knots.last().y))
            }
        }
    }


    fun part1(input: List<String>): Int {
        val rope = Rope(2)

        input.forEach {
            val (d,v) = it.trim().split(" ")
            rope.move(d, v.toInt())
        }
        return rope.tailPositions.size
    }

    fun part2(input: List<String>): Int {
        val rope = Rope(10)

        input.forEach {
            val (d,v) = it.trim().split(" ")
            rope.move(d, v.toInt())

        }
        return rope.tailPositions.size
    }
}

fun main(){
    val testInput = readInput("Day09_test")
    check(Day09().part1(testInput) == 13)

    measureTimeMillisPrint {
        val input = readInput("Day09")
        println("Part 1: ${Day09().part1(input)}")
        println("Part 2: ${Day09().part2(input)}")
    }
}