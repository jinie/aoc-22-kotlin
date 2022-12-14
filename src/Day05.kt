import java.util.Stack

class Day05(input: String) {
    private val stacks = prepareStacks(input.substringBefore("\n\n").lines())
    private val pattern = """move (\d+) from (\d+) to (\d+)""".toRegex()
    private val commands = input.substringAfter("\n\n").lines()
        .map { pattern.matchEntire(it)!!.destructured.toList() }
        .map { it.map(String::toInt) }

    fun part1(): String {
        commands.forEach { (times, from, to) ->
            repeat(times) {
                stacks[to - 1].push(stacks[from - 1].pop())
            }
        }
        return stacks.map { it.peek() }.joinToString("")
    }

    fun part2(): String {
        commands.forEach { (times, from, to) ->
            stacks[to - 1].addAll(
                buildList {
                    repeat(times) {
                        add(stacks[from - 1].pop())
                    }
                }.reversed()
            )
        }
        return stacks.map { it.peek() }.joinToString("")
    }

    private fun prepareStacks(definition: List<String>): List<Stack<Char>> {
        val numbers = definition.last()
        val contents = definition.dropLast(1).reversed()

        return numbers.mapIndexedNotNull { index, char ->
            if (!char.isDigit()) {
                null
            } else {
                Stack<Char>().apply { addAll(contents.map { it[index] }.filter { it.isLetter() }) }
            }
        }
    }
}

fun main(){
    val testInput = readInput("Day05_test").joinToString("")
    check(Day05(testInput).part1().compareTo("CMZ")==0)
    measureTimeMillisPrint {
        val input = readInput("Day05").joinToString("")
        println(Day05(input).part1())
        println(Day05(input).part2())
    }
}