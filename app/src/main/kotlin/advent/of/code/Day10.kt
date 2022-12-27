package advent.of.code

class Day10(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 10

  override fun partOne() {
    val signalStrengths = mutableListOf<Int>()
    var register = 1
    var cycle = 0
    readInput().forEachLine { line ->
      line.trim().split(' ').let {
        if (it[0] == "addx") {
          register += it[1].toInt()
          cycle += 2
        } else {
          cycle += 1
        }
      }
      if ((cycle) % 40 == 20) {
        signalStrengths.add(cycle * register)
      }
      println("$cycle, $register")
    }
    val sumOfSignalStrengths = signalStrengths.take(6).sum()
    println(signalStrengths)
    println("sum of signal strengths = $sumOfSignalStrengths")
  }

  override fun partTwo() {
    println("???")
  }

}
