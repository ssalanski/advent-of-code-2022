package advent.of.code

class Day04(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 4

  override fun partOne() {
    val fullyContainedPairs = readInput().lineSequence().map { line ->
      line.split(',').map { assignmentRange ->
        assignmentRange.split('-').let {
          it[0].toInt()..it[1].toInt()
        }
      }.let {
        it[0] to it[1]
      }
    }
      .count { (elf1, elf2) ->
        elf1.within(elf2) || elf2.within(elf1)
      }
    println("fully contained pairs: $fullyContainedPairs")
  }

  override fun partTwo() {
    val overlappingPairs = readInput().lineSequence().map { line ->
      line.split(',').map { assignmentRange ->
        assignmentRange.split('-').let {
          it[0].toInt()..it[1].toInt()
        }
      }.let {
        it[0] to it[1]
      }
    }
      .count { (elf1, elf2) ->
        elf1.overlaps(elf2)
      }
    println("fully contained pairs: $overlappingPairs")
  }
}