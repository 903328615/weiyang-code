package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 718. 最长重复子数组 这道题理解上和解法上和最长公共子串的解法和思路是一样的
 * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
 * 示例：
 *
 * 输入：
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * 输出：3
 * 解释：
 * 长度最长的公共子数组是 [3, 2, 1] 。
 *  
 *
 * 提示：
 *
 * 1 <= len(A), len(B) <= 1000
 * 0 <= A[i], B[i] < 100
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_718_最长重复子数组 {

    /*
    * 经典动态规划
    * dp 含义是 A 以 i 字符结尾 与 B 以 j 位置字符结尾最长公共子串长度为多少
    * 时间 O(m*n) 空间 （m*n）
    * 这里提一下 O(m*n) 的时间复杂度不是最优解 最优可优化到 O(m+n)
    */
    public int findLength(int[] A, int[] B) {
        int m=A.length;
        int n=B.length;
        int[][] dp=new int[m][n];
        int max=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0||j==0){
                    //初始边界 不相等即为 0
                    dp[i][j]=A[i]==B[j]?1:0;
                }else{
                    //非边界 不相等即为 0 相等则斜上方 + 1
                    dp[i][j]=A[i]==B[j]?dp[i-1][j-1]+1:0;
                }
                //更新全局 max
                if(max<dp[i][j]){
                    max=dp[i][j];
                }
            }
        }
        return max;
    }

    /*
    * 这道题还可以在空间复杂度上在做优化 可以优化到 O(1)
    * 使用有限几个变量即可 思路为 因为动态规划只依赖斜上方的值
    * 这样我们只需要从右往左 从上往下 遍历边界 同时遍历斜线求最大值 即可 无需申请二维数组
    */
    public int findLengthNoSpace(int[] A, int[] B) {
        int m=A.length;
        int n=B.length;
        int max=0;
        //列位置
        int col=n-1;
        //行位置
        int row=0;
        while(row<m){
            int i=row;
            int j=col;
            int ans=0;
            //求斜线下面 dp 数 更新 max
            while(i<m&&j<n){
                ans=A[i++]==B[j++]?ans+1:0;
                if(max<ans){
                    max=ans;
                }
            }
            //从 0 行最后一个位置 遍历边界
            if(col==0){
                row++;
            }else{
                col--;
            }
        }

        return max;
    }

}