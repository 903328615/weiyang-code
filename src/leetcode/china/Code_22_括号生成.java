package leetcode.china;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//22. 括号生成
//https://leetcode-cn.com/problems/generate-parentheses/
//整体思路 暴力递归 优化：剪枝
public class Code_22_括号生成 {

    public List<String> generateParenthesis(int n) {
        List<String> ans=new ArrayList<String>();
        char[] arr=new char[n*2];
        generateAll(arr, 0,0,0,n, ans);
        return ans;
    }
    //暴力递归 + 剪枝
    //递归含义 n 个括号生成序列 为 n-1 个序列 + '(' or ')'
    public void generateAll(char[] arr,int pos,int l,int r,int n,List<String> res){
        if(l>n||r>n||r>l){
            return;
        }
        if(pos==arr.length){
            // if(isRight(arr)){
            //     res.add(String.valueOf(arr));
            // }
            res.add(String.valueOf(arr));
            return;
        }else{
            arr[pos]='(';
            generateAll(arr, pos+1,l+1,r,n, res);
            arr[pos]=')';
            generateAll(arr, pos+1,l,r+1,n, res);
        }
    }

    public boolean isRight(char[] arr){
        if(arr==null||arr.length<2||arr[0]==')'){
            return false;
        }
        Stack<Character> stack=new Stack<>();
        for(int i=0;i<arr.length;i++){
            if(arr[i]=='('){
                stack.add(')');
            }else{
                if(stack.isEmpty()){
                    return false;
                }else{
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }
}
