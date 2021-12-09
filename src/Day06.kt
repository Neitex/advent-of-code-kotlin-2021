private const val Day = 6

fun main() {
    val dayIdentifier = "Day${Day.toString().padStart(2, '0')}"

    fun part1(input: List<Int>, days: Int = 80): Int {
        val fishes = mutableListOf<Pair<Int, Int>>()
        for (age in input) {
            fishes.add(Pair(-1, age))
        }
        repeat(days) {
            val tList = fishes.toList()
            tList.forEachIndexed { index, fish ->
                if (fish.first != it) {
                    if (fish.second - 1 == -1) {
                        fishes[index] = Pair(fish.first, 6)
                        fishes.add(Pair(it, 8))
                    } else fishes[index] = Pair(fish.first, fish.second - 1)
                }
            }
        }
        return fishes.size
    }

    fun part2(input: List<Int>): Long {
        val eCount = input.count { it == 8 }.toLong()
        val seCount = input.count { it == 7 }.toLong()
        val siCount = input.count { it == 6 }.toLong()
        val fiCount = input.count { it == 5 }.toLong()
        val foCount = input.count { it == 4 }.toLong()
        val thCount = input.count { it == 3 }.toLong()
        val twCount = input.count { it == 2 }.toLong()
        val onCount = input.count { it == 1 }.toLong()
        val zeCount = input.count { it == 0 }.toLong()
        var count = FishCounts(eCount, seCount, siCount, fiCount, foCount, thCount, twCount, onCount, zeCount)
        repeat(256) {
            count = count.rotate()
        }
        return count.sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayIdentifier}_test").map { it.split(',').map { it.toInt() } }
    check(part1(testInput.first()) == 5934)
    check(part2(testInput.first()) == 26984457539)

    val input = readInput(dayIdentifier).map { it.split(',').map { it.toInt() } }
    println(part1(input.first()))
    println(part2(input.first()))
}

private data class FishCounts(
    var eiCount: Long,
    var seCount: Long,
    var siCount: Long,
    var fiCount: Long,
    var foCount: Long,
    var thCount: Long,
    var twCount: Long,
    var onCount: Long,
    var zeCount: Long
) {
    fun rotate(): FishCounts {
        var eiTCount: Long = eiCount
        var seTCount: Long = seCount
        var siTCount: Long = siCount
        var fiTCount: Long = fiCount
        var foTCount: Long = foCount
        var thTCount: Long = thCount
        var twTCount: Long = twCount
        var onTCount: Long = onCount

        val zeTCount: Long = onTCount
        onTCount = twTCount
        twTCount = thTCount
        thTCount = foTCount
        foTCount = fiTCount
        fiTCount = siTCount
        siTCount = seTCount + zeCount
        seTCount = eiTCount
        eiTCount = zeCount
        return FishCounts(eiTCount, seTCount, siTCount, fiTCount, foTCount, thTCount, twTCount, onTCount, zeTCount)
    }

    val sum: Long
        get() = onCount + twCount + thCount + foCount + fiCount + siCount + seCount + eiCount + zeCount
}