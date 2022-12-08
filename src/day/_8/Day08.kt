package day._8

import java.io.File

fun main() {

	fun part1(input: List<String>): Int {
		var treeCount = 2 * (input.size - 2) + input[0].length * 2
		for ((indexInput, str) in input.withIndex()) {
			if (indexInput == 0 || indexInput == input.size - 1) continue

			for (indexStr in 1 until str.length - 1) {

				// Check left
				val leftMax = str.substring(0, indexStr).max()
				if (leftMax < str[indexStr]) {
					treeCount++
					continue
				}

				// Check right
				val rightMax = str.substring(indexStr + 1).max()
				if (rightMax < str[indexStr]) {
					treeCount++
					continue
				}

				val verticalLine = input.map { it[indexStr] }.joinToString("")

				// Check top
				val topMax = verticalLine.substring(0, indexInput).max()
				if (topMax < str[indexStr]) {
					treeCount++
					continue
				}

				// Check bottom
				val botMax = verticalLine.substring(indexInput + 1).max()
				if (botMax < str[indexStr]) {
					treeCount++
					continue
				}

			}

		}

		return treeCount
	}

	fun part2(input: List<String>): Int {
		var maxTreeScenicScore = 0
		for ((indexInput, str) in input.withIndex()) {
			if (indexInput == 0 || indexInput == input.size - 1) continue

			for (indexStr in 1 until str.length - 1) {

				// Check left
				val leftSubstring = str.substring(0, indexStr).reversed()
				var treeBlockingViewLeftRange = 0
				for (element in leftSubstring) {
					if (str[indexStr] > element) {
						treeBlockingViewLeftRange++
						continue
					} else if (str[indexStr] <= element) {
						treeBlockingViewLeftRange++
					}
					break
				}

				// Check right
				val rightSubstring = str.substring(indexStr + 1)
				var treeBlockingViewRightRange = 0
				for (element in rightSubstring) {
					if (str[indexStr] > element) {
						treeBlockingViewRightRange++
						continue
					} else if (str[indexStr] <= element) {
						treeBlockingViewRightRange++
					}

					break
				}

				val verticalLine = input.map { it[indexStr] }.joinToString("")

				// Check top
				val topSubstring = verticalLine.substring(0, indexInput).reversed()
				var treeBlockingViewTopRange = 0
				for (element in topSubstring) {
					if (str[indexStr] > element) {
						treeBlockingViewTopRange++
						continue
					} else if (str[indexStr] <= element) {
						treeBlockingViewTopRange++
					}
					break
				}

				// Check bottom
				val botSubstring = verticalLine.substring(indexInput + 1)
				var treeBlockingViewBotRange = 0
				for (element in botSubstring) {
					if (str[indexStr] > element) {
						treeBlockingViewBotRange++
						continue
					} else if (str[indexStr] <= element) {
						treeBlockingViewBotRange++
					}
					break
				}

				val currentTreeScenicScore =
					treeBlockingViewLeftRange * treeBlockingViewRightRange * treeBlockingViewTopRange * treeBlockingViewBotRange
				if (maxTreeScenicScore < currentTreeScenicScore) maxTreeScenicScore = currentTreeScenicScore

			}

		}

		return maxTreeScenicScore
	}

	val testInput = File("src/day", "_8/Day08_test.txt").readLines()
	check(part1(testInput) == 21)
	check(part2(testInput) == 8)

	val input = File("src/day", "_8/Day08.txt").readLines()
	println(part1(input))
	println(part2(input))

}