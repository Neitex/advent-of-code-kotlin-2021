private const val Day = 2

private enum class Directions { forward, up, down }

fun main() {
    val DayIdentifier = "Day${Day.toString().padStart(2, '0')}"
    fun part1(input: List<Pair<Directions, Int>>): Int {
        var depth = 0
        var distance = 0
        input.forEach {
            when (it.first) {
                Directions.forward -> distance += it.second
                Directions.up -> depth -= it.second
                Directions.down -> depth += it.second
            }
        }
        return depth * distance
    }

    fun part2(input: List<Pair<Directions, Int>>): Int {
        var depth = 0
        var aim = 0
        var distance = 0
        input.forEach {
            when (it.first) {
                Directions.forward -> {
                    distance += it.second
                    depth += aim*it.second
                }
                Directions.up -> {
                    aim -= it.second
                }
                Directions.down -> {
                    aim += it.second
                }
            }
        }
        return distance*depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${DayIdentifier}_test").map {
        val parts = it.split(" ")
        Pair(Directions.valueOf(parts.component1()), parts.component2().toInt())
    }
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput(DayIdentifier).map {
        val parts = it.split(" ")
        Pair(Directions.valueOf(parts.component1()), parts.component2().toInt())
    }
    println(part1(input))
    println(part2(input))
}
