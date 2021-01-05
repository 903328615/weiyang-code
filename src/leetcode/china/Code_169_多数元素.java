package leetcode.china;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//https://leetcode-cn.com/problems/majority-element/
//多数元素
public class Code_169_多数元素 {

    //解法1 借助 hashMap 记录数字出现次数
    //时间复杂度 O(n)
    //空间复杂度 O(n)
    public int majorityElement(int[] nums) {

        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                int num=map.get(nums[i])+1;
                if(num>=nums.length/2+1){
                    return nums[i];
                }
                map.put(nums[i],num);
            }else{
                map.put(nums[i],1);
            }
        }
        return nums[0];
    }
    //解法 2 利用多数元素特性 排序取中位数
    //时间 O(n*log n) 空间 O(1)
    public int majorityElement2(int[] nums) {

        Arrays.sort(nums);
        return nums[nums.length/2];
    }

    //解法 3 摩尔投票法
    //时间 O(n) 空间 O(1)
    public int majorityElement3(int[] nums) {
        //最后结果
        Integer ans=nums[0];
        int count=1;
        for(int i=1;i<nums.length;i++){
            //这里是投票逻辑，当前数与结果数相等则票数增加 不等则减少，当等于 0 时则换人
            // 多数元素最后一定会胜出 则返回 ans 即可
            if(count==0){
                ans=nums[i];
            }
            count+=ans==nums[i]?1:-1;
        }
        return ans;
    }

}
