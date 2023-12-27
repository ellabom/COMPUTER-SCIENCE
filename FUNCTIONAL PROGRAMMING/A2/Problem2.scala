//EMMANUELLA EYO 11291003 EEE917
sealed trait Tree[+A]
case class Node[A](left: Tree[A], value: A, right: Tree[A] ) extends Tree[A]
case class Leaf[A](value: A) extends Tree[A]

object Tree{

  def inOrder[A](tree: Tree[A]): List[A] = tree match {
    // traverse the left tree, print value, traverse the right tree
    case Node(left, value, right) =>
      inOrder(left) ++ List(value) ++ inOrder(right)
    case Leaf(value) => List(value)
  }

  def preOrder[A](tree: Tree[A]): List[A] = tree match {
    // travers the root, traverse the right, traverse
    case Node(left, value, right) => List(value) ++ preOrder(left) ++ preOrder(right)
    case Leaf(value) => List(value)
  }

  def postOrder[A](tree: Tree[A]): List[A] = tree match {
    case Node(left, value, right) => postOrder(left) ++ postOrder(right) ++ List(value)
    case Leaf(value) => List(value)
  }

  def search[A](tree: Tree[A], key: A): Boolean = {
    def traverse(tree: Tree[A]): Boolean = tree match {
      case Leaf(value) => value == key
      case Node(left, value, right) =>
        if (traverse(left)) true
        else if (value == key) true
        else if (traverse(right)) true
        else false
    }

    traverse(tree)
  }

  def main(args: Array[String]): Unit = {
    val tree = Node(Node(Leaf(1), 3, Leaf(4)), 5, Node(Leaf(6), 7, Leaf(8)))

    println(search(tree, 18))
  }
}
