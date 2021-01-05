package leetcode.china;

/**
 * @program: weiyang-code
 * https://leetcode-cn.com/problems/remove-duplicate-letters/
 * 与 1081. 不同字符的最小子序列 相同
 * @description: 去重复字母
 * @author: wangzibin
 * @create: 2020-12-20 16:13
 **/
public class Code_316_不同字符的最小子序列 {

    /**
     * 这道题的思路利用单调栈，但这个单调栈需要进行特殊处理一下。
     *
    */
    public String removeDuplicateLetters(String s) {
        //nums 数组 用来遍历存储字符串中所有字符出现次数
        int[] nums=new int[26];
        //have 数组 用来记录返回串中是否已经存在字符
        boolean[] have=new boolean[26];
        for(int i=0;i<s.length();i++){
            nums[s.charAt(i)-'a']++;
        }
        //返回串，同时也作为我们的单调栈来用
        StringBuilder sb=new StringBuilder();

        for(int i=0;i<s.length();i++){
            //1. 获取当前字符
            char ch=s.charAt(i);
            //2.判断之前有没有已经添加了字符，没添加才进行添加 给个例子  abacb  结果为 abc
            if(!have[ch-'a']){
                //维护单调栈，
                // 1.单调栈不能为空
                // 2.如果当前栈顶元素如果大于当前入栈元素则栈顶元素弹出，以此循环
                // 3.因为题意要求保留一个，则如果当前栈顶元素后续没有了，则不弹出直接跳出循环
                while(sb.length()>0&&sb.charAt(sb.length()-1)>ch&&nums[sb.charAt(sb.length()-1)-'a']>0){
                    //移除拥有标识
                    have[sb.charAt(sb.length()-1)-'a']=false;
                    //弹出元素
                    sb.deleteCharAt(sb.length()-1);
                }
                //将当前元素放入栈顶
                sb.append(ch);
                //标识已拥有该元素
                have[ch-'a']=true;

            }

            //1. 先理解不管当前这个字符怎么样遍历一遍即消耗
            nums[ch-'a']--;
        }

        return sb.toString();
    }
}