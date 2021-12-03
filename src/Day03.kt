import kotlin.math.pow

private const val Day = 3

fun main() {
    fun getBit(offset: Int, list: List<List<Boolean>>, most: Boolean = true): Boolean {
        val binaries = list.map { it[offset] }
        val map = mutableMapOf<Boolean, Int>()
        binaries.forEach {
            map[it] = (map[it] ?: 0) + 1
        }
        return map.entries.let {
            if (most)
                it.maxByOrNull { it.value }!!.key
            else
                it.minByOrNull { it.value }!!.key
        }
    }

    val DayIdentifier = "Day${Day.toString().padStart(2, '0')}"
    fun part1(input: List<List<Boolean>>): Int {
        val gamma = mutableListOf<Boolean>()
        repeat(input.first().size) {
            gamma.add(getBit(it, input))
        }
        val epsilon = mutableListOf<Boolean>()
        repeat(input.first().size) {
            epsilon.add(getBit(it, input, false))
        }
        return gamma.reversed().mapIndexed { index, b ->
            if (b) (2.0).pow(index).toInt() else 0
        }.sum() * epsilon.reversed().mapIndexed { index, b ->
            if (b) (2.0).pow(index).toInt() else 0
        }.sum()
    }

    fun part2(input: List<List<Boolean>>): Int {
        return 0 //TODO: Solve second part
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${DayIdentifier}_test").map { it.padStart(12, '0').map { it - '0' == 1 } }
    check(part1(testInput) == 198)
//    check(part2(testInput) == 900)

    val input = readInput(DayIdentifier).map { it.padStart(12, '0').map { it - '0' == 1 } }
    println(part1(input))
    println(part2(input))
}
