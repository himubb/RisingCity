import java.util.LinkedList;
import java.util.List;

public class Rbt{

    public RbtNodeImpl defaultNode;
    public RbtNodeImpl root;

    //Constructor
    public Rbt(){
        defaultNode = RbtNodeImpl.defaultNode;
        root = defaultNode;
        root.setRight(defaultNode); 
        root.setLeft(defaultNode);
    }
//interface for search to be called from risingCity.java
    public RbtNodeImpl search(int buildingNum){
        return search(root, buildingNum);
    }

    //for searching the node with buildingNum(binary search)
    private RbtNodeImpl search(RbtNodeImpl root, int buildingNum){
        if (root == defaultNode){
            return null;
        }
        if (root.getBuildingNo() == buildingNum){
            return root;
        }
        else if (buildingNum < root.getBuildingNo()){
            return search(root.getLeft(), buildingNum);
        }
        else{
            return search(root.getRight(), buildingNum);
        }
    }

   

    //Interface for searching  all building nos between buildingNum1 and buildingNum2 returns String output with all values present.
    public String searchInRange(int buildingNum1, int buildingNum2){
        String list = "";
        list=searchInRange(root, list, buildingNum1, buildingNum2);
        return list;
    }
//get the output in String list  and implement search function
    private String searchInRange(RbtNodeImpl root, String list, int buildingNum1, int buildingNum2) {
        if (root == defaultNode) {
            return "";
        }
        if (buildingNum1 < root.getBuildingNo()) {
           list+= searchInRange(root.getLeft(), "", buildingNum1, buildingNum2);
        }

        if (buildingNum1 <= root.getBuildingNo() && buildingNum2 >= root.getBuildingNo()) {
            list+="("+Integer.toString(root.getBuildingNo())+","+Integer.toString(root.getHeapNode().getExecutedTime())+","+Integer.toString(root.getHeapNode().getTotalTime())+"),";
        }

        if (buildingNum2 > root.getBuildingNo()) {
            list+=searchInRange(root.getRight(), "", buildingNum1, buildingNum2);
        }
        return list;
    }


     // right rotation by update all pointers after performing rotation
    private RbtNodeImpl rightRotate(RbtNodeImpl parent){

        RbtNodeImpl leftchild = parent.getLeft();
        //RbtNodeImpl rightchild = parent.getRight();
        //RbtNodeImpl leftgrandchild = leftchild.getLeft();
        RbtNodeImpl rightgrandchild = leftchild.getRight();
        parent.setLeft(rightgrandchild);
        if(rightgrandchild != defaultNode){
            rightgrandchild.setParent(parent);
        }
        leftchild.setParent(parent.getParent());

        if (parent.getParent() == defaultNode){
            root = leftchild;
        }
        else if (parent== parent.getParent().getLeft()){
            parent.getParent().setLeft(leftchild);
        }
        else {
            parent.getParent().setRight(leftchild);
        }
       leftchild.setRight(parent);
       parent.setParent(leftchild);
        return leftchild;
    }

        // left rotation by update all pointers after performing rotation
    private RbtNodeImpl leftRotate(RbtNodeImpl parent){
        RbtNodeImpl rightchild = parent.getRight();
       // RbtNodeImpl leftchild = parent.getLeft();
        RbtNodeImpl leftgrandchild= rightchild.getLeft();
        //RbtNodeImpl rightgrandchild = rightchild.getRight();
       parent.setRight(leftgrandchild);
        if(leftgrandchild!= defaultNode){
            leftgrandchild.setParent(parent);
        }
        rightchild.setParent(parent.getParent());

        if (parent.getParent() == defaultNode){
            root = rightchild;
        }
        else if (parent== parent.getParent().getLeft()){
            parent.getParent().setLeft(rightchild);
        }
        else {
            parent.getParent().setRight(rightchild);
        }
        rightchild.setLeft(parent);
        parent.setParent(rightchild);
        return rightchild;
    }
//insert node interface in RBT ,if buildingNum is already present throw exception and stop execution
    public void insert(int buildingNum) throws Exception{
        RbtNodeImpl node= new RbtNodeImpl(buildingNum);
        if(search(node, buildingNum)==null)
            throw new Exception("Building number already exists");
        insertNode(node);
    }

    //performs insert operation by initializing node
    public void insertNode(RbtNodeImpl node){
        
        node.setColour("RED");
        //Update root if defaultNode
        if (root == defaultNode || root.getBuildingNo() == node.getBuildingNo()){
            root = node;
            root.setColour("BLACK"); //Root = black
            root.setParent(defaultNode);
            return;
        }
        insertOperation(root, node);//insertion
        fixAfterInsertion(node);//correct the violations of rbt
    }

    //performs insert operation wherever fall off the tree
    private void insertOperation(RbtNodeImpl root, RbtNodeImpl parent){
        if (parent.getBuildingNo() < root.getBuildingNo()){
            if (root.getLeft() == defaultNode){
                root.setLeft(parent);
                parent.setParent(root);
            }
            else {
                insertOperation(root.getLeft(), parent);
            }
        }
        else{
            if (root.getRight() == defaultNode){
                root.setRight(parent);
                parent.setParent(root);
            }
            else {
                insertOperation(root.getRight(), parent);
            }
        }
    }

   //fix violations after insertion by using properties of Rbt
    private void fixAfterInsertion(RbtNodeImpl child){
        //colour of p is null at this point
        RbtNodeImpl parent = defaultNode;
        RbtNodeImpl grandparent = defaultNode;
        if (child.getBuildingNo() == root.getBuildingNo()){
            child.setColour("BLACK");
            return;
        }
        while (root.getBuildingNo() != child.getBuildingNo() && child.getColour() != "BLACK" && child.getParent().getColour() == "RED"){
            parent=child.getParent();
            grandparent=parent.getParent();
            //LXy
            if (parent == grandparent.getLeft()){
                RbtNodeImpl uncle = grandparent.getRight();
                //when uncle is red
                if (uncle != defaultNode && uncle.getColour() == "RED"){
                    //3 node colours are changed 
                    parent.setColour("RED");
                    grandparent.setColour("BLACK");
                    uncle.setColour("BLACK");
                    child = grandparent;
                }
                else {
                    //LRb  when uncle is black
                    if (parent.getRight() == child){
                        parent = leftRotate(parent);
                        child = parent.getLeft();
                    }
                    //LLb  when uncle is black
                    rightRotate(grandparent);
                    swapColours(parent,grandparent);
                    child = parent;
                }

            }
            //RXy
            else if (parent == grandparent.getRight()){
                RbtNodeImpl uncle = grandparent.getLeft();
                //uncle is red
                if (uncle != defaultNode && uncle.getColour() == "RED"){
                    //3 node colours are changed 
                    grandparent.setColour("RED");
                    parent.setColour("BLACK");
                    uncle.setColour("BLACK");
                    child = grandparent;
                }
                else {
                    
                    //RLb uncle is Black
                    if (parent.getLeft() == child){
                        parent = rightRotate(parent);
                        child = parent.getRight();
                    }
                    //RRb uncleis Black
                    leftRotate(grandparent);
                    swapColours(parent,grandparent);
                    child = parent;
                }
            }
        }
        root.setColour("BLACK"); 
    }

    //fix the order of node  operation whenever delete is performed
    private void fixUp(RbtNodeImpl node1, RbtNodeImpl node2){
        if (node1.getParent() == defaultNode){
            root = node2;
        }
        else if (node1== node1.getParent().getLeft()){
            node1.getParent().setLeft(node2) ;
        }
        else {
            node1.getParent().setRight(node2) ;
        }
        node2.setParent(node1.getParent());
    }
//delete interface
    public boolean delete(RbtNodeImpl node){
        return delete(node.getBuildingNo());
    }

    //if the colour is red we do delete as we did in BST else we  delete and fix violations
    public boolean delete(int buildingNum){
        RbtNodeImpl result = search(root,buildingNum);
        if (result == null){
            return false;
        }
        RbtNodeImpl result1;
        RbtNodeImpl result2 = result;
        String realcolour = result.getColour();

        //left child is null
        if (result.getLeft() == defaultNode){
            result1 = result.getRight();
            fixUp(result, result.getRight());
        //right child is null
        } else if (result.getRight() == defaultNode){
            result1 = result.getLeft();
            fixUp(result, result.getLeft());
        //both children are not null
        } else {
            result2 = getMin(result.getRight());
            realcolour = result2.getColour();
            result1= result2.getRight();
            if (result2.getParent() == result) {
                result1.setParent(result2);
            }
            //fix right
            else {
                fixUp(result2, result2.getRight());
                result2.setRight(result.getRight());
                result2.getRight().setParent(result2);
            }
            fixUp(result, result2);
            result2.setLeft(result.getLeft());
            result2.getLeft().setParent(result2);
            result2.setColour(result.getColour());
        }
        //if red the no fixes are required
        if (realcolour == "BLACK") {
            fixAfterDeletion(result1);
        }
        return true;
    }

  //handle violations after deletion
    private void fixAfterDeletion(RbtNodeImpl node){

        while(node!=root && node.getColour() == "BLACK"){
            //If node is left child of parent
            if(node == node.getParent().getLeft()){
                //uncle holds the grandparent's right child
                RbtNodeImpl uncle = node.getParent().getRight();

                // uncle =red, left rotation is required
                if(uncle.getColour() == "RED"){
                    uncle.setColour("BLACK");
                    node.getParent().setColour("RED");
                    leftRotate(node.getParent());
                    uncle = node.getParent().getRight();
                }
                // uncle's both children = black, change the colour
                if(uncle.getLeft().getColour() == "BLACK" && uncle.getRight().getColour() == "BLACK"){
                    uncle.setColour("RED");
                    node = node.getParent();
                    continue;
                }
                //only right child = black, change the colour and right rotate
                else if(uncle.getRight().getColour() == "BLACK"){
                    uncle.getLeft().setColour("BLACK");
                    uncle.setColour("RED");
                    rightRotate(uncle);
                    uncle = node.getParent().getRight();
                }
                //uncle's right child =red, change the colour and rotate left
                if(uncle.getRight().getColour() == "RED"){
                    uncle.setColour(node.getParent().getColour());
                    node.getParent().setColour("BLACK");
                    uncle.getRight().setColour("BLACK");
                    leftRotate(node.getParent());
                    node = root;
                }

             //uncle = right child of gradparent
            } else {
                //uncle is parents left child
                RbtNodeImpl uncle = node.getParent().getLeft();

                //uncle is red, change the colour and right rotate
                if(uncle.getColour() == "RED"){
                    uncle.setColour("BLACK");
                    node.getParent().setColour("RED");
                    rightRotate(node.getParent());
                    uncle = node.getParent().getLeft();
                }

                //uncles's children = black, change uncle's colour
                if(uncle.getRight().getColour() == "BLACK" && uncle.getLeft().getColour() == "BLACK"){
                    uncle.setColour("RED");
                    node = node.getParent();
                    continue;
                }
                //uncle's left child= black, change the colour and left rotate
                else if(uncle.getLeft().getColour() == "BLACK"){
                    uncle.getRight().setColour("BLACK");
                    uncle.setColour("RED");
                    leftRotate(uncle);
                    uncle = node.getParent().getLeft();
                }
                //uncle's left child= red, change the colour and right rotate
                if(uncle.getLeft().getColour() == "RED"){
                    uncle.setColour(node.getParent().getColour());
                    node.getParent().setColour("BLACK");
                    uncle.getLeft().setColour("BLACK");
                    rightRotate(node.getParent());
                    node = root;
                }
            }
        }
        node.setColour("BLACK") ;
    }

    // Get minimum by getiing the leftmost node
    private RbtNodeImpl getMin(RbtNodeImpl root){
        while (root.getLeft() != defaultNode){
            root = root.getLeft();
        }
        return root;
    }
//swapColours
    private void swapColours(RbtNodeImpl node1, RbtNodeImpl node2) {
        String temp = node1.getColour();
        node1.setColour(node2.getColour());
        node2.setColour(temp);
    }

    
}