package algo.base;

import util.ArrayUtil;

import java.util.Random;

/**
 * 常见排序算法实现
 */
public class Sort {

	// ============== n(log n)复杂度排序算法 ==============

	public static void quick3way(int[] arr) {

	}

	/**
	 * 优化重复元素
	 */
	public static void quick2(int[] arr) {
		quickSort2(arr, 0, arr.length - 1);
	}

	private static void quickSort2(int[] arr, int l, int r) {
		if (l >= r)
			return;
		int p = partition2(arr, l, r);
		quickSort2(arr, l, p - 1);
		quickSort2(arr, p + 1, r);
	}

	private static int partition2(int[] arr, int l, int r) {
		ArrayUtil.swap(arr, l, new Random().nextInt(r - l) + l);
		int v = arr[l];
		// [l+1, i) <= V, (j,r]>=v
		int i = l + 1, j = r;
		while (i <= j) {
			// 第一个>=V
			while (i <= r && arr[i] < v)
				i++;
			// 最后一个<=V
			while (j >= l + 1 && arr[j] > v)
				j--;
			ArrayUtil.swap(arr, i++, j--);
		}
		// 因为标定点在<=V一端,所以要与j交换
		ArrayUtil.swap(arr, l, j);
		return j;
	}

	public static void quick(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	private static void quickSort(int[] arr, int l, int r) {
		if (l >= r) {
			return;
		}
		int p = partition(arr, l, r);
		quickSort(arr, l, p - 1);
		;
		quickSort(arr, p + 1, r);
	}

	/**
	 * 确定标定元素在数组中的索引位
	 */
	private static int partition(int[] arr, int l, int r) {
		// 优化点1: 使用随机标定点. 近乎有序的数组会退化成n^2
		ArrayUtil.swap(arr, l, new Random().nextInt(r - l) + l);
		int v = arr[l];
		int j = l;
		// < V [l + 1, j], >V [j + 1, i)
		for (int i = l + 1; i <= r; i++) {
			if (arr[i] < v) {
				ArrayUtil.swap(arr, i, ++j);
			}
		}
		ArrayUtil.swap(arr, l, j);
		return j;
	}

	/**
	 * 自底向上的归并(效率更高, 同时更适合链表排序)
	 */
	public static void mergeBottomUp(int[] arr) {
		for (int sz = 1; sz <= arr.length; sz += sz) {
			for (int i = 0; i + sz < arr.length; i += sz + sz) {
				merge(arr, i, i + sz - 1, Math.min(i + sz - 1 + sz, arr.length - 1));
			}
		}
	}

	public static void merge(int[] arr) {
		mergeSort(arr, 0, arr.length - 1);
	}

	/**
	 * [l, r]归并排序
	 */
	private static void mergeSort(int[] arr, int l, int r) {
		if (l >= r) {
			return;
		}
		int mid = l + (r - l) / 2;
		mergeSort(arr, l, mid);
		mergeSort(arr, mid + 1, r);
		merge(arr, l, mid, r);
	}

	/**
	 * 以mid为界, 归并[l, mid] [mid + 1, r] 数组
	 */
	private static void merge(int[] arr, int l, int mid, int r) {
		int[] merge = new int[r - l + 1];
		// 分别为左索引, 右索引, 与临时存储的索引
		int m = l, n = mid + 1, i = 0;
		for (; i < merge.length && m <= mid && n <= r; i++) {
			if (arr[m] > arr[n]) {
				merge[i] = arr[n];
				n++;
			} else {
				merge[i] = arr[m];
				m++;
			}
		}
		for (int j = m; j <= mid; j++) {
			merge[i++] = arr[j];
		}
		for (int j = n; j <= r; j++) {
			merge[i++] = arr[j];
		}
		for (int j = l, k = 0; j <= r; j++, k++) {
			arr[j] = merge[k];
		}
	}

	// ============== n^2复杂度排序算法 ==============

	/**
	 * 待排序元素中, 每次取第一个元素A与后面元素B比较, A>B -> swap A, B 否则叫大数取B A的索引始终是B-1
	 */
	public static void bubble(int[] arr) {
		int sorted = 0;
		// 已排序元素个数
		while (sorted <= arr.length) {
			// 标定元素 index -> i - 1
			int p = arr[0];
			// 待排序元素(标定元素的下一个) index -> i
			for (int i = 1; i < arr.length - sorted; i++) {
				if (arr[i] > p) {
					p = arr[i];
				} else {
					ArrayUtil.swap(arr, i, i - 1);
				}

			}
			sorted++;
		}
	}

	/**
	 * 每次从待排序元素中选择一个元素插入到合适位置: 如何插入? 每次与前一个元素(已排序)比较, 小于则交换位置 利用已排序元素的特性, 优化插入效率 =>
	 * 希尔排序
	 *
	 * 待排序索引位: [i, length) 初始为[0, length) 已排序索引位: [0, i) 初始为[0, i)
	 */
	public static void insert(int[] arr) {
		// 待排序
		for (int i = 0; i < arr.length; i++) {
			// 已排序
			for (int j = 0; j < i; j++) {
				if (arr[i] < arr[j])
					ArrayUtil.swap(arr, i, j);
			}
		}
	}

	/**
	 * 每次从待排序的元素中选择一个最小的索引, 与第一个待排序交换索引
	 */
	public static void select(int[] arr) {
		int sorted = 0;
		while (sorted < arr.length) {
			int minIndex = findMinIndex(arr, sorted, arr.length - 1);
			ArrayUtil.swap(arr, sorted, minIndex);
			sorted++;
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
		int[] arr = ArrayUtil.random(10, 100000);
		long start = System.currentTimeMillis();
		// ArrayUtil.print(arr);
		// select(arr);
		// insert(arr);
		// bubble(arr);
		// merge(arr);
		// mergeBottomUp(arr);
		// quick(arr);
		quick2(arr);
		long end = System.currentTimeMillis();
		// ArrayUtil.print(arr);
		System.out.println("sorted:" + ArrayUtil.isSorted(arr) + " cost:" + (end - start));
	}

}
