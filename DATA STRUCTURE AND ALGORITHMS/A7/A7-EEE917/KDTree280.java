//Emmanuella Eyo eee917     11291003


package lib280.tree;

import lib280.base.NDPoint280;

public class KDTree280 {


    protected KDNode280 root;
    protected int dimension;

    public KDTree280(int dimension){
        this.dimension = dimension;
        this.root = null;
    }

    /**
     * Partition a subarray using its last element as pivot
     * @param list array of comparable elements
     * @param left lower limit on subarray to be partitioned
     * @param right upper limit on subarray to be partitioned
     * @precond     all elements in "list" are unique
     * @postcond    all elements smaller than the pivot appear in the leftmost
     *              part of the subarray, then the pivot element, followed by
     *              the elements larger than the pivot.
     *              (no guarantee about ordering)
     *
     * @return the offset at which the pivot element ended up
     */
    private int partition(NDPoint280[] list, int left, int right, int d){

        NDPoint280 pivot = list[right];
        int swapOffset = left;

        for(int i = left; i < right; i++){
            if( list[i].idx(d) <= pivot.idx(d)) {

                NDPoint280 t = list[swapOffset];
                list[swapOffset] = list[i];
                list[i] = t;
                swapOffset += 1;
            }
        }

        NDPoint280 temp = list[swapOffset];
        list[swapOffset] = list[right];
        list[right] = temp;

        return swapOffset;
    }


    /**
     * To find the median of the subarray between array indices 'left' and 'right',
     * pass in j = (right+left)/2
     * @param list array of comparable elements
     * @param left offset of start of subarray for which we want the median element
     * @param right offset of end of subarray  for which we want the median element
     * @param j     we want to find the element that belongs at array index j
     * @precond     LEFT <= J <= RIGHT
     * @precond     all elements in 'list' are unique
     * @postcond    the element x that belongs at the index j is the subarray were
     *              sorted is the position j. Elements in the subarray smaller than
     *              x are to the left of offset j and the element in the subarray larger than
     *              x are to the right of offset j.
     */
    private void jSmallest(NDPoint280[] list, int left, int right, int j, int d){

        if(right > left){
            int pivotIndex = partition(list, left, right, d);

            // if the position j is smaller than the pivot index,
            // recursively look for the j-th smallest element in that subarray.
            if(j < pivotIndex)
                jSmallest(list, left, pivotIndex - 1, j, d);

            //Otherwise, the position j must be larger than the pivotIndex,
            //so the j-th smallest element must be between pivotIndex+1 and right
            else if (j > pivotIndex)
                jSmallest(list, pivotIndex+1, right, j, d);
        }

    }

    /**
     *
     * @param pointArray array of k-dimensional points
     * @param left  offset of start of subarray from which to build a kd-tree
     * @param right offset of end of subarray from which to build a kd-tree
     * @param depth the current depth in the partially built tree
     * @return
     */
    protected KDNode280 kdTree(NDPoint280[] pointArray, int left, int right, int depth){

        if(pointArray == null)
            return null;

        //select axis based on depth so that axis cycles through all
        //valid values
        int d = depth % this.dimension;
        int medianOffset = (left + right)/2;

        //put the median element in the correct position
        jSmallest(pointArray, left, right, d, medianOffset);

        //create node and construct subtree
        KDNode280 node = new KDNode280(pointArray[medianOffset]);
        node.setItem(pointArray[medianOffset]);

        node.setLeftNode(kdTree(pointArray, left, medianOffset-1, depth+1));

        node.setRightNode(kdTree(pointArray, medianOffset+1, right, depth+1));

        return node;

    }

    public void kdtree(NDPoint280[] pointArray){
        this.root = kdTree(pointArray, 0, pointArray.length-1, 0);
    }

    public String toStringByLevel(KDNode280 root, int i){
        StringBuffer blanks = new StringBuffer((i - 1) * 5);
        for (int j = 0; j < i - 1; j++)
            blanks.append("            ");

        String result = new String();
        if (root != null && (root.left() != null || root.right() != null))
            result += toStringByLevel(root.right(),i+1);

        result += "\n" + blanks + i + "/";
        if( root != null)
            result +=  "/";
        result += ": ";
        if (root == null)
            result += "-";
        else
        {
            result += root.toString();
            if (root.right() != null || root.left() != null)
                result += toStringByLevel(root.left(),i+1);
        }
        return result;
    }



    public static void main(String[] args){

        double[] pt1 = {5.0, 2.0}; double[] pt2 = {9.0, 10.0};
        double[] pt3 = {11.0, 1.0}; double[] pt4 = {4.0, 3.0};
        double[] pt5 = {2.0, 12.0}; double[] pt6 = {3.0, 7.0};
        double[] pt7 = {1.0, 5.0};

        NDPoint280[] list = new NDPoint280[7];
        list[0] = new NDPoint280(pt1); list[1] = new NDPoint280(pt2);
        list[2] = new NDPoint280(pt3); list[3] = new NDPoint280(pt4);
        list[4] = new NDPoint280(pt5); list[5] = new NDPoint280(pt6);
        list[6] = new NDPoint280(pt7);


        KDTree280 tree280 = new KDTree280(2);
        System.out.println("Input 2D points:");
        for(int i = 0; i < 7; i++){
            System.out.println(list[i].toString());
        }

        System.out.println("\nThe 2D lib280.tree built from these point is:");
        tree280.kdtree(list);

        System.out.println(tree280);



    }
}

