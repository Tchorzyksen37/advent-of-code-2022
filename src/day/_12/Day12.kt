package day._12

import readInput

class Day12(private val nodes: List<List<Node>>) {

	private val bounds = Bounds(nodes.first().indices, nodes.indices)

	private val end = findNode { it.state == State.END }

	fun findShortestPath(isDestination: (Node) -> Boolean): Int {
		val frontier: ArrayDeque<Step> = ArrayDeque(listOf(Step(end, 0)))
		val reached = mutableSetOf(end)

		while (frontier.isNotEmpty()) {
			val current = frontier.removeFirst()
			if (isDestination(current.node)) return current.distance

			current.node.coordinates.adjacent
				.filter { bounds.contains(it) }
				.map { nodes[it] }
				.filter { current.node.height - 1 <= it.height }
				.filter { it !in reached }
				.forEach {
					frontier.add(current nextStep it)
					reached.add(it)
				}

		}

		throw IllegalStateException("Could not find a path that meets the criteria.")
	}

	private fun findNode(predicate: (Node) -> Boolean): Node = nodes.flatten().first(predicate)

	private operator fun <T> List<List<T>>.get(coordinates: Coordinates) = this[coordinates.y][coordinates.x]

	private class Bounds(private val eastWestBounds: IntRange, private val northSouthBounds: IntRange) {
		operator fun contains(coordinates: Coordinates): Boolean =
			coordinates.x in eastWestBounds && coordinates.y in northSouthBounds
	}

	companion object {
		fun parse(input: List<String>): List<List<Node>> = input.mapIndexed { index, s ->
			s.mapIndexed { strIndex, char ->
				Node.from(Coordinates(strIndex, index), char)
			}
		}
	}
}

data class Step(val node: Node, val distance: Int) {
	infix fun nextStep(node: Node) = Step(node, distance + 1)

}

data class Coordinates(val x: Int, val y: Int) {
	val adjacent: List<Coordinates> by lazy {
		listOf(Coordinates(x - 1, y), Coordinates(x + 1, y), Coordinates(x, y - 1), Coordinates(x, y + 1))
	}
}

data class Node(val coordinates: Coordinates, val height: Int, val state: State) {
	companion object {
		fun from(coordinates: Coordinates, char: Char): Node {
			val height = when (char) {
				'S' -> 0; 'E' -> 'z' - 'a'; else -> char - 'a'
			}
			return Node(coordinates, height, State.from(char))
		}
	}
}

enum class State {
	START, INTERMEDIATE, END;

	companion object {
		fun from(char: Char): State = when (char) {
			'S' -> START;'E' -> END;else -> INTERMEDIATE
		}
	}

}

fun main() {

	val testNodes = Day12.parse(readInput("day/_12/Day12_test"))
	check(Day12(testNodes).findShortestPath { it.state == State.START } == 31)
	check(Day12(testNodes).findShortestPath { it.height == 0 } == 29)


	val nodes = Day12.parse(readInput("day/_12/Day12"))
	println(Day12(nodes).findShortestPath { it.state == State.START })
	println(Day12(nodes).findShortestPath { it.height == 0 })

}