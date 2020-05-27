package util;

import java.util.Random;

public class ArrayUtil {

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

}
