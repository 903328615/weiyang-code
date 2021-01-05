package leetcode.china;
//238. 除自身以外数组的乘积
//https://leetcode-cn.com/problems/product-of-array-except-self/
public class Code_238_除自身以外数组的乘积 {

    //使用除法
    //时间 O(n) 空间 O(1)
    public int[] productExceptSelf(int[] nums) {
        int ans=1;
        //记录数组中 0 的个数，需要特殊处理一下，
        // 1 个 0 则 这个0位置最后结果为其他数相乘
        //多个 0 则都为 0
        int count=0;
        for(int i=0;i<nums.length;i++){
            //只有一个 0 时不乘到结果里
            if(count==0&&nums[i]==0){
                count++;
                continue;
            }
            ans*=nums[i];
            //小小加个速
            if(ans==0){
                break;
            }
        }
        for(int i=0;i<nums.length;i++){
            if(count==1){
                nums[i]=nums[i]==0?ans:0;
            }else{
                nums[i]=ans/(nums[i]==0?1:nums[i]);
            }
            //非人哉的三元，这里就不合了
            //nums[i]=count==1?nums[i]==0?ans:0:ans/(nums[i]==0?1:nums[i]);
        }
        return nums;
    }

}
