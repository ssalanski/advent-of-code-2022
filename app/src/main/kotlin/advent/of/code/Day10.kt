package advent.of.code

class Day10(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 10

  private fun registerGenerator() = sequence {
    yield(0)
    var register = 1
    readInput().lineSequence().forEach { line ->
      line.trim().split(' ').let {
        if (it[0] == "addx") {
          yield(register)
          yield(register)
          register += it[1].toInt()
        } else {
          yield(register)
        }
      }
    }
  }

  override fun partOne() {
    val sumOfSignalStrengths =
      registerGenerator().withIndex()
        .filter { it.index % 40 == 20 }
        .map { it.index * it.value }
        .take(6)
        .sum()
    println("sum of signal strengths = $sumOfSignalStrengths")
  }

  override fun partTwo() {
    registerGenerator().windowed(40,40).forEach {row ->
      println(row.joinToString(",") { "%02d".format(it) })
    }
    registerGenerator().windowed(40,40).forEach { row ->
      println(row.mapIndexed { index, i -> if ((index-1).withinOne(i)) "#" else "." }.joinToString(""))

    }
  }

}

private fun Int.withinOne(i: Int): Boolean {
  return this == i || this == i+1 || this == i-1
}
