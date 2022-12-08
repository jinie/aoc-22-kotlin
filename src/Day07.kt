import java.io.File
import java.util.*
class Day07 {
    fun parseInput(input: List<String>): Map<String, Int>{
        val currentDir = StringBuilder()
        val dirTree: MutableMap<String, Int> = mutableMapOf()

        var i = 0
        while(i < input.size){
            val it = input[i]
            val cmd = it.removePrefix("$").trim()
            if(cmd.startsWith("cd")) {
                when (val nd = cmd.split(" ").last()) {
                    "/" -> currentDir.clear().append("/")
                    ".." -> {
                        val lastDirInd = currentDir.dropLast(1).lastIndexOf('/') + 1
                        currentDir.delete(lastDirInd, currentDir.length)
                    }

                    else -> currentDir.append("$nd/")
                }
                i++
            }else{
                var j = i
                val files: MutableList<String> = mutableListOf()
                while(++j < input.size && !input[j].startsWith("$")) {
                    files.add(input[j++])
                }
                dirTree[currentDir.toString()] = files
                    .filter { it.startsWith("dir").not() }
                    .fold(initial = 0) { acc, str -> acc + str.split(" ").first().toInt() }
                i=j-1
            }
        }
        return dirTree
    }
}

fun main() {
    val testInput = readInput("Day07_test")
    val res = Day07().parseInput(testInput)
    println(res["/"])
    check(Day07().parseInput(testInput)["/"] == 48381165)
}