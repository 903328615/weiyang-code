package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 85. 最大矩形
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 * 输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * 输出：6
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximal-rectangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author: wangzibin
 * @create: 2020-12-26 12:45
 **/
public class Code_85 {

    //遍历矩形 至少 n^2 * m ^2 使用暴力法是达不到时间复杂度要求的
    //当前解法是暴力法+预处理数组 可以将时间复杂度压到 O(m^2 * n)  空间复杂度 O(mn)
    public int maximalRectangle(char[][] matrix) {

        if(matrix==null||matrix.length<1){
            return 0;
        }
        int m=matrix.length;
        int n=matrix[0].length;
        //预处理面积 m*n r的含义为 当前点向右连续 1 的个数
        int [][] left=new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(j==0){
                    left[i][j]=matrix[i][j]=='1'?1:0;
                }else if(matrix[i][j]=='1'){
                    left[i][j]= left[i][j-1]+1;
                }
            }
        }
        int max=0;
        // m^2*n
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]=='0'){
                    continue;
                }
                int width = left[i][j];
                int area = width;
                for (int k = i - 1; k >= 0; k--) {
                    width = Math.min(width, left[k][j]);
                    area = Math.max(area, (i - k + 1) * width);
                }

                max=Math.max(max,area);
            }
        }
        return max;
    }
}