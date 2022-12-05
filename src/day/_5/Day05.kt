package day._5

import readInput


class Day05(filePath: String) {

	private val inputAsStringParts = readInput(filePath).joinToString("\n").split("\n\n")

	private val numberOfStacks = inputAsStringParts[0].lines()
		.last()
		.split(" ")
		.mapNotNull { if (it.isBlank()) null else it.toInt() }
		.last()

	private val moves = getMoves()

	private fun readStacks(stacks: List<ArrayDeque<Char>>): List<ArrayDeque<Char>> {
		inputAsStringParts[0].lines()
			.map { line ->
				line.mapIndexed { index, char ->
					if (char.isLetter()) {
						val stackNumber = index / 4
						stacks[stackNumber].addLast(char)
					}
				}
			}
		return stacks
	}

	private fun getMoves(): List<Move> {
		return inputAsStringParts[1].split("\n").map { Move.of(it) }
	}

	fun part1(): String {
		val stacks = readStacks(List(numberOfStacks) { ArrayDeque() })
		moves.forEach { step ->
			repeat(step.quantity) {
				val crate = stacks[step.source - 1].removeFirst()
				stacks[step.target - 1].addFirst(crate)
			}
		}

		return stacks.joinToString("") { it.first().toString() }
	}

	fun part2(): String {
		val stacks = readStacks(List(numberOfStacks) { ArrayDeque() })
		moves.forEach { step ->
			stacks[step.source - 1]
				.subList(0, step.quantity)
				.asReversed()
				.map { stacks[step.target - 1].addFirst(it) }
				.map { stacks[step.source - 1].removeFirst() }
		}

		return stacks.joinToString("") { it.first().toString() }
	}

	data class Move(val quantity: Int, val source: Int, val target: Int) {

		companion object {
			private val NON_INT_DELIMITER = """(\D)+""".toRegex()

			fun of(line: String): Move {
				return line.extractInts().let { Move(it[0], it[1], it[2]) }
			}

			private fun String.extractInts(): List<Int> = this.split(NON_INT_DELIMITER).mapNotNull { it.toIntOrNull() }
		}
	}

}

fun main() {

	val day05Test = Day05("day/_5/Day05_test")
	check(day05Test.part1() == "CMZ")
	check(day05Test.part2() == "MCD")

	val day05 = Day05("day/_5/Day05")
	println(day05.part1())
	println(day05.part2())

}
