package leetcode.china;

import base.structure.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @program: weiyang-code
 * @description:103. 二叉树的锯齿形层序遍历
 * @author: wangzibin
 * @create: 2020-12-22 12:30
 **/
public class Code_103 {


    /**
     * 整体思路为使用两个栈来进行层序遍历
     * 交替进栈 出栈  两个栈的入栈顺序需要颠倒
     * 时间 O(n) 空间 O(n)
    */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (root == null) {
            return ans;
        }
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            //确定出栈和入栈
            Stack<TreeNode> popStack=stack1.isEmpty()?stack2:stack1;
            Stack<TreeNode> pushStack=stack1.isEmpty()?stack1:stack2;
            List<Integer> item = new ArrayList<>();
            ans.add(item);
            while (!popStack.isEmpty()) {
                TreeNode node = popStack.pop();
                //记录当前层
                item.add(node.val);
                //确定左右节点入栈先后顺序
                TreeNode firstIn=popStack==stack2?node.right:node.left;
                TreeNode secondIn=popStack==stack2?node.left:node.right;
                if (firstIn != null) {
                    pushStack.push(firstIn);
                }
                if (secondIn != null) {
                    pushStack.push(secondIn);
                }
            }
        }
        return ans;
    }
}