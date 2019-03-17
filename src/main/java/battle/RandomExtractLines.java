package battle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class RandomExtractLines {

	/**
	 * 方法1: 获取最大行, 生成行内随机N个数, 读取文件
	 * 
	 * @param filePath
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public List<String> extractLines1(String filePath, int n) throws IOException {
		// 获取最大行数(看了下实现, bufferreader.readline, 可能不符合题意)
		int maxLine = (int) Files.lines(Paths.get(filePath)).count();
		// 生成最大行数内的n个随机数
		Iterator<Integer> lineNumbers = sampleNumbers(maxLine, n);
		List<String> lines = new LinkedList<>();
		int curNumber = lineNumbers.next();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
		String line;
		int count = 0;
		while ((line = bufferedReader.readLine()) != null) {
			count++;
			if (count == curNumber) {
				lines.add(line);
				if (lineNumbers.hasNext()) {
					curNumber = lineNumbers.next();
				} else {
					return lines;
				}
			}
		}
		return lines;
	}

	private Iterator<Integer> sampleNumbers(int max, int n) {
		max += 1;
		Random random = new Random(System.currentTimeMillis());
		Set<Integer> numbers = new TreeSet<>();
		while (numbers.size() <= n) {
			numbers.add(random.nextInt(max));
		}
		return numbers.iterator();
	}

	/**
	 * 方法二: 总体随机放到每一行的随机, 问题:数据倾斜严重
	 *
	 * @param filePath
	 * @param n
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public List<String> extractLines2(String filePath, int n) throws IOException {
		LinkedList<String> samples = new LinkedList<>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
		String line;
		Random random = new Random(System.currentTimeMillis());
		while ((line = bufferedReader.readLine()) != null) {
			if (samples.size() <= n) {
				samples.add(line);
			} else {
				int prob =random.nextInt(2);
				if (prob == 1) {
					samples.removeFirst();
					samples.offer(line);
				}
			}
		}
		return samples;
	}

	public static void main(String[] args) throws IOException {
		RandomExtractLines rel = new RandomExtractLines();
		String path = "C:/Users/Administrator/Desktop/temp/data/sample_data_int.txt";
		int n = 12;
		System.out.println(rel.extractLines1(path, n));
		System.out.println(rel.extractLines2(path, n));
	}

}
