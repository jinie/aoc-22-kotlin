class Day24(input: List<String>) {

    private val initialMapState: MapState = MapState.of(input)
    private val start: Point2d = Point2d(input.first().indexOfFirst { it == '.' }, 0)
    private val goal: Point2d = Point2d(input.last().indexOfFirst { it == '.' }, input.lastIndex)

    fun solvePart1(): Int =
        solve().first

    fun solvePart2(): Int {
        val toGoal = solve()
        val backToStart = solve(goal, start, toGoal.second, toGoal.first)
        val backToGoal = solve(start, goal, backToStart.second, backToStart.first)
        return backToGoal.first
    }

    private fun solve(
        startPlace: Point2d = start,
        stopPlace: Point2d = goal,
        startState: MapState = initialMapState,
        stepsSoFar: Int = 0
    ): Pair<Int, MapState> {
        val mapStates = mutableMapOf(stepsSoFar to startState)
        val queue = mutableListOf(PathAttempt(stepsSoFar, startPlace))
        val seen = mutableSetOf<PathAttempt>()

        while (queue.isNotEmpty()) {
            val thisAttempt = queue.removeFirst()
            if (thisAttempt !in seen) {
                seen += thisAttempt
                val nextMapState = mapStates.computeIfAbsent(thisAttempt.steps + 1) { key ->
                    mapStates.getValue(key - 1).nextState()
                }

                // Can we just stand still here?
                if (nextMapState.isOpen(thisAttempt.location)) queue.add(thisAttempt.next())

                val neighbors = thisAttempt.location.cardinalNeighbors()

                // Is one of our neighbors the goal?
                if (stopPlace in neighbors) return Pair(thisAttempt.steps + 1, nextMapState)

                // Add neighbors that will be open to move to on the next turn.
                neighbors
                    .filter { it == start || (nextMapState.inBounds(it) && nextMapState.isOpen(it)) }
                    .forEach { neighbor ->
                        queue.add(thisAttempt.next(neighbor))
                    }
            }
        }
        throw IllegalStateException("No path to goal")
    }

    private data class PathAttempt(val steps: Int, val location: Point2d) {
        fun next(place: Point2d = location): PathAttempt =
            PathAttempt(steps + 1, place)
    }

    private data class MapState(val boundary: Point2d, val blizzards: Set<Blizzard>) {
        private val unsafeSpots = blizzards.map { it.location }.toSet()

        fun isOpen(place: Point2d): Boolean =
            place !in unsafeSpots

        fun inBounds(place: Point2d): Boolean =
            place.x > 0 && place.y > 0 && place.x <= boundary.x && place.y <= boundary.y

        fun nextState(): MapState =
            copy(blizzards = blizzards.map { it.next(boundary) }.toSet())

        companion object {
            fun of(input: List<String>): MapState =
                MapState(
                    Point2d(input.first().lastIndex - 1, input.lastIndex - 1),
                    input.flatMapIndexed { y, row ->
                        row.mapIndexedNotNull { x, char ->
                            when (char) {
                                '>' -> Blizzard(Point2d(x, y), Point2d(1, 0))
                                '<' -> Blizzard(Point2d(x, y), Point2d(-1, 0))
                                'v' -> Blizzard(Point2d(x, y), Point2d(0, 1))
                                '^' -> Blizzard(Point2d(x, y), Point2d(0, -1))
                                else -> null
                            }
                        }
                    }.toSet()
                )
        }
    }

    private data class Blizzard(val location: Point2d, val offset: Point2d) {
        fun next(boundary: Point2d): Blizzard {
            var nextLocation = location + offset
            when {
                nextLocation.x == 0 -> nextLocation = Point2d(boundary.x, location.y)
                nextLocation.x > boundary.x -> nextLocation = Point2d(1, location.y)
                nextLocation.y == 0 -> nextLocation = Point2d(location.x, boundary.y)
                nextLocation.y > boundary.y -> nextLocation = Point2d(location.x, 1)
            }
            return copy(location = nextLocation)
        }
    }
}

fun main() {
    val testInput = readInput("Day24_test")

    measureTimeMillisPrint {
        val input = readInput("Day24")
        val d24 = Day24(input)
        println(d24.solvePart1())
        println(d24.solvePart2())
    }
}