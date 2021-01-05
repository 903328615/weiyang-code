package leetcode.china;

/**
 * @program: weiyang-code
 * @description: 200. 岛屿数量
 * https://leetcode-cn.com/problems/number-of-islands/
 * @author: wangzibin
 * @create: 2020-12-22 19:01
 **/
public class Code_200_岛屿数量 {

    //时间 O(N*M) 此算法时间复杂度已经是极好的了
    public int numIslands(char[][] grid) {
        int num=0;
        int N=grid.length;
        int M=grid[0].length;
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                if(grid[i][j]!='1'){
                    continue;
                }
                num++;
                // 函数调用，那就是感染多少次就是有多少个岛屿
                infect(grid,i,j,N,M);
            }
        }
        return num;
    }

    //infect 感染函数
    //将 i,j 这个点如果是 1 周围及改点感染成 2
    public void infect(char[][] grid ,int i,int j,int N,int M){
        if(i>=N||i<0||j>=M||j<0||grid[i][j]!='1'){
            return;
        }
        grid[i][j]='2';
        infect(grid,i+1,j,N,M);
        infect(grid,i-1,j,N,M);
        infect(grid,i,j+1,N,M);
        infect(grid,i,j-1,N,M);
    }
}