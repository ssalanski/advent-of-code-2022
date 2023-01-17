package advent.of.code.challenge

import advent.of.code.AdventOfCode
import advent.of.code.splitSequenceOn
import java.util.*

class Day05 : AdventOfCode() {
  override val day: Int = 5

  override fun partOne() {
    val inputSequence = readInput().lineSequence().splitSequenceOn { it.isEmpty() }.iterator()
    val stacks = parseStartingArrangement(inputSequence.next().toList())
//    stacks.forEachIndexed { index, chars -> println("stack $index is:" + chars.joinToString(",")) }
    val operations = parseMoveOperations(inputSequence.next())
//    operations.forEachIndexed { index, triple -> println("operation $index is $triple") }
    operations.forEach { op ->
      repeat(op.count) {
        stacks[op.too - 1].push(stacks[op.from - 1].pop())
      }
    }
    val tops = stacks.map { it.peek() }.joinToString("")
    println("final top crates after rearrangement: $tops")
  }

  @OptIn(ExperimentalStdlibApi::class)
  private fun parseStartingArrangement(inputLines: List<String>): List<Stack<Char>> {
    val numStacks = inputLines.last().split(" +".toRegex()).count { it.isNotEmpty() }
    val stacks = buildList(numStacks) {
      repeat(numStacks) {
        add(Stack<Char>())
      }
    }
    inputLines.dropLast(1).reversed().forEach { line ->
      line.chunked(4).map { it.trim() }.forEachIndexed { index, s ->
        if (s.trim().isNotEmpty()) {
          stacks[index].push(s[1])
        }
      }
    }
    return stacks
  }

  private fun parseMoveOperations(inputLines: Sequence<String>): Sequence<MoveOp> {
    return inputLines.map { line ->
      "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(line)?.groups?.drop(1)?.map {
        it?.value?.toInt() ?: 0
      }?.let {
        MoveOp(it[0], it[1], it[2])
      }!!
    }
  }

  override fun partTwo() {
    val inputSequence = readInput().lineSequence().splitSequenceOn { it.isEmpty() }.iterator()
    val stacks = parseStartingArrangement(inputSequence.next().toList()).map { it.toMutableList() }
    val operations = parseMoveOperations(inputSequence.next())
    operations.forEach { op ->
      stacks[op.too - 1].addAll(stacks[op.from - 1].takeLast(op.count))
      repeat(op.count) { stacks[op.from - 1].removeLast() }
    }
    val tops = stacks.map { it.last() }.joinToString("")
    println("final top crates after _actual_ rearrangement: $tops")
  }

  data class MoveOp(val count: Int, val from: Int, val too: Int)
}