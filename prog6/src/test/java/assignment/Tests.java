package assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;

public class Tests {

    @Test
    public void checkLookupContains() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        Assertions.assertTrue(t.lookup(1) != null);
    }

    @Test
    public void checkLookupNoContain() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        Assertions.assertTrue(t.lookup(5) == null);
    }

    @Test
    public void checkInsertOne() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        Iterator iter = t.iterator();
        boolean test = false;

        while (iter.hasNext()) {
            int curr = (int) iter.next();
            if (curr == 2) {
                test = true;
            }
        }

        Assertions.assertTrue(test);
    }

    @Test
    public void checkInsertValue() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        boolean test = t.lookup(1) == 0 && t.lookup(2) == 5 && t.lookup(6) == 9;

        Assertions.assertTrue(test);
    }

    @Test
    public void checkInsertDuplicate() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);
        t.insert(1, 2);

        Assertions.assertTrue(t.lookup(1) == 2);
    }

    @Test
    public void checkRemovePresent() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);
        t.insert(7, 8);
        t.insert(5, 2);
        t.insert(3, 3);

        Assertions.assertTrue(t.remove(1) == 0);
    }

    @Test
    public void checkIteratorSize() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(3, 3);
        t.insert(2, 5);
        t.insert(7, 8);
        t.insert(6, 9);
        t.insert(5, 2);

        Iterator iter = t.iterator();

        int count = 0;

        while (iter.hasNext()) {
            iter.next();
            count++;
        }

        Assertions.assertTrue(count == 6);
    }

    @Test
    public void checkIteratorSizeDuplicate() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(3, 3);
        t.insert(2, 5);
        t.insert(7, 8);
        t.insert(6, 9);
        t.insert(5, 2);
        t.insert(5, 21);

        Iterator iter = t.iterator();

        int count = 0;

        while (iter.hasNext()) {
            iter.next();
            count++;
        }

        Assertions.assertTrue(count == 6);
    }
}
