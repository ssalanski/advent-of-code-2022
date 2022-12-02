package advent.of.code

class Day01 : AdventOfCode {
  override fun partOne(runOnExample: Boolean) {
    val filename = if (runOnExample) "example.txt" else "input.txt"
    var currentElf = 0
    var maxElf = 0
    readInput("/day01/$filename").forEachLine {
      if (it.isEmpty()) {
        currentElf = 0
      } else {
        currentElf += it.toInt()
      }
      maxElf = Integer.max(maxElf, currentElf)
    }
    println("highest calorie elf has $maxElf calories")
  }

  override fun partTwo(runOnExample: Boolean) {
    val filename = if (runOnExample) "example.txt" else "input.txt"
    var currentElf = 0
    val maxElves = TopNKeeper(3)
    readInput("/day01/$filename").forEachLine {
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

class TopNKeeper(val n: Int) {
  val vals = mutableListOf(0, 0, 0)
  fun consider(x: Int) {
    if (vals.any { x > it }) {
      vals[0] = x
      vals.sort()
    }
  }
}
