package leetcode.china;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @program: weiyang-code
 * @description: 最大数
 * @author: wangzibin
 * @create: 2020-12-26 17:01
 **/
public class Code_179_最大数 {

    //重点是自定义排序
    //时间 O(nlongn) 空间 O(n)
    public static String largestNumber(int[] nums) {
        if (nums == null || nums.length < 1) {
            return "";
        }
        String[] s = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            s[i] = String.valueOf(nums[i]);
        }
        //自定义排序方法
        Arrays.sort(s, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String order1 = o1 + o2;
                String order2 = o2 + o1;
                return order2.compareTo(order1);
            }
        });
        if (s[0].equals("0")) {
            return "0";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            builder.append(s[i]);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 30, 34, 5, 9};
        System.out.println(largestNumber(nums));
    }
}
