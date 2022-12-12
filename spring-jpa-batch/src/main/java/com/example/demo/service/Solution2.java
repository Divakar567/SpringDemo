package com.example.demo.service;

public class Solution2 {

    public static void main(String args[]) {
        int[] A = new int[]{2, 3, 4, 5, 6, 1};
        longestSubarray(A.length, A);
    }

    private static void longestSubarray(int N, int[] A) {
        int longestSubarrayLength = 0;
        for (int i = 0; i < N; i++) {
            if (isFibonacciNumber(A[i])) {
                longestSubarrayLength++;
            }
        }
        System.out.println(longestSubarrayLength);
    }

    private static boolean isFibonacciNumber(int i) {
        return isPerfectSquare(5 * i * i + 4) || isPerfectSquare(5 * i * i - 4);
    }

    private static boolean isPerfectSquare(int i) {
        int s = (int) Math.sqrt(i);
        return (s * s == i);
    }
}
