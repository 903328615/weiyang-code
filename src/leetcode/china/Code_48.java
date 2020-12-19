package leetcode.china;

import java.util.Arrays;

/**
 * @program: weiyang-code
 * @description: 旋转图像
 * https://leetcode-cn.com/problems/rotate-image/
 * @author: wangzibin
 * @create: 2020-12-19 09:26
 **/
public class Code_48 {

    /**
     * 这个题要求原地操作 即 空间复杂度要达到 O(1)
     * 这样看来只能 swap 了 那具体要怎么 swap
     * 解法考虑分成两步
     * 对于数组
     * 1  2  3
     * 4  5  6
     * 7  8  9
     * 第一步 斜角对称交换 即 以 1 5 9 为对称轴两边树互换
     * 1  4  7
     * 2  5  8
     * 3  6  9
     * 第二部 中轴对称交换 即 以 4 5 6 为对称轴两边交换
     * 7  4  1
     * 8  5  2
     * 9  6  3
     * 此时得到数组即为所求
     */

    public static void main(String[] args) {
        int[][] arr = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotate(arr);
        System.out.println(Arrays.deepToString(arr));
    }

    public static void rotate(int[][] matrix) {
        //斜角对称轴交换
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[0].length; j++) {
                swap(matrix, i, j, j, i);
            }
        }

        //中轴交换
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length / 2; j++) {
                swap(matrix, i, j, i, matrix[0].length - j - 1);
            }
        }


    }

    public static void swap(int[][] arr, int i, int j, int x, int y) {
        int tmp = arr[i][j];
        arr[i][j] = arr[x][y];
        arr[x][y] = tmp;
    }


}