fun main() {

    fun getList(input: List<String>): HashMap<Int, Int> {
        var idx = 0
        var sum = 0
        val sums = HashMap<Int, Int>()

        input.map { it.trim() }.forEach {
            if (it.isEmpty()) {
                sums[idx++] = sum
                sum = 0
            } else {
                sum += it.toInt()
            }
        }
        return sums
    }

    fun part1(input: List<String>): Int {
        return getList(input).values.sortedDescending()[0]
    }

    fun part2(input: List<String>): Int {
        val sums = getList(input)
        return sums.values.sortedDescending().subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    measureTimeMillisPrint {
        val input = readInput("Day01")
        println(part1(input))
        println(part2(input))
    }
}
