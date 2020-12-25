package leetcode.china;

import base.structure.TreeNode;

/*
 * @program: weiyang-code
 * @description: 104. 二叉树的最大深度
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_104 {

    /*
    * 这道题用递归的思想去做还是比较好理解的，该方法返回当前节点最大高度
    * 则求根节点最大值为左树最大高度 和 右树最大高度 取 最大一个 +1
    * 深度优先遍历 时间 O(n) 空间 O(height)
    * 还有一种思路是广度优先遍历，这个代码需要控制每打印一层 depth++ 即可
    */
    public int maxDepth(TreeNode root) {

        if(root==null){
            return 0;
        }else{
            int leftMaxDepth=maxDepth(root.left);
            int rightMaxDepth=maxDepth(root.right);
            return Math.max(leftMaxDepth,rightMaxDepth)+1;
        }

    }
}