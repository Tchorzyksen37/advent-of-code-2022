package day._10

import readInput
import java.time.Clock

class Day10(filePath: String) {

	private val input = readInput(filePath).map { it.trim() }

	fun part1(): Int {
		fun Int.isSignalStrengthCycle() = this % 40 == 20
		var clock = 0
		var counter = 1
		var signalStrengths = 0
		for (str in input) {
			val strParts = str.split(" ")
			when (strParts[0]) {
				"noop" -> {
					clock++
					if (clock.isSignalStrengthCycle()) {
						signalStrengths += (clock * counter)
					}
				}

				"addx" -> {
					repeat(2) {
						clock++
						if (clock.isSignalStrengthCycle()) {
							signalStrengths += (clock * counter)
						}
						if (it == 1) {
							counter += strParts[1].toInt()
						}
					}
				}

				else -> throw IllegalArgumentException("Instruction unknown ${strParts[0]}")
			}

		}
		return signalStrengths
	}

	fun part2(): String {
		var clock = 0
		var x = 1
		var result = ""
		fun printCrtValue(clock: Int, x: Int) {
			if (clock != 0 && clock % 40 == 0) {
				println()
				result += "\n"
			}
			val printCharacter = if (clock % 40 in x - 1..x + 1) {
				result += "#"
				"\u001B[1;92m#\u001B[0m"
			} else {
				result += "."
				"."
			}

			print(printCharacter)

		}

		for (str in input) {
			val strParts = str.split(" ")
			when (strParts[0]) {
				"noop" -> {
					printCrtValue(clock, x)
					clock++
				}

				"addx" -> {
					repeat(2) {
						printCrtValue(clock, x)
						clock++
						if (it == 1) {
							x += strParts[1].toInt()
						}

					}
				}

				else -> throw IllegalArgumentException("Instruction unknown ${strParts[0]}")
			}

		}


		return result
	}

}


fun main() {

	check(Day10("day/_10/Day10_test").part1() == 13140)
	val part2Result = "##..##..##..##..##..##..##..##..##..##..\n" +
			"###...###...###...###...###...###...###.\n" +
			"####....####....####....####....####....\n" +
			"#####.....#####.....#####.....#####.....\n" +
			"######......######......######......####\n" +
			"#######.......#######.......#######....."
	check(Day10("day/_10/Day10_test").part2() == part2Result)
	println()
	println(Day10("day/_10/Day10").part1())
	Day10("day/_10/Day10").part2()
}