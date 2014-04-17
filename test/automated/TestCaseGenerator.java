package automated;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Random;

public class TestCaseGenerator {
	final static int DEBUG_LEVEL = 5;
	final static int TIMER_LEVEL = 5;

	final static String FILE_NAME = "";// <<<<<<<<<--------
	final static long NUMBER_OF_CASES = 1000L;// <<<--------
	final static int DOUBLE_PRECISION = 7;// <<<<<<--------

	final static String BASE = "C:/CodeJam/GeneratedTestCase_" + FILE_NAME;
	final static String INPUT = BASE + ".test.in";
	static PrintWriter out;
	static long startTime;
	static long seed;
	// CharSets:-----------------------------------------------------------------------------------------------------\
	final static Charset US_ASCII = Charset.forName("US-ASCII");// -----Seven-bit ASCII(Basic Latin block of Unicode)|
	final static Charset ISO_LATIN_1 = Charset.forName("ISO-8859-1");// ISO Latin Alphabet No. 1---------------------|
	final static Charset UTF_8 = Charset.forName("UTF-8");// -----------Eight-bit UCS Transformation Format----------|
	final static Charset UTF_16 = Charset.forName("UTF-16");// ---------UTF-16, optional byte-order------------------|
	// --------------------------------------------------------------------------------------------------------------/
	final static char[] A_z = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	final static char[] A_Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	final static char[] a_z = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	final static char[] digits = "1234567890".toCharArray();

	private static void caseGenerator() {

		nextLongSequence(2, 1, 10000000);
		int N = nextIntSequence(1, 1, 10000)[0];
		out.println();
		nextLongSequence((int) N, 1, 10000000);
		// Remember to change NUMBER_OF_CASES up;
		// // Questão A1:
		// nextIntSequence(1, 1, 4);
		// out.println();
		// Object[] num = new Object[16];
		// for (int i = 1; i < 17; i++) {
		// num[i] = i;
		// }
		// shuffleArray(num);
		// for (int i = 1; i < 17; i++) {
		// out.print(num[i]);
		// if (i % 4 == 0)
		// out.println();
		// }
		// nextIntSequence(1, 1, 4);
		// out.println();
		// shuffleArray(num);
		// for (int i = 1; i < 17; i++) {
		// out.print(num[i]);
		// if (i % 4 == 0 && i != 16)
		// out.println();
		// }
		// --------------------------------
		// // Questão A2 (new line doesn't make difference for IOTemplate):
		// nextIntSequence(1, 1, 4);
		// out.println();
		// nextVLongSequence(16, 1, 16);
		// out.println();
		// nextIntSequence(1, 1, 4);
		// out.println();
		// nextVLongSequence(16, 1, 16);
		// -------------------------------
		// //Questão B:
		// nextDoubleSequence(1, 1, 10000);
		// nextDoubleSequence(1, 1, 100);
		// nextDoubleSequence(1, 1, 100000);
		// --------------------------------
		// //Quesão C:
		// int[] rc = nextIntSequence(2, 1, 50);
		// nextIntSequence(1, 0, rc[0] * rc[1] - 1);
		// -------------------------------
		// //Questão D:
		// int n = nextIntSequence(1, 1, 1000)[0];
		// out.println();
		// nextDoubleSequence(n, 0, 1);
		// out.println();
		// nextDoubleSequence(n, 0, 1);
		// -------------------------------
		// //Random Usage:
		// out.println();
		// nextDoubleVSequence(1000, -Math.pow(10, 100), Math.pow(10, 100));
		// out.println();
		// nextLongSequence(1000, 1, 100);
		// out.println();
		// nextStringSequence(1000, 0, 100, null);
	}

	@SuppressWarnings("unused")
	private static int[] nextIntSequence(int count, int min, int max) {
		int[] ret = new int[count];
		Random rnd = new Random();
		for (int i = 0; i < count; i++) {
			ret[i] = rnd.nextInt(max - min + 1) + min;
			out.print(ret[i] + " ");
		}
		return ret;
	}

	// (Do not generate all possible numbers for large ranges)
	@SuppressWarnings("unused")
	private static long[] nextLongSequence(int count, long min, long max) {
		long[] ret = new long[count];
		Random rnd = new Random();
		for (int i = 0; i < count; i++) {
			ret[i] = (rnd.nextLong() % (max - min) + (max - min)) % (max - min) + min;
			out.print(ret[i] + " ");
		}
		return ret;
	}

	// rnd.nextDouble() is only 2^52~10^16 significant (Do not generate all possible numbers)
	@SuppressWarnings("unused")
	private static double[] nextDoubleSequence(int count, double min, double max) {
		double[] ret = new double[count];
		Random rnd = new Random();
		for (int i = 0; i < count; i++) {
			double rd = rnd.nextDouble();
			ret[i] = rd * max + (1 - rd) * min;
			out.print(ret[i] + " ");
		}
		return ret;
	}

	@SuppressWarnings("unused")
	private static Object[] nextStringSequence(int count, long min, long max, char[] avaiable) {
		if (avaiable == null)
			avaiable = allChars(US_ASCII);
		Object[] ret = new Object[count];
		long pace = max / count - min / count;
		if (pace == 0)
			pace = 1;
		ret[0] = min;
		for (int i = 1; i < count; i++) {
			if ((long) ret[i - 1] >= max)
				ret[i] = min;
			else
				ret[i] = (long) ret[i - 1] + pace;
		}
		shuffleArray(ret);
		int len = avaiable.length;
		Random rnd = new Random();
		for (int i = 0; i < count; i++) {
			StringBuilder sb = new StringBuilder();
			for (long j = 0; j < (long) ret[i]; j++)
				sb.append(avaiable[rnd.nextInt(len)]);
			ret[i] = sb.toString();
			out.print(ret[i].toString() + " ");
		}
		return ret;
	}

	// /--------------------------------------------------------------------------------------------------\
	// |All number sequences from here are equally spaced in the given interval, but shuffled (V sequence)|
	// \--------------------------------------------------------------------------------------------------/
	@SuppressWarnings("unused")
	private static Object[] nextVLongSequence(int count, long min, long max) {
		Object[] ret = new Object[count];
		long pace = max / count - min / count;
		if (pace == 0)
			pace = 1;
		ret[0] = min;
		for (int i = 1; i < count; i++) {
			if ((long) ret[i - 1] >= max)
				ret[i] = min;
			else
				ret[i] = (long) ret[i - 1] + pace;
		}
		shuffleArray(ret);
		for (int i = 0; i < count; i++) {
			out.print(ret[i] + " ");
		}
		return ret;
	}

	@SuppressWarnings("unused")
	private static Object[] nextVBigIntegerSequence(int count, BigInteger min, BigInteger max) {
		Object[] ret = new Object[count];
		BigInteger pace = max.subtract(min).divide(BigInteger.valueOf(count));
		ret[0] = min;
		for (int i = 1; i < count; i++) {
			ret[i] = ((BigInteger) ret[i - 1]).add(pace);
		}
		shuffleArray(ret);
		for (int i = 0; i < count; i++) {
			out.print(ret[i].toString() + " ");
		}
		return ret;
	}

	@SuppressWarnings("unused")
	private static Object[] nextVDoubleSequence(int count, double min, double max) {
		Object[] ret = new Object[count];
		double pace = BigDecimal.valueOf(max / count - min / count).setScale(DOUBLE_PRECISION, RoundingMode.HALF_DOWN)
				.doubleValue();
		ret[0] = min;
		for (int i = 1; i < count; i++) {
			ret[i] = (double) ret[i - 1] + pace;
		}
		shuffleArray(ret);
		for (int i = 0; i < count; i++) {
			out.print(ret[i] + " ");
		}
		return ret;
	}

	static void shuffleArray(Object[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Object a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	// /--------------------------------------------------------------------------------------------------\
	// |allLetters & allChars return a char[] with a sequence of chars depending on the CharSet-----------|
	// \--------------------------------------------------------------------------------------------------/
	@SuppressWarnings("unused")
	private static char[] allLetters(Charset charset) {
		CharsetEncoder ce;
		if (charset != null)
			ce = charset.newEncoder();
		else
			ce = Charset.defaultCharset().newEncoder();

		StringBuilder result = new StringBuilder();
		for (char c = 0; c < Character.MAX_VALUE; c++) {
			if (ce.canEncode(c) && Character.isLetter(c)) {
				result.append(c);
			}
		}
		return result.toString().toCharArray();
	}

	@SuppressWarnings("unused")
	private static char[] allChars(Charset charset) {
		CharsetEncoder ce;
		if (charset != null)
			ce = charset.newEncoder();
		else
			ce = Charset.defaultCharset().newEncoder();

		StringBuilder result = new StringBuilder();
		for (char c = 0; c < Character.MAX_VALUE; c++) {
			if (ce.canEncode(c)) {
				result.append(c);
			}
		}
		return result.toString().toCharArray();
	}

	/*
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 * XXXXXXXXXXXXXXXX-----CODEJAM IO TEMPLATE-----XXXXXXXXXXXXXXXXX
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 */
	public static void main(String[] args) {
		try {
			out = new PrintWriter(INPUT);
			out.println(NUMBER_OF_CASES);

			for (long c = 0; c < NUMBER_OF_CASES; c++) {
				log("Generating case " + (c + 1) + "...", 1);
				startTimer(1);
				caseGenerator();
				endTimer(1);
				out.println();
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void log(String message, int d_level) {
		if (DEBUG_LEVEL >= d_level) {
			System.out.println(message);
		}
	}

	private static void startTimer(int t_level) {
		if (TIMER_LEVEL >= t_level) {
			startTime = System.currentTimeMillis();
		}
	}

	private static void endTimer(int t_level) {
		if (TIMER_LEVEL >= t_level) {
			long endTime = System.currentTimeMillis();
			System.out.println((endTime - startTime) + " ms");
		}
	}
	// XXXXXXXXXXXXXXXX-----CODEJAM IO TEMPLATE-----XXXXXXXXXXXXXXXXX
}
