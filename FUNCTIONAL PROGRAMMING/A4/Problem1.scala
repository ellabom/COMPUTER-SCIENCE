/*EMMANUELLA EYO 11291003 EEE917*/
import akka.actor._

object Sorting {
  case class miminumValue(number: Int)
}

class Filter extends Actor {
  import Sorting._

  var minVal: Int = 0
  var nextActor: Option[ActorRef] = None

  def receive: PartialFunction[Any, Unit] = {
    case 0 =>
      sender() ! miminumValue(minVal)
      nextActor.foreach(_ ! 0)

    // If received message is an integer and minimum value is 0, set minimum value to the integer
    case x: Int if minVal == 0 =>
      minVal = x

    // If received message is an integer, and next actor is None, create a new Filter actor and send integer to it
    // If received integer is less than minimum value, send minimum value to next actor and set minimum value to the integer
    // Else send integer to next actor
    case x: Int =>
      if (nextActor.isEmpty) {
        nextActor = Some(context.actorOf(Props[Filter], "FilterActor"))
      }

      if (x < minVal) {
        nextActor.foreach(_ ! minVal)
        minVal = x
      } else {
        nextActor.foreach(_ ! x)
      }

    // If received message is minimum value, send it to parent actor
    case miminumValue(num) =>
      context.parent ! miminumValue(num)

  }
}

class Client(listOfValues: List[Int]) extends Actor {
  import Sorting._

  var sortedList: List[Int] = Nil

  def receive: PartialFunction[Any, Unit] = {
    case minVal: miminumValue =>
      sortedList = sortedList :+ minVal.number
      println(s"Current minimum value: ${minVal.number}, sorted list so far: $sortedList")

    case "start" =>
      val fActor = context.actorOf(Props[Filter], "Filter")

      listOfValues.foreach(fActor ! _)

      fActor ! 0

  }
}



object Problem1 extends App {
  val system = ActorSystem("SortSystem")

  println("---test1--- \n test1 = List(9, 5, 64, 1, 15, 2)")
  val test1 = List(9, 5, 64, 1, 15, 2)
  val clientActor1 = system.actorOf(Props(classOf[Client], test1), "Client1")
  clientActor1 ! "start"

//  println("---test2--- \n test2 = List(6,6, 6, 6, 6)")
//  val test2 = List(6,6, 6, 6, 6)
//  val clientActor2 = system.actorOf(Props(classOf[Client], test2), "Client2")
//  clientActor2 ! "start"
//
//  println("---test3---")
//  val test3 = List()
//  val clientActor3 = system.actorOf(Props(classOf[Client], test3), "Client3")
//  clientActor3 ! "start"
//
//
//  println("---test4---")
//  val test4 = List(5, 2)
//  val clientActor4 = system.actorOf(Props(classOf[Client], test4), "Client4")
//  clientActor4 ! "start"
//
//
//  println("---test5---")
//  val test5 = List(22, 4, -1, 10, 3)
//  val clientActor5 = system.actorOf(Props(classOf[Client], test5), "Client5")
//  clientActor5 ! "start"
}
