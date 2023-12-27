/*EMMANUELLA EYO 11291003 EEE917*/
import akka.actor._


object ShufflingProblem {
  case class ShuffleCards(cards: List[Int], nShuffles: Int, shuffling: Boolean)
  case class SplitCards(deck: List[Int])
  case class ShuffleHalfDeck(halfDeck: List[Int], cardCollector: ActorRef, numOfCards: Int)
  case class ShuffledDeck(deck: List[Int])
  case class CollectCard(card: Int)
  case class ShuffleDeck(deck: List[Int], shuffling: Boolean, collector: ActorRef)
}

class Shuffler extends Actor {
  import ShufflingProblem._

  var cards: List[Int] = List()
  var nShuffles: Int = 0
  var shuffling: Boolean = false
  var shuffledCards: List[Int] = List()

  def receive: PartialFunction[Any, Unit] = {
    case ShuffleCards(card, numShuffles, inOrOutShuffle) =>
      cards = card
      nShuffles = numShuffles
      shuffling = inOrOutShuffle

      val split = context.actorOf(Props[Splitter], "split")
      split ! SplitCards(cards)
      println(s"Shuffler: Sent SplitCards message to Splitter with cards $cards")

    case ShuffledDeck(cards) =>
      nShuffles -= 1
      if (nShuffles > 0) {
        val faroShufflerActor = context.actorOf(Props[FaroShuffler], "faroShuffler")
        faroShufflerActor ! ShuffleDeck(cards, shuffling, self)
        println(s"Shuffler: Sent ShuffleDeck message to FaroShuffler with cards $cards")
      } else {
        shuffledCards = cards
        sender() ! shuffledCards
        println(s"Shuffler: Sent ShuffledDeck message to sender with cards $cards")
      }
  }
}

class Splitter extends Actor {
  import ShufflingProblem._

  def receive: PartialFunction[Any, Unit] = {
    case SplitCards(cards) =>
      val mid = cards.length / 2
      val firstHalf = cards.take(mid)
      val secondHalf = cards.drop(mid)
      val faroShuffling = context.actorOf(Props[FaroShuffler], "faroShuffler")

      faroShuffling ! ShuffleDeck(firstHalf, true, self)
      println(s"Splitter: Sent ShuffleDeck message to ${faroShuffling.path.name} with first half of deck $firstHalf")
      faroShuffling ! ShuffleDeck(secondHalf, true, self)
      println(s"Splitter: Sent ShuffleDeck message to ${faroShuffling.path.name} with second half of deck $secondHalf")
  }
}


class FaroShuffler extends Actor {
  import ShufflingProblem._

  var shuffledHalfDeck1: List[Int] = List()
  var shuffledHalfDeck2: List[Int] = List()
  var isShuffling: Boolean = false
  var cardCollector: ActorRef = _

  def receive: PartialFunction[Any, Unit] = {
    case ShuffleDeck(halfDeck, outOrINShuffle, theCollector) =>
      isShuffling = outOrINShuffle
      cardCollector = theCollector
      if (isShuffling) {
        shuffleOut(halfDeck)
      } else {
        shuffleIn(halfDeck)
      }

    case ShuffledDeck(cards) =>
      println(s"FaroShuffler: Received ShuffledDeck message with cards $cards")
      cardCollector ! ShuffledDeck(cards)
      self ! PoisonPill
  }

  def shuffleOut(halfDeck: List[Int]): Unit = {
    shuffledHalfDeck1 = halfDeck.take(halfDeck.length / 2)
    shuffledHalfDeck2 = halfDeck.drop(halfDeck.length / 2)

    val shuffledDeck = for (i <- shuffledHalfDeck1.indices)
      yield if (i % 2 == 0) shuffledHalfDeck1(i / 2)
      else shuffledHalfDeck2(i / 2 + shuffledHalfDeck1.length / 2)

    val shuffledDeckList = shuffledDeck.toList

    println(s"FaroShuffler: Shuffled out deck $halfDeck to $shuffledDeckList")
    cardCollector ! ShuffledDeck(shuffledDeckList)
    self ! PoisonPill
  }


  def shuffleIn(halfDeck: List[Int]): Unit = {
    shuffledHalfDeck1 = halfDeck.take(halfDeck.length / 2)
    shuffledHalfDeck2 = halfDeck.drop(halfDeck.length / 2)


    val shuffledDeck = for (i <- shuffledHalfDeck2.indices.reverse)
      yield if (i % 2 == 0) shuffledHalfDeck2(i / 2) else shuffledHalfDeck1(i / 2)

    val shuffledDeckList = shuffledDeck.toList

    println(s"FaroShuffler: Shuffled in deck $halfDeck to $shuffledDeckList")
    cardCollector ! ShuffledDeck(shuffledDeckList) // send message to cardCollector actor
    self ! PoisonPill
  }

}

class CardCollector(expectedCards: Int, replyTo: ActorRef) extends Actor {
  import ShufflingProblem._

  var cards: List[Int] = List()

  def receive: PartialFunction[Any, Unit] = {
    case ShuffleHalfDeck(halfDeck, faroShuffler, numOfCards) =>
      val firstCard = if (numOfCards == expectedCards) halfDeck.head else halfDeck.last
      self ! CollectCard(firstCard)
      faroShuffler ! ShuffleDeck(halfDeck, true, self)

    case CollectCard(card) =>
      cards = card :: cards
      if (cards.length == expectedCards) {
        val shuffledDeck = ShuffledDeck(cards.reverse)
        replyTo ! shuffledDeck
        println(s"Shuffled deck: $shuffledDeck")
        self ! PoisonPill
      }

    case ShuffledDeck(shuffledCards) =>
      shuffledCards.foreach(card => self ! CollectCard(card))
  }
}



object Problem2 extends App {
  import ShufflingProblem._

  val system = ActorSystem("ShufflingSystem")
  val splitter = system.actorOf(Props[Splitter], "splitter")

  // create a card collector actor to receive the shuffled deck
  val cardCollector = system.actorOf(Props(new CardCollector(8, system.deadLetters)), "cardCollector")

  splitter ! SplitCards(List(1, 2, 3, 4, 5, 6, 7, 8))
  cardCollector ! CollectCard

}
