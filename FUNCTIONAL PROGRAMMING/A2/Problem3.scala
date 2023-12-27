//EMMANUELLA EYO 11291003 EEE917

import scala.annotation.tailrec

object Problem3 {

  def luhnDouble(digit: Int) : Int = (digit * 2) match {
    case double if double > 9 =>
      double - 9
    case double => double
  }

  def altMap[A, B](func: List[A => B], lst: List[A]): List[B] = {
    @tailrec
    def loop(lst: List[A], i: Int, result: List[B]): List[B] = lst match {
      case x :: list =>
        loop(list, i + 1, result :+ func(i % func.length)(x))
      case Nil => result
    }
    loop(lst, 0, Nil)
  }

  def luhn(card: List[Int]): Boolean = {
    val doubleNumbers = altMap[Int, Int](List(luhnDouble, (x => x)), card)

    def sumList(lst: List[Int]): Int = {
      var sum = 0
      for (num <- lst) {
        sum += num
      }
      sum
    }
    val sum = sumList(doubleNumbers)
    sum % 10 == 0
  }


  def main(args: Array[String]): Unit = {

    val cardNumber = List(4, 5, 5, 6, 7, 3, 7, 5, 8, 6, 8, 9, 9, 8, 5, 5)
    println(luhn(List(1,2,3,4))) // outputs: true
  }

}
