// EMMANUELLA EYO EEE917 11291003
import scala.annotation.tailrec

object Problem1 {

  // returns the result of a combining 2 lists
  def shuffle[A](l1: List[A], l2: List[A]): List[A] = {
    @tailrec
    def loop(result: List[A], l1: List[A], l2: List[A]): List[A] = {
      if (l1.isEmpty && l1.isEmpty) result
      else if (l1.isEmpty) result ++ l2
      else if (l2.isEmpty) result ++ l1
      else loop(result :+ l1.head :+ l2.head, l1.tail, l2.tail)
    }
    loop(List.empty[A], l1, l2)
  }

  //t splits the contents of the provided list into two lists, the first with the first n
  // elements of the list (in order), and the second with the remaining elements (again, in order)
  def split[A](list: List[A], n: Int): List[List[A]] = {
    @tailrec
    def loop(x: Int, l1: List[A], l2: List[A]): List[List[A]] = {
      if (x >= list.length) List(l1, l2)
      else if (x < n) loop(x + 1, l1 :+ list(x), l2)
      else loop(x + 1, l1, l2 :+ list(x))
    }

    loop(0,List(), List())
  }

  // Checks is a deck is even numbered
  def isDeckValid[A](list: List[A]): Either[String, Int] = {
    if (list.length % 2 != 0) Left("A stack is meant to be even numbered")
    else Right(0)
  }


  // hey take a single stack of an even number of cards, break them up and
  // return the result of in the first and last  cards becoming the second and
  // the second last cards respectively of the shuffled stack
  def inshuffle[A](list: List[A], n: Int): List[A] = {
    var result: List[List[A]] = List()
    isDeckValid(list) match {
      case Left(err) => println(err)
      case Right(0) =>
        result = split(list, n)
    }
    shuffle(result(1), result(0))
  }


  // hey take a single stack of an even number of cards, break them up and
  // return the result of in the first and last  cards of the stack remaining
  // the same after the shuffle
  def outshuffle[A](list: List[A], n: Int): List[A] = {
    var split_result: List[List[A]] = List()
    isDeckValid(list) match {
      case Left(err) => println(err)
      case Right(0) =>
        split_result = split(list, n)
    }
    shuffle(split_result(0), split_result(1))
  }


  // carries out the required number of shuffles on the list, and returns the resul
  def nshuffle[A](f:(List[A], Int) => List[A], n:Int, list: List[A]): List[A] = {
    val result = list
    @tailrec
    def loop(x: Int, l1: List[A]): List[A] = {
      if (x >= n) l1
      else loop(x + 1, f(l1, l1.length / 2))
    }
    loop(0, result)
  }

  // Applies the the shuffle function on the first of the two lists, until it
  // becomes identical to the second list, and returns the count of the number of
  // shuffles that were required.
  def howManyShuffles[A](f:(List[A], Int) => List[A], l1:List[A], l2: List[A]) : Int = {

    val result = l1
    @tailrec
    def loop(n: Int, list1:List[A]) : Int = {
      if(list1 != l2) loop(n+1, f(list1, list1.length/2))
      else n
    }
    loop(0, result)
  }



  // testing
  def main(args: Array[String]): Unit = {
    val deck = List(1, 2, 3, 4, 5, 6, 7, 8)
    val deck1 = List(1, 5, 2, 6, 3, 7, 4, 8)

    //    println(nshuffle(outshuffle[Int], 1, deck))
    println(outshuffle(deck, 4))

//    println(howManyShuffles(outshuffle[Int], deck, deck1))
//
//    val deck2: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
//      32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52)
//
//    val savedDeck: List[Int] = nshuffle(outshuffle[Int], 15, deck2)
//
//    println(howManyShuffles(outshuffle[Int], deck2, savedDeck))

  }
}
