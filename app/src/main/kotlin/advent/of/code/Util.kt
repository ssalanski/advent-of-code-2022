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
