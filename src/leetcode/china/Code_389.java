package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 找不同
 * 389. 找不同
 * https://leetcode-cn.com/problems/find-the-difference/
 * @author: wangzibin
 * @create: 2020-12-18 10:35
 **/
public class Code_389 {

    public static void main(String[] args) {

        System.out.println( findTheDifference(null,null));
        System.out.println( findTheDifference(null,"a"));
        System.out.println( findTheDifference("a",null));
        System.out.println( findTheDifference("a","ac"));
        System.out.println( findTheDifference2("a","ac"));
        System.out.println( findTheDifference2(null,"a"));
    }

    /**
     * 时间复杂度：O(N)，其中 N 为字符串的长度。
     * 空间复杂度：O(∣Σ∣)，其中 Σ 是字符集，这道题中字符串只包含小写字母，∣Σ∣=26。需要使用数组对每个字符计数。
     * 这里空间复杂度也可以看做 O(1); 毕竟常数个
    */
    public static char findTheDifference(String s, String t) {

        //空值校验
        t=t!=null?t:"";
        s=s!=null?s:"";
        //题目中限定为 26 个小写字母，故这里给出一个数组存储字母个数
        int[] arr=new int[26];
        //先将 s 串存入
        for(int i=0;i<s.length();i++){
            arr[s.charAt(i)-'a']++;
        }
        //检索 t 串
        for(int i=0;i<t.length();i++){
            char tmp=t.charAt(i);
            int index=tmp-'a';
            // 如果当前所匹配 t 串字符为 0 则说明为新增字符 直接返回
            if(arr[index]==0){
                return tmp;
            }else{
                // 匹配不为 0 则消除一个
                arr[index]--;
            }
        }
        return ' ';
    }

    /**
     * 解法 2 位运算
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
    */
    public static char findTheDifference2(String s, String t) {

        //空值校验
        t=t!=null?t:"";
        s=s!=null?s:"";
        int res=0;
        //这里根据异或的性质 0 与任何值异或为当前值，两个相同的值异或为 0
        // 则 s t 所有数都异或一遍即为多出的数
        for(int i=0;i<s.length();i++){
            res^=s.charAt(i);
        }
        //检索 t 串
        for(int i=0;i<t.length();i++){

            res^=t.charAt(i);
        }
        return (char) res;
    }
}