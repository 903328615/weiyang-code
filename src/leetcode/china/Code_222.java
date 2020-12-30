package leetcode.china;

import base.structure.TreeNode;

/**
 * @program: weiyang-code
 * @description: 222. 完全二叉树的节点个数
 * 给出一个完全二叉树，求出该树的节点个数。
 *
 * 说明：
 *
 * 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-complete-tree-nodes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_222 {

    public int countNodes(TreeNode root) {

        if(root==null){
            return 0;
        }
        return process(root,1,getMaxLevel(root,1));

    }

    /*
    * 这里我们可以利用完全二叉树的性质进行递归求解，可以将时间复杂度降到 O(h) = O(logN ^ 2)
    *
    */
    public int process(TreeNode node,int level,int maxLevel){
        if(level==maxLevel){
            return 1;
        }
        //如果右子树的层数等于 maxLevel 说明 左树为满二叉树
        if(getMaxLevel(node.right,level+1)==maxLevel){
            return (1<<(maxLevel-level))+process(node.right,level+1,maxLevel);
        }else{
            //否则 右子树为满二叉树 计算右子树 递归左子树
            return (1<<(maxLevel-level-1))+process(node.left,level+1,maxLevel);
        }
    }

    //以 node 为头的树 层数为 level 返回其最大层数
    public int getMaxLevel(TreeNode node, int level){
        while(node!=null){
            node=node.left;
            level++;
        }
        return level-1;
    }
}