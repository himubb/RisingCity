public class MinHeapNodeImpl {

    private int buildingNo;
    private int totalTime;
    private int executedTime;
    private RbtNodeImpl rbtNode;

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

    public RbtNodeImpl getRbtNode() {
        return this.rbtNode;
    }

    public void setRbtNode(RbtNodeImpl rbtNode) {
        this.rbtNode = rbtNode;
    }

    //Constructor
    public MinHeapNodeImpl(int executedTime,int buildingNo,int totalTime){
        this.setBuildingNo(buildingNo);
        this.setExecutedTime(executedTime);
        this.setTotalTime(totalTime);
    }

    @Override
    public String toString() {
        return "BuildingNo: "+this.getBuildingNo()+",ExecutedTime: "+this.getExecutedTime();
    }
}
