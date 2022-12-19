package day._11

import lcm
import java.io.File

typealias WorryLevel = Long

class Day11 {

	fun part1(monkeys: List<Monkey>): Int {
		for (round in 1..20) {
			for (monkey in monkeys) {
				while (monkey.itemList.isNotEmpty()) {
					val item = monkey.itemList.removeFirst()
					val worryLevel = monkey.operation(item) / 3
					if (worryLevel % monkey.divisibleBy == 0L) {
						monkeys[monkey.onTruePassesToMonkeyIdx].itemList.addLast(worryLevel)
					} else {
						monkeys[monkey.onFalsePassesToMonkeyIdx].itemList.addLast(worryLevel)
					}
					monkey.passesCount++
				}
			}
		}

		return monkeys.map { it.passesCount }.sortedByDescending { it }.take(2).reduce { acc, i -> acc * i }
	}

	fun part2(monkeys: List<Monkey>): Long {

		val theLeastCommonMultiple = monkeys.map { it.divisibleBy }.reduce(::lcm)

		for (round in 1..10_000) {
			for (monkey in monkeys) {
				while (monkey.itemList.isNotEmpty()) {
					val item = monkey.itemList.removeFirst()
					val newWorryLevel = monkey.operation(item) % theLeastCommonMultiple
					if (newWorryLevel % monkey.divisibleBy == 0L) {
						monkeys[monkey.onTruePassesToMonkeyIdx].itemList.addLast(newWorryLevel)
					} else {
						monkeys[monkey.onFalsePassesToMonkeyIdx].itemList.addLast(newWorryLevel)
					}
					monkey.passesCount++
				}
			}
		}

		return monkeys.map { it.passesCount.toLong() }.sortedByDescending { it }.take(2).reduce { acc, i -> acc * i }
	}

	companion object {
		private val NON_INT_DELIMITER = """(\D)+""".toRegex()
		fun parse(input: String): List<Monkey> {
			fun getOperation(operationInput: String): (WorryLevel) -> WorryLevel {
				val operationInputParts = operationInput.substringAfter("new = old ").split(" ")
				when (operationInputParts[0]) {
					"+" -> return { it + operationInputParts[1].toLong() }
					"*" -> return { it * if (operationInputParts[1] == "old") it else operationInputParts[1].toLong() }
					else -> throw IllegalStateException("Operation ${operationInputParts[0]} unknown.")
				}
			}

			return input.split("\n\n")
				.map { it.lines() }
				.map { monkeyLines ->
					Monkey(
						monkeyLines[0].trim().replace(":", ""),
						ArrayDeque(monkeyLines[1].extractLongs()),
						getOperation(monkeyLines[2]),
						monkeyLines[3].extractInts().first(),
						monkeyLines[4].extractInts().first(),
						monkeyLines[5].extractInts().first()
					)
				}
		}

		private fun String.extractInts(): List<Int> = this.split(NON_INT_DELIMITER).mapNotNull { it.toIntOrNull() }

		private fun String.extractLongs(): List<Long> = this.split(NON_INT_DELIMITER).mapNotNull { it.toLongOrNull() }

	}
}


data class Monkey(
	val label: String,
	var itemList: ArrayDeque<Long>,
	val operation: (WorryLevel) -> WorryLevel,
	val divisibleBy: Int,
	val onTruePassesToMonkeyIdx: Int,
	val onFalsePassesToMonkeyIdx: Int,
	var passesCount: Int = 0
) : Cloneable {
	public override fun clone(): Monkey = Monkey(
		label,
		ArrayDeque(itemList),
		operation,
		divisibleBy,
		onTruePassesToMonkeyIdx,
		onFalsePassesToMonkeyIdx,
		passesCount
	)

}

fun List<Monkey>.deepCopy(): List<Monkey> {

	val copy = mutableListOf<Monkey>()
	copy.addAll(this.map { it.clone() })

	return copy
}

fun main() {

	val parsedTestInput = Day11.parse(File("src/day/_11/Day11_test.txt").readText())
	check(Day11().part1(parsedTestInput.deepCopy()) == 10605)
	check(Day11().part2(parsedTestInput) == 2713310158)

	val input = Day11.parse(File("src/day/_11/Day11.txt").readText())
	println(Day11().part1(input.deepCopy()))
	println(Day11().part2(input))

}