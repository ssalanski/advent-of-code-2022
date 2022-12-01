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

class AdventOfCode {

    fun `day 1 part 1`(runOnExample: Boolean = false) {
        val filename = if (runOnExample) "example.txt" else "input.txt"
        var currentElf = 0
        var maxElf = 0
        readInput("/day01/$filename").forEachLine {
            if (it.isEmpty()) {
                currentElf = 0
            } else {
                currentElf += it.toInt()
            }
            maxElf = max(maxElf,currentElf)
        }
        println("highest calorie elf has $maxElf calories")
    }
    private fun readInput(name: String): BufferedReader =
        this.javaClass::class.java.getResource(name)?.openStream()?.bufferedReader() ?: throw IllegalArgumentException("$name was not found")
}
fun main() {
    println(App().greeting)

    AdventOfCode().`day 1 part 1`(true)
    AdventOfCode().`day 1 part 1`()

}
