package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 283. 移动零
 * @author: wangzibin
 * @create: 2020-12-24 23:00
 *
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 示例:
 *
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 说明:
 *
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/move-zeroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 **/
public class Code_283_移动零 {

    /**
     * 比较好的思路是使用双指针 notZero 表示非0数字区域，index 为遍历指针
     * 当index 需要非 0 数字时 与 notZero 边界交换数字边界自增 这样同时不会改变非 0 元素顺序
     * 时间 O(n) 空间 O(1)
    */
    public void moveZeroes(int[] nums) {
        int n=nums.length;
        int notZero=0;
        int index=0;
        while(index<n){
            if(nums[index]!=0){
                swap(nums, notZero++, index);
            }
            index++;

        }
    }
    public void swap(int[] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
}