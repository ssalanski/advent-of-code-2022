package advent.of.code.challenge

import advent.of.code.AdventOfCode
import java.util.*
import java.util.stream.Stream
import kotlin.streams.asSequence
import kotlin.streams.toList

class IntOrList : Comparable<IntOrList> {
  constructor(anInt: Int) {
    theInt = anInt
  }

  constructor(aList: List<IntOrList>) {
    theList = aList
  }

  private var theInt: Int? = null
  private var theList: List<IntOrList>? = null
  private val isInt: Boolean
    get() = when (theInt) {
      null -> false
      else -> true
    }
  private val isList: Boolean
    get() = when (theList) {
      null -> false
      else -> true
    }

  override fun compareTo(other: IntOrList): Int {
    if (isInt && other.isInt) {
      return other.theInt!!.let { theInt!!.compareTo(it) }
    } else if (isList && other.isList) {
      theList!!.forEachIndexed { index, item ->
        if (index >= other.theList!!.size) return 1
        item.compareTo(other.theList!!.get(index)).let {
          when (it) {
            0 -> {}
            else -> return it
          }
        }
      }
      if (other.theList!!.size > theList!!.size) return -1
    } else if (isList && other.isInt) {
      return compareTo(IntOrList(listOf(other)))
    } else if (isInt && other.isList) {
      return IntOrList(listOf(this)).compareTo(other)
    } else throw IllegalStateException("none of the comparison cases matched, what gives? $this  =?=  $other)")
    return 0
  }

  override fun toString(): String {
    if (isInt) return theInt.toString()
    else if (isList) return "[" + theList!!.map { it.toString() }.joinToString(",") + "]"
    return "?????"
  }

  private fun append(item: IntOrList) {
    when (theList) {
      null -> throw IllegalStateException("cant append to an integer, only to list")
      else -> theList = (theList!! + item)
    }
  }

  companion object {
    fun fromString(str: String): IntOrList {
      val stack: Stack<IntOrList> = Stack()
      var value: Int? = null
      str.toList().forEach { theChar ->
        when (theChar) {
          '[' -> stack.push(IntOrList(emptyList()))

          ']' -> {
            value?.let {
              stack.peek().append(IntOrList(it))
              value = null
            }
            val finished = stack.pop()
            if (stack.empty()) return finished
            stack.peek().append(finished)
          }

          ',' -> value?.let {
            stack.peek().append(IntOrList(it))
            value = null
          }

          else -> value = (value?.times(10) ?: 0) + theChar.digitToInt()
        }
      }
      return stack.pop() // actually this shouldnt happen, assuming the str ends with a final ] it will return when the stack is empty
    }
  }
}

class Day13 : AdventOfCode() {
  override val day = 13

  override fun partOne() {
    val sumOfCorrectOrderIndexes =
      readInput().lines().asSequence().windowed(2, 3).mapIndexedNotNull { index, pair ->
        val first = IntOrList.fromString(pair.get(0))
        //println("1) ${pair.get(0)}")
        //println("-> $first")
        val second = IntOrList.fromString(pair.get(1))
        //println("2) ${pair.get(1)}")
        //println("-> $second")
        //println("--------")
        (index + 1).takeIf { first < second }
      }.sum()
    println("sum of correctly ordered pair indexes: $sumOfCorrectOrderIndexes")
  }

  override fun partTwo() {
    val productOfMarkerPacketIndexes =
      Stream.concat(readInput().lines(), Stream.of("[[2]]", "[[6]]"))
        .filter(String::isNotBlank).map(String::trim)
        .map(IntOrList.Companion::fromString)
        .sorted().toList()
        .mapIndexedNotNull { index, item ->
          (index + 1).takeIf {
            item.compareTo(IntOrList(listOf(IntOrList(listOf(IntOrList(2)))))) == 0
              ||
              item.compareTo(IntOrList(listOf(IntOrList(listOf(IntOrList(6)))))) == 0
          }
        }.also { println("marker packet count: ${it.size}") }
        .reduce(Int::times)
    println("product of marker packet indexes: $productOfMarkerPacketIndexes")
  }
}