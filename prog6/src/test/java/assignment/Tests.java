package assignment;

import junit.framework.Assert;
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
    public void checkIteratorSorted() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(3, 3);
        t.insert(2, 5);
        t.insert(7, 8);
        t.insert(6, 9);
        t.insert(5, 2);

        Iterator iter = t.iterator();

        int prev = -1;

        while (iter.hasNext()) {
            int curr = (int) iter.next();

            if (prev >= curr) {
                Assertions.assertTrue(false);
            }

            prev = curr;
        }

        Assertions.assertTrue(true);
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

    @Test
    public void checkJoinSize() {
        // We assume all nodes in t1 are less than those in t2
        Treap<Integer, Integer> t1 = new TreapMap<>();

        t1.insert(1, 0);
        t1.insert(3, 3);
        t1.insert(2, 5);

        Treap<Integer, Integer> t2 = new TreapMap<>();

        t2.insert(4, 6);
        t2.insert(6, 7);
        t2.insert(8, 9);

        t1.join(t2);

        Iterator iter = t1.iterator();
        int count = 0;

        while (iter.hasNext()) {
            iter.next();
            count++;
        }

        Assert.assertTrue(count == 6);
    }

    @Test
    public void checkJoinNodes() {
        // We assume all nodes in t1 are less than those in t2
        Treap<Integer, Integer> t1 = new TreapMap<>();

        t1.insert(1, 0);
        t1.insert(3, 3);
        t1.insert(2, 5);

        Treap<Integer, Integer> t2 = new TreapMap<>();

        t2.insert(4, 6);
        t2.insert(6, 7);
        t2.insert(8, 9);

        t1.join(t2);

        boolean test = t1.lookup(1) == 0 && t1.lookup(3) == 3 && t1.lookup(2) == 5 && t1.lookup(4) == 6 && t1.lookup(6) == 7 && t1.lookup(8) == 9;

        Assert.assertTrue(test);
    }

    @Test
    public void testSplitDifferentKey() {
        Treap<Integer, Integer> t1 = new TreapMap<>();

        t1.insert(1, 0);
        t1.insert(3, 3);
        t1.insert(2, 5);
        t1.insert(4, 6);
        t1.insert(6, 7);
        t1.insert(8, 9);

        Treap[] splits = t1.split(5);

        int split1 = 0;
        int split2 = 0;

        Iterator t2iter = splits[0].iterator();
        Iterator t3iter = splits[1].iterator();

        while (t2iter.hasNext()) {
            int curr = (int) t2iter.next();
            if (curr < 5) {
                split1++;
            }
        }

        while (t3iter.hasNext()) {
            int curr = (int) t3iter.next();
            if (curr > 5) {
                split2++;
            }
        }

        Assertions.assertTrue(split1 == 4 && split2 == 2);

    }

    @Test
    public void testSplitSameKey() {
        Treap<Integer, Integer> t1 = new TreapMap<>();

        t1.insert(1, 0);
        t1.insert(3, 3);
        t1.insert(2, 5);
        t1.insert(4, 6);
        t1.insert(6, 7);
        t1.insert(8, 9);

        Treap[] splits = t1.split(4);

        int split1 = 0;
        int split2 = 0;

        Iterator t2iter = splits[0].iterator();
        Iterator t3iter = splits[1].iterator();

        while (t2iter.hasNext()) {
            int curr = (int) t2iter.next();
            if (curr < 4) {
                split1++;
            }
        }

        while (t3iter.hasNext()) {
            int curr = (int) t3iter.next();
            if (curr >= 4) {
                split2++;
            }
        }

        Assertions.assertTrue(split1 == 3 && split2 == 3);
    }
}
