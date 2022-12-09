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

	fun printMap(head: List<Pair<Int, Int>>, nodes: List<Pair<String, List<Pair<Int, Int>>>>) {

		for ((idx, pair) in head.withIndex()) {
			val idxAsStr = if (idx < 100) "$idx " else if (idx < 10) "$idx  " else idx
			println("<<--------------                         Movement no.$idxAsStr                         -------------->>\n")
			for (y in 30 downTo 0) {
				if (y < 10) print("\u001B[31m $y:\u001B[0m ") else print("\u001B[31m $y:\u001B[0m")
				for (x in 0..30) {
					if (pair == Pair(x, y)) {
						print(" \u001B[33mH\u001B[0m ")
					} else {
						val node =
							nodes.filter { node -> node.second.size > idx }
								.firstOrNull { node -> node.second[idx] == Pair(x, y) }
						if (node != null) {
							print(" \u001B[36m${node.first}\u001B[0m ")
						} else {
							print(" . ")
						}

					}
				}
				println()
			}
			println("\u001B[31m #   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30\u001B[0m ")
			println("\n\u001B[31m<===============================================================================================>\u001B[0m\n")
		}
	}

	fun findTailPositions(headPositions: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
		val tailPositions = mutableListOf<Pair<Int, Int>>()

		for (pair in headPositions) {
			if (pair == Pair(100, 100)) {
				tailPositions.add(Pair(100, 100))
			} else {
				if (tailPositions.last().first - pair.first > 1 && tailPositions.last().second - pair.second > 1) {
					tailPositions.add(Pair(tailPositions.last().first - 1, tailPositions.last().second - 1))
				} else if (tailPositions.last().first - pair.first < -1 && tailPositions.last().second - pair.second < -1) {
					tailPositions.add(Pair(tailPositions.last().first + 1, tailPositions.last().second + 1))
				} else if (tailPositions.last().first - pair.first > 1 && tailPositions.last().second - pair.second < -1) {
					tailPositions.add(Pair(tailPositions.last().first - 1, tailPositions.last().second + 1))
				} else if (tailPositions.last().first - pair.first < -1 && tailPositions.last().second - pair.second > 1) {
					tailPositions.add(Pair(tailPositions.last().first + 1, tailPositions.last().second - 1))
				} else if (tailPositions.last().first - pair.first > 1) {
					tailPositions.add(Pair(tailPositions.last().first - 1, pair.second))
				} else if (tailPositions.last().first - pair.first < -1) {
					tailPositions.add(Pair(tailPositions.last().first + 1, pair.second))
				} else if (tailPositions.last().second - pair.second > 1) {
					tailPositions.add(Pair(pair.first, tailPositions.last().second - 1))
				} else if (tailPositions.last().second - pair.second < -1) {
					tailPositions.add(Pair(pair.first, tailPositions.last().second + 1))
				} else {
					tailPositions.add(tailPositions.last())
				}
			}
		}

//		println("Tail visited positions   $tailPositions")
		return tailPositions
	}

	fun findHeadPositions(input: List<String>): List<Pair<Int, Int>> {
		val headPositions = mutableListOf<Pair<Int, Int>>()
		headPositions.add(Pair(100, 100))
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

		return headPositions
	}

	fun part1(input: List<String>): Int {
		val headPositions = findHeadPositions(input)
		return findTailPositions(headPositions).toSet().size
	}

	fun part2(input: List<String>): Int {
		val headPositions = findHeadPositions(input)
//		println("Head positioned visited  $headPositions")
		val nodes = mutableListOf<Pair<String, List<Pair<Int, Int>>>>()
		var node = headPositions
		for (knots in 0 until 9) {
			node = findTailPositions(node)
			nodes.add(Pair((knots + 1).toString(), node))
		}
//		printMap(headPositions, nodes)
//		println("${nodes.map { it.second }.flatten().toSet()}\n${nodes.map { it.second }.flatten().toSet().size}")
		var tail = nodes.filter { it.first.toInt() < 9 }.map { it.second }.flatten().toSet()
		var lastNode = nodes.last().second.toMutableSet()
		lastNode.removeAll(tail)
		lastNode.removeAll(headPositions)
		return lastNode.size

	}

	var testInput = File("src/day", "_9/Day09_part01_test.txt").readLines()
	check(part1(testInput) == 13)
	testInput = File("src/day", "_9/Day09_part02_test.txt").readLines()
	check(part2(testInput) == 36)

	val input = File("src/day", "_9/Day09.txt").readLines()
	println(part1(input))
	println(part2(input))

}