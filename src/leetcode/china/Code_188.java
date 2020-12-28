package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 188. 买卖股票的最佳时机 IV
 * 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：k = 2, prices = [2,4,1]
 * 输出：2
 * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
 * 示例 2：
 *
 * 输入：k = 2, prices = [3,2,6,5,0,3]
 * 输出：7
 * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
 *      随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
 *  
 *
 * 提示：
 *
 * 0 <= k <= 109
 * 0 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12-28 12:02
 **/
public class Code_188 {


    /**
     * 整体这道题的解法还是动态规划求解，只是这个思路比较难想
     * 需要两个数组来相互配合进行 dp
     * 时间 O(n*(min(k,n/2)))) 空间  O(n*(min(k,n/2))))
     * 根据状态转移方程理论上 dp 数组的空间还可以进行压缩 到 O(min(k,n/2)) 忽略系数即 O(min(k,n))
    */
    public int maxProfit(int k, int[] prices) {

        if(prices==null||prices.length<2||k==0){
            return 0;
        }
        int N=prices.length;
        //修正 k 的范围
        k=k>(N>>1)?(N>>1):k;
        k++;

        //sell[i][j] 表示在 prices[0-i] 购买次数为 j 笔所获得的最大利润且手上不持有股票
        int[][] sell=new int[N][k];
        //buy 表示最后持有股票
        int[][] buy=new int[N][k];

        //初始化边界条件
        //0位置手上持有股票 只能是 -prices[0]
        buy[0][0] = -prices[0];
        //0 位置 没有股票 最好的做法就是什么都不做
        sell[0][0] = 0;
        //prices[0] 购买次数为 j 无法进行任何购买 设置为一个极小值表示不合法
        for(int j=1;j<k;j++){
            buy[0][j]=sell[0][j]=Integer.MIN_VALUE/2;
        }
        int max=0;
        for(int i=1;i<N;i++){
            for(int j=0;j<k;j++){
                //意思为本次购买 和 上次购买股票的利润最大值
                buy[i][j]=Math.max(buy[i-1][j],(sell[i-1][j]-prices[i]));
                //意思为 本次卖出 和上次卖出利润最大值  1 0 这里当 j==0 时 sell 无需状态转移
                if(j!=0){
                    sell[i][j]=Math.max(sell[i-1][j],(buy[i-1][j-1]+prices[i]));
                }
            }
        }
        //最后遍历 sell 最后一行找出最大值
        for(int j=0;j<k;j++){
            max=Math.max(sell[N-1][j],max);
        }

        return max;

    }
}