package advent.of.code

class Day03(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 3

  override fun partOne() {
    val sumOfPriorities = readInput().lineSequence()
      .map { line ->
        val compartment1 = line.take(line.length / 2).toSet()
        val compartment2 = line.takeLast(line.length / 2).toSet()
        compartment1.intersect(compartment2).first()
      }.sumOf {
        priorityValue[it]!!
      }
    println("sum of shared item priorities = $sumOfPriorities")
  }

  override fun partTwo() {
    val sumOfPriorities = readInput().lineSequence()
      .chunked(3)
      .map { group ->
        group.map { it.toCharArray().toSet() }
          .reduce(Set<Char>::intersect)
          .first()
      }.sumOf {
        priorityValue[it]!!
      }
    println("sum of badge priorities = $sumOfPriorities")
  }
}

val priorityValue: Map<Char, Int> =
  CharRange('a', 'z').zip(IntRange(1, 26)).toMap() +
    CharRange('A', 'Z').zip(IntRange(27, 52)).toMap()