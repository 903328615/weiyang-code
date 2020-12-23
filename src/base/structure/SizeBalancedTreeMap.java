package base.structure;

import javax.xml.soap.Node;

//二叉平衡搜索树
// SizeBalancedTree 任何叔叔节点个数不小于其侄子节点的节点个数。
// SB 树 相对于 AVL 树 会维持一个相对平衡的状态
public class SizeBalancedTreeMap<K extends Comparable<K>,V> {

    //定义 SBTree 节点
    private static class SBTNode<K extends Comparable<K>,V> {
        K key;
        V value;
        public SBTNode<K,V> left;
        public SBTNode<K,V> right;
        //当前节点所包含不同 key 的数量 及不同 key 节点数量，比较平衡性时有用
        public int size;
        public SBTNode(K k,V v){
            this.key=k;
            this.value=v;
        }
    }
    //根节点，注意左右旋时需要变换 root
    private SBTNode<K,V> root;

    //对 cur 节点进行左旋 返回左旋后的头节点，返回值供上层赋值链接
    // 这里只需要单纯考虑左旋问题即可
    private SBTNode<K,V> leftRotate(SBTNode<K,V> cur){
        //当前节点左旋则其右孩子必为最后根 记录下返回
        SBTNode<K,V> rightNode=cur.right;
        //当前节点左旋 下移 右孩子上移 右孩子左孩子给当前节点
        //当前节点变为右孩子的左孩子
        cur.right=rightNode.left;
        rightNode.left=cur;
        //修改 size 个数
        rightNode.size=cur.size;
        cur.size=(cur.left==null?0:cur.left.size)+(cur.right==null?0:cur.right.size)+1;
        return rightNode;
    }

    //同理右旋
    private SBTNode<K,V> rightRotate(SBTNode<K,V> cur){
        SBTNode<K,V> leftNode=cur.left;
        cur.left=leftNode.right;
        leftNode.right=cur;
        leftNode.size=cur.size;
        cur.size=(cur.left==null?0:cur.left.size)+(cur.right==null?0:cur.right.size)+1;
        return leftNode;
    }

    //平衡
    private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
        if(cur==null){
            return null;
        }
        //首先判断是否为 LL 型
        if(cur.left!=null&&cur.left.left!=null&&cur.right!=null&&cur.right.size<cur.left.left.size){
            //确定为 LL 型 进行右旋
            cur=rightRotate(cur);
            //右旋后 变动子节点的节点递归调用 maintain
            // 右旋后 当前节点变为 左孩子的右孩子 上一步右旋已经将 cur 赋值为左孩子 故先调用 cur.right
            cur.right=maintain(cur.right);
            //注意递归调用 maintain 时 先调用儿子节点 再调用父节点 下方同理
            cur=maintain(cur);
        }else if(cur.left!=null&&cur.left.right!=null&&cur.right!=null&&cur.right.size<cur.left.right.size){
            //确定为 LR 型  当前节点 左孩子 左旋 然后 当前节点右旋
            cur.left=leftRotate(cur.left);
            cur=rightRotate(cur);
            //子节点改变节点递归 注意由下到上顺序
            cur.left=maintain(cur.left);
            cur.right=maintain(cur.right);
            cur=maintain(cur);
        }else if(cur.right!=null&&cur.right.right!=null&&cur.left!=null&&cur.left.size<cur.right.right.size){
            //确定为 RR 型 进行左旋
            cur=leftRotate(cur);
            cur.left=maintain(cur.left);
            cur=maintain(cur);
        }else if(cur.right!=null&&cur.right.left!=null&&cur.left!=null&&cur.left.size<cur.right.left.size){
            //确定为 RL 型
            cur.right=rightRotate(cur.right);
            cur=leftRotate(cur);
            cur.left=maintain(cur.left);
            cur.right=maintain(cur.right);
            cur=maintain(cur);
        }
        return cur;
    }
    //找到 key 节点 没有则返回最后的叶子节点
    private SBTNode<K, V> findLastIndex(K key) {
        SBTNode<K, V> pre = root;
        SBTNode<K, V> cur = root;
        while (cur != null) {
            pre = cur;
            if (key.compareTo(cur.key) == 0) {
                break;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return pre;
    }

    // add 函数为 已确定没有修改 key 的行为 即 无 key 相等情况使用
    private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
        if(cur==null){
            return new SBTNode<K,V>(key,value);
        }else {
            cur.size++;
            if (cur.key.compareTo(key)>0){
                cur.left=add(cur.left,key,value);
            }else {
                cur.right=add(cur.right,key,value);
            }
            return maintain(cur);
        }
    }

    public void put(K key,V value){
        if (key==null){
            throw  new RuntimeException("key can not be null");
        }else {
            SBTNode<K,V> cur=findLastIndex(key);
            if (cur!=null&&cur.key.compareTo(key)==0){
                //找到同 key 值 直接修改
                cur.value=value;
            }else {
                //未找到则直接在 root 位置进行添加
                root=add(root,key,value);
            }
        }
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        SBTNode<K, V> lastNode = findLastIndex(key);
        return lastNode != null && key.compareTo(lastNode.key) == 0;
    }

    public void remove(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        if (containsKey(key)) {
            root = delete(root, key);
        }
    }

    private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
        cur.size--;
        if (cur.key.compareTo(key)>0){
            cur.left=delete(cur.left,key);
        }else if(cur.key.compareTo(key)<0){
            cur.right=delete(cur.right,key);
        }else {
            //当前 cur 即为删除 key
            //进行讨论
            if(cur.left==null&&cur.right==null){
                //叶子节点直接删
                cur=null;
            }else if (cur.left!=null&&cur.right==null){
                //左孩子不为空 右孩子为 null
                cur=cur.left;
            }else if(cur.left==null&&cur.right!=null){
                cur=cur.right;
            }else {
                //左右孩子都有
                //找到右孩子最左顶替现在
                SBTNode<K, V> pre = null;
                SBTNode<K, V> des = cur.right;
                //此时右孩子 size --
                des.size--;
                //找右孩子最左
                while (des.left != null) {
                    pre = des;
                    des = des.left;
                    des.size--;
                }
                //可能出现 cur.right 就已经是最左 即 cur.right.left==null
                // 此时需要特殊处理 进行判断
                if (pre != null) {
                    pre.left = des.right;
                    des.right = cur.right;
                }
                des.left=cur.left;
                des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                cur = des;

            }
        }
        //若想在删除时也平衡则增加 下面代码
        //cur=maintain(cur);
        return cur;
    }

    public V get(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter.");
        }
        SBTNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && key.compareTo(lastNode.key) == 0) {
            return lastNode.value;
        } else {
            return null;
        }
    }

    public int size() {
        return root == null ? 0 : root.size;
    }

    public K getIndexKey(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter.");
        }
        return getIndex(root, index + 1).key;
    }

    public V getIndexValue(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter.");
        }
        return getIndex(root, index + 1).value;
    }

    private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
        if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
            return cur;
        } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
            return getIndex(cur.left, kth);
        } else {
            return getIndex(cur.right, kth - (cur.right != null ? cur.right.size : 0) - 1);
        }

    }

    //有点问题，后面看看
    public static void main(String[] args) {
        SizeBalancedTreeMap<String, Integer> sbt = new SizeBalancedTreeMap<String, Integer>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
    }

}
