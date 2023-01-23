package advent.of.code.challenge

import advent.of.code.AdventOfCode
import java.util.stream.Stream
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign


class Day14 : AdventOfCode() {
  override val day = 14
  override fun partOne() {
    val rockLocations = findRockLocations(readInput().lines())
    val cave = Cave(rockLocations)
    while (!cave.simulate()) {
//      println(cave)
    }
    println(cave)
    println("${cave.sandLocations.size} units of sand came to rest")
  }


  override fun partTwo() {
    println("Not yet implemented")
  }

  private fun findRockLocations(lines: Stream<String>): List<Pair<Int, Int>> = buildList {
    lines.forEach { line ->
      println(line)
      line.split(" -> ").map { point ->
        println(point)
        point.split(",").map(String::toInt).toPair()
      }.windowed(2, 1).map {
        println("segment: $it")
        val p0 = it[0]
        val p1 = it[1]
        val dx = p1.first - p0.first
        val dy = p1.second - p0.second
        var pt = p0.copy()
        repeat(abs(dx) + abs(dy) + 1) {
          add(pt)
          pt = pt.first + dx.sign to pt.second + dy.sign
        }
      }
    }
  }

}

private class Cave(
  val rockLocations: List<Pair<Int, Int>>,
  var sandLocations: MutableList<Pair<Int, Int>> = emptyList<Pair<Int, Int>>().toMutableList()
) {
  val maxCoordinate: Pair<Int, Int> =
    rockLocations.maxBy(Pair<Int, Int>::first).first to rockLocations.maxBy(Pair<Int, Int>::second).second
  val minCoordinate: Pair<Int, Int> =
    rockLocations.minBy(Pair<Int, Int>::first).first to min(
      0,
      rockLocations.minBy(Pair<Int, Int>::second).second
    )

  private fun Pair<Int, Int>.nextLocs(): Sequence<Pair<Int, Int>> = sequence {
    yield(first to second + 1)
    yield(first - 1 to second + 1)
    yield(first + 1 to second + 1)
  }

  private fun Pair<Int, Int>.nextLoc(): Pair<Int, Int>? =
    nextLocs().firstOrNull { !(rockLocations.contains(it) || sandLocations.contains(it)) }

  fun simulate(): Boolean {
    var sand = (500 to 0)
    while (true) {
      val next = sand.nextLoc()
      if (next == null) {
        sandLocations.add(sand)
        return false
      } else if (next.second > maxCoordinate.second) {
        return true
      }
      sand = next
    }
  }

  override fun toString(): String {
    return "${rockLocations.size} total rocks\n" +
      "$minCoordinate  ->  $maxCoordinate\n" +
      (minCoordinate.second..maxCoordinate.second).map { y ->
        (minCoordinate.first..maxCoordinate.first).map { x ->
          when {
            (x to y) == 500 to 0 -> "+"
            (x to y) in rockLocations -> "#"
            (x to y) in sandLocations -> "o"
            else -> "."
          }
        }.joinToString("")
      }.joinToString("\n")
  }
}

private fun <T> List<T>.toPair(): Pair<T, T> {
  assert(size == 2)
  return get(0) to get(1)
}
