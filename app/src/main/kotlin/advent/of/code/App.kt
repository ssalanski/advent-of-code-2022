/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package advent.of.code

import advent.of.code.challenge.*
import java.io.BufferedReader

class App {
  val greeting: String
    get() {
      return "Hello World!"
    }
  val challenges: Map<Int, AdventOfCode> = listOf(
    Day01(),
    Day02(),
    Day03(),
    Day04(),
    Day05(),
    Day06(),
    Day07(),
    Day08(),
    Day09(),
    Day10(),
    Day11(),
    Day12(),
    Day13(),
    Day14(),
  ).associateBy { it.day }
}

abstract class AdventOfCode {
  private lateinit var filename: String
  abstract val day: Int
  abstract fun partOne()
  abstract fun partTwo()
  private fun readInput(name: String): BufferedReader =
    this.javaClass.getResource(name)?.openStream()?.bufferedReader()
      ?: throw IllegalArgumentException("$name was not found")

  fun readInput(): BufferedReader = readInput("/day%02d/$filename".format(day))

  fun run(runOnExample: Boolean) {
    filename = if (runOnExample) "example.txt" else "input.txt"
    partOne()
    partTwo()
  }
}


fun main(args: Array<String>) {
  App().let {
    println(it.greeting)

    val day = args[0].toInt()
    val example = args.getOrNull(1).toBoolean()

    it.challenges.getValue(day).run(example)

  }
}
