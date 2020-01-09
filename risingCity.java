import java.io.*;
import java.util.List;

public class risingCity {
    static public  Rbt mainRedBlackTree = new Rbt();//Main RBT storage
    public static MinHeap mainMinHeap = new MinHeap();//Main minheap storage
    static MinHeapNodeImpl currentBuilding = null;//current building on which work is going on
    static int global_time = 0;//Global time counter
    static int workFinishTime = 0;// Current job completion time
    static int currentBuildingCompletionTime = 0;//Current complete job completion time
    static FileWriter output;//To write output file
    static FileReader input = null;//to read input
    static BufferedReader bufRead = null;

    public static void main(String[] args) throws Exception {
        try {
            input = new FileReader(args[0]);
            bufRead = new BufferedReader(input);

            String myLine = null;
            int time = 0;

            output = new FileWriter("output_file.txt");
            //read input linewise
            while ((myLine = bufRead.readLine()) != null) {
                String[] array1 = myLine.split(": ");
                time = Integer.parseInt(array1[0]);
                //increment counter global time until time matches time in input file
                while (global_time < time) {
                    scheduler();
                    global_time += 1;
                    if (currentBuilding != null)
                        currentBuilding.setExecutedTime(currentBuilding.getExecutedTime() + 1);
                }
                    //Enter only when global time matches time in input
                if (global_time == time) {
                    String[] array2 = array1[1].split("\\(");
                    if (array2[0].trim().equals("Insert")) {
                        String str = array2[1].substring(0, array2[1].length() - 1);
                        String[] array4 = str.split(",");
                        int buildingNum = Integer.parseInt(array4[0]);
                        int timeRequired = Integer.parseInt(array4[1]);
                        insertUtil(buildingNum, timeRequired);
                    }
                    if (array2[0].trim().equals("PrintBuilding")) {
                        String str = array2[1].substring(0, array2[1].length() - 1);
                        String[] array3 = str.split(",");

                        if (array3.length == 1) {
                            int lowerRangeValue = Integer.parseInt(array3[0]);
                            printBuilding(lowerRangeValue);
                        }
                        if (array3.length == 2) {
                            int lowerRangeValue = Integer.parseInt(array3[0]);
                            int higherRangeValue = Integer.parseInt(array3[1]);
                            printBuilding(lowerRangeValue, higherRangeValue);

                        }

                    }
                }
                scheduler();

            }
            finishWork();// after all buildings have been inserted work on remaining buildings
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                bufRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void finishWork() throws IOException {
        while (currentBuilding != null) {
            scheduler();
            global_time += 1;
            if (currentBuilding != null)
                currentBuilding.setExecutedTime(currentBuilding.getExecutedTime() + 1);
        }
    }

    public static void insertUtil(int buildingNum, int timeRequired) {
        try {
            RbtNodeImpl rbtNode = new RbtNodeImpl(buildingNum, timeRequired);
            MinHeapNodeImpl minHeapNode = new MinHeapNodeImpl(0, buildingNum, timeRequired);
            rbtNode.setHeapNode(minHeapNode);
            minHeapNode.setRbtNode(rbtNode);
            mainRedBlackTree.insertNode(rbtNode);
            mainMinHeap.insert(minHeapNode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
//resets currentBuilding and its parameters.
    public static void resetCounter() {
        currentBuilding = null;
        workFinishTime = 0;
        currentBuildingCompletionTime = 0;
    }
//This function is used to perform operations and updating running time
    private static void scheduler() throws IOException {
//no building present
        if (currentBuilding == null) {
            if (mainMinHeap.isEmpty()) {
                return;
            } else {
                //add 5 days  and find the building completeion time
                currentBuilding = mainMinHeap.extractMin();
                workFinishTime = global_time + 5;
                currentBuildingCompletionTime = global_time + currentBuilding.getRbtNode().getTotalTime()
                        - currentBuilding.getExecutedTime();

            }
        } else {
            //will print the output after completion of execution and delete the building
            if (currentBuildingCompletionTime <= workFinishTime) {
                if (global_time == currentBuildingCompletionTime) {
                    output.write("(" + currentBuilding.getBuildingNo() + "," + global_time + ")\n");
                    mainRedBlackTree.delete(currentBuilding.getRbtNode());
                    resetCounter();
                    scheduler();// recursive call to run the next operation
                }
            } else {
                // 5 counter ends insert back to heap
                if (global_time == workFinishTime) {
                    mainMinHeap.insert(currentBuilding);
                    resetCounter();
                    scheduler();// recursive call to run the next operation
                }
            }

        }
    }
//Write out output
    private static void printBuilding(int buildingNum) throws IOException {
        RbtNodeImpl building = mainRedBlackTree.search(buildingNum);
        if (building == null)
            output.write("(0,0,0)\n");
        else if (building.getExecutedTime() == currentBuilding.getRbtNode().getExecutedTime())
            output.write("(" + building.getBuildingNo() + "," + currentBuilding.getExecutedTime() + ","
                    + building.getTotalTime() + ")\n");
        else
            output.write("(" + building.getBuildingNo() + "," + building.getHeapNode().getExecutedTime() + ","
                    + building.getTotalTime() + ")\n");

    }

    private static void printBuilding(int buildingNum1, int buildingNum2) throws IOException 
{
    
    String result=mainRedBlackTree.searchInRange(buildingNum1,buildingNum2);
    if (!result.equals("")){
        output.write(result.substring(0, result.length()-1)+"\n");
        }
    
    
}
    }