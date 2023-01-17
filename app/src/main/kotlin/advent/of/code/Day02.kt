package advent.of.code

class Day02 : AdventOfCode() {
  override val day: Int = 2

  override fun partOne() {
    val score = readInput().lineSequence().map { line ->
      line.split(' ').let { it[0][0].toRPS() to it[1][0].toRPS() }
    }.sumOf { (them, us) ->
      us.scoreForPlaying + us.outcomeAgainst(them).score
    }
    println("total score according to strategy guide: $score")
  }

  override fun partTwo() {
    val score = readInput().lineSequence().map { line ->
      line.split(' ').let { it[0][0].toRPS() to it[1][0].toWLT() }
    }.sumOf { (them, outcome) ->
      RPS.values().first { it.outcomeAgainst(them) == outcome }.scoreForPlaying + outcome.score
    }
    println("total score according to correctly read strategy guide: $score")
  }

  private fun Char.toRPS(): RPS = when (this) {
    'A' -> RPS.Rock
    'B' -> RPS.Paper
    'C' -> RPS.Scissors
    'X' -> RPS.Rock
    'Y' -> RPS.Paper
    'Z' -> RPS.Scissors
    else -> throw IllegalArgumentException("can only decode ABC/XYZ, got $this")
  }

  private fun Char.toWLT(): WLT = when (this) {
    'X' -> WLT.Loss
    'Y' -> WLT.Tie
    'Z' -> WLT.Win
    else -> throw IllegalArgumentException("can only decode XYZ, got $this")
  }

  enum class WLT {
    Loss, Tie, Win;

    val score: Int = ordinal * 3
  }

  enum class RPS {
    Rock, Paper, Scissors;

    val scoreForPlaying: Int = ordinal + 1

    fun outcomeAgainst(other: RPS): WLT =
      if (this == other) WLT.Tie else
        when (this) {
          Rock -> if (other == Scissors) WLT.Win else WLT.Loss
          Paper -> if (other == Rock) WLT.Win else WLT.Loss
          Scissors -> if (other == Paper) WLT.Win else WLT.Loss
        }
  }
}
