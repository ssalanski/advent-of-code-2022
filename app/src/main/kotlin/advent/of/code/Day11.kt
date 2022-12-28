package advent.of.code

import kotlin.streams.asSequence

class Day11(runOnExample: Boolean = false) : AdventOfCode(runOnExample) {
  override val day: Int = 11

  override fun partOne() {
    val monkeys = parse_monkeys()
    monkeys.forEach {
      println(it)
    }
    repeat(20) {roundNum ->
      monkeys.forEach { monkey -> monkey.items = monkey.items.map(monkey.operation) }
      monkeys.flatMap { monkey -> monkey.items.map { monkey.target(it) to it } }
        .fold(monkeys.associate { it.index to mutableListOf() }) { param: Map<Int, MutableList<Int>>, (a, b): Pair<Int, Int> ->
          param.apply {
            get(a)!!.add(b)
          }
        }
        .forEach { (monkeyId, newItems) ->
          monkeys.find { it.index == monkeyId }!!.items = newItems
        }
      println("~~ Round $roundNum ~~")
      monkeys.forEach {
        println(it)
      }
    }
  }

  override fun partTwo() {
    println("???")
  }

  class Monkey(
    val index: Int,
    var items: List<Int>,
    val operation: (item: Int) -> Int,
    val target: (value: Int) -> Int
  ) {
    override fun toString(): String {
      return "Monkey $index has $items 0 -> ${operation(0)}, 1->${operation(1)}, targets: ${(1..99).map(target).toSet()}"
    }
  }

  fun parse_monkeys(): List<Monkey> {
    val monkeys: List<Monkey> = buildList {
      readInput().lines().asSequence().windowed(6, 7).forEachIndexed { index, strings ->
        assert(strings[0].trim().matches(Regex.fromLiteral("Monkey $index:")))
        val startingItems =
          strings[1].trim().drop("Starting items: ".length).split(",").map(String::trim)
            .map(String::toInt)
        val (operator, operand) = Regex("([+*-]) (\\d+|old)").matchAt(
          strings[2].trim(),
          "Operation: new = old ".length
        )!!.destructured
        val operation = fun(item: Int): Int {
          val operandValue = when (operand) {
            "old" -> item
            else -> operand.toInt()
          }
          return when (operator) {
            "+" -> item + operandValue
            "-" -> item - operandValue
            "*" -> item * operandValue
            else -> throw Error("ahh")
          }.let { it / 3 }
        }
        val divisor =
          Regex("\\d+").matchAt(strings[3].trim(), "Test: divisible by ".length)!!.value.toInt()
        val targetTrue =
          Regex("(\\d+)").matchAt(strings[4].trim(), "If true: throw to monkey ".length)!!.value.toInt()
        val targetFalse =
          Regex("(\\d+)").matchAt(strings[5].trim(), "If false: throw to monkey ".length)!!.value.toInt()
        val target = fun(value: Int) = if (value % divisor == 0) targetTrue else targetFalse
        add(Monkey(index, startingItems, operation, target))
      }
    }
    return monkeys
  }
}