public class RbtNodeImpl {


    private int buildingNo;
    private int totalTime;
    private int executedTime;
    private RbtNodeImpl left = defaultNode;
    private RbtNodeImpl right = defaultNode;
    private RbtNodeImpl parent = defaultNode;
    private String colour;
    private MinHeapNodeImpl heapNode;
    public static final RbtNodeImpl defaultNode = new RbtNodeImpl(Integer.MIN_VALUE,"Black");

    public int getBuildingNo() {
        return this.buildingNo;
    }

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getExecutedTime() {
        return this.executedTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    public RbtNodeImpl getLeft() {
        return this.left;
    }

    public void setLeft(RbtNodeImpl left) {
        this.left = left;
    }

    public RbtNodeImpl getRight() {
        return this.right;
    }

    public void setRight(RbtNodeImpl right) {
        this.right = right;
    }

    public RbtNodeImpl getParent() {
        return this.parent;
    }

    public void setParent(RbtNodeImpl parent) {
        this.parent = parent;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public MinHeapNodeImpl getHeapNode() {
        return this.heapNode;
    }

    public void setHeapNode(MinHeapNodeImpl heapNode) {
        this.heapNode = heapNode;
    }

    public RbtNodeImpl(int buildingNo, MinHeapNodeImpl minHeapNode){
        this.setBuildingNo(buildingNo);
        this.setHeapNode(minHeapNode);
    }

    public RbtNodeImpl(int buildingNo,int totalTime){
        this.setBuildingNo(buildingNo);
        this.setTotalTime(totalTime);
    }

    public RbtNodeImpl(int buildingNo){
        this.setBuildingNo(buildingNo);
        
    }
    public RbtNodeImpl(int buildingNo, String colour){
        this.setBuildingNo(buildingNo);
        this.setColour(colour);
    }

    @Override
    public String toString() {
        return "BuildingNo: "+this.getBuildingNo()+",Colour: "+this.getColour();
    }
}