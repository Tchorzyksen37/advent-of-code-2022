package day._6

import readInput

fun main() {

	fun lookUpStartMarker(signal: String, startMarkerLength: Int): Int {
		for (idx in startMarkerLength..signal.length) {
			val subStringAsSet = signal.substring(startIndex = idx - startMarkerLength, endIndex = idx)
				.split("")
				.mapNotNull { it.ifBlank { null } }
				.toSet()
			if (subStringAsSet.size < startMarkerLength) continue
			return idx
		}
		return -1
	}

	fun part1(input: List<String>): Int {
		return lookUpStartMarker(input.first(), 4)
	}

	fun part2(input: List<String>): Int {
		return lookUpStartMarker(input.first(), 14)
	}

	val testInput = readInput("day/_6/Day06_test")
	check(part1(testInput) == 5)
	check(part2(testInput) == 23)

	val input = readInput("day/_6/Day06")
	println(part1(input))
	println(part2(input))

}
