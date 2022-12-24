package advent.of.code

import kotlin.math.abs
import kotlin.math.max

class Day09(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 9

  override fun partOne() {
    var head = Coord(0, 0)
    var tail = Coord(0, 0) // a position relative to the head
    val tailVisits = mutableSetOf(tail)
    readInput().forEachLine { line ->
      line.split(' ').let {
        val dir = it[0]
        val dist = it[1].toInt()
        repeat(dist) {
          val prevHead = head.copy()
          when (dir) {
            "R" -> {
              head.x++
            }

            "L" -> {
              head.x--
            }

            "U" -> {
              head.y++
            }

            "D" -> {
              head.y--
            }
          }
          if (tail.chebyshevDistance(head) > 1) {
            tail = prevHead
            tailVisits.add(tail)
          }
        }
      }
    }
    println("number of positions tail visited: ${tailVisits.size}")
  }

  override fun partTwo() {
    println("???")
  }

  private data class Coord(var x: Int, var y: Int)

  private operator fun Coord.minus(other: Coord) = Coord(x - other.x, y - other.y)
  private fun Coord.chebyshevDistance(other: Coord) =
    (this - other).let { max(abs(it.x), abs(it.y)) }
}
