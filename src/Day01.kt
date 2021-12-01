const val Day = 1

fun main() {
    val DayIdentifier = "Day${Day.toString().padStart(2,'0')}"
    fun part1(input: List<Int>): Int {
        var prev = -1
        var count = 0
        input.forEachIndexed { index, i ->
            if (prev < i && index != 0) {
                count++
            }
            prev = i
        }
        return count
    }

    fun part2(input: List<Int>): Int {
        val sums = mutableListOf<Int>()
        input.windowed(3).forEach { sums.add(it.sum()) }
        return part1(sums)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${DayIdentifier}_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput(DayIdentifier).map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
