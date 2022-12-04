package advent.of.code

class TopNKeeper(val n: Int) {
  val vals = mutableListOf(0, 0, 0)
  fun consider(x: Int) {
    if (vals.any { x > it }) {
      vals[0] = x
      vals.sort()
    }
  }
}

fun IntRange.within(other: IntRange): Boolean =
  other.contains(start) and other.contains(endInclusive)

fun IntRange.overlaps(other: IntRange): Boolean =
  other.contains(start) or other.contains(endInclusive) or contains(other.start)