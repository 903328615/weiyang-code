package leetcode.china;
//387. 字符串中的第一个唯一字符
//https://leetcode-cn.com/problems/first-unique-character-in-a-string/
public class Code_387 {

    //空间复杂度 O(Σ) 在这个题中 Σ=26 时间复杂度 O(n)
    public int firstUniqChar(String s) {
        char[] arr=s.toCharArray();
        //初始为 0 表示都没记录，有记录则为第一次出现下标加 1 如果为二次出现则置为 -1
        int [] pos=new int[26];
        // O(n)
        for(int i=0;i<arr.length;i++){
            if(pos[arr[i]-'a']==0){
                pos[arr[i]-'a']=i+1;
            }else{
                pos[arr[i]-'a']=-1;
            }
        }
        int min=Integer.MAX_VALUE;
        //常数级别的循环
        for (int po : pos) {
            if (po != 0 && po != -1 && po < min) {
                min = po;
            }
        }
        return min==Integer.MAX_VALUE?-1:min-1;
    }
}
