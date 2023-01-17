package advent.of.code.challenge

import advent.of.code.AdventOfCode
import java.util.*
import kotlin.streams.asSequence

class Day11 : AdventOfCode() {
  override val day: Int = 11

  override fun partOne() {
    val monkeys = parse_monkeys().associateBy { it.index }.toSortedMap()
    monkeys.values.forEach {
      println(it)
    }
    monkeyAround(monkeys, 20, 3U)
  }

  override fun partTwo() {
    val monkeys = parse_monkeys().associateBy { it.index }.toSortedMap()
    monkeys.values.forEach {
      println(it)
    }
    monkeyAround(monkeys, 10000, 1U)
  }

  fun monkeyAround(monkeys: SortedMap<Int, Monkey>, numRounds: Int, worryDivisor: UInt) {
    val testDivisorLCM = monkeys.values.map { it.testDivisor }.reduce { a, b -> a * b }
    repeat(numRounds) {roundNum ->
      monkeys.values.forEach { monkey ->
        monkey.items
          .map(monkey.operation)
          .map { it / worryDivisor }
          .map { it % testDivisorLCM }
          .forEach {
            monkeys[monkey.target(it)]!!.items.add(it)
          }
        monkey.totalInspections += monkey.items.size
        monkey.items.clear()
      }
//      println("~~ Round $roundNum ~~")
//      monkeys.forEach {
//        println(it)
//      }
    }
    monkeys.values.forEach {
      println(" - monkey ${it.index} inspected ${it.totalInspections} items")
    }
    val monkeyBusiness = monkeys.values.map { it.totalInspections.toULong() }.sortedDescending().take(2).also { println(it) }.reduce { a, b -> a * b }
    println("monkey business level: $monkeyBusiness")
  }

  class Monkey(
    val index: Int,
    var items: MutableList<ULong>,
    val operation: (item: ULong) -> ULong,
    val target: (value: ULong) -> Int,
    val testDivisor: UInt
  ) {
    var totalInspections = 0
    override fun toString(): String {
      return "Monkey $index has $items 0 -> ${operation(0U)}, 1->${operation(1U)}, targets: ${(1UL..99UL).map(target).toSet()}"
    }
  }

  fun parse_monkeys(): List<Monkey> {
    val monkeys: List<Monkey> = buildList {
      readInput().lines().asSequence().windowed(6, 7).forEachIndexed { index, strings ->
        assert(strings[0].trim().matches(Regex.fromLiteral("Monkey $index:")))
        val startingItems =
          strings[1].trim().drop("Starting items: ".length).split(",").map(String::trim)
            .map(String::toULong).toMutableList()
        val (operator, operand) = Regex("([+*-]) (\\d+|old)").matchAt(
          strings[2].trim(),
          "Operation: new = old ".length
        )!!.destructured
        val operation = fun(item: ULong): ULong {
          val operandValue = when (operand) {
            "old" -> item
            else -> operand.toULong()
          }
          return when (operator) {
            "+" -> item + operandValue
            "-" -> item - operandValue
            "*" -> item * operandValue
            else -> throw Error("ahh")
          }
        }
        val divisor =
          Regex("\\d+").matchAt(strings[3].trim(), "Test: divisible by ".length)!!.value.toUInt()
        val targetTrue =
          Regex("(\\d+)").matchAt(strings[4].trim(), "If true: throw to monkey ".length)!!.value.toInt()
        val targetFalse =
          Regex("(\\d+)").matchAt(strings[5].trim(), "If false: throw to monkey ".length)!!.value.toInt()
        val target = fun(value: ULong) = if (value % divisor == 0UL) targetTrue else targetFalse
        add(Monkey(index, startingItems, operation, target, divisor))
      }
    }
    return monkeys
  }
}