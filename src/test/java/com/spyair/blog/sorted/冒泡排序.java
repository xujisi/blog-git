package com.spyair.blog.sorted;


/**
 * 冒泡排序
 *
 * @param
 * @return
 * @author: 许集思
 * @date: 2020/11/6 17:39
 **/
public class 冒泡排序 {
    public static void main(String[] args) {
        int intArray[] = {30, 31, 32, 5, 1, 2, 22, 66, 212, 231};
        int length = intArray.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (intArray[i] > intArray[j]) {
                    int temp = intArray[i];
                    intArray[i] = intArray[j];
                    intArray[j] = temp;
                }
            }
        }
        for (int i : intArray) {
            System.out.print(i);
            if (i != intArray[intArray.length - 1]) {
                System.out.print(",");
            }
        }

    }
}
