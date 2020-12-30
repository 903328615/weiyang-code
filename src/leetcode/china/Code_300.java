package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 300. 最长递增子序列
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 *  
 * 示例 1：
 * <p>
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * 示例 2：
 * <p>
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * 示例 3：
 * <p>
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 2500
 * -104 <= nums[i] <= 104
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_300 {

    public static void main(String[] args) {
        int[] nums=new int[]{0,1,0,3,2,3,4,5,6,7,3,8,9,10,11,234};
        System.out.println(lengthOfLISOnPlus(nums));
        System.out.println(1>>1);
        System.out.println(1/2);
        System.out.println(25/2);
        System.out.println(10+(5>>1));
    }

    /*
     * 一个可行的动态规划  dp[i] 表示 nums[0-i] 中以 i 结尾最长递增子序列长度
     * dp[i]=max(dp[0 ~ i-1])
     * 理论上是个 O(n^2) 的时间复杂度 空间复杂度 O(n)
     */
    public int lengthOfLIS(int[] nums) {

        if (nums == null || nums.length < 1) {
            return 0;
        }

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;

    }

    /*
    * 时间复杂度 O(n*log n) 的解法 ends 数组
    * 空间 O(n)
    */
    public static int lengthOfLISOn(int[] nums) {

        if (nums == null || nums.length < 1) {
            return 0;
        }
        int n = nums.length;
        //ends 数组含义为 ends[i] 为最长公共子序列为 i 长度的最小结尾
        int[] ends=new int[n];
        ends[0]=nums[0];
        int right=0;
        int max = 1;
        for (int i = 1; i < n; i++) {
            //二分查找 ends 数组中第一个小于 nums[i] 的数 ends i+1 即为 i 的长递增
            int l=0;
            int r=right;
            //10,9,2,5,3,7,101,18
            // 2 3 7
            // 0 1 2
            while(l<r){
                //0
               int mid=l+((r-l)>>1);
                if(ends[mid]<nums[i]){
                    l=mid+1;
                }else {
                    //大于等于的情况,此时 r 记录的是第一个小于 nums[i] 位置
                    r=mid-1;
                }
            }
            r= Math.max(r, 0);
            if(ends[r]<nums[i]){
                int index=r+1;
                if(index>right){
                    ends[index]=nums[i];
                    //此时新增 ends 更新 max
                    max=Math.max(max,index+1);
                    right++;
                }else {
                    ends[index]=Math.min(ends[index],nums[i]);
                }
            }else {
                ends[r]=nums[i];
            }


        }
        return max;

    }


    /*
        代码结构优化一下
     * 时间复杂度 O(n*log n) 的解法 ends 数组
     * 空间 O(n)
     */
    public static int lengthOfLISOnPlus(int[] nums) {

        if (nums == null || nums.length < 1) {
            return 0;
        }
        int n = nums.length;
        //ends 数组含义为 ends[i] 为最长公共子序列为 i 长度的最小结尾
        int[] ends=new int[n];
        ends[0]=nums[0];
        int right=0;
        for (int i = 1; i < n; i++) {
            //二分查找 ends 数组中第一个小于 nums[i] 的数 ends i+1 即为 i 的长递增
            int l=0;
            int r=right;
            //10,9,2,5,3,7,101,18
            while(l<=r){
                int mid=l+((r-l)>>1);
                if(ends[mid]<nums[i]){
                    l=mid+1;
                }else {
                    //大于等于的情况,此时 r 记录的是第一个小于 nums[i] 位置
                    r=mid-1;
                }
            }
            right=Math.max(right,l);
            // 此时 ends[0-right] 中未找到小于 nums[i] 的数 则为 l 为 right +1 可以直接更新
            // 若存在大于 nums[i] 的数 l 则正好为 第一个大于 l 的数则同时可以直接更新
            ends[l]=nums[i];

        }
        return right+1;

    }

}