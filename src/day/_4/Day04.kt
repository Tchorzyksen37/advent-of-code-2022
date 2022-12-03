package day._4

import readInput
import java.util.stream.Collectors

fun main() {

fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("day/_4/Day04_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput("day/_4/Day04")
    println(part1(input))
    println(part2(input))

}
