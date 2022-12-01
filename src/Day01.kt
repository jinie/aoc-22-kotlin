fun getList(input: List<String>): HashMap<Int, Int> {
    var idx = 0
    var sum = 0
    val sums = HashMap<Int, Int>()

    input.map { it.trim() }.forEach {
        if (it.isNotEmpty()) {
            sum += it.toInt()
        } else {
            sums[idx++] = sum
            sum = 0
        }
    }
    return sums
}

fun part1(input: List<String>): Int = getList(input).values.sortedDescending()[0]
fun part2(input: List<String>): Int = getList(input).values.sortedDescending().subList(0, 3).sum()

fun main() {
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    measureTimeMillisPrint {
        val input = readInput("Day01")
        println(part1(input))
        println(part2(input))
    }
}
