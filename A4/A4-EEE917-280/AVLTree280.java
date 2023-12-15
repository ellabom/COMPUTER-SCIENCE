// Emmanuella eyo
// cmpt 215
// a4
// eee917

package lib280.tree;

import lib280.base.Dispenser280;
import lib280.base.Searchable280;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.ContainerFull280Exception;
import lib280.exception.DuplicateItems280Exception;
import lib280.exception.NoCurrentItem280Exception;

public class AVLTree280<I extends Comparable<? super I>> extends LinkedSimpleTree280<I>
        implements Dispenser280<I>, Searchable280<I>{

    /** the current node as set by search. */
    protected BinaryNode280<I> cur;

    /** the parent node of the current node as set by search.*/
    protected BinaryNode280<I> parent;

    /** Do searches continue?. */
    protected boolean searchesContinue = false;

    /** are equality comparisons done using object reference comparison*/
    protected boolean objectReferenceComparison = false;

    /** Create an empty tree */

    /** Create a new node that is appropriate to this lib280.tree*/
    protected AVLNode280<I> creatNewNode(I item){
        return new AVLNode280<I>(item);
    }


    /** is the lib280.tree empty? */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /** Is the lib280.tree full? */
    @Override
    public boolean isFull() {
        return super.isFull();
    }

    @Override
    public I item() throws NoCurrentItem280Exception {
        if(!itemExists()){
            throw new NoCurrentItem280Exception("Cannot access item when it does not exist");
        }
        return cur.item();
    }

    @Override
    public boolean itemExists() {
        return cur != null;
    }


    /** Left subtree of the root]
     *  Analysis: Time = O(1)
     *  Precondition:
     *      !isEmpty()
     *      */
    public AVLTree280<I> rootRightSubtree() throws ContainerEmpty280Exception{
        return (AVLTree280<I>) super.rootRightSubtree();
    }

    /** Right subtree of the root.
     * Analysis: Time = O(1)
     * Precondition:
     *           !isEmpty()*/
    public AVLTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception {
        return (AVLTree280<I>) super.rootLeftSubtree();
    }

    /** Set the left subtree to t (set isEmpty if t== null).
     *  Analysis: Time = O(1)
     *  Precondition:
     *          !isEmpty()
     *          values in the new left subtree are less than rootItem()
     *          */
    public void setRootLeftSubtree(AVLTree280<I> t) throws ContainerEmpty280Exception{
        if(isEmpty())
            throw new ContainerEmpty280Exception("Cannot see subtree of an empty lib280.tree");

        if(t != null)
            rootNode.setLeftNode(t.rootNode());
        else
            rootNode.setLeftNode(null);
    }

    /** Set the right subtree to t (set isEmpty if t == null)
     *  Analysis: Time = O(1)
     *  Precondition:
     *      isEmpty()
     *      values in the new right subtree are less than rootItem()
     *
     * @param t
     * @throws ContainerEmpty280Exception
     */
    public void setRootRightSubtree(AVLTree280<I> t) throws ContainerEmpty280Exception{
        if(isEmpty())
            throw new ContainerEmpty280Exception("Cannot see subtree of an empty lib280.tree");
        if(t != null)
            rootNode.setRightNode(t.rootNode);
        else
            rootNode.setRightNode(null);
    }

    public AVLNode280<I> rootNode(){
        return (AVLNode280<I>) super.rootNode();
    }

    /** Is the current position below the bottom ogf the lib280.tree?.*/
    protected boolean below(){return (cur == null) && (parent != null || isEmpty());}

    /** Is the current position above the root of the lib280.tree? */
    protected boolean above(){return (parent == null) && (cur == null);}


    private void insert(I x, AVLNode280<I> r) {

        // if tree is empty, make x the root of lib280.tree
        if (this.isEmpty()) {
            this.setRootNode(this.creatNewNode(x));
        }


        if(x.compareTo(this.rootNode().item()) > 0){

            // go right
            if(this.rootNode().rightNode() == null){
                this.rootNode().setRightNode(new AVLNode280<I>(x));

            }else{
                this.rootRightSubtree().insert(x, this.rootNode());
            }
            this.rootNode().setRHeight(Math.max(this.rootNode().rightNode().getLHeight(),
                    this.rootNode().rightNode().getRHeight()) + 1);
        }

        else{

            //go left
            if(this.rootNode().leftNode() != null){
                this.rootLeftSubtree().insert(x, this.rootNode());
            }else{
                this.rootNode().setLeftNode(new AVLNode280<I>(x));
            }

            this.rootNode().setlHeight(Math.max(this.rootNode().leftNode().getLHeight(),
                    this.rootNode().leftNode().getRHeight()) + 1);
        }
        restoreAVLProperty(r);
    }

    @Override
    public void insert(I x) throws ContainerFull280Exception, DuplicateItems280Exception {

        insert(x, null);
    }

    @Override
    public void deleteItem() throws NoCurrentItem280Exception {



    }

    protected void delete(I x, AVLNode280<I> root) throws NoCurrentItem280Exception{

        if(root == null){
            throw new NoCurrentItem280Exception("Cannot access data that is not in the tree");
        }

        boolean foundReplacement = false;
        AVLNode280<I> replaceNode = null;
        if(x == root.item()){

            if(root.rightNode() == null){
                replaceNode = root.leftNode();
                foundReplacement = true;
            }

            else if(root.leftNode == null){
                replaceNode = root.rightNode();
                foundReplacement = true;
            }
            else{
                foundReplacement = false;
            }

            if(foundReplacement){
                if(parent == null){
                    setRootNode(replaceNode);
                }
                else if(parent == root.leftNode()){
                    parent.setLeftNode(replaceNode);
                }
                else{
                    parent.setRightNode(replaceNode);
                }
            }
        }

        else if (x.compareTo(root.item()) < 0){
            this.rootLeftSubtree().delete(x, root.leftNode());

        }
        else{
            this.rootRightSubtree().delete(x, root.rightNode());
        }

        root.setHeight(Math.max(root.getLHeight(), root.getLHeight()) + 1);
        restoreAVLProperty(root);

    }

    /*** Determine the imbalance of the tree
     * @param node: node in a lib280.tree
     * @return the imbalance of the tree
     */
    public int signed_imbalance(AVLNode280<I> node){
        return node.getLHeight() - node.getRHeight();
    }

    /**
     *
     * @param root root of a lib280.tree
     */

    private AVLNode280<I> RightRotate(AVLNode280<I> root){

        AVLNode280<I> nodeB = root.leftNode();       // node B
        root.leftNode = nodeB.rightNode();           // node E
        nodeB.rightNode = root;                      //node A

        root.setHeight(Math.max(root.getLHeight(), root.getRHeight()) + 1);
        nodeB.setHeight(Math.max(nodeB.getLHeight(), nodeB.getRHeight()) + 1);

        return nodeB;
    }

    /**
     *
     * @param root root of lib280.tree
     */
    private AVLNode280<I> LeftRotate(AVLNode280<I> root){

        AVLNode280<I> nodeC = root.rightNode();
        root.rightNode = nodeC.leftNode();
        nodeC.leftNode = root;

        root.setlHeight(Math.max(root.leftNode().getLHeight(), root.leftNode().getRHeight()) + 1);
        root.setRHeight(Math.max(root.rightNode().getLHeight(), root.rightNode().getRHeight()) + 1);

        nodeC.setlHeight(Math.max(nodeC.leftNode().getLHeight(), nodeC.leftNode().getRHeight()) + 1);
        nodeC.setRHeight(Math.max(nodeC.rightNode().getLHeight(), nodeC.rightNode().getRHeight()) + 1);

        return nodeC;

    }

    /**
     *
     * @param node possible critical node
     */
    public void restoreAVLProperty(AVLNode280<I> node) {

        int imbalanceR = this.signed_imbalance(rootNode());

        if (Math.abs(imbalanceR) <= 1)
            return;                             //imbalance is 1 or 0
        // node is not critical

        if (imbalanceR == 2) {                               // node is left heavy
            if (signed_imbalance(this.rootNode().leftNode()) >= 0) {   // node.left is left heavy
                this.RightRotate(node);                          // LL imbalance, Do right rotation
            } else {
                this.LeftRotate(node.rightNode());   // LR imbalance! Do double right rotation
                this.RightRotate(node);
            }
        } else {
            if (signed_imbalance(this.rootNode().rightNode()) <= 0) {     // node is right heavy
                this.LeftRotate(node);
            } else {
                this.RightRotate(node.rightNode());              // RL imbalance, DO double-left rotation.
                this.LeftRotate(node);
            }
        }

    }


    /**
     *
     * @param y item whose presence is to be determined
     * @return a boolean value whether the lib280.tree has item y
     */
    @Override
    public boolean has(I y) {
        BinaryNode280<I> saveParent = parent;
        BinaryNode280<I> saveCur = cur;
        boolean saveSearchContinue = this.searchesContinue;

        // Search
        this.search(y);
        boolean result = itemExists();

        // Restore cursor state and search restart state
        this.parent = saveParent;
        this.cur = saveCur;
        this.searchesContinue = saveSearchContinue;
        return result;
    }

    /**
     *
     * @param x item to be compared to y
     * @param y item to be compared to x
     * @return
     */
    @Override
    public boolean membershipEquals(I x, I y) {

        if(objectReferenceComparison)
            return x == y;
        else if((x instanceof Comparable) && (y instanceof Comparable))
            return 0 == x.compareTo(y);
        else if (x.equals(y))
            return true;
        else
            return false;
    }

    /**Go to item x, if its is in the lib280.tree */
    @Override
    public void search(I x) {

        boolean found = false;
        if (!searchesContinue || above()){
            parent = null;
            cur = rootNode;
        }
        else if(!below()){
            parent = cur;
            cur = cur.rightNode();
        }
        while(!found && itemExists()){
            if(x.compareTo(item()) < 0){
                parent = cur;
                cur = parent.leftNode();
            }
            else if(x.compareTo(item()) > 0){
                parent = cur;
                cur = parent.rightNode();
            }else{
                found = true;
            }
        }


    }


    /**
     *
     */
    @Override
    public void restartSearches() { searchesContinue = false;}

    /**
     *
     */
    @Override
    public void resumeSearches() { searchesContinue = true;}

    /** Form a string representation that includes level numbers.
     * @param i the level for the root of this (sub)lib280.tree*/
    protected String toStringByLevel(int i)
    {
        StringBuffer blanks = new StringBuffer((i - 1) * 5);
        for (int j = 0; j < i - 1; j++)
            blanks.append("     ");

        String result = new String();
        if (!isEmpty() && (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty()))
            result += rootRightSubtree().toStringByLevel(i+1);

        result += "\n" + blanks + i + ": " ;
        if (isEmpty())
            result += "-";
        else
        {
            result += rootItem();
            if (!rootLeftSubtree().isEmpty() || !rootRightSubtree().isEmpty())
                result += rootLeftSubtree().toStringByLevel(i+1);
        }
        return result;
    }

    /** String representation of the lib280.tree, level by level.*/
    public String toStringByLevel(){ return toStringByLevel(1);}

    /** String containing an inorder list of the items of the lib280.tree*/
    public String toString(){
        if(isEmpty())
            return "";
        else
            return rootNode().toString();
    }

    public static void main (String[] args) {

        AVLTree280<Integer> tree = new AVLTree280<>();

        if(!tree.isEmpty()){
            System.out.println("Tree is expected to be empty.");
        }
        tree.insert(17);
        if(tree.rootNode.item() != 17){
            System.out.println("Root node should be 17");
        }

        tree.insert(22);
        tree.insert(34);
        tree.insert(2);
        System.out.println(tree.toStringByLevel());


        // Test insert()
    }
}
