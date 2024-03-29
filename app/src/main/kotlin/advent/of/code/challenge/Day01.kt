package advent.of.code.challenge

import advent.of.code.AdventOfCode
import advent.of.code.TopNKeeper

class Day01 : AdventOfCode() {
  override val day: Int = 1

  override fun partOne() {
    var currentElf = 0
    var maxElf = 0
    readInput().forEachLine {
      if (it.isEmpty()) {
        currentElf = 0
      } else {
        currentElf += it.toInt()
      }
      maxElf = Integer.max(maxElf, currentElf)
    }
    println("highest calorie elf has $maxElf calories")
  }

  override fun partTwo() {
    var currentElf = 0
    val maxElves = TopNKeeper(3)
    readInput().forEachLine {
      if (it.isEmpty()) {
        maxElves.consider(currentElf)
        currentElf = 0
      } else {
        currentElf += it.toInt()
      }
    }
    maxElves.consider(currentElf) // to make sure we consider the last line
    println("3 highest calorie elves have ${maxElves.vals.sum()} calories")
  }
}