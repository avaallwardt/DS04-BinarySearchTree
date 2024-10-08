import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyBST<Type> {

    /*
    -ask when corrections are due
    -where do we need to use a stack? -- we don't need to
     */

    Node head;

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    // done
    public boolean insert(int value, Type data){ // Mr. U said it would be ok to add element to the constructor
        if(data == null){
            System.out.println("Data provided is null.");
            return false;
        }
        if(head == null){
            head = new Node(data, value);
            return true;
        }
        return insertHelper(value, data, head); // then it will recursively call within the method until it returns true or false
        // does this work?
        // can data be null? is that ok or should i add a null check?
    }

    // done
    public boolean insertHelper(int value, Type data, Node element){
        Node node = new Node(data, value);
        if(value < element.getValue()){
            if(element.getLeftNode() != null){
                insertHelper(value, data, element.getLeftNode());
            }
            else{
                element.setLeftNode(node);
                //node.setRightNode(element);
                return true;
            }
        }
        else if(value > element.getValue()){
            if(element.getRightNode() != null){
                insertHelper(value, data, element.getRightNode());
            }
            else{
                element.setRightNode(node);
                //node.setLeftNode(element);
                return true;
            }
        }
        else if(value == element.getValue()){
            System.out.println("The value provided already exists in the tree.");
            return false;
        }
        return false;
    }


    // need to figure out head case!!!
    // create 3 remove helper methods for the 3 cases
    // remove with checking data --> check data to be null (both the value inserted in the parameter and the data in the instance variable because i am accepting elements with null data)
    public Type remove(Type element){
        if(element == null){
            System.out.println("Data provided is null.");
            return null;
        }
        if(head == null){
            System.out.println("Head is null.");
            return null;
        }

        if(head.getData().equals(element)){
            // need to figure out this head case
            Type temporary = head.getData();
            if(head.getRightNode() == null && head.getLeftNode() == null){
                head = null;
            }
            else if(head.getRightNode() != null && head.getLeftNode() != null){
                return removeHead();
            }
            else if(head.getRightNode() != null){
                head.setRightNode(null);
                head = head.getRightNode();
            }
            else if(head.getLeftNode() != null){
                head.setLeftNode(null);
                head = head.getLeftNode();
            }
            return temporary;
        }

        boolean removed = false; // represents if the item was removed or not

        // Using a breadth first search approach to find the node we need to remove
        Queue<Node> queue = new LinkedList();
        queue.add(head);
        while(!queue.isEmpty() && removed == false){
            Node current = queue.remove();
            if(current.getLeftNode() != null){
                queue.add(current.getLeftNode());
                // put it in here to keep track of the parent node to fix those links
                if(current.getLeftNode().getData().equals(element)){
                    Node nodeToRemove = current.getLeftNode();
                    if(nodeToRemove.getLeftNode() != null && nodeToRemove.getRightNode() != null){
                        // i can incoporate value when i am finding the largest of the left or smallest of the right here, right?
                        remove2Children(nodeToRemove, current, "left");
                    }
                    else if(nodeToRemove.getLeftNode() == null && nodeToRemove.getRightNode() == null){ // no children
                        removeNoChildren(nodeToRemove, current, "left");
                    }
                    else if(nodeToRemove.getLeftNode() != null){ // it is the left child that isn't null
                        remove1Child(nodeToRemove, current, "left", "left");
                    }
                    else if(nodeToRemove.getRightNode() != null){ // it is the right child that isn't null
                        remove1Child(nodeToRemove, current, "left", "right");
                    }
                    removed = true; // this variable does not really matter
                    return nodeToRemove.getData();
                }
            }
            if(current.getRightNode() != null){
                queue.add(current.getRightNode());
                if(current.getRightNode().getData().equals(element)){
                    Node nodeToRemove = current.getRightNode();
                    if(nodeToRemove.getLeftNode() != null && nodeToRemove.getRightNode() != null){
                        // i can incoporate value when i am finding the largest of the left or smallest of the right here, right?
                        remove2Children(nodeToRemove, current, "right");
                    }
                    else if(nodeToRemove.getLeftNode() == null && nodeToRemove.getRightNode() == null){ // no children
                        removeNoChildren(nodeToRemove, current, "right");
                    }
                    else if(nodeToRemove.getLeftNode() != null){ // it is the left child that isn't null
                        remove1Child(nodeToRemove, current, "right", "left");
                    }
                    else if(nodeToRemove.getRightNode() != null){ // it is the right child that isn't null
                        remove1Child(nodeToRemove, current, "right", "right");
                    }
                    removed = true;
                    return nodeToRemove.getData();
                }
            }
        }
        System.out.println("The element provided does not exist in the binary search tree.");
        return null;
    }

    // done
    public void removeNoChildren(Node nodeToRemove, Node parentNode, String NodeIsleftOrRight){
        if(nodeToRemove == null || parentNode == null || NodeIsleftOrRight == null){ // extra null checks just in case
            return;
        }
        if(NodeIsleftOrRight.equals("left")){
            parentNode.setLeftNode(null);
        }
        else if(NodeIsleftOrRight.equals("right")){
            parentNode.setRightNode(null);
        }
    } // i don't think i need the nodeToRemove parameter but can i keep it?

    // done
    public void remove1Child(Node nodeToRemove, Node parentNode, String NodeIsleftOrRight, String childIsLeftOrRight){
        if(nodeToRemove == null || parentNode == null || NodeIsleftOrRight == null || childIsLeftOrRight == null){ // extra null checks just in case
            return;
        }
        if(NodeIsleftOrRight.equals("left")){
            if(childIsLeftOrRight.equals("left")){
                parentNode.setLeftNode(nodeToRemove.getLeftNode());
            }
            else if(childIsLeftOrRight.equals("right")){
                parentNode.setLeftNode(nodeToRemove.getRightNode());
            }
        }
        else if(NodeIsleftOrRight.equals("right")){
            if(childIsLeftOrRight.equals("left")){
                parentNode.setRightNode(nodeToRemove.getLeftNode());
            }
            else if(childIsLeftOrRight.equals("right")){
                parentNode.setRightNode(nodeToRemove.getRightNode());
            }
        }
    }

    // done
    public void remove2Children(Node nodeToRemove, Node parentNode, String NodeIsleftOrRight){
        // do you prefer to find the largest in the left subtree or smallest in the right
        // i am doing the smallest in the right
        if(nodeToRemove == null || parentNode == null){ // extra null checks just in case
            return;
        }
        Node previous = nodeToRemove;
        Node current = nodeToRemove.getRightNode(); // don't need a null check bc we are calling this method when nodeToRemove has both a left and right node
        while(current.getLeftNode() != null){
            previous = current;
            current = current.getLeftNode();
        }
        Node rightNode = current.getRightNode(); // store the right node of the smallest to the right bc it will be changed once we give it the left and right node of the note to be replaced

        // this changes what the parent node points to
        if(NodeIsleftOrRight.equals("left")){
            parentNode.setLeftNode(current);
        }
        else if(NodeIsleftOrRight.equals("right")){
            parentNode.setRightNode(current);
        }

        // this updates the node smallest in the right to point to what the node we want to remove pointed to
        current.setLeftNode(nodeToRemove.getLeftNode());
        current.setRightNode(nodeToRemove.getRightNode());


        if(rightNode != null){ // we already know current.getLeftNode() is null
            previous.setLeftNode(rightNode);
        }
        else{
            previous.setLeftNode(null);
        }

    }

    public Type removeHead(){
        Type temporary = head.getData();
        if(head.getRightNode() == null && head.getLeftNode() == null){
            head = null;
        }
        else if(head.getRightNode() != null && head.getLeftNode() != null){
            Node previous = head;
            Node current = head.getRightNode(); // don't need a null check bc we are calling this method when nodeToRemove has both a left and right node
            while(current.getLeftNode() != null){
                previous = current;
                current = current.getLeftNode();
            }
            Node rightNode = current.getRightNode(); // store the right node of the smallest to the right bc it will be changed once we give it the left and right node of the note to be replaced

            // this updates the node smallest in the right to point to what the node we want to remove pointed to
            current.setLeftNode(head.getLeftNode());
            current.setRightNode(head.getRightNode());

            head = current;


            if(rightNode != null){ // we already know current.getLeftNode() is null
                previous.setLeftNode(rightNode);
            }
            else{
                previous.setLeftNode(null);
            }
        }
        else if(head.getRightNode() != null){
            head.setRightNode(null);
            head = head.getRightNode();
        }
        else if(head.getLeftNode() != null){
            head.setLeftNode(null);
            head = head.getLeftNode();
        }
        return temporary;
    }

    // if(currentNode.getLeftChild().getLeftChild())

    // done
    public Type remove(int value, Type data){
        // the rubric says to check equality of the element, but does that refer to the data in the node or the node itself? ==> data
        // if it refers to the entire node itself, value is an instance variable within node so do we need it in the parameters? ==> it refers to data not the entire node itself
        // ^^ in the example with adding and removing ryan and jesse, it looks like the "element" refers to the entire node object, so does it still need to pass in the value?
        if(data == null){
            System.out.println("Element provided is null.");
        }
        if(head == null){
            System.out.println("The head is null.");
            return null;
        }
        return removeWithValueHelper(value, data, head, null);
    }

    // done
    public Type removeWithValueHelper(int value, Type data, Node element, Node previous){
        System.out.println("start");

        if(element == null){
            return null;
        }

        if(element == head){
            System.out.println(head.getValue());
            if(head.getData().equals(data) && head.getValue() == value){
                return removeHead();
            }
        }

        System.out.println("after check");
        if(value < element.getValue()){
            if(element.getLeftNode() != null){
                removeWithValueHelper(value, data, element.getLeftNode(), element);
            }
            else{
                System.out.println("Node with given data and value not found.");
                return null;
            }
        }
        else if(value > element.getValue()){
            if(element.getRightNode() != null){
                removeWithValueHelper(value, data, element.getRightNode(), element);
            }
            else{
                System.out.println("Node with given data and value not found.");
                return null;
            }
        }
        else if(value == element.getValue()){
            if(data.equals(element.getData())){
                System.out.println("Node with given data and value found!");
                if(element.getLeftNode() != null && element.getRightNode() != null){
                    // i can incoporate value when i am finding the largest of the left or smallest of the right here, right?
                    remove2Children(element, previous, "left");
                }
                else if(element.getLeftNode() == null && element.getRightNode() == null){ // no children
                    if(previous.getRightNode() == element){
                        removeNoChildren(element, previous, "right");
                    }
                    else if(previous.getLeftNode() == element){
                        removeNoChildren(element, previous, "left");
                    }
                }
                else if(element.getLeftNode() != null){ // it is the left child that isn't null
                    if(previous.getRightNode() == element){
                        remove1Child(element, previous, "right", "left");
                    }
                    else if(previous.getLeftNode() == element){
                        remove1Child(element, previous, "left", "left");
                    }
                }
                else if(element.getRightNode() != null){ // it is the right child that isn't null
                    if(previous.getRightNode() == element){
                        remove1Child(element, previous, "right", "right");
                    }
                    else if(previous.getLeftNode() == element){
                        remove1Child(element, previous, "left", "right");
                    }
                }
                System.out.println("Node with given data and value removed successfully.");
                return data;
            }
            else{
                System.out.println("Node with value given found but not with data given.");
                return null; // it works to return null here bc there can only be one of a certain value in a bst
            }
        }
        return null;
    }


    // done
    public Type remove(int value){
        // use same approach as in insert
        if(head == null){
            System.out.println("The head is null.");
            return null;
        }
        return removeWithValueOnlyHelper(value, head, null);
    }

    // done
    public Type removeWithValueOnlyHelper(int value, Node element, Node previous){
        if(element == null){
            return null;
        }
        if(element == head){
            if(head.getValue() == value){
                return removeHead();
            }
        }

        if(value < element.getValue()){
            if(element.getLeftNode() != null){
                removeWithValueOnlyHelper(value, element.getLeftNode(), element);
            }
            else{
                System.out.println("Node with given data and value not found.");
                return null;
            }
        }
        else if(value > element.getValue()){
            if(element.getRightNode() != null){
                removeWithValueOnlyHelper(value, element.getRightNode(), element);
            }
            else{
                System.out.println("Node with given data and value not found.");
                return null;
            }
        }
        else if(value == element.getValue()){
            System.out.println("Node with given value found!");
            Type data = element.getData();
            if(element.getLeftNode() != null && element.getRightNode() != null){
                // i can incoporate value when i am finding the largest of the left or smallest of the right here, right?
                remove2Children(element, previous, "left");
            }
            else if(element.getLeftNode() == null && element.getRightNode() == null){ // no children
                if(previous.getRightNode() == element){
                    removeNoChildren(element, previous, "right");
                }
                else if(previous.getLeftNode() == element){
                    removeNoChildren(element, previous, "left");
                }
            }
            else if(element.getLeftNode() != null){ // it is the left child that isn't null
                if(previous.getRightNode() == element){
                    remove1Child(element, previous, "right", "left");
                }
                else if(previous.getLeftNode() == element){
                    remove1Child(element, previous, "left", "left");
                }
            }
            else if(element.getRightNode() != null){ // it is the right child that isn't null
                if(previous.getRightNode() == element){
                    remove1Child(element, previous, "right", "right");
                }
                else if(previous.getLeftNode() == element){
                    remove1Child(element, previous, "left", "right");
                }
            }
            System.out.println("Node with given value removed successfully.");
            return data;
        }
        return null;
    }

    // DONE
    public boolean isEmpty(){ //i can just check the head, right, because the head is the starting point for looking for other objects anyway
        if(head == null){
            return true;
        }
        else{
            return false;
        }
    }

    // DONE
    public boolean isHeightBalanced(){
        // is balanced just based on number of levels or also number of objects too?
        if(head == null){
            return true; // should height be balanced if there is nothing there?
        }
        Node current = head;
        Stack<Node> stack = new Stack<>();


        int left = leftHeightBalancedHelper(current, 0, 0);
        int right = rightHeightBalancedHelper(current, 0, 0);
        System.out.println("left: " + left + " right: " + right);
        if(left == right){
            System.out.println("Balanced!");
            return true;
        }
        else{
            System.out.println("Not balanced!");
            return false;
        }
    }

    public int leftHeightBalancedHelper(Node current, int currentLevel, int deepestLevel){
        currentLevel++;
        if(current.getLeftNode() == null && current.getRightNode() == null){
            if(currentLevel > deepestLevel){
                deepestLevel = currentLevel;
            }
            return deepestLevel;
        }
        if(current.getLeftNode() != null){
            deepestLevel = leftHeightBalancedHelper(current.getLeftNode(), currentLevel, deepestLevel);
            currentLevel--;
        }
        if(current != head){
            if(current.getRightNode() != null){
                deepestLevel = leftHeightBalancedHelper(current.getRightNode(), currentLevel, deepestLevel);
                currentLevel--;
            }
        }
        else if(current == head){
            return deepestLevel;
        }
        return deepestLevel;
    }

    public int rightHeightBalancedHelper(Node current, int currentLevel, int deepestLevel){
        currentLevel++;
        if(current.getLeftNode() == null && current.getRightNode() == null){
            if(currentLevel > deepestLevel){
                deepestLevel = currentLevel;
            }
            return deepestLevel;
        }
        if(current.getRightNode() != null){
            deepestLevel = rightHeightBalancedHelper(current.getRightNode(), currentLevel, deepestLevel);
            currentLevel--;
        }
        if(current != head){
            if(current.getLeftNode() != null){
                deepestLevel = rightHeightBalancedHelper(current.getLeftNode(), currentLevel, deepestLevel);
                currentLevel--;
            }
        }
        else{
            return deepestLevel;
        }
        return deepestLevel;
    }


    // DONE
    public void empty(){
        head = null;
    }

    // post order traversal
    // question - should i be trying to set the left and right nodes of everything to null? how would i do that with post order
    // should the baseline of the code i am writing for all the traversals be the same? i am really only changing when i m dealing with the node itself (as opposed to going right or left)
    public void emptyPostOrder(){
        if(head == null){
            return;
        }
        ArrayList<Node> arrayList = new ArrayList<>();
        emptyPostOrderHelper(head, arrayList);
        for(int i = 0; i < arrayList.size(); i++){
            remove(arrayList.get(i).getValue(), arrayList.get(i).getData());
        }
    }

    public void emptyPostOrderHelper(Node current, ArrayList<Node> arrayList){
        if(current.getLeftNode() != null){
            emptyPostOrderHelper(current.getLeftNode(), arrayList);
        }
        if(current.getRightNode() != null){
            emptyPostOrderHelper(current.getRightNode(), arrayList);
        }
        arrayList.add(current);
    }


    // DONE
    public int size(){
        int numNodes = 0;
        ArrayList<Type> arrayList = breadthFirstSearch(); // can I just call the breadthFirstSearch method here to calculate size with that or do i need to rewrite a traversal method?
        for(int i = 0; i < arrayList.size(); i++){
            numNodes++;
        }
        return numNodes;
    }

    // pre order traversal
    // MyBST should have a generic, right? bc the rubric doesn't include a generic in the return type for this method - yes
    public MyBST<Type> duplicatePreOrder(){
        // when i duplicate, do i literally have to make a copy of each object/node? - that is deep cloning. can do either. using insertion automatically creates a new node object for each
        // how do i clone it? - just insert it into a new tree

        if(head == null){
            System.out.println("The head is null.");
            return null;
        }
        MyBST<Type> bst = new MyBST<>();
        Node newHead = new Node(head.getData(), head.getValue()); // this is a deep clone
        bst.setHead(newHead); // can i set the head here?

        Stack<Node> stack = new Stack<>();
        stack.add(head);
        Node current = head;

        duplicatePreOrderHelper2(current, stack, bst);

        return bst;
    }

    public void duplicatePreOrderHelper2(Node current, Stack<Node> stack, MyBST<Type> bst) {
        while(current.getLeftNode() != null){
            stack.add(current.getLeftNode());

            bst.insert(current.getLeftNode().getValue(), current.getLeftNode().getData()); // can i just use insertion??? - yes

            current = current.getLeftNode();
        }
        Node currentNode = stack.pop();
        while(currentNode.getRightNode() == null){
            if(stack.isEmpty() == true){
                return;
            }
            currentNode = stack.pop();
            if(currentNode == null){
                return;
            }
        }
        // as soon as the right node is not null --> repeat entire thing

        if(currentNode.getRightNode() != null){
            stack.add(currentNode.getRightNode());
            System.out.println(currentNode.getRightNode());
            bst.insert(currentNode.getRightNode().getValue(), currentNode.getRightNode().getData());
        }

        duplicatePreOrderHelper2(currentNode.getRightNode(), stack, bst);
    }

    // in order traversal

    public ArrayList<Type> outputSorted(boolean isIncreasing){
        if(head == null){
            System.out.println("The BST does not exist because the head is null.");
            return null;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(head);
        Node current = head;

        ArrayList<Type> arrayList= new ArrayList<>();

        outputSortedHelper(current, stack, arrayList);

        if(isIncreasing == false){
            ArrayList<Type> decreasing = new ArrayList<>();
            for(int i = arrayList.size() - 1; i >= 0; i--){
                decreasing.add(arrayList.get(i));
            }
            return decreasing;
        }

        return arrayList;
    }

    public void outputSortedHelper(Node current, Stack<Node> stack, ArrayList<Type> arrayList){
        // can i just use the same logic for pre order traversal just add it to an array list after visiting the left tree as opposed to before?

        while(current.getLeftNode() != null){
            stack.add(current.getLeftNode());
            current = current.getLeftNode();
        }
        Node currentNode = stack.pop();
        arrayList.add(currentNode.getData()); // just add it to the array list / print it out in the middle of going to the left and going to the right

        while(currentNode.getRightNode() == null){
            if(stack.isEmpty()){
                return;
            }
            currentNode = stack.pop();
            arrayList.add(currentNode.getData());
            if(currentNode == null){
                return;
            }
        }
        // as soon as the right node is not null --> repeat entire thing

        if(currentNode.getRightNode() != null){
            stack.add(currentNode.getRightNode());
        }
        outputSortedHelper(currentNode.getRightNode(), stack, arrayList);
    }


    // DONE
    public ArrayList<Type> breadthFirstSearch(){ // so i return an array list of data, right?
        ArrayList<Type> arrayList = new ArrayList<>();
        if(head == null){
            System.out.println("The head is null.");
            return arrayList; // if head is null, should i return null or an empty array list?
        }
        Queue<Node> queue = new LinkedList();
        queue.add(head);
        while(!queue.isEmpty()){
            Node current = queue.remove();
            arrayList.add(current.getData());
            if(current.getLeftNode() != null){
                queue.add(current.getLeftNode());
            }
            if(current.getRightNode() != null){
                queue.add(current.getRightNode());
            }
        }
        return arrayList;
    }




    // for finding if balanced, can do a counter keeping track of how many nodes you have visited, or can insert nulls into the queue where they exist and do it that way
    // could add a height value in the node class --> but then would need to keep this value updated


    public class Node {
        Node rightNode;
        Node leftNode;
        int value;
        Type data;

        public Node(Type data, int value){
            this.data = data;
            this.value = value;
            leftNode = null;
            rightNode = null;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Type getData() {
            return data;
        }

        public void setData(Type data) {
            this.data = data;
        }
    }
}
