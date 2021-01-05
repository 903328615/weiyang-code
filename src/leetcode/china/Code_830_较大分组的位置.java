package leetcode.china;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: weiyang-code
 * @description: 830. 较大分组的位置
 * @author: wangzibin
 * @create: 2021-01
 **/
public class Code_830_较大分组的位置 {

    /*
    * 思路一次遍历，遍历过程中记录每个组的起始位置及终止位置，在更新组时判断是否需要加入结果集
    * 时间 O(N) 空间 O(1);
    * 以下代码并不优雅，这里考虑优化一下
    */
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> ans=new ArrayList<List<Integer>>();
        if(s==null||s.length()<3){
            return ans;
        }
        char[] str=s.toCharArray();
        int n=str.length;
        int start=0;
        int end=1;
        while(end<n){
            if(str[start]!=str[end]||end==n-1){
                //当前 end 位置不等于 start
                end=str[start]==str[end]?end+1:end;
                if(end-start>=3){
                    List<Integer> item=new ArrayList<>(2);
                    item.add(start);
                    item.add(end-1);
                    ans.add(item);
                }
                start=end;
            }else{
                end++;
            }
        }
        return ans;
    }

    /*
    * 优化格式如下，使用计数器
    */
    public List<List<Integer>> largeGroupPositionsPlus(String s) {
        List<List<Integer>> ans=new ArrayList<List<Integer>>();
        if(s==null||s.length()<3){
            return ans;
        }
        char[] str=s.toCharArray();
        int n=str.length;
        //计数器
        int nums=1;
        //向后探测
        for(int i=0;i<n;i++){
            if(i==n-1||str[i]!=str[i+1]){
                //不相等判断是否可以入结果集
                if(nums>=3){
                    ans.add(Arrays.asList(i-nums+1,i));
                }
                //重置计数器
                nums=1;
            }else {
                //相等计数器 +1
                nums++;
            }
        }

        return ans;
    }
}