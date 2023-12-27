// EMMANUELLA EYO 11291003 EEEE917
trait Partial[+A, +B] {

  def map[C](f: B => C): Partial[A, C] = this match {
    case Success(a) => Success(f(a))
    case Errors(err) => Errors(err)

  }

  def getOrElse[C >: B](default: => C): C = this match {
    case Success(a) => a
    case Errors(_) => default

  }

  def flatMap[AA >: A, C](f: B => Partial[AA, C]): Partial[AA, C] = this match {
    case Errors(err) => Errors(err)
    case Success(a) => f(a)
  }

  def orElse[AA >: A, C2 >: B](ob: => Partial[AA, C2]): Partial[AA, C2] = this match {
    case Errors(_) => ob
    case _ => this
  }


  def map2[AA >: A, C, D](b: Partial[AA, C])(f: (B, C) => D): Partial[AA, D] = {
    for {
      aa <- this
      bb <- b
    } yield f(aa, bb)
  }


  def traverse[E, C, D](as: List[D])(f: D => Partial[E, C]): Partial[E, List[C]] = {
    as.foldRight[Partial[E, List[C]]](Success(Nil))((a, b) => f(a).map2(b)(_ :: _))
  }


  def Try[T](a: => T): Partial[Exception, T] = {
    try Success(a)
    catch {
      case e: Exception => Errors(Seq(e))
    }
  }
}

case class Errors[+A](get: Seq[A]) extends Partial[A,Nothing]
case class Success[+B](get: B) extends Partial[Nothing,B]
