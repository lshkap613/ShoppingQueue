import java.util.ArrayList;
import java.util.List;

public class PriorityHeap<T extends Comparable<T>> {

    // field
    private List<Shopper> heap;
    private int insertionCounter;

    //constructor
    public PriorityHeap() {
        this.heap = new ArrayList<>();
        insertionCounter = 0;
    }
    
    public Shopper peek() {
    	return heap.get(0);
    }

    //add to the queue
    public void enqueue(Shopper shopper) {
    	shopper.setCheckoutInsertionCounter(++insertionCounter);
        heap.add(shopper);
        heapifyUp(); // Restore heap property
    }

    //remove from the queue
    public Shopper dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        Shopper highestPriority = heap.get(0);
        int lastIndex = heap.size() - 1;
        // Move the last element of the array to the zeroth position
        heap.set(0, heap.get(lastIndex));
        // Delete from the back of the queue
        heap.remove(lastIndex);
        heapifyDown(); // Restore heap property
        return highestPriority;
    }

    //check if queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    //checks that the heap is keeping to the rules of a MAX heap
    //if it has a violation it swaps up until its corrected
    public void heapifyUp() {
        int currentIndex = heap.size() - 1;

        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;

            if (heap.get(currentIndex).compareTo(heap.get(parentIndex)) > 0) {
                // Swap elements if the current element has higher priority than its parent
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex; // Move up in the heap
            } else {
                break; // Heap property is restored
            }
        }
    }

    //checks that the heap is keeping to the rules of a MAX heap
    //if it has a violation it swaps down until its corrected
    public void heapifyDown() {
        int currentIndex = 0;
        int size = heap.size();

        while (true) {
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;
            int biggestChildIndex = currentIndex;

            // Find the biggest child (if any)
            // Compare current to contents of left child
            if (leftChildIndex < size && heap.get(leftChildIndex).compareTo(heap.get(biggestChildIndex)) > 0) {
                biggestChildIndex = leftChildIndex;
            } // Compare current to contents of right child
            if (rightChildIndex < size && heap.get(rightChildIndex).compareTo(heap.get(biggestChildIndex)) > 0) {
                biggestChildIndex = rightChildIndex;
            }

            if (biggestChildIndex != currentIndex) {
                // Swap elements if the biggest child has higher priority than the current
                // element
                swap(currentIndex, biggestChildIndex);
                currentIndex = biggestChildIndex; // Move down in the heap
            } else {
                break; // Heap property is restored
            }
        }
    }

    //swapping method
    public void swap(int i, int j) {
    	Shopper temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

}