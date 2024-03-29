package advent.of.code.challenge

import advent.of.code.AdventOfCode

class Day06 : AdventOfCode() {
  override val day: Int = 6

  private fun findFirstWindowOfDistinctCharacters(stream: Iterable<Char>, size: Int): Int {
    return size + stream.windowed(size, 1, false).indexOfFirst {
      it.distinct().count() == size
    }
  }

  override fun partOne() {
    readInput().forEachLine { line ->
      val marker = findFirstWindowOfDistinctCharacters(line.asIterable(), 4)
      println("four-character marker position: $marker")
    }
  }

  override fun partTwo() {
    readInput().forEachLine { line ->
      val marker = findFirstWindowOfDistinctCharacters(line.asIterable(), 14)
      println("fourteen-character marker position: $marker")
    }
  }

}