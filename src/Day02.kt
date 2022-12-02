fun main(){
    // X, A = Rock
    // Y, B = Paper
    // Z, C = Scissors
    val points = mapOf("A" to 1, "X" to 1, "B" to 2, "Y" to 2, "C" to 3, "Z" to 3)

    fun part2(input: List<String>): Int{

        val results = mapOf(
            "X" to mapOf("A" to 3,"B" to 1,"C" to 2),
            "Z" to mapOf("A" to 2,"B" to 3,"C" to 1),
            "Y" to mapOf("A" to 1,"B" to 2,"C" to 3)
        )
        val states = mapOf("X" to 0,"Y" to 3,"Z" to 6)

        return input.map { it.trim().split((" ")) }.sumOf {
            states[it.last()]!!.plus(results[it.last()]!![it.first()]!!)
        }
    }

    // |   | A | B | C |
    // | X | 3 | 0 | 6 |
    // | Y | 6 | 3 | 0 |
    // | Z | 0 | 6 | 3 |

    fun part1(input: List<String>): Int{
        val states = mapOf(
            "A" to mapOf("X" to 3, "Y" to 6, "Z" to 0),
            "B" to mapOf("X" to 0, "Y" to 3, "Z" to 6),
            "C" to mapOf("X" to 6, "Y" to 0, "Z" to 3)
        )

        return input.map { it.trim().split((" ")) }.sumOf {
            states[it.first()]!![it.last()]!! + points[it.last()]!!
        }

    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    measureTimeMillisPrint {
        val input = readInput("Day02")
        println("Part 1: "+part1(input).toString())
        println("Part 2: "+part2(input).toString())
    }
}