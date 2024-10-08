import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        MyBST<Double> bst = new MyBST<>();
        bst.insert(50, 50.0);
        bst.insert(20, 20.0);
        bst.insert(70, 70.0);
        bst.insert(10, 10.0);
        bst.insert(25, 25.0);

        bst.insert(60, 60.0);
        bst.insert(90, 90.0);
        bst.insert(5, 5.0);
        bst.insert(21, 21.0);
        bst.insert(75, 75.0);
        bst.insert(100, 100.0);
        bst.insert(110, 110.0);
        bst.insert(4,4.0);
        System.out.println(bst.size());

        bst.remove(50, 50.0);

        ArrayList<Double> list = bst.breadthFirstSearch();
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }

        MyBST<Double> bst2 = bst.duplicatePreOrder();
        System.out.println(bst2.size());
        ArrayList<Double> list2 = bst2.outputSorted(true);
        for(int i = 0; i < list2.size(); i++){
            System.out.println(list2.get(i));
        }



        MyBST<String> bst3 = new MyBST<>();
        bst.duplicatePreOrder();




    }
}