private const val Day = 4

fun main() {
    val dayIdentifier = "Day${Day.toString().padStart(2, '0')}"

    fun List<String>.toComputableData(): Pair<List<Int>, List<List<List<Int>>>> {
        val nums = this.first().split(',').map { it.toInt() }
        val data = mutableListOf<MutableList<List<Int>>>()
        repeat((this.size - 1) / 6) { ind ->
            val baseIndex = 2 + (ind * 6)
            data.add(mutableListOf())
            repeat(5) {
                data[ind].add(this[baseIndex + it].split(' ').filter { it.isNotEmpty() }.map { it.toInt() })
            }
        }
        return Pair(nums, data)
    }

    fun part1(input: Pair<List<Int>, List<List<List<Int>>>>): Int {
        val cards =
            input.second.map { it.map { it.map { Pair(it, false) }.toMutableList() }.toMutableList() }.toMutableList()
        val checks = input.first
        for (check in checks) {
            cards.forEach {
                it.replaceAll {
                    val p = it
                    p.replaceAll { elem ->
                        if (elem.first == check)
                            Pair(check, true)
                        else elem
                    }
                    p
                }
            }
            cards.forEach { card ->
                val row = card.find {
                    it.count { it.second } == it.size
                }
                if (row != null) {
                    return card.sumOf { it.filter { !it.second }.sumOf { it.first } } * check
                }
            }
            cards.forEach { card ->
                repeat(5) { columnIndex ->
                    val column = card.map { it[columnIndex] }
                    if (column.count { it.second } == column.size)
                        return card.sumOf { it.filter { !it.second }.sumOf { it.first } } * check
                }
            }
        }
        return 0
    }

    fun part2(input: Pair<List<Int>, List<List<List<Int>>>>): Int {
        val cards =
            input.second.map { it.map { it.map { Pair(it, false) }.toMutableList() }.toMutableList() }.toMutableList()
        val checks = input.first
        for (check in checks) {
            cards.forEach {
                it.replaceAll {
                    val p = it
                    p.replaceAll { elem ->
                        if (elem.first == check)
                            Pair(check, true)
                        else elem
                    }
                    p
                }
            }
            val toRemove = mutableSetOf<Int>()
            cards.forEachIndexed { index, card ->
                val row = card.find {
                    it.count { it.second } == it.size
                }
                if (row != null) {
                    if (cards.size != 1)
                        toRemove.add(index)
                    else
                        return card.sumOf { it.filter { !it.second }.sumOf { it.first } } * check
                }
            }
            cards.forEachIndexed { index, card ->
                repeat(5) { columnIndex ->
                    val column = card.map { it[columnIndex] }
                    if (column.count { it.second } == column.size)
                        if (cards.size != 1)
                            toRemove.add(index)
                        else
                            return card.sumOf { it.filter { !it.second }.sumOf { it.first } } * check
                }
            }
            toRemove.reversed().forEach {
                cards.removeAt(it)
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayIdentifier}_test").toComputableData()
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput(dayIdentifier).toComputableData()
    println(part1(input))
    println(part2(input))
}
