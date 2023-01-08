package advent.of.code

import kotlin.streams.toList

class Day12(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 12

  override fun partOne() {
    val grid = readMap()
    println(grid)
    val solution = grid.solve()
    println(solution.joinToString("->"))
    (0 until grid.h).forEach {y ->
      (0 until grid.w).forEach {x ->
        if( x to y in solution) {
          print("%02d ".format(solution.indexOf(x to y)))
        } else {
          print("XX ")
        }
      }
      println()
    }
    print("fewest steps to reach goal: ${solution.size}")
  }

  override fun partTwo() {
  }

  private fun readMap(): AStarGrid {
    val z = readInput().lines().map { line ->
      line.chars().toList().toMutableList()
    }.toList().toMutableList()
    lateinit var start: Pair<Int, Int>
    lateinit var goal: Pair<Int, Int>
    z.forEachIndexed { y, row ->
      row.forEachIndexed { x, v ->
        when (v) {
          'S'.code -> {
            z[y][x] = 'a'.code
            start = x to y
          }

          'E'.code -> {
            z[y][x] = 'z'.code
            goal = x to y
          }
        }
      }
    }
    return AStarGrid(z, start, goal)
  }

}