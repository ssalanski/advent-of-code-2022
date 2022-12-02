/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package advent.of.code

import java.io.BufferedReader
import java.lang.Integer.max

class App {
  val greeting: String
    get() {
      return "Hello World!"
    }
}

interface AdventOfCode {
  fun partOne(runOnExample: Boolean = false)
  fun partTwo(runOnExample: Boolean = false)
  fun readInput(name: String): BufferedReader =
    this.javaClass::class.java.getResource(name)?.openStream()?.bufferedReader()
      ?: throw IllegalArgumentException("$name was not found")
}

fun main() {
  println(App().greeting)

  Day01().partOne(true)
  Day01().partOne()
  Day01().partTwo(true)
  Day01().partTwo()

}
