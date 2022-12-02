package day._1

import readInput
import java.util.stream.Collectors

fun main() {
    fun getTotalCaloriesCarriedSortedList(input: List<String>): List<Int> {
        var caloriesSum = 0
        val caloriesSums = mutableListOf<Int>()
        for (str in input) {
            if (str.isBlank()) {
                caloriesSums.add(caloriesSum)
                caloriesSum = 0
            } else {
                caloriesSum += str.toInt()
            }
        }
        if (caloriesSum != 0) {
            caloriesSums.add(caloriesSum)
        }

        return caloriesSums.stream().sorted().collect(Collectors.toList())
    }

    fun part1(input: List<String>): Int {
        val totalCaloriesCarriedList = getTotalCaloriesCarriedSortedList(input)
        return totalCaloriesCarriedList.last()
    }

    fun part2(input: List<String>): Int {
        val totalCaloriesCarriedList = getTotalCaloriesCarriedSortedList(input)
        val totalCaloriesCarriedListSize = totalCaloriesCarriedList.size
        return totalCaloriesCarriedList[totalCaloriesCarriedListSize - 1] + totalCaloriesCarriedList[totalCaloriesCarriedListSize - 2] + totalCaloriesCarriedList[totalCaloriesCarriedListSize - 3]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day/_1/Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("day/_1/Day01")
    println(part1(input))
    println(part2(input))
}
