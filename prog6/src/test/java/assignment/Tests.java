package assignment;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Tests {

    // Check if the lookup function is functional
    @Test
    public void checkLookupContains() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        Assertions.assertTrue(t.lookup(1) != null);
    }

    // Check if lookup works if the key is not present in the Treap
    @Test
    public void checkLookupNoContain() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        Assertions.assertTrue(t.lookup(5) == null);
    }

    // Check if the treap contains the keys after inserting
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

    // Check if the values are inserted properly as well
    @Test
    public void checkInsertValue() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);

        boolean test = t.lookup(1) == 0 && t.lookup(2) == 5 && t.lookup(6) == 9;

        Assertions.assertTrue(test);
    }

    // Check if duplicate values can be inserted
    @Test
    public void checkInsertDuplicate() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);
        t.insert(1, 2);

        Assertions.assertTrue(t.lookup(1) == 2);
    }

    // Check if duplicate values can be inserted
    @Test
    public void checkInsertDuplicateSize() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);
        t.insert(1, 2);

        Iterator iter = t.iterator();
        int count = 0;

        while (iter.hasNext()) {
            iter.next();
            count++;
        }

        Assertions.assertTrue(count == 3);
    }

    // Check if we can remove a node that is present
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

    // Check if we can remove a node that is present
    @Test
    public void checkRemoveSize() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 0);
        t.insert(2, 5);
        t.insert(6, 9);
        t.insert(7, 8);
        t.insert(5, 2);
        t.insert(3, 3);

        t.remove(1);

        Iterator iter = t.iterator();
        int count = 0;

        while (iter.hasNext()) {
            iter.next();
            count++;
        }

        Assertions.assertTrue(count == 5);
    }

    // Check if the size of iterator is correct
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

    // Check if the iterator is sorted
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

    // Check if the iterator works for duplicate values (i.e. should be distinct)
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

    // Check if join method works (with regard to size)
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

    @Test
    public void rootIsNullJoin() {
        Treap<Integer, Integer> t = new TreapMap<>();

        t.join(null);

        int count = 0;

        Iterator i = t.iterator();

            while (i.hasNext()) {
            i.next();
            count++;
        }

        Assertions.assertTrue(count == 0);
    }

    @Test
    public void splitKeyIsNull() {
        Treap<Integer, Integer> t = new TreapMap<>();
        t.insert(1, 1);
        t.insert(2, 2);
        t.insert(3, 3);
        t.insert(4, 43);

        Treap[] splits = t.split(null);

        Assertions.assertTrue(true);
    }

    @Test
    public void toStringEmptyTree() {
        Treap<Integer, Integer> t = new TreapMap<>();

        String s = t.toString();

        Assertions.assertTrue(s.equals(""));
    }

    @Test
    public void lookupIsNull() {
        Treap<Integer, Integer> t = new TreapMap<>();

        t.lookup(null);

        Assertions.assertTrue(true);
    }

    @Test
    public void insertKeyIsNull() {
        Treap<Integer, Integer> t = new TreapMap<>();

        t.insert(null, 0);

        int count = 0;

        Iterator i = t.iterator();

        while (i.hasNext()) {
            i.next();
            count++;
        }

        Assertions.assertTrue(count == 0);
    }

    @Test
    public void insertValueIsNull() {
        Treap<Integer, Integer> t = new TreapMap<>();

        t.insert(0, null);

        int count = 0;

        Iterator i = t.iterator();

        while (i.hasNext()) {
            i.next();
            count++;
        }

        Assertions.assertTrue(count == 0);
    }

    @Test
    public void removeKeyIsNull() {
        Treap<Integer, Integer> t = new TreapMap<>();

        t.remove(null);

        Assertions.assertTrue(true);
    }

    @Test
    public void removeEmptyTree() {
        Treap<Integer, Integer> t = new TreapMap<>();

        Integer val = t.remove(0);

        Assertions.assertTrue(val == null);
    }

    // Test is tree is balanced
    @Test
    public void testBalancedTree() {
        Treap<Integer, Integer> t = new TreapMap<>();

        for (int i = 0; i < 100; i++) {
            t.insert((int) (Math.random() * 1000), 0);
        }

        System.out.println(t);
    }

    @Test
    public void testConcurrentModification() {
        Treap<Integer, Integer> map = new TreapMap<>();
        map.insert(1, 1);
        map.insert(3, 2);

        try {
            Iterator<Integer> i = map.iterator();
            i.next();
            map.insert(1, 4);
            i.next();
        } catch (ConcurrentModificationException e) {
            Assertions.assertTrue(true);
            return;
        }

        Assertions.assertTrue(false);
    }

    @Test
    public void testToString() {

    }
}
