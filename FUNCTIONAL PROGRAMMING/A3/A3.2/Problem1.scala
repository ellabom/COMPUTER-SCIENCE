//EMMANUELLA EYO 11291003 EEE917

object Problem1 {

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): LazyList[A] = f(z) match {

    case Some((h, s)) => h #:: unfold(s)(f)

    case None => LazyList()

  }



  def pyth(n: Int): List[(Int, Int, Int)] = {
    val nums = unfold(1)(i => if (i <= n) Some(i, i + 1) else None)
    val lists = for {
      a <- nums
      b <- nums.takeWhile(_ <= n).dropWhile(_ <= 0)
      c <- nums.takeWhile(_ <= n).dropWhile(_ <= 0)
      if a * a + b * b == c * c
    } yield (a, b, c)

    lists.toList
  }



  def main(args: Array[String]): Unit = {

    val t = pyth(5)
    println(t)


  }


}
