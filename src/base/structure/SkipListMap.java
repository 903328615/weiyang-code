package base.structure;

import java.util.ArrayList;
import java.util.TreeMap;

/*
 * 跳表实现
 */
public class SkipListMap<K extends Comparable<K>, V> {

    //随机因子 random <0.5 继续增加 next 指针 否则停止
    //跳表 logN 的关键因素
    private static final double PROBABILITY = 0.5d;
    //头节点
    private SkipListNode<K, V> head;

    //跳表大小
    private int size;

    //最高层数即 head 中 nextNode 个数
    private int maxLevel;


    //定义跳表节点
    private static class SkipListNode<K extends Comparable<K>, V> {
        K key;
        V value;
        ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.nextNodes = new ArrayList<>(16);
        }

        /*
         *  @Description: 判断当前 node 是否小于 other
         * other 为 null 返回 false 意为如果非根节点空 key 则大于所有值
         * 这里定义 根节点 空 key 全局最小 非根节点空 key 全局最大
         *  @Param otherKey
         *  @Return: boolean
         *  @Author: wangzibin
         *  @Date: 2020/12/25
         */
        public boolean isKeyLess(K otherKey) {
            // 如果当前节点 key==null 则为根节点，根节点小于任何节点
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        /*
         *  @Description: 判断两个 key 是否相等
         *  @Param otherKey
         *  @Return: boolean
         *  @Author: wangzibin
         *  @Date: 2020/12/25
         */
        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null) || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }

    }

    public SkipListMap() {
        head = new SkipListNode<K, V>(null, null);
        //构造方法，初始复制一个节点
        head.nextNodes.add(null);
        size = 0;
        //level 从 0 开始
        maxLevel = 0;
    }

    /*
     *  @Description: 在当前节点当前层找到小于 key 的最右节点
     *  @Param key
     *  @Param cur
     *  @Param level
     *  @Return: base.structure.SkipListMap.SkipListNode<K,V>
     *  @Author: wangzibin
     *  @Date: 2020/12/25
     */
    private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
        //记录 cur 的 pre 最后要返回的
        SkipListNode<K, V> pre = cur;
        while (cur != null && cur.isKeyLess(key)) {
            pre = cur;
            cur = cur.nextNodes.get(level);
        }
        return pre;
    }

    /*
     *  @Description:找到整个跳表中 最右 level=0 的 小于 key 的节点
     *  @Param key
     *  @Return: base.structure.SkipListMap.SkipListNode<K,V>
     *  @Author: wangzibin
     *  @Date: 2020/12/25
     */
    private SkipListNode<K, V> mostRightLessNodeInSkipList(K key) {
        if (key == null) {
            return null;
        }
        SkipListNode<K, V> cur = head;
        int level = maxLevel;
        while (level >= 0) {
            cur = mostRightLessNodeInLevel(key, cur, level--);
        }
        return cur;
    }

    private boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        SkipListNode<K, V> mostRight = mostRightLessNodeInSkipList(key);
        SkipListNode<K, V> next = mostRight.nextNodes.get(0);
        return next != null && next.isKeyEqual(key);
    }

    /*
     *  @Description: 添加一个数
     *  @Param key
     *  @Param value
     *  @Return: void
     *  @Author: wangzibin
     *  @Date: 2020/12/25
     */
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        //这里两种情况 一种存在 key 进行修改
        // 一种不存在添加
        SkipListNode<K, V> less = mostRightLessNodeInSkipList(key);
        SkipListNode<K, V> find = less.nextNodes.get(0);
        if (find != null && find.isKeyEqual(key)) {
            find.value = value;
        } else {
            // 进行添加
            SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
            //初始化第一个节点
            newNode.nextNodes.add(null);
            int newNodeLevel = 0;
            //根据可能性随机初始化其余节点
            while (Math.random() < PROBABILITY) {
                newNodeLevel++;
                newNode.nextNodes.add(null);
            }
            //如果新增节点 next 层数大于 head 补全 head 层数
            while (maxLevel < newNodeLevel) {
                head.nextNodes.add(null);
                maxLevel++;
            }

            //按层遍历当前 node 需要插入的节点 依次插入
            int curLevel = maxLevel;
            SkipListNode<K, V> levelMostLessNode = head;
            while (curLevel >= 0) {
                levelMostLessNode = mostRightLessNodeInLevel(key, levelMostLessNode, curLevel);
                //新增节点有当前层，则进行插入
                if (newNodeLevel >= curLevel) {
                    SkipListNode<K, V> firstBigThen = levelMostLessNode.nextNodes.get(curLevel);
                    //新增节点当前层指向 当前层第一个大于 新增节点的节点
                    newNode.nextNodes.set(curLevel, firstBigThen);
                    //第一个小于新增节点的 当前层指针指向当前节点
                    levelMostLessNode.nextNodes.set(curLevel, newNode);
                }
                curLevel--;
            }
            //插入完成 大小自增
            size++;
        }

    }

    /*
     *  @Description: 移除一个数
     *  @Param key
     *  @Return: void
     *  @Author: wangzibin
     *  @Date: 2020/12/25
     */
    public void remove(K key) {
        if (containsKey(key)) {
            //从最高层遍历找到 key 逐层删除
            int curLevel = maxLevel;
            SkipListNode<K, V> pre = head;
            while (curLevel >= 0) {
                pre = mostRightLessNodeInLevel(key, pre, curLevel);
                SkipListNode<K, V> next = pre.nextNodes.get(curLevel);
                //在这一层 进行删除
                if (next != null && next.isKeyEqual(key)) {
                    pre.nextNodes.set(curLevel, next.nextNodes.get(curLevel));
                }
                //当前层只剩 head 节点增删除 head 节点在当前层的指针
                if (curLevel != 0 && pre == head && pre.nextNodes.get(curLevel) == null) {
                    head.nextNodes.remove(curLevel);
                    maxLevel--;
                }
                curLevel--;
            }

        }

    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        SkipListNode<K, V> less = mostRightLessNodeInSkipList(key);
        SkipListNode<K, V> target = less.nextNodes.get(0);
        return target != null && target.isKeyEqual(key) ? target.value : null;
    }

    //最近 key
    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        SkipListNode<K, V> less = mostRightLessNodeInSkipList(key);
        SkipListNode<K, V> next = less.nextNodes.get(0);
        return next != null ? next.key : null;
    }

    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.value + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        test.put("a", "aa");
        test.put("e", "ee");
        test.put("g", "gg");
        test.put("c", "cc");
        test.put("b", "bb");
        test.put("d", "dd");
        test.put("f", "ff");
        printAll(test);
        //test.remove("e");
        System.out.println(test.ceilingKey("e"));
    }
}
