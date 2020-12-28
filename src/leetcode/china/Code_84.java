package leetcode.china;

import java.util.Stack;

/**
 * @program: weiyang-code
 * 84. 柱状图中最大的矩形
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 * @description:
 * @author: wangzibin
 * @create: 2020-12-26 18:10
 **/
public class Code_84 {

    /**
     * 这道题思路变换一下，求 i 的最大面积 即 找到左边最近小于 i 的值 和右边 最近小于 i的值 中间即为最大面积
     * 所以为 数组预处理 + 单调栈
    */
    public int largestRectangleArea(int[] heights) {
        if(heights==null||heights.length<1){
            return 0;
        }

        int n=heights.length;
        //i 左侧最近小于 i 的数的下标
        int[] left=new int[n];
        int[] right=new int[n];
        Stack<Integer> stack=new Stack<>();

        //初始化 left
        for(int i=0;i<n;i++){
            //注意这里等于也要出栈
            while(!stack.isEmpty()&&heights[stack.peek()]>=heights[i]){
                stack.pop();
            }
            //为空则表明 0 也属于范围 返回 -1
            left[i]=stack.isEmpty()?-1:stack.peek();
            stack.push(i);
        }
        stack.clear();

        //初始化 right
        for(int i=n-1;i>=0;i--){
            while(!stack.isEmpty()&&heights[stack.peek()]>=heights[i]){
                stack.pop();
            }
            //为 null 表示 n-1 属于返回 返回 n
            right[i]=stack.isEmpty()?n:stack.peek();
            stack.push(i);
        }

        int max=0;
        for(int i=0;i<n;i++){
            //最后计算每个面积 求取最大
            int area=(right[i]-left[i]-1)*heights[i];
            max=Math.max(max,area);
        }
        return max;
    }
}