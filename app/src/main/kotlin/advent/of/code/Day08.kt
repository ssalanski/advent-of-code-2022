package advent.of.code

class Day08(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 8

  override fun partOne() {
    val forest = readInput().readLines().map { line ->
      line.toCharArray().map { it.digitToInt() }
    }
    val h = forest.size
    val w = forest.first().size
    val visibleTreeCount = forest.flatMapIndexed { r, row ->
      row.mapIndexed { c, treeHeight ->
        (fun(z: Int): Boolean = z < treeHeight).let { p ->
          (r == 0) || (c == 0) || (r == h) || (c == w) ||
            forest[0 until r].map { it[c] }.all(p) ||
            forest[r + 1 until h].map { it[c] }.all(p) ||
            forest[r][0 until c].all(p) ||
            forest[r][c + 1 until w].all(p)
        }
      }
    }.count { it }
    println("number of visible trees: $visibleTreeCount")
  }

  override fun partTwo() {
    val forest = readInput().readLines().map { line ->
      line.toCharArray().map { it.digitToInt() }
    }
    val h = forest.size
    val w = forest.first().size
    val maxScore = forest.flatMapIndexed { r, row ->
      row.mapIndexed { c, treeHeight ->
        if ((r == 0) || (c == 0) || (r == h) || (c == w)) 0 else
          listOf(
            forest[0 until r].map { it[c] }.reversed().takeWhile(true) { it < treeHeight }.count(),
            forest[r + 1 until h].map { it[c] }.takeWhile(true) { it < treeHeight }.count(),
            forest[r][0 until c].reversed().takeWhile(true) { it < treeHeight }.count(),
            forest[r][c + 1 until w].takeWhile(true) { it < treeHeight }.count()
          ).reduce { acc, x -> acc * x }
      }
    }.max()
    println("max scenic score: $maxScore")
  }

}
