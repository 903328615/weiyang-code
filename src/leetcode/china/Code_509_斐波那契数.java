package leetcode.china;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: weiyang-code
 * @description: 509. 斐波那契数
 *
 * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
 *
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * 给你 n ，请计算 F(n) 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/fibonacci-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2021-01
 **/
public class Code_509_斐波那契数 {

    /*
    * 斐波那契数，经典的递归 动态规划题
    * 这里我们直接给出动态规划，题目已经给出动态规划方程 如下
    * 时间 O(n) 空间 O(n)
    * 事实上我们仍然可以优化空间复杂度，由动规方程可以看出我们只需要记录之前两个的值就可以推出后面的值
    * 即有限的几个变量即可，故空间可以优化到 O(1)
    */

    public int fib1(int n) {
        if(n<1){
            return 0;
        }
        int[] dp=new int[n+1];
        dp[0]=0;
        dp[1]=1;
        for(int i=2;i<=n;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }

        return dp[n];
    }


    /*
    * 时间 O(n) 空间 O(1)
    */
    public int fib2(int n) {
        if(n<1){
            return 0;
        }
        int f1=0;
        int f2=1;
        int ans=f2;
        for(int i=2;i<=n;i++){
            ans=f1+f2;
            f1=f2;
            f2=ans;
        }
        LockSupport.park();
        return ans;
    }
}