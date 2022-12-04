package day._1

import readInput

fun main() {

	fun input(filePath: String): List<Int> = readInput(filePath).joinToString("\n") { it.trim() }.split("\n\n")
		.map { block -> block.lines().mapNotNull { if (it.trim().isEmpty()) null else it.trim().toInt() } }.map { it.sum() }

	fun part1(input: List<Int>): Int = input.max()

	fun part2(input: List<Int>): Int = input.sortedDescending().take(3).sum()

	val testInput = input("day/_1/Day01_test")
	check(part1(testInput) == 24000)
	check(part2(testInput) == 45000)

	val input = input("day/_1/Day01")
	println(part1(input))
	println(part2(input))

}
