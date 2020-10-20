package com.spyair.blog.util.sklearn.knn;


public class Client {
    public static void main(String[] args){
        String trainDataPath = "D:\\Blog\\src\\main\\java\\com\\spyair\\blog\\util\\sklearn\\knn\\testData\\traInInput.txt";
        String testDataPath = "D:\\Blog\\src\\main\\java\\com\\spyair\\blog\\util\\sklearn\\knn\\testData\\testInput.txt";

        KNNTool tool = new KNNTool(trainDataPath, testDataPath);
        tool.knnCompute(3);
    }


}
