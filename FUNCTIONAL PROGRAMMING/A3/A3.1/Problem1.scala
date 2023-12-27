// EMMANUELLA EYO 11291003 EEEE917

object Problem1 {

  val royalParent: Map[String, (String, String, String)] = Map("George" -> ("m", "William", "Catherine"),
    "Charlotte" -> ("f", "William", "Catherine"), "Louis" -> ("m", "William", "Catherine"),
    "Archie" -> ("m", "Harry", "Meghan"),  "Lilibet" -> ("f", "Harry", "Meghan"), "Savannah" -> ("f", "Autumn", "Peter"),
    "Isla" -> ("f", "Autumn", "Peter"), "Mia" -> ("f", "Zara", "Mike"), "Lena" -> ("f", "Zara", "Mike"),
    "Lucas" -> ("m", "Zara", "Mike"), "Sienna" -> ("f", "Beatrice", "Edoardo"), "August" -> ("m", "Eugenie", "Jack"),
    "Beatrice" -> ("f", "Andrew", "Sarah"), "Eugenie" -> ("f", "Andrew", "Sarah"),
    "Louise" -> ("f", "Edward", "Sophie"), "James" -> ("m", "Edward", "Sophie"), "Peter" -> ("m", "Mark", "Anne"),
    "Zara" -> ("f", "Mark", "Anne"), "William" -> ("m", "Diana", "Charles"), "Harry" -> ("m", "Diana", "Charles"),
    "Charles" -> ("m", "Elizabeth", "Philip"), "Anne" -> ("f", "Elizabeth", "Philip"),
    "Andrew" -> ("m", "Elizabeth", "Philip"), "Edward" -> ("m", "Elizabeth", "Philip"), "Elizabeth" -> ("f", "", ""),
    "Philip" -> ("m", "", ""), "Diana" -> ("f", "", ""), "Mark" -> ("m", "", ""), "Sophie" -> ("f", "", ""),
    "Sarah" -> ("f", "", ""), "Mike" -> ("m", "", ""), "Autumn" -> ("f", "", ""), "Meghan" -> ("f", "", ""),
    "Catherine" -> ("f", "", ""), "Timothy" -> ("m", "", ""), "Jack" -> ("m", "", ""), "Camilla" -> ("f", "", ""),
    "Edoardo" -> ("m", "", ""));


  /**
   * Returns a list of children given the names of two parents
   * @param parent1 the name of the first parent
   * @param parent2 the name of the second parent
   * @return an Option list of strings representing the children's names, or None if no children found
   */
  def children(parent1: String, parent2: String): Option[List[String]] = {
    var childList: List[String] = List()

    //using a for-comprehension
    for{
      (c, (g, p1, p2)) <- royalParent if g == "f" || g == "m" if
        (p1 == parent1 && p2 == parent2) || (p1 == parent2 && p2 == parent1)
    } childList = c :: childList
    if (childList.nonEmpty) Some(childList)
    else None
    }


  /** helper function for grandparents
   * Returns a list of parents given the name of a child
   * @param child the name of the child
   * @return an Option containing a list of strings representing the parents' names, or None if no parents found
   */
  def parents(child: String): Option[List[String]] = {
    var pList: List[String] = List()
    for {
      (c, (g, p1, p2)) <- royalParent
      if c == child
      if p1.nonEmpty && p2.nonEmpty
    } pList = p1 :: p2 :: pList

    if (pList.nonEmpty) Some(pList)
    else None
  }


  /**
   * Returns a list of grandparents given the name of a child
   * @param p the name of the child
   * @return an Option list of strings representing the grandparents of p,
   *         or None if no grandparents found
   */
  def grandparents(p: String): Option[List[String]] = {
    parents(p).map { parentList =>
      for {
        parent <- parentList
        grandparent <- parents(parent).getOrElse(Nil).filter(_ != "")
      } yield grandparent
    }.filter(_.nonEmpty)
  }


  /**
   * HELPER FUNCTION FOR SISTER
   * Returns a list of siblings given the name of a person
   * @param p the name of the person
   * @return an Option list of strings representing the p's siblings' names,
   *         or f there is parentage information about a person, but no sibling can be found
   *         should return Some(Nil); if parentage information is unavailable, it should return None
   */
  def siblings(p: String): Option[List[String]] = {
    val parentList = parents(p)
    parentList match {
      case Some(List(p1, p2)) => children(p1, p2).map(_.filter(_ != p)) match {
        case Some(List()) => Some(Nil)
        case res => res
      }
      case _ => None
    }
  }

  /**
   * Returns a list of p's sisters
   * @param p the name of the person
   * @return an Option list of strings representing the p's sisters' names,
   *         or f there is parentage information about a person, but no sisters can be found
   *         should return Some(Nil); if parentage information is unavailable, it should return None
   */
  def sisters(p: String): Option[List[String]] = {
    siblings(p) match {
      case Some(siblings) => Some(siblings.filter(s => royalParent(s)._1 == "f"))
      case None => None
    }
  }

  /**
   * Returns a list of first cousins for a given person.
   *
   * @param p the name of the person
   * @return an optional list of the person's first cousins; returns None if the person has no first cousins or if
   *         the person is not in the royalParent map
   */

   def firstCousins(p: String): Option[List[String]] = {
     royalParent.get(p) match {
       case Some((_, parent1, parent2)) if parent1.nonEmpty =>
         val grandparentOpt = grandparents(p)
         Some(royalParent.collect {
           case (name, (_, gp1, gp2)) if grandparentOpt == grandparents(name) && gp1 != parent1 => name
         }.toList)
       case _ => None
     }
   }


  /** HELPER FUNCTION FOR UNCLES()
   * Returns a list of brothers for a given person.
   * @param p the name of the person
   * @return an optional list of the person's brothers; returns None if the person has no brothers or if the person is not in the royalParent map
   */
  def brothers(p: String): Option[List[String]] = {
    siblings(p) match {
      case Some(siblings) => Some(siblings.filter(s => royalParent(s)._1 == "m"))
      case None => None
    }
  }

  /**
   * Returns a list of uncles for a given person.
   * @param p the name of the person
   * @return an optional list of the person's uncles; returns None if
   *         the person has no uncles or if the person is not in the royalParent map
   */
  def uncles(p: String): Option[List[String]] = {
    parents(p) match {
      case Some(List(p1, p2)) =>
        val uncles = (brothers(p1).getOrElse(Nil) ++ brothers(p2).getOrElse(Nil))
        Some(uncles)
      case _ => None
    }
  }

}