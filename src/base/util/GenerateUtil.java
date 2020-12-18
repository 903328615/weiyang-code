package base.util;

import java.util.Random;

public class GenerateUtil {

    private static Random random=new Random();
    //随机生成 int arr 数组 长度不超过 maxLength 最大值不超过 maxNum
    public static int[] generateArray(int maxLength,int maxNum){
       int length= random.nextInt(maxLength);
       int[] arr=new int[length];
       if(maxNum<=0){
           return arr;
       }
       for (int i=0;i<length;i++){
           arr[i]=random.nextInt(maxNum);
       }
       return arr;
    }

}
