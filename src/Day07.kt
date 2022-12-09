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
                val files: MutableList<String> = mutableListOf()
                while(++i < input.size){
                    if(input[i].startsWith("$"))
                        break
                    files.add(input[i])
                }
                dirTree[currentDir.toString()] = files
                    .filter { it.startsWith("dir").not() }
                    .fold(initial = 0) { acc, str -> acc + str.split(" ").first().toInt() }
            }
        }
        return calculateDirectorySizes(dirTree)
    }

    private fun calculateDirectorySizes(dirTree: Map<String,Int>): Map<String,Int>{
        val sizedTree = mutableMapOf<String,Int>()
        dirTree.entries.forEach{ 
            var curDir = it.key
            while(curDir.isNotEmpty()){
                curDir = curDir.dropLast(1)
                val key = curDir.ifBlank { "/" }
                sizedTree.merge(key, it.value) { a,b -> a+b }
                curDir = curDir.dropLastWhile { itt -> itt != '/' }
            }
        }
        return sizedTree
    }

    fun part1(dirMap: Map<String,Int>): Int{
        return dirMap.values.filter { it <= 100000 }.sum()
    }

    fun part2(dirMap: Map<String, Int>): Int {
        return dirMap.values.groupBy { it > (30000000 - (70000000 - dirMap["/"]!!)) }[true]!!.min()
    }
}

fun main() {
    val d7 = Day07()
    val testInput = readInput("Day07_test")
    val test = d7.parseInput(testInput)
    check(d7.part1(test) == 95437)

    measureTimeMillisPrint {
        val tree = d7.parseInput(readInput("Day07"))
        println(d7.part1(tree))
        println(d7.part2(tree))
    }
}