class Day23(input: List<String>) {
    private val startingPositions = parseInput(input)
    private val nextTurnOffsets: List<List<Point2d>> = createOffsets()

    fun solvePart1(): Int {
        val locations = (0 until 10).fold(startingPositions) { carry, round -> carry.playRound(round) }
        val gridSize =
            ((locations.maxOf { it.x } - locations.minOf { it.x }) + 1) * ((locations.maxOf { it.y } - locations.minOf { it.y }) + 1)
        return gridSize - locations.size
    }

    fun solvePart2(): Int {
        var thisTurn = startingPositions
        var roundId = 0
        do {
            val previousTurn = thisTurn
            thisTurn = previousTurn.playRound(roundId++)
        } while (previousTurn != thisTurn)
        return roundId
    }

    private fun Set<Point2d>.playRound(roundNumber: Int): Set<Point2d> {
        val nextPositions = this.toMutableSet()
        val movers: Map<Point2d, Point2d> = this
            .filter { elf -> elf.neighbors().any { it in this } }
            .mapNotNull { elf ->
                nextTurnOffsets.indices.map { direction -> nextTurnOffsets[(roundNumber + direction) % 4] }
                    .firstNotNullOfOrNull { offsets ->
                        if (offsets.none { offset -> (elf + offset) in this }) elf to (elf + offsets.first())
                        else null
                    }
            }.toMap()

        val safeDestinations = movers.values.groupingBy { it }.eachCount().filter { it.value == 1 }.keys
        movers
            .filter { (_, target) -> target in safeDestinations }
            .forEach { (source, target) ->
                nextPositions.remove(source)
                nextPositions.add(target)
            }
        return nextPositions
    }

    private fun createOffsets(): List<List<Point2d>> =
        listOf(
            listOf(Point2d(0, -1), Point2d(-1, -1), Point2d(1, -1)), // N
            listOf(Point2d(0, 1), Point2d(-1, 1), Point2d(1, 1)), // S
            listOf(Point2d(-1, 0), Point2d(-1, -1), Point2d(-1, 1)), // W
            listOf(Point2d(1, 0), Point2d(1, -1), Point2d(1, 1)), // E
        )

    private fun parseInput(input: List<String>): Set<Point2d> =
        input.flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, char ->
                if (char == '#') Point2d(x, y) else null
            }
        }.toSet()
}

fun main() {
    val testInput = readInput("Day23_test")
    check(Day23(testInput).solvePart1() == 110)

    measureTimeMillisPrint {
        val input = readInput("Day23")
        val d23 = Day23(input)
        println(d23.solvePart1())
        println(d23.solvePart2())
    }
}