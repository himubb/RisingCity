public class MinHeap{
  MinHeapNodeImpl[] minArr = new MinHeapNodeImpl[2000];
	int index = 0;
	
	//this method is used to insert element in minHeap array
	public void insert(MinHeapNodeImpl node) {
		minArr[index] = node;
		
		//Rearrange min heap after insertion
		minArr = fixAfterInsert(index, minArr);
		index += 1;
		return;
	}
	//method to rearrange minHeap array after insertion
	public static MinHeapNodeImpl[] fixAfterInsert(int index, MinHeapNodeImpl[] arr) {
		if(index == 0) {
			return arr;
		}
		
		int p;
		if(index%2 == 0)
			p = index/2 - 1;
		else
			p = (index-1)/2;
//Tying case while insert or if parent is greater that the current node swap values
		if(arr[p].getExecutedTime()> arr[index].getExecutedTime()|| (arr[p].getExecutedTime()== arr[index].getExecutedTime()&& arr[p].getBuildingNo() > arr[index].getBuildingNo())){
			MinHeapNodeImpl temp = arr[p];
			arr[p] = arr[index];
			arr[index] = temp;
			return fixAfterInsert(p, arr);
		}
		return arr;
	}
	
	//returns node with minimum execution time
	public MinHeapNodeImpl extractMin() {
		MinHeapNodeImpl min = minArr[0];
		minArr[0] = minArr[index-1];
		minArr[index-1] = null;
		index -= 1;
		minArr = heapify(minArr, index-1);
		return min; 
	}
  
  //get value of counter till which heapify has to run(n/2)
  public static int getVal(int i){

    if(i%2 == 0)
    return i/2;
  else
    return  (i- 1)/2;
  }
  //get leftChild
  public static int getleft(int i){
    return  2*i + 1;
  }
  //get rightChild
  public static int getRight(int i){

    return  2*i + 2;
  }
  //runs heapify on the array in logarithmic time
	public static MinHeapNodeImpl[] heapify(MinHeapNodeImpl[] arr, int index) {
		int i = 0;
		int count=getVal(index);
		
		
		while(i <= count) {
			int left = getleft(i);
			int right = getRight(i);
			int val;
			if(left > index && right > index)
				break;
			else if(right > index) {
				val = left;
			}
			else {
				if(arr[right].getExecutedTime()> arr[left].getExecutedTime())
          val = left;
          //tying case
				else if(arr[right].getExecutedTime()== arr[left].getExecutedTime()) {
					if(arr[right].getBuildingNo() > arr[left].getBuildingNo())
						val = left;
					else
						val = right;
				}
				else
					val = right;	
			}
			//swap only when node executed time is greater that the value of the node to be compared .If equal the use building number
			if(arr[i].getExecutedTime()> arr[val].getExecutedTime()|| (arr[i].getExecutedTime()== arr[val].getExecutedTime()&& arr[i].getBuildingNo() > arr[val].getBuildingNo())) {
				MinHeapNodeImpl temp = arr[i];
				arr[i] = arr[val];
				arr[val] = temp;
				i = val;
			}
			else
				break;
		}
		return arr;
	}

	
	//Check if heap is empty
  public boolean isEmpty(){
    return index == 0;
}

}