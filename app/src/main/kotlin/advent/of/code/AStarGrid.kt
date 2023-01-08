package advent.of.code

import kotlin.math.abs


class AStarGrid(
  private val gridValues: List<List<Int>>,
  private val start: Pair<Int, Int>,
  private val goal: Pair<Int, Int>
) {
  val h = gridValues.size
  val w = gridValues[0].size

  init {
    require(start.first in 0 until w) { "start x coord out of bounds: ${start.first} ∉ [0,$w)" }
    require(start.second in 0 until h) { "start y coord out of bounds: ${start.second} ∉ [0,$h)" }
    require(goal.first in 0 until w) { "goal x coord out of bounds: ${goal.first} ∉ [0,$w)" }
    require(goal.second in 0 until h) { "goal y coord out of bounds: ${goal.second} ∉ [0,$h)" }
  }

  fun solve(): List<Pair<Int, Int>> {
    val known = mutableSetOf(start)
    val returnPath: MutableMap<Pair<Int, Int>, Pair<Int, Int>> = mutableMapOf()
    val score: MutableMap<Pair<Int, Int>, Int> =
      mutableMapOf(start to 0).withDefault { Int.MAX_VALUE shr 1 }
    val hScore: MutableMap<Pair<Int, Int>, Int> =
      mutableMapOf(start to start.taxiDistanceTo(goal)).withDefault { Int.MAX_VALUE shr 1 }

    while (known.isNotEmpty()) {
      var current = known.minBy { hScore.getValue(it) }
      if (current == goal) {
        return buildList {
          while (current != start) {
            add(current)
            current = returnPath[current]!!
          }
        }.asReversed()
      }
      known.remove(current)
      current.neighbors().inRange(w, h).filter { gridValues[it] <= gridValues[current] + 1 }
        .forEach { neighbor ->
          val gScore = score[current]!! + 1
          if (gScore < score.getValue(neighbor)) {
            returnPath[neighbor] = current
            score[neighbor] = gScore
            hScore[neighbor] = gScore + goal.taxiDistanceTo(neighbor)
            known.add(neighbor)
          }
        }

//      println("----------------------")
//      gridValues.forEachIndexed { y, row ->
//        row.forEachIndexed { x, v ->
//          when (x to y) {
//            start -> print("S")
//            goal -> print("G")
//            in known -> print("*")
//            else -> print("%02d".format(v))
//          }
//          print(",")
//        }
//        println()
//      }
    }
    throw Exception("A* algorithm unable to find any path from start to goal node")
  }

  override fun toString(): String {
    return ("$start to $goal\n") +
      gridValues.joinToString("\n") { it.joinToString(",") { "%02d".format(it) } }
  }
}

private operator fun <T> List<List<T>>.get(coords: Pair<Int, Int>): T =
  get(coords.second).get(coords.first)

private fun Pair<Int, Int>.taxiDistanceTo(other: Pair<Int, Int>): Int {
  return abs(first - other.first) + abs(second - other.second)
}

private fun Pair<Int, Int>.neighbors(): Collection<Pair<Int, Int>> {
  return listOf(
    first + 1 to second,
    first - 1 to second,
    first to second + 1,
    first to second - 1
  )
}

private fun Collection<Pair<Int, Int>>.inRange(w: Int, h: Int): Collection<Pair<Int, Int>> {
  return filter { it.first in 0 until w && it.second in 0 until h }
}
