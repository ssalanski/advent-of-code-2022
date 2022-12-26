package advent.of.code

import kotlin.math.abs
import kotlin.math.max

class Day09(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 9

  override fun partOne() {
    val head = Coord(0, 0)
    var tail = Coord(0, 0)
    val tailVisits = mutableSetOf(tail)
    readInput().forEachLine { line ->
      line.split(' ').let {
        val dir = it[0]
        val dist = it[1].toInt()
        repeat(dist) {
          val prevHead = head.copy()
          when (dir) {
            "R" -> { head.x++ }
            "L" -> { head.x-- }
            "U" -> { head.y++ }
            "D" -> { head.y-- }
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
    val rope = listOf(
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0),
      Coord(0, 0)
    )
    val tailVisits = mutableSetOf(Coord(0, 0))
    readInput().forEachLine { line ->
      line.split(' ').let {
        val dir = it[0]
        val dist = it[1].toInt()
//        println("moving $dir ($dist)")
        repeat(dist) {
//          println(" > step $it")
          // update the head, unrestricted
          when (dir) {
            "R" -> { rope[0].x++ }
            "L" -> { rope[0].x-- }
            "U" -> { rope[0].y++ }
            "D" -> { rope[0].y-- }
          }
          // update the rest of the links, each treating the previous as its "head"
          for (i in 1 until rope.size) {
            val h = rope[i - 1]
            val t = rope[i]
            when (t - h) {
              Coord(2, 0) -> t.x--
              Coord(-2, 0) -> t.x++
              Coord(0, 2) -> t.y--
              Coord(0, -2) -> t.y++
              Coord(1,2),Coord(2,1),Coord(2, 2) -> { t.x--; t.y-- }
              Coord(-1,2),Coord(-2,1),Coord(-2, 2) -> { t.x++; t.y-- }
              Coord(1,-2),Coord(2,-1),Coord(2, -2) -> { t.x--; t.y++ }
              Coord(-1,-2),Coord(-2,-1),Coord(-2, -2) -> { t.x++; t.y++ }
            }
          }
          tailVisits.add(rope.last())
        }
//        println(rope)
      }
    }
    println("number of positions long tail visited: ${tailVisits.size}")

  }

  private data class Coord(var x: Int, var y: Int)

  private operator fun Coord.minus(other: Coord) = Coord(x - other.x, y - other.y)
  private fun Coord.chebyshevDistance(other: Coord) =
    (this - other).let { max(abs(it.x), abs(it.y)) }
}
