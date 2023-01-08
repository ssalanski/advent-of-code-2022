package advent.of.code

import kotlin.streams.toList

class Day12(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 12

  override fun partOne() {
    val grid = readMap("1")
    println(grid)
    val solution = grid.solve()
    println(solution.joinToString("->"))
    (0 until grid.h).forEach { y ->
      (0 until grid.w).forEach { x ->
        if (x to y in solution) {
          print("%02d ".format(solution.indexOf(x to y)))
        } else {
          print("XX ")
        }
      }
      println()
    }
    println("fewest steps to reach goal: ${solution.size}")
  }

  override fun partTwo() {
    val grid = readMap("2")
    println(grid)
    val solution = grid.solve()
    println(solution.joinToString("->"))
    (0 until grid.h).forEach { y ->
      (0 until grid.w).forEach { x ->
        if (x to y in solution) {
          print("%02d ".format(solution.indexOf(x to y)))
        } else {
          print("XX ")
        }
      }
      println()
    }
    println("fewest steps from nearest 'a' level: ${solution.size}")
  }

  private fun readMap(part: String): AStarGrid {
    val grid = readInput().lines().map { line ->
      line.chars().toList().toMutableList()
    }.toList().toMutableList()
    lateinit var start: Pair<Int, Int>
    lateinit var goal: Pair<Int, Int>
    val aList: MutableList<Pair<Int, Int>> = mutableListOf()
    grid.forEachIndexed { y, row ->
      row.forEachIndexed { x, v ->
        when (v) {
          'S'.code -> {
            grid[y][x] = 'a'.code
            start = x to y
            aList.add(x to y)
          }

          'E'.code -> {
            grid[y][x] = 'z'.code
            goal = x to y
          }

          'a'.code -> aList.add(x to y)
        }
        if(part == "2") {
          grid[y][x] = -grid[y][x]
        }
      }
    }
    return if (part == "1") {
      AStarGrid(grid, start, goal)
    } else {
      ModAStarGrid(grid, goal, start, aList)
    }

  }

}