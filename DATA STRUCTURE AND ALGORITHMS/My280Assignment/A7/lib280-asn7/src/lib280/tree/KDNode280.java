//Emmanuella Eyo eee917     11291003
package lib280.tree;
import lib280.base.NDPoint280;

public class KDNode280 extends BinaryNode280<NDPoint280> {

    /**
     * Construct a new node with item x.
     *
     * @param x the item placed in the new node
     * @timing Time = O(1)
     */
    public KDNode280(NDPoint280 x) {
        super(x);
    }

    public int dim(){
        return item().dim();
    }

    public KDNode280(NDPoint280 x, KDNode280 left, KDNode280 right ){
        super(x);
        this.leftNode = left;
        this.rightNode = right;

    }

    public NDPoint280 item(){
        return super.item();
    }

    public KDNode280 left(){
        return (KDNode280) this.leftNode;
    }

    public KDNode280 right(){
        return (KDNode280) this.rightNode;
    }
}
