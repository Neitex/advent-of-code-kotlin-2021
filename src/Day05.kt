private const val Day = 5

private enum class Direction { LEFT_DOWN, LEFT_UP, RIGHT_DOWN, RIGHT_UP }

private fun Pair<Pair<Int, Int>, Pair<Int, Int>>.toDirection(): Direction = when {
    this.first.first < this.second.first -> { // RIGHT
        if (this.first.second < this.second.second)
            Direction.RIGHT_DOWN
        else Direction.RIGHT_UP
    }
    this.first.first > this.second.first -> { // LEFT
        if (this.first.second < this.second.second)
            Direction.LEFT_DOWN
        else Direction.LEFT_UP
    }
    else -> error("undefined")
}

fun main() {
    val dayIdentifier = "Day${Day.toString().padStart(2, '0')}"

    fun List<Int>.toPair(): Pair<Int, Int> {
        return Pair(this.first().toInt(), this[1])
    }

    fun List<Pair<Int, Int>>.toPair(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return Pair(this[0], this[1])
    }

    fun part1(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        val fieldMap = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach {
            if (it.first.first == it.second.first) {
                for (i in minOf(it.first.second, it.second.second)..maxOf(it.first.second, it.second.second)) {
                    fieldMap[Pair(it.first.first, i)] = (fieldMap[Pair(it.first.first, i)] ?: 0) + 1
                }
            } else if (it.first.second == it.second.second) {
                for (i in minOf(it.first.first, it.second.first)..maxOf(it.first.first, it.second.first)) {
                    fieldMap[Pair(i, it.first.second)] = (fieldMap[Pair(i, it.first.second)] ?: 0) + 1
                }
            }
        }
        return fieldMap.count { it.value >= 2 }
    }

    fun part2(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        val fieldMap = mutableMapOf<Pair<Int, Int>, Int>()
        input.forEach {
            if (it.first.first == it.second.first) {
                for (i in minOf(it.first.second, it.second.second)..maxOf(it.first.second, it.second.second)) {
                    fieldMap[Pair(it.first.first, i)] = (fieldMap[Pair(it.first.first, i)] ?: 0) + 1
                }
            } else if (it.first.second == it.second.second) {
                for (i in minOf(it.first.first, it.second.first)..maxOf(it.first.first, it.second.first)) {
                    fieldMap[Pair(i, it.first.second)] = (fieldMap[Pair(i, it.first.second)] ?: 0) + 1
                }
            } else {
                val direction = it.toDirection()
                for ((line, i) in (when (direction) {
                    Direction.LEFT_DOWN -> (it.second.first..it.first.first).reversed()
                    Direction.LEFT_UP -> (it.second.first..it.first.first).reversed()
                    Direction.RIGHT_DOWN -> it.first.first..it.second.first
                    Direction.RIGHT_UP -> it.first.first..it.second.first
                }).withIndex()) {
                    val index = Pair(
                        i, when (direction) {
                            Direction.LEFT_DOWN -> it.first.second + line
                            Direction.LEFT_UP -> it.first.second - line
                            Direction.RIGHT_DOWN -> it.first.second + line
                            Direction.RIGHT_UP -> it.first.second - line
                        }
                    )
                    fieldMap[index] = (fieldMap[index] ?: 0) + 1
                }
            }
        }
        return fieldMap.count { it.value >= 2 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayIdentifier}_test").map {
        it.split(" -> ").map { it.split(',').map { it.toInt() }.toPair() }.toPair()
    }
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput(dayIdentifier).map {
        it.split(" -> ").map { it.split(',').map { it.toInt() }.toPair() }.toPair()
    }
    println(part1(input))
    println(part2(input))
}
