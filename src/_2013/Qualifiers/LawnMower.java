package _2013.Qualifiers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

//100% (40pts)
public class LawnMower {
	final static String base = "C:/CodeJam/B-large-practice";
	final static String input = base + ".in";
	final static String output = base + ".out";
	static Scanner in;
	static PrintWriter out;
	
    public static void main(String[] args){
        try {
            in = new Scanner(new FileReader(input));
            out = new PrintWriter(output);

            int number_of_cases = in.nextInt();
            in.nextLine();
            for (int c = 0; c < number_of_cases; c++) {
                System.out.println("Testing case " + (c + 1) + "...");
                out.print("Case #" + (c + 1) + ": ");
                if(caseSolver())
                	out.print("YES");
                else
                	out.print("NO");
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

	private static boolean caseSolver(){
		int N = in.nextInt();//1 <= N, M <= 100
		int M = in.nextInt();
		int[][] A = new int[N][M];
		int[] Ai = new int[M];
		int[] Bi = new int[N];
		
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				A[i][j] = in.nextInt();//1 <= A(i,j) <= 100
			}
		}
		//Ai[j] will be the biggest high of the vertical line j
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				if(Ai[j] == 0 || Ai[j] < A[i][j])
					Ai[j] = A[i][j];
			}
		}
		//Bi[j] will be the biggest high of the horizontal line j
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				if(Bi[j] == 0 || Bi[j] < A[j][i])
					Bi[j] = A[j][i];
			}
		}
		//any A[i][j] have to be the minimum between Ai[j] and Bi[i]
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){
				if(A[i][j] != Math.min(Bi[i], Ai[j]))
					return false;
			}
		}
		return true;
	}
}
