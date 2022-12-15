import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun ints(input: String): List<Int>{
    return """-?\d+""".toRegex().findAll(input).map { it.value.toInt()}.toList()
}
fun  String.aints() = ints(this)

private class Day15(val lines: List<String>) {

    val sensors = lines.map { it.aints() }.map { Point2d(it[0], it[1]) to Point2d(it[2], it[3]) }
    val beaconDistance = lines.map { it.aints() }.map { Point2d(it[0], it[1]) to Point2d(it[2], it[3]) }.map {it.first to it.first.manhattanDistance(it.second) }.toList()

    fun findOpenSpot(i: Int): Long {
        val notHereRanges = mutableSetOf<IntRange>()
        for (s in beaconDistance) {
            val sensorLocation = s.first

            val dSToI = i - sensorLocation.y
            val total = s.second
            if (abs(dSToI) > total)
                continue

            val y = dSToI
            val dx = total - abs(y)
            val notHereRange = -dx + sensorLocation.x..dx + sensorLocation.x
            notHereRanges.add(notHereRange)
        }

        val rangeList = notHereRanges.sortedBy { it.first }.toList()
        var bigRange = rangeList[0]
        if (bigRange.first > 0) {
            return 0
        }

        for (r in rangeList) {
            if (bigRange.contains(r)) {
                continue
            }

            if (bigRange.overlaps(r) || bigRange.last + 1 == r.first) {
                bigRange = min(bigRange.first, r.first)..max(bigRange.last, r.last)
            } else {
                return bigRange.last + 1.toLong()
            }
        }
        return -1
    }

    fun part1(targetY: Int): Long {
        val notHereRange = mutableSetOf<IntRange>()
        beaconDistance.forEach { s ->
            for (sensor in beaconDistance) {
                val sensorLocation = sensor.first
                val total = sensor.second
                if (abs(targetY - sensorLocation.y) > total) {
                    continue
                }
                val dx = total - abs(targetY - sensorLocation.y)
                notHereRange.add(-dx + sensorLocation.x..dx + sensorLocation.x)
            }
        }
        val lineII = notHereRange.flatten().distinct()
        val beaconsI = sensors.map { it.second }.filter { it.y == targetY }.distinct()
        return lineII.size - beaconsI.size.toLong()
    }


    fun part2(dimension: Int): Long {
        for (y in 0..dimension) {
            val x = findOpenSpot(y)
            if (x != -1L) {
                return x * 4_000_000 + y
            }
        }
        return -1
    }
}

fun main() {

    val todayTest = Day15(readInput("Day15_test"))
    check(todayTest.part1(20) == 27L)

    measureTimeMillisPrint {
        val today = Day15(readInput("Day15"))
        println(today.part1(2000000))
        println(today.part2(4_000_000))
    }
}