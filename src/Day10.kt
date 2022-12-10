class Day10 {
    var cycle = 0
    fun regX() = 1 + pendingInstructions.filter { it.first < cycle }.sumOf { it.second }
    var pendingInstructions = mutableListOf<Pair<Int,Int>>()

    fun part1(input: List<String>): Int {
        var ret = 0
        var cycles = 0
        input.forEach {
            when(it.substring(0,4)){
                "addx" -> {
                    cycles = 2
                    pendingInstructions.add(Pair(cycle+2, it.substring(5).toInt()))
                }
                "noop" -> cycles = 1
            }
            for(i in 0 until cycles){
                cycle++
                if(cycle in intArrayOf(20,60,100,140,180,220)){
                    ret += (cycle * regX())
                }
            }
        }
        return ret
    }
}

fun main(){
    val testInput = readInput("Day10_test")
    check(Day10().part1(testInput) == 13140)

    val input = readInput("Day10")
    println(Day10().part1(input))
}