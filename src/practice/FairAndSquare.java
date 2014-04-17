package practice;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

//66% (45/100 pts)
public class FairAndSquare {
	final static String base = "C:/CodeJam/C-large-practice-2";
	final static String input = base + ".in";
	final static String output = base + ".out";
	static Scanner in;
	static PrintWriter out;
	static ArrayList<BigInteger> fairandsquare = new ArrayList<BigInteger>();
	static BigInteger last_palindrome = BigInteger.valueOf(0);

	public static void main(String[] args) {
		try {
			in = new Scanner(new FileReader(input));
			out = new PrintWriter(output);

			int number_of_cases = in.nextInt();
			in.nextLine();
			for (int c = 0; c < number_of_cases; c++) {
				System.out.println("Testing case " + (c + 1) + "...");
				out.print("Case #" + (c + 1) + ": ");
				caseSolver();
				out.println();
			}
			out.println();
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void caseSolver() {
		BigInteger A = in.nextBigInteger();
		BigInteger B = in.nextBigInteger();
		refreshList(B);
		
		int i = 0;
		int counter = 0;
		while(A.compareTo(fairandsquare.get(i)) > 0)
			i++;
		while(i < fairandsquare.size() && B.compareTo(fairandsquare.get(i)) >= 0){
			counter++;
			i++;
		}
		out.print(counter);
	}

	private static Boolean isPalindrome(BigInteger num) {
		String str_num = num.toString();
		int len = str_num.length();
		for (int i = 0; i < len / 2; i++) {
			if (str_num.charAt(i) != str_num.charAt(len - i - 1))
				return false;
		}
		return true;
	}

	private static BigInteger nextPalindrome(BigInteger num) {
		if (num.compareTo(BigInteger.valueOf(0)) == 0)
			return BigInteger.valueOf(1);

		String str_num = num.toString();
		int len = str_num.length();
		// if the number is all nines, return number + 2
		if (str_num.charAt(0) == '9'
				&& num.add(BigInteger.valueOf(1)).toString().charAt(0) == '1')
			return num.add(BigInteger.valueOf(2));

		int parity = len % 2;
		boolean isBigger = false;
		for (int i = 0; i < len / 2; i++) {
			int cmp2 = Integer.valueOf(str_num.charAt(len / 2 - 1 - i))
					- Integer.valueOf(str_num.charAt(len / 2 + i + parity));
			if (cmp2 > 0) {
				isBigger = true;
				break;
			} else if (cmp2 < 0)
				break;
		}

		if (isBigger) {
			StringBuilder nextP = new StringBuilder();
			for (int i = 0; i < len / 2 + parity; i++) {
				nextP.append(str_num.charAt(i));
			}
			for (int i = 0; i < len / 2; i++) {
				nextP.append(str_num.charAt(len / 2 - i - 1));
			}
			return new BigInteger(nextP.toString());
		} else {
			StringBuilder nextP = new StringBuilder();
			for (int i = 0; i < len / 2 + parity; i++) {
				nextP.append(str_num.charAt(i));
			}
			nextP = new StringBuilder((new BigInteger(nextP.toString())).add(
					BigInteger.valueOf(1)).toString());
			for (int i = 0; i < len / 2; i++) {
				nextP.append(nextP.charAt(len / 2 - i - 1));
			}
			return new BigInteger(nextP.toString());
		}
	}

	private static BigInteger highSQRT(BigInteger num) {
		int tenpow = num.toString().length();
		if(tenpow % 2 == 0) return BigInteger.valueOf(10).pow(tenpow/2);
		else return BigInteger.valueOf(4).multiply(BigInteger.valueOf(10).pow(tenpow/2));
	}
	private static void refreshList(BigInteger num) {
		BigInteger numh = highSQRT(num);
		if (numh.compareTo(last_palindrome) <= 0)
			return;

		BigInteger current_palindrome = nextPalindrome(last_palindrome);
		while (current_palindrome.compareTo(numh) <= 0) {
			BigInteger current_square = current_palindrome
					.multiply(current_palindrome);
			if (isPalindrome(current_square))
				fairandsquare.add(current_square);
			current_palindrome = nextPalindrome(current_palindrome);
		}
		last_palindrome = numh;
	}
}
