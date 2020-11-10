package com.spyair.blog.sorted;

public class 选择排序 {
    public static void main(String[] args) {
        int intArray[] = {30, 31, 32, 5, 1, 2, 22, 66, 212, 231};
        for (int k = 0; k < intArray.length; k++) {
            //初始最小值
            int minItem = 0;
            //初始位置
            int location = k;
            //选择出最小的数
            minItem = intArray[k];
            for (int i = k; i < intArray.length; i++) {
                if (minItem > intArray[i]) {
                    minItem = intArray[i];
                    location = i;
                }
            }
            //选出最小值后和第k个数互换位置
            int temp = intArray[k];
            intArray[k] = minItem;
            intArray[location] = temp;
        }

        for (int i : intArray) {
            System.out.print(i);
            if (i != intArray[intArray.length - 1]) {
                System.out.print(",");
            }
        }
    }
}
