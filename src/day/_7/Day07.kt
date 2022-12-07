package day._7

import java.io.File

fun main() {

	data class File(val name: String, val size: Long) {

	}

	data class Directory(
		val name: String,
		val parentDirectory: Directory?
	) {
		var totalSize: Long? = null
		var nestedDirectories: MutableSet<Directory> = mutableSetOf()
		var files: MutableSet<File> = mutableSetOf()

	}

	lateinit var root: Directory

	fun readStructure(input: String) {
		lateinit var currentDir: Directory
		val instructions = input.split("$").mapNotNull { if (it.isBlank()) null else it.trim() }
		for (instruction in instructions) {
			val instructionLines = instruction.lines().mapNotNull { if (it.isBlank()) null else it.trim() }
			val command = instructionLines[0]
			if (command.startsWith("cd")) {
				val commandParts = command.split(" ")
				if (commandParts[1] == "/") {
					root = Directory(name = "root", parentDirectory = null)
					currentDir = root
				} else if (commandParts[1] == "..") {
					currentDir = currentDir.parentDirectory ?: throw RuntimeException("No parent directory. I can go to.")
				} else {
					if (!currentDir.nestedDirectories.map { it.name }.contains(commandParts[1])) {
						throw RuntimeException("It happened !!! Directory ${commandParts[1]} does not exist in nested directories")
					}
					currentDir = currentDir.nestedDirectories.single { it.name == commandParts[1] }
				}

			} else if (command == "ls") {
				for (index in 1 until instructionLines.size) {
					val outputParts = instructionLines[index].split(" ")
					if (outputParts[0] == "dir") {
						currentDir.nestedDirectories.add(Directory(name = outputParts[1], parentDirectory = currentDir))
					} else {
						currentDir.files.add(File(name = outputParts[1], size = outputParts[0].toLong()))
					}
				}

			} else {
				throw RuntimeException("Unknown command")
			}

		}

	}

	fun findDirectorySize(root: Directory): Long {
		val nestedDirectoriesTotalSize = if (root.nestedDirectories.isEmpty()) 0 else
			root.nestedDirectories.mapNotNull { if (it.totalSize == null) findDirectorySize(it) else it.totalSize }.sum()

		val filesTotalSize = root.files.sumOf { it.size }
		root.totalSize = nestedDirectoriesTotalSize + filesTotalSize

		return root.totalSize!!
	}

	fun getFlatDirectories(root: Directory, directories: List<Directory>): List<Directory> {
		return if (root.nestedDirectories.isEmpty()) mutableListOf(root) else {
			val mutableDirectories = mutableListOf<Directory>()
			mutableDirectories.addAll(directories)
			mutableDirectories.add(root)
			mutableDirectories.addAll(root.nestedDirectories.map {
				getFlatDirectories(
					it,
					directories
				)
			}.flatten())
			mutableDirectories
		}
	}

	fun part1(root: Directory): Long {
		return getFlatDirectories(root, mutableListOf()).map {
			it.totalSize ?: throw RuntimeException("Total Size is null")
		}
			.filter { it < 100000L }
			.sum()
	}

	fun part2(root: Directory): Long {
		return getFlatDirectories(root, mutableListOf()).map {
			it.totalSize ?: throw RuntimeException("Total Size is null")
		}
			.filter { it > 30000000L - (70000000L - root.totalSize!!) }
			.min()
	}

	val testInput = File("src/day", "_7/Day07_test.txt").readText()
	readStructure(testInput)
	findDirectorySize(root)
	check(part1(root) == 95437L)
	check(part2(root) == 24933642L)

	val input = File("src/day", "_7/Day07.txt").readText()
	readStructure(input)
	findDirectorySize(root)
	println(part1(root))
	println(part2(root))

}