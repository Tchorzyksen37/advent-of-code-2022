package day._4

import readInput

fun main() {

	fun part1(input: List<String>): Int {
		var overlappingSectionsCount = 0
		for (str in input) {
			println(str)
			val elvesPair = str.split(",")
			val sectionOneRange = elvesPair[0].split("-")
			val sectionTwoRange = elvesPair[1].split("-")

			if ((Integer.parseInt(sectionOneRange[0]) >= Integer.parseInt(sectionTwoRange[0])
							&& Integer.parseInt(sectionOneRange[1]) <= Integer.parseInt(sectionTwoRange[1]))
					|| (Integer.parseInt(sectionTwoRange[0]) >= Integer.parseInt(sectionOneRange[0])
							&& Integer.parseInt(sectionTwoRange[1]) <= Integer.parseInt(sectionOneRange[1]))) {
				overlappingSectionsCount++
			}

		}
		return overlappingSectionsCount
	}

	fun part2(input: List<String>): Int {
		var overlappingSectionsCount = 0
		for (str in input) {
			println(str)
			val elvesPair = str.split(",")
			val sectionOneRange = elvesPair[0].split("-")
			val sectionTwoRange = elvesPair[1].split("-")

			if ((Integer.parseInt(sectionOneRange[0]) >= Integer.parseInt(sectionTwoRange[0])
							&& Integer.parseInt(sectionOneRange[0]) <= Integer.parseInt(sectionTwoRange[1]))
					|| (Integer.parseInt(sectionOneRange[1]) <= Integer.parseInt(sectionTwoRange[1])
							&& Integer.parseInt(sectionOneRange[1]) >= Integer.parseInt(sectionTwoRange[0]))
					|| (Integer.parseInt(sectionTwoRange[0]) >= Integer.parseInt(sectionOneRange[0])
							&& Integer.parseInt(sectionTwoRange[0]) <= Integer.parseInt(sectionOneRange[1]))
					|| (Integer.parseInt(sectionTwoRange[1]) <= Integer.parseInt(sectionOneRange[1])
							&& Integer.parseInt(sectionTwoRange[1]) >= Integer.parseInt(sectionOneRange[0]))) {
				overlappingSectionsCount++
				println("Sections are overlapping. Count $overlappingSectionsCount")
			}

		}
		return overlappingSectionsCount
	}

	val testInput = readInput("day/_4/Day04_test")
	check(part1(testInput) == 2)
	check(part2(testInput) == 4)

	val input = readInput("day/_4/Day04")
	println(part1(input))
	println(part2(input))

}
