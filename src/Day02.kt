fun main(){
    // X, A = Rock
    // Y, B = Paper
    // Z, C = Scissors


    val points = mapOf("A" to 1, "X" to 1, "B" to 2, "Y" to 2, "C" to 3, "Z" to 3)

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
            states[it.first()]!!.get(it.last())!! + points[it.last()]!!
        }

    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    measureTimeMillisPrint {
        val input = readInput("Day02")
        println("Part 1: "+part1(input).toString())
    }
}