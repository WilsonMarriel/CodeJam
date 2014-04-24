package _2013.Round1A;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class BullsEye {
	final static String base = "C:/CodeJam/A-large-practice";
	final static String input = base + ".in";
	final static String output = base + ".out";
	static Scanner in;
	static PrintWriter out;

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
	
	private static Long lowSQRT(Long num){
		return Math.round(StrictMath.sqrt(num.doubleValue())) - 1;
	}

	private static void caseSolver() {
		long startTime = System.nanoTime();

		Long r = in.nextLong();
		Long t = in.nextLong();
		Long x = new Long(1);
		
		x = lowSQRT(t)/ 2  - (long)(0.148 * (double)r);
		
		System.out.println(x.toString());
		
		if(x < 0) x = new Long(1);
		
		while(2*r*x-x+2*x*x <= t)
			x++;
		
		out.print(x-1);
		
		long endTime = System.nanoTime();
		Double sec = ((double)(endTime - startTime) / 1000000000.0);
		System.out.println(sec.toString());
	}
}
