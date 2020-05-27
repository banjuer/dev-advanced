package algo.base;

import util.ArrayUtil;

/**
 * 常见排序算法实现
 */
public class Sort {

    public static void buble(int[] arr) {
    }

    public static void select(int[] arr) {
        int sorted = 0;
        while (sorted < arr.length) {
            int minIndex = findMinIndex(arr, sorted, arr.length - 1);
            ArrayUtil.swap(arr, sorted, minIndex);
            sorted ++;
        }
    }

    /**
     * [l, r]中最小元素index
     */
    private static int findMinIndex(int[] arr, int l, int r) {
        int index = l;
        int min = arr[l];
        for (int i = l + 1; i <= r; i++) {
            if (min > arr[i]) {
                min = arr[i];
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = ArrayUtil.random(1000, 20);
        ArrayUtil.print(arr);
        select(arr);
        ArrayUtil.print(arr);
        System.out.println(ArrayUtil.isSorted(arr));
    }

}
