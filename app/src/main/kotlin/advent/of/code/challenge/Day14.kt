package advent.of.code.challenge

import advent.of.code.AdventOfCode
import kotlin.math.sign


class Day14 : AdventOfCode() {
  override val day = 14
  override fun partOne() {
    val rockLocations = buildList {
      readInput().lines().map { line ->
        println(line)
        line.split(" -> ").map { point ->
          println(point)
          point.split(",").map(String::toInt).toPair()
        }.windowed(2, 1).map {
          val p0 = it[0]
          val p1 = it[1]
          val dx = p1.first - p0.first
          val dy = p1.second - p0.second
          var pt = p0.copy()
          repeat(dx + dy) {
            add(pt)
            pt = pt.first + dx.sign to pt.second + dy.sign
          }
        }
      }
    }
    val maxCoordinate = rockLocations.maxBy(Pair<Int, Int>::first).first to rockLocations.maxBy(Pair<Int, Int>::second).second
    println(rockLocations.size)
  }

  override fun partTwo() {
    println("Not yet implemented")
  }

}

private fun <T> List<T>.toPair(): Pair<T, T> {
  assert(size == 2)
  return get(0) to get(1)
}
