class Day17(input: List<String>) {

    enum class Rock(val height: Int, val parts: List<Point2d>) {
        Dash(1, listOf(Point2d(0, 0), Point2d(1, 0), Point2d(2, 0), Point2d(3, 0))),
        Plus(3, listOf(Point2d(1, 0), Point2d(0, 1), Point2d(1, 1), Point2d(2, 1), Point2d(1, 2))),
        Angle(3, listOf(Point2d(0, 0), Point2d(1, 0), Point2d(2, 0), Point2d(2, 1), Point2d(2, 2))),
        Pipe(4, listOf(Point2d(0, 0), Point2d(0, 1), Point2d(0, 2), Point2d(0, 3))),
        Square(2, listOf(Point2d(0, 0), Point2d(0, 1), Point2d(1, 0), Point2d(1, 1)))
    }

    private val pattern = input.first().map { if (it == '>') 1 else -1 }

    private val chamber = mutableMapOf<Point2d,Char>().apply {
        for (i in 0..6) {
            this[Point2d(i, 0)] = '-' // Add a floor to get something more print friendly while debugging
        }
    }

    private var currentMax = 0 // Floor is at y=0, first rock is at y=1
    private var jetOffset = 0
    fun <T> Map<Point2d, T>.yRange() = keys.minByOrNull { it.y }!!.y..keys.maxByOrNull { it.y }!!.y
    private fun fall(rock: Rock) {
        var x = 2
        var y = currentMax + 4
        while (true) {
            //push if possible
            val xAfterPush = x + pattern[jetOffset++ % pattern.size]
            if (rock.parts.map { it + Point2d(xAfterPush, y) }.all { it.x in 0..6 && it !in chamber.keys }) {
                x = xAfterPush
            }


            //fall if possible
            if (y > 1 && rock.parts.map { it + Point2d(x, y - 1) }.none { it in chamber.keys }) {
                y--
            } else {
                // update chamber with final position of rock
                rock.parts.map { it + Point2d(x, y) }.forEach { chamber[it] = '#' }
                // update currentMax as well
                currentMax = chamber.yRange().max()
                break
            }
        }
    }

    fun part1(): Int {
        (0 until 2022).forEach {
            fall(Rock.values()[it % 5])
        }
        return currentMax
    }

    // First entry is the floor height
    private val maxHeightHistory = mutableListOf(0)

    fun part2(): Long {
        val numRocks = 1_000_000_000_000

        // First simulate a bit and record with how much the height increases for each rock
        (0 until 2022).forEach {
            fall(Rock.values()[it % 5])
            maxHeightHistory.add(currentMax)
        }
        val diffHistory = maxHeightHistory.zipWithNext().map { (a, b) -> b - a }

        // Now detect loops. Manual inspection of the diffHistory shows no loops that start from the very beginning
        // so start a bit into the sequence. Then use a marker that's long enough to not be repeating anywhere else
        // than when the loop restarts. The marker is the first markerLength entries in the loop.
        val loopStart = 200 // could be anything that's inside the loop
        val markerLength = 10
        val marker = diffHistory.subList(loopStart, loopStart + markerLength)

        var loopHeight = -1
        var loopLength = -1
        val heightBeforeLoop = maxHeightHistory[loopStart - 1]
        for (i in loopStart + markerLength until diffHistory.size) {
            if (marker == diffHistory.subList(i, i + markerLength)) {
                // Marker matches the sequence starting at i, so Loop ends at i - 1
                loopLength = i - loopStart
                loopHeight = maxHeightHistory[i - 1] - heightBeforeLoop
                break
            }
        }

        // Calculate the height at the target based on the number of loops and the height of the loop
        val numFullLoops = (numRocks - loopStart) / loopLength
        val offsetIntoLastLoop = ((numRocks - loopStart) % loopLength).toInt()
        val extraHeight = maxHeightHistory[loopStart + offsetIntoLastLoop] - heightBeforeLoop
        return heightBeforeLoop + loopHeight * numFullLoops + extraHeight
    }
}
fun main(){
    val testInput = readInput("Day17_test")
    check(Day17(testInput).part1() == 3068)

    measureTimeMillisPrint {
        val input = readInput("Day17")

        println(Day17(input).part1())
        println(Day17(input).part2())
    }
}
