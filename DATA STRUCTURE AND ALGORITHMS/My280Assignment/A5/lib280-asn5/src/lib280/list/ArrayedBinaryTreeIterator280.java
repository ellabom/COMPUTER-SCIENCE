package lib280.list;

import lib280.base.LinearIterator280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.ContainerEmpty280Exception;
import lib280.exception.NoCurrentItem280Exception;
import lib280.tree.ArrayedBinaryTreePosition280;

public class ArrayedBinaryTreeIterator280<I>
        extends ArrayedBinaryTreePosition280<I> implements LinearIterator280<I> {

    public ArrayedBinaryTreeIterator280(int pos) {
        super(pos);
    }

    @Override
    public I item() throws NoCurrentItem280Exception {
        return null;
    }

    @Override
    public boolean itemExists() {
        return false;
    }

    @Override
    public boolean before() {
        return false;
    }

    @Override
    public boolean after() {
        return false;
    }

    @Override
    public void goForth() throws AfterTheEnd280Exception {

    }

    @Override
    public void goFirst() throws ContainerEmpty280Exception {

    }

    @Override
    public void goBefore() {

    }

    @Override
    public void goAfter() {

    }
}
