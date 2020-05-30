package util;

import algo.base.Sort;

import java.util.Random;

public class ArrayUtil {

    public static int[] merge(int[] arr1, int[] arr2) {
        int[] arr = new int[arr1.length + arr2.length];
        // arr1 index
        int m = 0;
        // arr2 index
        int n = 0;
        // arr index
        int i = 0;
        for (; i < arr.length && m < arr1.length && n < arr2.length; i++) {
            if (arr1[m] > arr2[n]) {
                arr[i] = arr2[n];
                n++;
            } else {
                arr[i] = arr1[m];
                m++;
            }
        }
        for (int j = m; j < arr1.length; j++) {
            arr[i++] = arr1[j];
        }
        for (int j = n; j < arr2.length; j++) {
            arr[i++] = arr2[j];
        }
        return arr;
    }

    public static void print(Object[] arr) {
        StringBuilder sb = new StringBuilder(arr.length);
        sb.append('[');
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i != arr.length - 1)
                sb.append(", ");
        }
        sb.append(']');
        System.out.println(sb);
    }

    public static void print(int[] arr) {
        StringBuilder sb = new StringBuilder(arr.length);
        sb.append('[');
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i != arr.length - 1)
                sb.append(", ");
        }
        sb.append(']');
        System.out.println(sb);
    }

    public static  boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int j = i + 1;
            if (j < arr.length) {
                if (arr[j] < arr[i])
                    return false;
            }
        }
        return true;
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static int[] random(int max, int length) {
        int[] arr = new int[length];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(max);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr1 = random(100, 5);
        int[] arr2 = random(200, 5);
        Sort.insert(arr1);
        Sort.insert(arr2);
        print(arr1);
        print(arr2);
        int[] merge = merge(arr1, arr2);
        print(merge);
        System.out.println(isSorted(merge));

    }

}
