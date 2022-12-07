package advent.of.code

class Day06(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 6

  override fun partOne() {
    readInput().forEachLine { line ->
      val marker = 4 + line.asSequence().windowed(4, 1, false).indexOfFirst {
        it.distinct().count() == 4
      }
      println("four-character marker position: $marker")
    }
  }

  override fun partTwo() {
    println("???")
  }

}