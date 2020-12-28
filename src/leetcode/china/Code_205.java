package leetcode.china;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * @program: weiyang-code
 * @description: 205. 同构字符串
 * 给定两个字符串 s 和 t，判断它们是否是同构的。

如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。

所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/isomorphic-strings
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_205 {

    /*
    * 第一时间想到了使用 HashMap 一一对应映射，同时要注意不能出现多个key对应同一个 value
    * 时间 O(n) 空间 O(∣Σ∣) Σ 为字符集长度
    * 时间复杂度上有点问题 containsValue 是个 O(n) 的方法
    * 所以应该是 O(n^2)
    * 这里可以在加个 set
    */
    public boolean isIsomorphic(String s, String t) {
        if(s==null&&t==null){
            return true;
        }
        //根据题意，这里不严格判空
        if(s.equals(t)){
            return true;
        }
        Map<Character,Character> map=new HashMap<>();
        char[] sArr=s.toCharArray();
        char[] tArr=t.toCharArray();

        for(int i=0;i<s.length();i++){
            if(map.containsKey(sArr[i])){
                if(map.get(sArr[i])!=tArr[i]){
                    return false;
                }
            }else{
                if(map.containsValue(tArr[i])){
                    return false;
                }
                map.put(sArr[i],tArr[i]);
            }
        }
        return true;
    }

    /*
    * 加 Set 实现
    * 时间 O(n) 空间 O(∣Σ∣)
    */
    public boolean isIsomorphicWithSet(String s, String t) {
        if(s==null&&t==null){
            return true;
        }
        if(s.equals(t)){
            return true;
        }
        Map<Character,Character> map=new HashMap<>();
        Set<Character> set=new HashSet<>();
        char[] sArr=s.toCharArray();
        char[] tArr=t.toCharArray();

        for(int i=0;i<s.length();i++){
            if(map.containsKey(sArr[i])){
                if(map.get(sArr[i])!=tArr[i]){
                    return false;
                }
            }else{
                if(set.contains(tArr[i])){
                    return false;
                }
                map.put(sArr[i],tArr[i]);
                set.add(tArr[i]);
            }
        }
        return true;
    }
}