package base.structure;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @program: weiyang-code
 * @description: 堆 大根堆 父节点大于左右孩子
 * @author: wangzibin
 * @create: 2020-12-22 20:08
 **/
public class Heap {

    public static void main(String[] args) {
        Heap heap=new Heap(5);
        heap.push(3);
        heap.push(6);
        heap.push(8);
        heap.push(7);
        heap.push(9);
        System.out.println(Arrays.toString(heap.arr));
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        heap.push(12);
        System.out.println(heap.poll());
        System.out.println(Arrays.toString(heap.arr));
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        PriorityQueue<ListNode> queue=new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return 0;
            }
        });

    }

    private int heapSize;

    private int limit;

    private int[] arr;

    public Heap(int maxSize){
        limit=maxSize;
        arr=new int[maxSize+1];
    }

    //从 1 开始 左孩子 i*2 右孩子 i*2+1 父亲 i/2

    public void push(int val){
        if(heapSize==limit){
            throw new RuntimeException("heap is full");
        }
        arr[++heapSize]=val;
        heapInsert(heapSize);
    }

    public int poll(){
        if (heapSize==0){
            throw new RuntimeException("heap is empty");
        }
        int ans=arr[1];
        arr[1]=arr[heapSize--];
        heapify(1);
        return ans;
    }

    private void heapInsert(int curIndex) {
       /* int fatherIndex=curIndex/2;
        if (fatherIndex<=0){
            return;
        }
        if(arr[fatherIndex]<arr[curIndex]){
            swap(fatherIndex,curIndex);
            heapInsert(fatherIndex);
        }*/
        while (curIndex/2>0&&arr[curIndex/2]<arr[curIndex]){
            swap(curIndex/2,curIndex);
            curIndex/=2;
        }
    }

    private void heapify(int curIndex){
        int left=curIndex<<1;
        int right=curIndex<<1|1;
        if (left<heapSize&&arr[left]>arr[curIndex]){
            swap(left,curIndex);
            heapify(left);
        }
        if (right<heapSize&&arr[right]>arr[curIndex]){
            swap(right,curIndex);
            heapify(right);
        }

    }



    private void swap(int i, int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }


}