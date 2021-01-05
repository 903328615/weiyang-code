package leetcode.china;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @program: weiyang-code
 * @description: 435. 无重叠区间
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_435_无重叠区间 {

    public static void main(String[] args) {
        //int [][] a=new int[][]{{1,2},{2,3},{1,5},{2,9},{5,7},{1,3},{1,2},{1,2}};
        //[[1,100],[11,22],[1,11],[2,12]]
        int[][] a = new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println(eraseOverlapIntervals(a));
        System.out.println(Arrays.deepToString(a));
    }

    /*
    * 贪心
    */
    public static int eraseOverlapIntervalsOlogN(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[1] - interval2[1];
            }
        });

        int n = intervals.length;
        int right = intervals[0][1];
        int ans = 1;
        for (int i = 1; i < n; ++i) {
            if (intervals[i][0] >= right) {
                ++ans;
                right = intervals[i][1];
            }
        }
        return n - ans;
    }
    /*
    * 快排 + 动态规划 时间 O(NM) 空间 O(N)
    */
    public static int eraseOverlapIntervals(int[][] intervals) {
        quickSort(intervals);
        int ans = 0;
        if (intervals == null || intervals.length < 2) {
            return ans;
        }


        //dp[i] 含义为 以 i 区间结尾 的最大符合条件区间数量
        int[] dp = new int[intervals.length];
        int max = 0;
        for (int i = 1; i < intervals.length; i++) {
            for (int j = 0; j < i; j++) {
                if (intervals[j][1] <= intervals[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    if (max < dp[i]) {
                        max = dp[i];
                    }
                }
            }
        }
        max++;
        return intervals.length - max;
    }

    public static void quickSort(int[][] arr) {
        partition(arr, 0, arr.length - 1);
    }


    public static void partition(int[][] arr, int left, int right) {

        if (left >= right) {
            return;
        }
        int small = left - 1;
        int big = right + 1;
        int index = left;
        int mid = arr[right][0];
        while (index < big) {
            if (arr[index][0] < mid) {
                swap(arr, ++small, index++);
            } else if (arr[index][0] > mid) {
                swap(arr, --big, index);
            }else{
                index++;
            }
        }
        partition(arr, left, small);
        partition(arr, big, right);

    }

    public static void swap(int[][] arr, int i, int j) {
        int[] tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}