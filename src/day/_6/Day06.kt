package day._6

import readInput

fun main() {

	fun part1(input: List<String>): Int {
		return input.size
	}

	fun part2(input: List<String>): Int {
		return input.size
	}

	val testInput = readInput("day/_6/Day06_test")
	check(part1(testInput) == 0)
	check(part2(testInput) == 0)

	val input = readInput("day/_6/Day06")
	println(part1(input))
	println(part2(input))

}