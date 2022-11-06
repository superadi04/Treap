package assignment;

public class Driver extends MyTreap {
    public static void main(String[] args) {
        Treap<Integer, Integer> t = new MyTreap();

        t.insert(5, 3);
        t.insert(3, 2);
        t.insert(7, 3);
        t.insert(4, 3);

        t.remove(7);

        System.out.println(t.toString());
    }
}
