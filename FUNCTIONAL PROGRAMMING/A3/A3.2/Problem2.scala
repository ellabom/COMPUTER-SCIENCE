// EMMANUELLA EYO 11291003 EEE917
object Problem2 {

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): LazyList[A] = f(z) match {

    case Some((h, s)) => h #:: unfold(s)(f)

    case None => LazyList()

  }

  def perfects: LazyList[Int] = {

    //function to  construct a list of factors
    def factors(n: Int): LazyList[Int] =
      unfold(1) { i =>
        if (n % i == 0) Some((i, i + 1))
        else if (i > n) None
        else Some((0, i + 1))
      }.filter(_ != 0)

    def sumTheFactors(n: Int): Int = {
      factors(n).foldLeft(0)(_ + _)
    }

    def checkIfPerfect(n: Int): Boolean = {
     factors(n).sum - n == n
    }

    //unfold to generate the LazyList of perfect numbers
    unfold(1) { i =>
      if (checkIfPerfect(i)) Some((i, i + 1))
      else Some((0, i + 1))
    }.filter(_ != 0)

  }


  def main(args: Array[String]): Unit = {

    perfects.take(4).foreach(println)

  }
}
