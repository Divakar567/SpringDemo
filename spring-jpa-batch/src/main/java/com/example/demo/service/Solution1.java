package com.example.demo.service;

public class Solution1 {
    public static void main(String args[]) {
        int[] arr = new int[] {3, 4, 1, 2};
        weirdSum(arr.length, arr);
    }

    private static void weirdSum(int N, int[] A) {
        int totalSum = 0;
        for(int i: A) {
            totalSum += i;
        }

        for(int i=0; i<N; i++) {
            System.out.println(totalSum - A[i]);
        }
    }
}
