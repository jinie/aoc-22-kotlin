import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/resources", "$name.txt").readLines()

fun readInputString(name: String) = File("src/resources", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16).padStart(32, '0')


/**
 * Measures execution time in ms, and calls loggingFunction with the result
 * Stolen from https://proandroiddev.com/measuring-execution-times-in-kotlin-460a0285e5ea
 */
inline fun <T> measureTimeMillis(
    loggingFunction: (Long) -> Unit, function: () -> T
): T {

    val startTime = System.currentTimeMillis()
    val result: T = function.invoke()
    loggingFunction.invoke(System.currentTimeMillis() - startTime)

    return result
}

/**
 *  Measures execution time in milliseconds and prints it
 */
inline fun <T> measureTimeMillisPrint(
    function: () -> T
): T {
    return measureTimeMillis({ println("Time Taken $it ms") }) {
        function.invoke()
    }
}

/**
 * Transposes a list, converting rows to columns ( and vice versa )
 */
fun transpose(matrix: List<List<Int>>): List<List<Int>> = (0 until matrix[0].size).map { column ->
    (0 until matrix.size).map { row ->
        matrix[row][column]
    }
}

/**
 * Finds up/down/left/right neighbours in a grid
 */
fun neighbours(input: List<List<Int>>, rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> {
    return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> rowIdx + dx to colIdx + dy }
        .filter { (x, y) -> x in input.indices && y in input.first().indices }
}

/**
 * Find all neighbours in a grid
 */
private fun allNeighbours(values: List<List<Int>>, y: Int, x: Int): List<Pair<Int, Int>> {
    return (-1..1).flatMap { dy -> (-1..1).map { dx -> dy to dx } }
        .filterNot { (dy, dx) -> dy == 0 && dx == 0 }
        .map { (dy, dx) -> y + dy to x + dx }
        .filter { (y, x) -> y in values.indices && x in values.first().indices }
}

/**
 * Grid helper class, from
 * https://todd.ginsberg.com/post/advent-of-code/2021/
 */
data class Point2d(var x: Int, var y: Int) {

    infix fun sharesAxisWith(that: Point2d): Boolean =
        x == that.x || y == that.y

    infix fun lineTo(that: Point2d): List<Point2d> {
        val xDelta = (that.x - x).sign
        val yDelta = (that.y - y).sign
        val steps = maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)
        return (1..steps).scan(this) { last, _ -> Point2d(last.x + xDelta, last.y + yDelta) }
    }

    fun chebyshevDistance(that: Point2d): Int{
        return maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)
    }


    fun neighbors(): List<Point2d> =
        listOf(
            Point2d(x, y + 1),
            Point2d(x, y - 1),
            Point2d(x + 1, y),
            Point2d(x - 1, y)
        )

    fun allNeighbors(): List<Point2d> =
        neighbors() + listOf(
            Point2d(x - 1, y - 1),
            Point2d(x - 1, y + 1),
            Point2d(x + 1, y - 1),
            Point2d(x + 1, y + 1)
        )

    fun allNeighborsInclusive(): List<Point2d> =
        neighbors() + listOf(
            Point2d(x - 1, y - 1),
            Point2d(x - 1, y + 1),
            Point2d(x + 1, y - 1),
            Point2d(x + 1, y + 1)
        )

    fun times(times: Int): List<Point2d> {
        return (1..times).map { this }
    }

    fun plus(that: Point2d): Point2d {
        return Point2d(x + that.x, y + that.y)
    }

    fun minus(that: Point2d): Point2d {
        return Point2d(x - that.x, y - that.y)
    }

    fun compareTo(that: Point2d): Int {
        return when {
            y < that.y -> -1
            y > that.y -> 1
            x < that.x -> -1
            x > that.x -> 1
            else -> 0
        }
    }
}

infix fun IntRange.intersects(other: IntRange): Boolean =
    first <= other.last && last >= other.first

infix fun IntRange.intersect(other: IntRange): IntRange =
    maxOf(first, other.first)..minOf(last, other.last)

fun IntRange.size(): Int =
    last - first + 1