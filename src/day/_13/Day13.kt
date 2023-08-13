package day._13

import java.io.File

data class Node(
	val numbers: MutableList<Int>,
	val children: MutableList<Node>,
)

class Parser {

	enum class Syntax(val char: Char) {
		LEFT_SQUARE_BRACKET('['),
		RIGHT_SQUARE_BRACKET(']'),
		COMMA(',')

	}

	private val syntax = Syntax.values().map { it.char }

	fun parse(input: String): Node {
		val tokenList = lex(input)

		var currentNode: Node? = null
		val parents: ArrayDeque<Node> = ArrayDeque()

		for (token in tokenList) {
			if (token == "${Syntax.LEFT_SQUARE_BRACKET.char}") {
				currentNode = if (currentNode == null) {
					Node(mutableListOf(), mutableListOf())
				} else {
					val child = Node(mutableListOf(), mutableListOf())
					currentNode.children.add(child)
					parents.add(currentNode)
					child
				}
			} else if (token == "${Syntax.RIGHT_SQUARE_BRACKET.char}") {
				if (parents.isEmpty()) {
					break
				} else {
					currentNode = parents.last()
					parents.removeLast()
				}
			} else if (token != "${Syntax.COMMA.char}") {
				currentNode!!.numbers.add(token.toInt())
			}

		}

		return currentNode!!

	}

	fun parseV2(input: String): ArrayDeque<Any> {
		val tokenList = lex(input)

		var currentNode: ArrayDeque<Any> = ArrayDeque()
		val parents: ArrayDeque<ArrayDeque<Any>> = ArrayDeque()
		var isOuterMost = true
		for (token in tokenList) {
			if (token == "${Syntax.LEFT_SQUARE_BRACKET.char}") {
				val newNode = ArrayDeque<Any>()
				if (!isOuterMost) {
					currentNode.addLast(newNode)
					parents.addLast(currentNode)
				}
				isOuterMost = false
				currentNode = newNode
			} else if (token == "${Syntax.RIGHT_SQUARE_BRACKET.char}") {
				if (parents.isNotEmpty()) {
					currentNode = parents.removeLast()
				}
			} else if (token != "${Syntax.COMMA.char}") {
				currentNode.addLast(token.toInt())
			}

		}

		return currentNode

	}

	private fun lex(input: String): MutableList<String> {
		val tokenList = mutableListOf<String>()
		var num = ""
		for (index in input.indices) {
			if (input[index] in syntax) {
				if (num.isNotEmpty()) {
					tokenList.add(num)
					num = ""
				}
				tokenList.add("${input[index]}")
			} else if (input[index].isDigit()) {
				num = num.plus(input[index])
			} else {
				throw RuntimeException("Lexical error '${input[index]}' token unknown")
			}
		}
		return tokenList
	}

}

fun main() {

	val dividerPackets: List<String> = listOf("[[2]]", "[[6]]")

	fun compare(inputPair: Pair<ArrayDeque<*>, ArrayDeque<*>>): Int {
		val limit = inputPair.first.size.coerceAtMost(inputPair.second.size)
		for (index in 0 until limit) {
			val left = inputPair.first[index]
			val right = inputPair.second[index]

			if (left is Int && right is Int) {
				return if (left == right) continue else if (left < right) -1 else 1
			}

			if (left is ArrayDeque<*> && right is Int) {
				val result = compare(Pair(left, ArrayDeque<Any>(listOf(right))))
				return if (result == 0) continue else result
			}

			if (left is Int && right is ArrayDeque<*>) {
				val result = compare(Pair(ArrayDeque<Any>(listOf(left)), right))
				return if (result == 0) continue else result
			}

			if (left is ArrayDeque<*> && right is ArrayDeque<*>) {
				val result = compare(Pair(left, right))
				return if (result == 0) continue else result
			}

		}

		return if (inputPair.first.size == inputPair.second.size) 0 else if (inputPair.first.size < inputPair.second.size) -1 else 1
	}

	fun part1(packages: List<Pair<ArrayDeque<*>, ArrayDeque<*>>>): Int {
		var answer = 0
		for (index in packages.indices) {
			answer += if (compare(packages[index]) == -1) index + 1 else 0
		}
		return answer
	}

	fun part2(packages: List<ArrayDeque<*>>): Int {
		val indexFirst = packages.indexOf(Parser().parseV2(dividerPackets[0])) + 1
		val indexSecond = packages.indexOf(Parser().parseV2(dividerPackets[1])) + 1

		return indexFirst * indexSecond
	}

	val testInput = File("src/day/_13/Day13_test.txt")

	val testInputPart1 = testInput
		.readText()
		.split("\n\n")
		.map { it.split("\n") }
		.map { Pair(Parser().parseV2(it[0]), Parser().parseV2(it[1])) }
	check(part1(testInputPart1) == 13)

	val testInput2 = (testInput.readLines() + dividerPackets)
		.filter(String::isNotBlank)
		.map { Parser().parseV2(it) }
		.sortedWith { o1, o2 -> compare(Pair(o1, o2)) }
	check(part2(testInput2) == 140)

	val input = File("src/day/_13/Day13.txt")

	val input1 = input.readText()
		.split("\n\n")
		.map { it.split("\n") }
		.map { Pair(Parser().parseV2(it[0]), Parser().parseV2(it[1])) }

	println("Part 1 answer is ${part1(input1)}")

	val input2 = (input.readLines() + dividerPackets)
		.filter(String::isNotBlank)
		.map { Parser().parseV2(it) }
		.sortedWith { o1, o2 -> compare(Pair(o1, o2)) }

	println("Part 2 answer is ${part2(input2)}")

}
