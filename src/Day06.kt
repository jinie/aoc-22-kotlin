class Day06 {
    private fun parse(input: String, length: Int): Int{
        for(idx in 0..input.length-length){
            if(input.substring(idx,idx+length).toSet().size == length)
                return idx + length
        }
        throw Exception("Not found")
    }

    fun part1(input: String): Int{
        return parse(input,4)
    }

    fun part2(input: String): Int{
        return parse(input, 14)
    }
}

fun main(){
    val input = readInput("Day06").joinToString("")
    measureTimeMillisPrint {
        val d = Day06()
        println(d.part1(input))
        println(d.part2(input))
    }
}