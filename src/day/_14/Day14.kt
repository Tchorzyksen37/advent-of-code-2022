package day._14

import readInput
import kotlin.math.max
import kotlin.math.min

class Cave(
	private val caveMap: MutableList<MutableList<Char>>,
	val xDeviation: Int,
	val yDeviation: Int,
	private val xRange: Pair<Int, Int>,
	private val yRange: Pair<Int, Int>
) {

	fun isWithinRange(x: Int, y: Int): Boolean {
		return x in xRange.first..xRange.second
				&& y in yRange.first..yRange.second
	}

	fun get(x: Int, y: Int): Char {
		return caveMap[y][x]
	}

	fun plotRocks(inputData: List<List<Pair<Int, Int>>>) {
		createRangesFromInputLine(inputData).forEach { rangeUpdateCaveMap(it.first, it.second) }
	}

	companion object {
		private var maxX = Int.MIN_VALUE
		private var minX = Int.MAX_VALUE
		private var maxY = Int.MIN_VALUE
		private var minY = Int.MAX_VALUE

		fun createCavePlot(testInput: List<List<Pair<Int, Int>>>): Cave {

			for (pair in testInput.flatten() + listOf(Pair(500, 0))) {
				maxX = max(maxX, pair.first)
				minX = min(minX, pair.first)
				maxY = max(maxY, pair.second)
				minY = min(minY, pair.second)
			}

			val xUpperbound = maxX - minX
			val yUpperbound = maxY - minY

			val caveMap = MutableList(yUpperbound + 1) { _ -> MutableList(xUpperbound + 1) { _ -> '.' } }
			val cave = Cave(caveMap, minX, minY, Pair(0, xUpperbound), Pair(0, yUpperbound))
			cave.plotRocks(testInput)
			return cave
		}

		fun createCavePlot(testInput: List<List<Pair<Int, Int>>>, plusFrame: Int): Cave {

			for (pair in testInput.flatten() + listOf(Pair(500, 0), Pair(0, 0), Pair(1000, 0))) {
				maxX = max(maxX, pair.first)
				minX = min(minX, pair.first)
				maxY = max(maxY, pair.second)
				minY = min(minY, pair.second)
			}

			val xUpperbound = maxX - minX
			val yUpperbound = maxY - minY + 1

			val caveMap =
				MutableList(yUpperbound + 1) { _ -> MutableList(xUpperbound + 1) { _ -> '.' } }
			val cave = Cave(caveMap, minX, minY, Pair(0, xUpperbound), Pair(0, yUpperbound))
			cave.plotRocks(testInput)
			return cave
		}

	}

	private fun rangeUpdateCaveMap(rangeX: Pair<Int, Int>, rangeY: Pair<Int, Int>) {
		if (rangeX.isEqual()) {
			for (index in rangeY.min()..rangeY.max()) {
				updateCaveMap(rangeX.first - xDeviation, index - yDeviation, '#')
			}
		}

		if (rangeY.isEqual()) {
			for (index in rangeX.min()..rangeX.max()) {
				updateCaveMap(index - xDeviation, rangeY.first - yDeviation, '#')
			}
		}
	}

	private fun Pair<Int, Int>.isEqual(): Boolean {
		return this.first == this.second
	}

	private fun Pair<Int, Int>.min(): Int {
		return min(this.first, this.second)
	}

	private fun Pair<Int, Int>.max(): Int {
		return max(this.first, this.second)
	}

	fun updateCaveMap(x: Int, y: Int, sign: Char) {
		caveMap[y][x] = sign
	}

	private fun createRangesFromInputLine(inputData: List<List<Pair<Int, Int>>>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
		return inputData.map { inputDataLine -> pointsToRanges(inputDataLine) }.flatten()
	}

	private fun pointsToRanges(points: List<Pair<Int, Int>>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
		if (points.size < 2)
			throw RuntimeException("Cannot create range for less than two points.")

		val ranges = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
		for (index in 1 until points.size) {
			val previousPoint = points[index - 1]
			val currentPoint = points[index]
			ranges.add(createRange(previousPoint, currentPoint))
		}

		return ranges
	}

	private fun createRange(fromPoint: Pair<Int, Int>, toPoint: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
		return Pair(Pair(fromPoint.first, toPoint.first), Pair(fromPoint.second, toPoint.second))
	}

}

fun predictSandFall(cave: Cave, sandPouringPoint: Pair<Int, Int>) {
	var currentSandPosition = sandPouringPoint
	var isSandGrainSettled = false
	while (!isSandGrainSettled) {
		if (cave.get(currentSandPosition.first, currentSandPosition.second + 1) == '.') {
			currentSandPosition = Pair(currentSandPosition.first, currentSandPosition.second + 1)
			continue
		}

		if (cave.get(currentSandPosition.first - 1, currentSandPosition.second + 1) == '.') {
			currentSandPosition = Pair(currentSandPosition.first - 1, currentSandPosition.second + 1)
			continue
		}

		if (cave.get(currentSandPosition.first + 1, currentSandPosition.second + 1) == '.') {
			currentSandPosition = Pair(currentSandPosition.first + 1, currentSandPosition.second + 1)
			continue
		}

		cave.updateCaveMap(currentSandPosition.first, currentSandPosition.second, 'o')
		isSandGrainSettled = true
	}

}

fun predictSandFallV2(cave: Cave, sandPouringPoint: Pair<Int, Int>): Pair<Int, Int> {
	var currentSandPosition = sandPouringPoint
	var isSandGrainSettled = false
	while (!isSandGrainSettled) {
		if (cave.isWithinRange(currentSandPosition.first, currentSandPosition.second + 1)
			&& cave.get(currentSandPosition.first, currentSandPosition.second + 1) == '.'
		) {
			currentSandPosition = Pair(currentSandPosition.first, currentSandPosition.second + 1)
			continue
		}

		if (cave.isWithinRange(currentSandPosition.first - 1, currentSandPosition.second + 1)
			&& cave.get(currentSandPosition.first - 1, currentSandPosition.second + 1) == '.'
		) {
			currentSandPosition = Pair(currentSandPosition.first - 1, currentSandPosition.second + 1)
			continue
		}

		if (cave.isWithinRange(currentSandPosition.first + 1, currentSandPosition.second + 1)
			&& cave.get(currentSandPosition.first + 1, currentSandPosition.second + 1) == '.'
		) {
			currentSandPosition = Pair(currentSandPosition.first + 1, currentSandPosition.second + 1)
			continue
		}

		cave.updateCaveMap(currentSandPosition.first, currentSandPosition.second, 'o')
		isSandGrainSettled = true
	}

	return currentSandPosition
}

fun part1(cave: Cave): Int {
	val sandPouringPoint = Pair(500 - cave.xDeviation, 0)

	var isSandFlowingIntoTheAbyss = false
	var counter = 0
	while (!isSandFlowingIntoTheAbyss) {
		try {
			predictSandFall(cave, sandPouringPoint)
			counter++
		} catch (e: IndexOutOfBoundsException) {
			isSandFlowingIntoTheAbyss = true
		}
	}
	return counter
}

fun Pair<Int, Int>.equalTo(other: Pair<Int, Int>): Boolean {
	return this.first == other.first && this.second == other.second
}

fun part2(cave: Cave): Int {
	val sandPouringPoint = Pair(500 - cave.xDeviation, 0)
	var currentSandPosition = sandPouringPoint
	var counter = 0
	do {
		currentSandPosition = predictSandFallV2(cave, sandPouringPoint)
		counter++
	} while (!currentSandPosition.equalTo(sandPouringPoint))
	return counter
}

fun main() {

	val testInput = readInput("day/_14/Day14_test").map { inputLine ->
		inputLine.split("->")
			.map { it.split(",").map { str -> str.trim() } }
			.map { Pair(it[0].toInt(), it[1].toInt()) }
	}

	val testCavePartOne = Cave.createCavePlot(testInput)
	check(part1(testCavePartOne) == 24)
	val testCasePartTwo = Cave.createCavePlot(testInput, 2)
	check(part2(testCasePartTwo) == 93)

	val input = readInput("day/_14/Day14").map { inputLine ->
		inputLine.split("->")
			.map { it.split(",").map { str -> str.trim() } }
			.map { Pair(it[0].toInt(), it[1].toInt()) }
	}

	val cavePartOne = Cave.createCavePlot(input)
	println(part1(cavePartOne))
	val cavePartTwo = Cave.createCavePlot(input, 2)
	println(part2(cavePartTwo))

}
