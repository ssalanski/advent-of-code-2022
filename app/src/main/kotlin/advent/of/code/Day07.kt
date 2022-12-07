package advent.of.code

import java.util.*

class Day07(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 7

  override fun partOne() {
    val dirStack = Stack<Dir>()
    var sumOfTotals: Int = 0
    readInput().forEachLine { line ->
      if (line.startsWith("$ cd ..")) {
        // we completed a directory and move back up
        val completedDir = dirStack.pop()
        // roll up the size of the completed directory into the current directory
        dirStack.peek().size += completedDir.size
//        println("${completedDir.name} was ${completedDir.size} bytes")
        if (completedDir.size <= 100000) {
          sumOfTotals += completedDir.size
        }
      } else if (line.startsWith("$ cd")) {
        // we CD into a new dir
        dirStack.push(Dir(line.split(' ').last()))
      } else if (line.startsWith("$ ls")) {
        // nothing, this just always comes after a $ cd ___
      } else if (line.startsWith("dir ")) {
        // nothing for now, we'll see it later when we cd into it
      }
      else {
        // an actual file listing #### ____, though the name doesnt matter
        dirStack.peek().size += line.split(' ')[0].toInt()
      }
    }
    // since the cmd output doesnt `$ cd ..` a bunch at the end, make sure to roll back up to the top
    while(dirStack.size > 1) {
      // we completed a directory and move back up
      val completedDir = dirStack.pop()
      // roll up the size of the completed directory into the current directory
      dirStack.peek().size += completedDir.size
//      println("${completedDir.name} was ${completedDir.size} bytes")
      if (completedDir.size <= 100000) {
        sumOfTotals += completedDir.size
      }
    }
    println("sum of total sizes of directories with at most 100000: $sumOfTotals")
  }

  override fun partTwo() {
    println("???")
  }

}

data class Dir(val name: String, var size: Int = 0)