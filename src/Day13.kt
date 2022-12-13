class Day13 {

    private fun ordered(l: MutableList<Char>, r: MutableList<Char>): Boolean {
        fun MutableList<Char>.addBrackets(len: Int) = apply { add(len, ']'); add(0, '[') }
        fun List<Char>.num(): Int? =
            if (!this.first().isDigit()) null else this.takeWhile { it.isDigit() }.joinToString("").toInt()

        if (l.first() == '[' && r.num() != null) r.addBrackets(r.num().toString().length)
        if (r.first() == '[' && l.num() != null) l.addBrackets(l.num().toString().length)

        return when {
            l[0] == ']' && r[0] != ']' -> true
            l[0] != ']' && r[0] == ']' -> false
            l.num() == (r.num() ?: -1) -> ordered(
                l.subList(l.num().toString().length, l.size),
                r.subList(r.num().toString().length, r.size)
            )

            l.num() != null && r.num() != null -> l.num()!! < r.num()!!
            else -> ordered(l.subList(1, l.size), r.subList(1, r.size))
        }
    }

    val comparePackets: (String, String) -> Int =
        { s1: String, s2: String -> if (ordered(s1.toMutableList(), s2.toMutableList())) -1 else 1 }

    fun part1(input: List<String>): Int {
        return (input.windowed(2, 3)
            .mapIndexed { i, packets -> if (comparePackets(packets.first(), packets.last()) != 1) i + 1 else 0 }.sum())
    }

    fun part2(input: List<String>): Int {
        val sorted = (input.windowed(2, 3).flatten() + "[[2]]" + "[[6]]").sortedWith(comparePackets)
        return ((1 + sorted.indexOf("[[2]]")) * (1 + sorted.indexOf("[[6]]")))
    }
}

fun main() {
    val testInput = readInput("Day13_test")
    check(Day13().part1(testInput) == 13)
    check(Day13().part2(testInput) == 140)

    measureTimeMillisPrint {
        val input = readInput("Day13")
        println(Day13().part1(input))
        println(Day13().part2(input))
    }
}