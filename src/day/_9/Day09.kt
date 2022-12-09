package day._9

import java.io.File

fun main() {

	fun findNewPosition(currentPosition: Pair<Int, Int>, direction: String, stepSize: Int = 1): Pair<Int, Int> {
		return when (direction) {
			"R" -> Pair(currentPosition.first + stepSize, currentPosition.second)
			"L" -> Pair(currentPosition.first - stepSize, currentPosition.second)
			"U" -> Pair(currentPosition.first, currentPosition.second + stepSize)
			"D" -> Pair(currentPosition.first, currentPosition.second - stepSize)
			else -> throw IllegalArgumentException("Direction invalid $direction")
		}

	}

	fun addPositions(
		currentHeadPosition: Pair<Int, Int>,
		direction: String,
		moveSize: Int
	): MutableList<Pair<Int, Int>> {
		val positions = mutableListOf<Pair<Int, Int>>()
		for (step in 0 until moveSize) {
			positions.add(findNewPosition(if (positions.isEmpty()) currentHeadPosition else positions.last(), direction))
		}

		return positions
	}

	fun findTailPositions(headPositions: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
		val tailPositions = mutableListOf(Pair(0, 0))

		for (pair in headPositions) {
			if (tailPositions.last().first - pair.first > 1) {
				tailPositions.add(Pair(tailPositions.last().first - 1, pair.second))
			} else if (tailPositions.last().first - pair.first < -1) {
				tailPositions.add(Pair(tailPositions.last().first + 1, pair.second))
			}

			if (tailPositions.last().second - pair.second > 1) {
				tailPositions.add(Pair(pair.first, tailPositions.last().second - 1))
			} else if (tailPositions.last().second - pair.second < -1) {
				tailPositions.add(Pair(pair.first, tailPositions.last().second + 1))
			}

		}

		return tailPositions
	}

	fun part1(input: List<String>): Int {
		var headPositions = mutableListOf<Pair<Int, Int>>()
		headPositions.add(Pair(0, 0))
		for (str in input) {
			val ropeHeadMovementParts = str.split(" ").map { it.trim() }

			headPositions.addAll(
				addPositions(
					headPositions.last(),
					ropeHeadMovementParts[0],
					ropeHeadMovementParts[1].toInt()
				)
			)

		}

		return findTailPositions(headPositions).toSet().size
	}

	fun part2(input: List<String>): Int {
		return input.size
	}

	val testInput = File("src/day", "_9/Day09_test.txt").readLines()
	check(part1(testInput) == 13)
//	check(part2(testInput) == 0)

	val input = File("src/day", "_9/Day09.txt").readLines()
	println(part1(input))
	println(part2(input))

}