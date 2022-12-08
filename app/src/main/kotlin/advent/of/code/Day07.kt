package advent.of.code

import java.util.*

class Day07(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 7

  private fun computeAllDirectorySizes(lines: Iterable<String>): List<Int> {
    val dirStack = Stack<Dir>()
    val directoryTotals = mutableListOf<Int>()
    lines.forEach { line ->
      if (line.startsWith("$ cd ..")) {
        // we completed a directory and move back up
        val completedDir = dirStack.pop()
        // roll up the size of the completed directory into the current directory
        dirStack.peek().size += completedDir.size
//        println("${completedDir.name} was ${completedDir.size} bytes")
        directoryTotals.add(completedDir.size)
      } else if (line.startsWith("$ cd")) {
        // we CD into a new dir
        dirStack.push(Dir(line.split(' ').last()))
      } else if (line.startsWith("$ ls")) {
        // nothing, this just always comes after a $ cd ___
      } else if (line.startsWith("dir ")) {
        // nothing for now, we'll see it later when we cd into it
      } else {
        // an actual file listing #### ____, though the name doesnt matter
        dirStack.peek().size += line.split(' ')[0].toInt()
      }
    }
    // since the cmd output doesnt `$ cd ..` a bunch at the end, make sure to roll back up to the top
    while (dirStack.size > 1) {
      // we completed a directory and move back up
      val completedDir = dirStack.pop()
      // roll up the size of the completed directory into the current directory
      dirStack.peek().size += completedDir.size
//      println("${completedDir.name} was ${completedDir.size} bytes")
      directoryTotals.add(completedDir.size)
    }
    directoryTotals.add(dirStack.peek().size)
    return directoryTotals
  }

  override fun partOne() {
    val sumOfTotals = computeAllDirectorySizes(readInput().readLines()).filter { it <= 100000 }.sum()
    println("sum of total sizes of directories with at most 100000: $sumOfTotals")
  }

  override fun partTwo() {
    val dirSizes = computeAllDirectorySizes(readInput().readLines())
    val freeSpace = 70000000 - dirSizes.max()
    val neededSpace = 30000000
    val spaceToReclaim = neededSpace - freeSpace
    println(
      "smallest directory size that reclaims at least $spaceToReclaim: ${
        dirSizes.filter { it > spaceToReclaim }.min()
      }"
    )
  }

  data class Dir(val name: String, var size: Int = 0)

}
