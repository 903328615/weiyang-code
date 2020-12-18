package base.algorithm;

import base.util.GenerateUtil;

import java.util.Arrays;
import java.util.Random;

//线段树 ，区间修改树
//该结构 提供方法
// add(int L,int R,int num) 在 L-R 上每个数加 num
// update(int L,int R,int num) 在 L-R 上每个数更新为 num
// getSum(int L,int R) 获取 L-R 上所有数的累加和
// 上述三个方法的时间复杂度 log(n) 其核心思想为利用树形结构分段 + 懒更新机制。
// 注意实现 时 L R 和当前节点 curL  curR 的区别 别传混了
public class SegmentTree {

    public static void main(String[] args) {
        //对数器
        int testTimes = 5000;
        int addOrUpdateTimes = 5000;
        int maxAddOrUpdateNum = 5000;
        int testQueryTimes = 5000;
        Random random = new Random();
        //进行测试
        for (int i = 0; i < testTimes; i++) {
            int[] origin = GenerateUtil.generateArray(30, 10);
           // System.out.println(Arrays.toString(origin));
            SegmentTree segmentTree = new SegmentTree(origin);
            SegmentTreeON segmentTreeON = new SegmentTreeON(origin);
            int originLength = origin.length;
            //每次测试进行添加更新操作次数
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = random.nextInt(originLength) + 1;
                int num2 = random.nextInt(originLength) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int num = random.nextInt(maxAddOrUpdateNum);
                if (random.nextInt(10) < 5) {
                    segmentTree.add(L, R, num);
                    segmentTreeON.add(L, R, num);
                } else {
                    segmentTree.update(L, R, num);
                    segmentTreeON.update(L, R, num);
                }
            }

            for (int k = 0; k < testQueryTimes; k++) {
                int num1 = random.nextInt(originLength) + 1;
                int num2 = random.nextInt(originLength) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                if (segmentTree.query(L, R) != segmentTreeON.query(L, R)) {
                    System.out.println("L-R=" + L + "-" + R);
                    System.out.println("segmentTree.query(L,R)=" + segmentTree.query(L, R));
                    System.out.println("segmentTreeON.query(L,R)=" + segmentTreeON.query(L, R));
                    System.out.println("error!!!!!");
                    return;
                }
            }
            System.out.println("right "+(i+1));
        }
    }

    //arr 数组长度
    private int REAL_LENGTH;
    //线段树初始化数组，该数组从 1 开始故 初始化时 copy 整体长度 +1
    private int[] arr;
    // sum 数组存储每个树节点的累加和该树 i 节点左树 2*i 右树 2*i+1
    //整体存储结构以长度为 4 的数组为例
    //            头节点 1-4 累加和
    //                  1-4
    //          1-2            3-4
    //      1-1     2-2    3-3     4-4
    // 从头节点开始二分整个区间 下标 1-4 的累加和 二分 1-2 的累加和  3-4 的累加和，以此类推
    // 为构建完全二叉树，这里节点数不够 2^n 时向上补齐到 2^n 个 故数组准备 4N 个大小 。
    private int[] sum;
    //lazy 数组用于记录 sum 中懒增加的数
    private int[] lazyAdd;
    //懒更新数组
    private int[] lazyChange;
    //懒更新表示，对懒更新数组做标识
    private boolean[] updateFlag;


    public SegmentTree(int[] origin) {

        this.REAL_LENGTH = origin.length + 1;
        this.arr = new int[REAL_LENGTH];
        for (int i = 0; i < origin.length; i++) {
            this.arr[i + 1] = origin[i];
        }
        this.sum = new int[REAL_LENGTH << 2];
        this.lazyAdd = new int[REAL_LENGTH << 2];
        this.lazyChange = new int[REAL_LENGTH << 2];
        this.updateFlag = new boolean[REAL_LENGTH << 2];
        build(1, REAL_LENGTH - 1, 1);
    }

    //初始化 sum 线段树
    public void build(int l, int r, int index) {
        if(l>r){
            return;
        }
        if (l == r) {
            sum[index] = arr[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(l, mid, index << 1);
        build(mid + 1, r, index << 1 | 1);
        reloadSumNum(index);
    }

    private boolean checkIsLegal(int l, int r) {
        if (l >= 1 && r < arr.length && l <= r) {
            return true;
        }
        return false;
    }

    public void add(int L, int R, int num) {
        if (!checkIsLegal(L, R)) {
            return;
        }
        add(L, R, num, 1, REAL_LENGTH - 1, 1);
    }

    public void update(int L, int R, int num) {
        if (!checkIsLegal(L, R)) {
            return;
        }
        update(L, R, num, 1, REAL_LENGTH - 1, 1);
    }

    public long query(int L, int R) {
        if (!checkIsLegal(L, R)) {
            return 0;
        }
        return query(L, R, 1, REAL_LENGTH - 1, 1);
    }

    //L R num 为 数组 L-R 范围 增加 num
    //curL 和 curR 为当前所处的范围， index 为去哪个树的点找对应信息 这三个参数主要配合递归使用
    public void add(int L, int R, int num, int curL, int curR, int index) {
        //当前节点范围完全在我们要修改的范围中
        //base case 意味我们可以在此处懒住，任务不再下发
        if (L <= curL && curR <= R) {
            //当前节点计算累加和 即为 当前节点 原累加和+包含节点数*num
            sum[index] += num * (curR - curL + 1);
            //记录当前区间节点懒住的数目,以便后面无法懒住的下发
            lazyAdd[index] += num;
            return;
        }

        //无法懒住 需要对任务进行下发
        //计算中间节点 分割点
        int mid = (curL + curR) >> 1;
        //在递归分发前，可能存在之前有懒任务，所以需要将之前的懒任务先进行下发
        pushDown(index, mid - curL + 1, curR - mid);

        //当分割点大于等于 原修改点左端时 意味着此时范围中包含左孩子
        //需要向左孩子下发 为啥 L可等于mid 例如 4个 mid=2 L应该是 1-2 R 应该是 3-4 故可等
        if (L <= mid) {
            //index<<1 这个代表计算当前节点左孩子位置 即 index*2
            add(L, R, num, curL, mid, index << 1);
        }
        if (R > mid) {
            //同理 index<<1|1 即右孩子位置 即 index*2+1
            add(L, R, num, mid + 1, curR, index << 1 | 1);
        }
        //左右孩子任务下发计算完成后 当前节点需要更新 sum 信息
        reloadSumNum(index);
    }

    //更新方法,参数意义同 add
    public void update(int L, int R, int num, int curL, int curR, int index) {
        //base case
        //更新操作会覆盖掉之前做的添加操作 故清空 lazy 直接更新 sum 值
        if (L <= curL && curR <= R) {
            updateFlag[index] = true;
            lazyChange[index] = num;
            sum[index] = num * (curR - curL + 1);
            lazyAdd[index] = 0;
            return;
        }
        //无法 hole 住 下发
        int mid = (curL + curR) >> 1;
        //这里 mid-curL+1 可以代实际数想 4 mid=2 2-1=1 1+1 =2  4-2=2 lN=2 rN=2 所以左边需要 +1
        pushDown(index, mid - curL + 1, curR - mid);
        if (L <= mid) {
            update(L, R, num, curL, mid, index << 1);
        }

        if (R > mid) {
            update(L, R, num, mid + 1, curR, index << 1 | 1);
        }
        reloadSumNum(index);

    }

    //查询累加和方法 思路和 add update 基本相同
    public long query(int L, int R, int curL, int curR, int index) {
        if (L <= curL && R >= curR) {
            return sum[index];
        }
        int mid = (curL + curR) >> 1;
        //查询不命中则将当前节点攒住的任务下发下去
        pushDown(index, mid - curL + 1, curR - mid);
        long ans = 0;
        if (L <= mid) {
            ans += query(L, R, curL, mid, index << 1);
        }
        if (R > mid) {
            ans += query(L, R, mid + 1, curR, index << 1 | 1);
        }
        return ans;
    }

    //更新当前节点 sum 信息 即左右孩子 sum 相加
    private void reloadSumNum(int index) {
        sum[index] = sum[index << 1] + sum[index << 1 | 1];
    }

    //index 表示当前节点位置，lNUm 表示当前节点左节区间长度 rNum 表示当前节点 右节点 线段长度
    //这个函数很重要 下发当前节点攒住的任务
    private void pushDown(int index, int lNum, int rNum) {
        //这边将懒更新发给左右两个孩子
        if (updateFlag[index]) {
            //将懒更新下发给左孩子
            updateFlag[index << 1] = true;
            lazyChange[index << 1] = lazyChange[index];
            sum[index << 1] = lNum * lazyChange[index];
            lazyAdd[index << 1] = 0;
            //懒更新下发右孩子
            updateFlag[index << 1 | 1] = true;
            lazyChange[index << 1 | 1] = lazyChange[index];
            sum[index << 1 | 1] = rNum * lazyChange[index];
            lazyAdd[index << 1 | 1] = 0;
            //重置当前更新为 false
            updateFlag[index] = false;
        }

        //如果当前节点存在懒任务，则进行下发
        //为啥懒增加要在懒更新后面再做一遍，首先理解一点
        //大范围的如果 lazy 了一个懒更新，则子范围任何 懒信息皆无效
        //所以当 update 时，会清空前面所有 lazyadd 方法 但 add 方法进来不会清掉 lazyupdate
        //因为 update 的清除性，所以只有可能一个 update 攒住后 当前节点攒住多个 add 方法
        //所以按时间顺序需要先下发 update 再下发 后续进来的 add 方法。
        if (lazyAdd[index] != 0) {
            //下发左节点懒任务及 sum
            lazyAdd[index << 1] += lazyAdd[index];
            sum[index << 1] += lNum * lazyAdd[index];
            //下发右节点懒任务及 sum
            lazyAdd[index << 1 | 1] += lazyAdd[index];
            sum[index << 1 | 1] += rNum * lazyAdd[index];
            //重置当前节点 lazy
            lazyAdd[index] = 0;

        }
    }


    //首先给个暴力解法，O(N) 的时间复杂度
    public static class SegmentTreeON {

        int[] arr;

        public SegmentTreeON(int[] origin) {
            this.arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                this.arr[i + 1] = origin[i];
            }
        }

        public void add(int L, int R, int num) {
            if (!checkIsLegal(L, R)) {
                return;
            }
            for (int i = L; i <= R; i++) {
                arr[i] += num;
            }
        }

        public void update(int L, int R, int num) {
            if (!checkIsLegal(L, R)) {
                return;
            }
            for (int i = L; i <= R; i++) {
                arr[i] = num;
            }
        }

        public long query(int L, int R) {
            if (!checkIsLegal(L, R)) {
                return 0;
            }
            int ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

        private boolean checkIsLegal(int l, int r) {
            if (l >= 1 && r < arr.length && l <= r) {
                return true;
            }
            return false;
        }

    }

}
