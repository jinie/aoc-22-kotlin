class Day10 {
    private var cycle = 0
    private fun regX() = 1 + pendingInstructions.filter { it.first < cycle }.sumOf { it.second }
    private var pendingInstructions = mutableListOf<Pair<Int, Int>>()

    private fun runInstruction(instruction: String): Int {
        var cycles = 0

        when (instruction.substring(0, 4)) {
            "addx" -> {
                cycles = 2
                pendingInstructions.add(Pair(cycle + 2, instruction.substring(5).toInt()))
            }
            "noop" -> cycles = 1
        }
        return cycles
    }

    fun part1(input: List<String>): Int {
        var ret = 0
        input.forEach {
            for (i in 0 until runInstruction(it)) {
                cycle++
                if (cycle in intArrayOf(20, 60, 100, 140, 180, 220)) {
                    ret += (cycle * regX())
                }
            }
        }
        return ret
    }

    fun part2(input: List<String>) {
        val displayLines = mutableListOf<String>()
        var currentLine = ""
        fun displayPos() = (cycle-1) % 40
        input.forEach {
            for (i in 0 until runInstruction(it)) {
                cycle++

                currentLine += if(regX() in intArrayOf(displayPos()-1, displayPos(), displayPos()+1))
                    "#"
                else
                    " "

                if(cycle % 40 == 0){
                    check(currentLine.length == 40)
                    displayLines.add(currentLine)
                    currentLine = ""
                }
            }
        }
        displayLines.forEach { println(it) }
    }
}

fun main() {
    val testInput = readInput("Day10_test")
    check(Day10().part1(testInput) == 13140)

    val input = readInput("Day10")
    println(Day10().part1(input))
    Day10().part2(input)
}