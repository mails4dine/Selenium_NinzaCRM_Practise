package ninza_CRM;

import java.util.Random;

public class Ex_Random {

	public static void main(String[] args) {
		String name="Dhinesh";
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 3; i++) {
		    char ch = (char) ('A' + ran.nextInt(26));
		    sb.append(ch);
		}

		String suffix = name+ sb.toString();
		String fname=name+suffix;
		System.out.println(name + suffix);
		System.out.println(fname);
		System.out.println(suffix);
	}
}
