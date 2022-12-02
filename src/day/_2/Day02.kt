import java.util.stream.Collectors

fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day/_2/Day02_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput("day/_2/Day02")
    println(part1(input))
    println(part2(input))
}
