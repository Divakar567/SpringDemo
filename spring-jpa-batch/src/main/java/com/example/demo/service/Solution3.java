package com.example.demo.service;

public class Solution3 {
    public static void main(String args[]) {
        int[] A = new int[]{1, 2, 1, 2, 3, 1, 1, 1, 1};
        int n = ascendingOrder(A.length, A);
        System.out.println(n);
    }

    private static int ascendingOrder(int N, int[] A) {
        int[] order = new int[N];
        order[0] = 1;
        int maxOrder = 1;
        for (int i = 1; i < N; i++) {
            if (A[i] >= A[i - 1]) {
                order[i] = order[i - 1] + 1;
            } else {
                order[i] = 1;
            }

            if (order[i] > maxOrder) {
                maxOrder = order[i];
            }
        }
        return N - maxOrder;
    }
}
