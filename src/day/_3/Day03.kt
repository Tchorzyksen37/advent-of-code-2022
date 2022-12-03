package day._3

import readInput
import java.util.stream.Collectors

fun main() {

    fun stringToCharacterList(str: String): List<String> {
        return str.chars().mapToObj(Character::toString).collect(Collectors.toList())
    }

    fun mapElementToIntValue(element: String): Int {
        return if (element[0].isLowerCase()) (element.codePointAt(0) - 96) else (element.codePointAt(0) - 38)

    }

    fun part1(input: List<String>): Int {
        var theSumOfPriorities = 0
        for (str in input) {
            val firstElementCompartmentOne = stringToCharacterList(str.subSequence(0, str.length / 2).toString())
            val firstElementCompartmentTwo = stringToCharacterList(str.subSequence(str.length / 2, str.length).toString())
            val duplicate = firstElementCompartmentOne.stream().filter { ch -> firstElementCompartmentTwo.contains(ch) }.findFirst()
            if (duplicate.isPresent) {
                theSumOfPriorities += mapElementToIntValue(duplicate.get())
            }
        }

        return theSumOfPriorities
    }

    fun mapToGroups(input: List<String>): List<List<String>> {
        val groups = mutableListOf<List<String>>()
        var group = mutableListOf<String>()
        for ((index, value) in input.withIndex()) {
            group.add(value)

            if (index % 3 == 2) {
                groups.add(group)
                group = mutableListOf()
            }
        }

        return groups
    }

    fun part2(input: List<String>): Int {
        val groups = mapToGroups(input)
        var badgesSum = 0
        for (group in groups) {
            val firstCrewMember = stringToCharacterList(group[0])
            val secondCrewMember = stringToCharacterList(group[1])
            val thirdCrewMember = stringToCharacterList(group[2])

            val duplicate = firstCrewMember.stream().filter { ch -> secondCrewMember.contains(ch) }.filter { ch -> thirdCrewMember.contains(ch) }.findFirst()
            if (duplicate.isPresent) {
                badgesSum += mapElementToIntValue(duplicate.get())
            }

        }
        return badgesSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day/_3/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day/_3/Day03")
    println(part1(input))
    println(part2(input))

}
