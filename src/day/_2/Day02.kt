package day._2

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        // opponent figure | your figure
        // A - Rock | Y - Paper
        // B - Paper | X - Rock
        // C - Scissors | Z - Scissors
        var totalScore = 0
        for (str in input) {
            var round = str.split(" ")
            var roundScore = 0
            if (round[1] == "X") {
                roundScore += 1
                if (round[0] == "A") {
                    roundScore += 3
                } else if (round[0] == "B") {
                    roundScore += 0
                } else if (round[0] == "C") {
                    roundScore += 6
                }
            } else if (round[1] == "Y") {
                roundScore += 2
                if (round[0] == "A") {
                    roundScore += 6
                } else if (round[0] == "B") {
                    roundScore += 3
                } else if (round[0] == "C") {
                    roundScore += 0
                }
            } else if (round[1] == "Z") {
                roundScore += 3
                if (round[0] == "A") {
                    roundScore += 0
                } else if (round[0] == "B") {
                    roundScore += 6
                } else if (round[0] == "C") {
                    roundScore += 3
                }
            }
            totalScore += roundScore
        }
        return totalScore
    }

    fun part2(input: List<String>): Int {
        // X - lose
        // Y - draw
        // z - win
        var totalScore = 0
        for (str in input) {
            var round = str.split(" ")
            var roundScore = 0
            if (round[1] == "X") {
                roundScore += 0
                if (round[0] == "A") {
                    roundScore += 3
                } else if (round[0] == "B") {
                    roundScore += 1
                } else if (round[0] == "C") {
                    roundScore += 2
                }
            } else if (round[1] == "Y") {
                roundScore += 3
                if (round[0] == "A") {
                    roundScore += 1
                } else if (round[0] == "B") {
                    roundScore += 2
                } else if (round[0] == "C") {
                    roundScore += 3
                }
            } else if (round[1] == "Z") {
                roundScore += 6
                if (round[0] == "A") {
                    roundScore += 2
                } else if (round[0] == "B") {
                    roundScore += 3
                } else if (round[0] == "C") {
                    roundScore += 1
                }
            }
            totalScore += roundScore
        }
        return totalScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day/_2/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day/_2/Day02")
    println(part1(input))
    println(part2(input))
}
