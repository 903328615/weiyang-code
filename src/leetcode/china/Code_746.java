package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 最小花费爬楼梯
 * https://leetcode-cn.com/problems/min-cost-climbing-stairs/
 * @author: wangzibin
 * @create: 2020-12-21 13:07
 **/
public class Code_746 {

    public int minCostClimbingStairs(int[] cost) {
        if(cost.length==2){
            return Math.min(cost[0],cost[1]);
        }
        return dp2(cost);

    }


    //动态规划
    public int dp(int[] cost){

        int[] dp=new int[cost.length+1];
        dp[0]=0;
        dp[1]=0;
        for(int i=2;i<dp.length;i++){
            dp[i]=Math.min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2]);
        }
        return dp[cost.length];

    }

    //动态规划 2
    public int dp2(int[] cost){

        for(int i=2;i<cost.length;i++){
            cost[i]+=Math.min(cost[i-1],cost[i-2]);
        }
        return Math.min(cost[cost.length-1],cost[cost.length-2]);

    }

    //暴力递归
    public int process(int[] cost,int index){
        if(index==0){
            return 0;
        }
        if(index==1){
            return 0;
        }

        int c1=cost[index-1]+process(cost,index-1);
        int c2=cost[index-2]+process(cost,index-2);
        return Math.min(c1,c2);

    }
}