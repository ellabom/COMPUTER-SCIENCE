// Emmanuella eyo
// cmpt 215
// a4
// eee917
package lib280.tree;

public class AVLNode280<I extends Comparable<? super I>> extends BinaryNode280<I>{

    //height of the tree
    protected int height;
    protected int rHeight;
    protected int lHeight;

    /**
     * Construct a new node with item x.
     *
     * @timing Time = O(1)
     */
    public AVLNode280(I x){
        super(x);
        rHeight = 0;
        lHeight = 0;
        height = 0;
    }

    public I item(){
        return item;
    }

    public AVLNode280<I> leftNode(){
        return (AVLNode280<I>) super.leftNode;
    }

    public AVLNode280<I> rightNode(){
        return (AVLNode280<I>) super.rightNode;
    }


    /** Get the height of right node
     *
     * @return height of right node
     */
    public int getLHeight(){
        return lHeight;
    }

    /** Get the height of left node
     *
     * @return height of left node
     */
    public int getRHeight(){
        return rHeight;
    }

    public void setRHeight(int height){
        this.rHeight = height;
    }

    public void setlHeight(int height){
        this.lHeight = height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}


