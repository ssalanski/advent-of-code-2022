/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package advent.of.code

import java.io.BufferedReader

class App {
  val greeting: String
    get() {
      return "Hello World!"
    }
}

abstract class AdventOfCode(runOnExample: Boolean = false) {
  private val filename = if (runOnExample) "example.txt" else "input.txt"
  abstract val day: Int
  abstract fun partOne()
  abstract fun partTwo()
  private fun readInput(name: String): BufferedReader =
    this.javaClass.getResource(name)?.openStream()?.bufferedReader()
      ?: throw IllegalArgumentException("$name was not found")

  fun readInput(): BufferedReader = readInput("/day%02d/$filename".format(day))

  fun run() {
    partOne()
    partTwo()
  }
}

fun main() {
  println(App().greeting)

//  Day01().run()
//  Day02().run()
//  Day03().run()
//  Day04().run()
//  Day05().run()
//  Day06().run()
//  Day07().run()
//  Day08().run()
//  Day09().run()
//  Day10().run()
  Day11(true).run()

}
