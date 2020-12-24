package leetcode.china;
//递增三元子序列
//https://leetcode-cn.com/problems/increasing-triplet-subsequence/
public class Code_334 {

    // 有点贪心的思想
    public boolean increasingTriplet(int[] nums) {
        //记录小值
        int min=Integer.MAX_VALUE;
        //记录第二小值
        int second=Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            if(nums[i]<=min){
                min=nums[i];
            }else if(nums[i]<=second){
                second=nums[i];
            }else{
                return true;
            }
        }
        return false;
    }
}
