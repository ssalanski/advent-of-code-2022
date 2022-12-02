package advent.of.code

class Day02(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 2

  override fun partOne() {
    val score = readInput().lineSequence().map { line ->
      line.split(' ').let { it[0][0].toRPS() to it[1][0].toRPS() }
    }.sumOf { (them, us) ->
      us.scoreForPlaying() + us.scoreAgainst(them)
    }
    println("total score according to strategy guide: $score")
  }

  override fun partTwo() {
  }
}

fun Char.toRPS(): RPS = when (this) {
  'A' -> RPS.Rock
  'B' -> RPS.Paper
  'C' -> RPS.Scissors
  'X' -> RPS.Rock
  'Y' -> RPS.Paper
  'Z' -> RPS.Scissors
  else -> throw IllegalArgumentException("can only decode ABC/XYZ")
}

enum class RPS {
  Rock, Paper, Scissors;

  fun scoreForPlaying(): Int =
    when (this) {
      Rock -> 1
      Paper -> 2
      Scissors -> 3
    }

  fun scoreAgainst(other: RPS): Int =
    if (this == other) 3 else
      when (this) {
        Rock -> if (other == Scissors) 6 else 0
        Paper -> if (other == Rock) 6 else 0
        Scissors -> if (other == Paper) 6 else 0
      }
}