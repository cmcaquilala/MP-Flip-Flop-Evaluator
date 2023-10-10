
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashMap;
import java.util.Collections;

public class Main {
	static String type = "";
	static int noFF = 0;
	static int noIn = 0;
	static int noOut = 0;
	static ArrayList<String> eqOut = new ArrayList<String>();
	static ArrayList<String> eqNext = new ArrayList<String>();
	static ArrayList<String> eqFFIn = new ArrayList<String>();
	static int noRows = 0;
	
	public static void main(String[] args) {
		
		// TODO
		// - ***Read user-input equations
		// - ***Update the outputs of each row after applying the equation
		// - User input error handling
		// - Table Header / Table formatting in general
		
		// Test Input
		// Example 1 of PPT
		
		//eqOut.add("Ax'+Bx'");
		//eqNext.add("Ax+Bx");
		//eqNext.add("A'x");
		
//		// Example 2 of PPT
//		String type = "JK";
//		int noFF = 2;
//		int noIn = 1;
//		int noOut = 0;
//		
//		ArrayList<String> eqNext = new ArrayList<String>();
//		ArrayList<String> eqFFIn = new ArrayList<String>();
//		eqFFIn.add("B");
//		eqFFIn.add("Bx'");
//		eqFFIn.add("x'");
//		eqFFIn.add("A'x+Ax'");
		
		
		
		
		
//		// gumagana pero di pa tapos (error handling, formatting, etc.)
//		// User Input Part
		Scanner sc = new Scanner(System.in);
//		
	    System.out.println("Input type of flip-flop (D, T, JK, RS): ");
        type = sc.nextLine();
//		
        System.out.println("Input number of flip-flops: ");
        noFF = sc.nextInt();
//		
        System.out.println("Input number of input variables: ");
        noIn = sc.nextInt();
//		
        System.out.println("Input number of output variables: ");
        noOut = sc.nextInt();
		sc.nextLine();
//		
		for(int i = 0; i < noOut; i++) {
		System.out.print("Input the equation for output variable " + ("\n") + ((char)(120 + noIn + i)) + "=");
		String temp = ((char)(120 + noIn + i)) + "=" + sc.nextLine();
			eqOut.add(temp);
		}
//		
		if(type.equals("D") || type.equals("T")) {
			for(int i = 0; i < noFF; i++) {
				System.out.print("Input the equation for flip-flop " + type + ("\n") + ((char)(65+i)) + "=");
				String temp = ((char)(65+i)) + "=" + sc.nextLine();
				eqNext.add(temp);
			}
		} else {
			for(int i = 0; i < noFF; i++) {
				System.out.print("Input the equation for flip-flop " + ((char)(65+i)) + ("\n") + (type.equals("JK")? "J":"R") + ((char)(65+i)) + "=");
				String temp = (type.equals("JK")? "J":"R") + ((char)(65+i)) + "=" + sc.nextLine();
				eqNext.add(temp);
				
				System.out.print("Input the equation for flip-flop " + ((char)(65+i)) + ("\n") + (type.equals("JK")? "K":"S") + ((char)(65+i)) + "=");
				temp = (type.equals("JK")? "K":"S") + ((char)(65+i)) + "=" + sc.nextLine();
				eqNext.add(temp);
			}
		}


		
//		// Displays the input (for testing only)
		System.out.println("Type: " + type);
		System.out.println("No. of Flip Flops: " + noFF);
		System.out.println("No. of input variables: " + noIn);
		System.out.println("No. of output variables: " + noOut);
		for(int i = 0; i < noOut; i++) {
			System.out.println("EQ of output variable " + ((char)(120 + noIn + i)) + ": " + eqOut.get(i));
		}
		for(int i = 0; i < noFF; i++) {
			System.out.println("EQ of flip-flop " + ((char)(65+i)) + ": ");
			if(type.equals("D") || type.equals("T")) {
				System.out.println("\t" + eqNext.get(i));
			}
			if(type.equals("JK") || type.equals("RS")) {
				System.out.println("\t" + eqNext.get(2*i));
				System.out.println("\t" + eqNext.get(2*i + 1));
			}
				
		}

		sc.close();
		
		ArrayList<String> ff = new ArrayList<String>(); //flip-flop
		ArrayList<String> in = new ArrayList<String>(); //input

		for (int i = 0; i < eqNext.size(); i++) { //collects all flip-flop and input variables
			String eqn = eqNext.get(i);
			Boolean e = false;

			for (int k = 0; k < eqn.length(); k++) {

				if(eqn.charAt(k) == '='){
					e = true;
					continue;
				}
				if(e == true) {
					if(Character.isUpperCase(eqn.charAt(k)) && !ff.contains( "" + eqn.charAt(k))) {
						ff.add("" + eqn.charAt(k));
					}

					if(Character.isLowerCase(eqn.charAt(k)) && !in.contains( "" + eqn.charAt(k))) {
						in.add("" + eqn.charAt(k));
					}
				}
			}
		}

		for (int i = 0; i < eqOut.size(); i++) {
			String eqn = eqOut.get(i);
			Boolean e = false;

			for (int k = 0; k < eqn.length(); k++) {

				if(eqn.charAt(k) == '='){
					e = true;
					continue;
				}
				if(e == true) {
					if(Character.isUpperCase(eqn.charAt(k)) && !ff.contains( "" + eqn.charAt(k))) {
						ff.add("" + eqn.charAt(k));
					}

					if(Character.isLowerCase(eqn.charAt(k)) && !in.contains( "" + eqn.charAt(k))) {
						in.add("" + eqn.charAt(k));
					}
				}
			}
		}

		Collections.sort(ff);
		Collections.sort(in);
		
		// Creates a table
		// Rows = 2^(noFF + noIn) 
		//
		// Columns = (2 * noFF) + noIn + noOut
		
		noRows = (int)Math.pow(2, noFF + noIn);
		Row[] table = new Row[noRows];
		for(int i = 0; i < noRows; i++) {
			Row row = new Row(type, noFF, noIn, noOut);
			row.setInputsFromDecimal(i);
			table[i] = row;
			if(type.equals("D")) { //D FLIP-FLOP

				for (int j = 0; j < eqNext.size(); j++) { //NEXT STATE
					String f = "";
					String eqn = eqNext.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);

					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);

					row.FFIn[j] = solver.evaluate();

				}

				for(int j = 0; j < row.FFIn.length; j++) {
					row.FFNext[j] = "" + row.FFIn[j];
				}

				for (int j = 0; j < eqOut.size(); j++) { //Output Variables
					String f = "";
					String eqn = eqOut.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);

					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);

					row.output[j] = solver.evaluate();

				}


			}

			if(type.equals("T")) {
				for (int j = 0; j < eqNext.size(); j++) { //NEXT STATE
					String f = "";
					String eqn = eqNext.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);

					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);

					int outputNext = solver.evaluate();

					row.FFIn[j] = outputNext;
					
					if(outputNext == 0) { //no change
						row.FFNext[j] = "" + row.FFCurr[j];
					}
					else{ //complement
						row.FFNext[j] = "" + ((row.FFCurr[j] == 0)? 1 : 0);
					}

				}

				for (int j = 0; j < eqOut.size(); j++) { //Output Variables
					String f = "";
					String eqn = eqOut.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);

					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);

					row.output[j] = solver.evaluate();

				}
			}

			if(type.equals("JK")) {

				for (int j = 0; j < eqNext.size(); j++) { //Solving for J and K
					String f = "";
					String eqn = eqNext.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);
					

					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);
					
					row.FFIn[j] = solver.evaluate();

				}

				for (int j = 0; j < row.FFNext.length; j++) { //Next State
					int jIn = row.FFIn[j*2];
					int kIn = row.FFIn[j*2 + 1];

					if (jIn == 0 && kIn == 0) { //No Change
						row.FFNext[j] = "" + row.FFCurr[j];
					}
					if (jIn == 0 && kIn == 1) { // Reset
						row.FFNext[j] = "" + 0;
					}
					if(jIn == 1 && kIn == 0){ // Set
						row.FFNext[j] = "" + 1;
					}
					if(jIn == 1 && kIn == 1){ //Complement
						row.FFNext[j] = "" + ((row.FFCurr[j] == 0)? 1 : 0);
					}
				}


				for (int j = 0; j < eqOut.size(); j++) { //Output Variables
					String f = "";
					String eqn = eqOut.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);


					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);

					row.output[j] = solver.evaluate();

				}
			}

			if(type.equals("RS")) {
				for (int j = 0; j < eqNext.size(); j++) { //Solving for R and S
					String f = "";
					String eqn = eqNext.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);


					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);
					
					row.FFIn[j] = solver.evaluate();

				}

				for (int j = 0; j < row.FFNext.length; j++) { //Next State
					int rIn = row.FFIn[j*2];
					int sIn = row.FFIn[j*2 + 1];

					if (rIn == 0 && sIn == 0) { //No Change
						row.FFNext[j] = "" + row.FFCurr[j];
					}
					if (rIn == 0 && sIn == 1) { // Set
						row.FFNext[j] = "" + 1;
					}
					if(rIn == 1 && sIn == 0){ // Reset
						row.FFNext[j] = "" + 0;
					}
					if(rIn == 1 && sIn == 1){ //Unpredictable
						row.FFNext[j] = "?";
					}
				}


				for (int j = 0; j < eqOut.size(); j++) { //Output Variables
					String f = "";
					String eqn = eqOut.get(j);
					Boolean e = false;

					ArrayList<String> ffVariables = new ArrayList<String>();
					ArrayList<String> inVariables = new ArrayList<String>();

					for (int k = 0; k < eqn.length(); k++) {

						if(eqn.charAt(k) == '='){
							e = true;
							continue;
						}
						if(e == true) {
							f += eqn.charAt(k);
							if(Character.isUpperCase(eqn.charAt(k)) && !ffVariables.contains( "" + eqn.charAt(k))) {
								ffVariables.add("" + eqn.charAt(k));
							}
	
							if(Character.isLowerCase(eqn.charAt(k)) && !inVariables.contains( "" + eqn.charAt(k))) {
								inVariables.add("" + eqn.charAt(k));
							}
						}
					}

					Collections.sort(ffVariables);
					Collections.sort(inVariables);


					HashMap<String, Integer> vars = new HashMap<String, Integer>();

					for (int l = 0; l < ffVariables.size(); l++) {
						String x = ffVariables.get(l);
						int y = ff.indexOf(x);
						vars.put(x, row.FFCurr[y]);
					}

					for(int l = 0; l < inVariables.size(); l++) {
						String x = inVariables.get(l);
						int y = in.indexOf(x);
						vars.put(x, row.input[y]);
					}

					InfixToPostfix inf = new InfixToPostfix(f);

					f = inf.convertToPostFix();

					BooleanPostfix solver = new BooleanPostfix(f, vars);

					row.output[j] = solver.evaluate();

				}
			}

		}
		
		// Prints table
		System.out.println();
		printTable(table);
		
	}
	
	public static void printTable(Row[] table) {
		System.out.println("Table: " + type);
		
		for(int i = 0; i < noFF; i++) {
			char a = (char)(65+i);
			System.out.print(a + "  ");
		}

		for(int i = 0; i < noIn; i++) {
			char a = (char)(120+i);
			System.out.print(a + "  ");
		}
		
		System.out.print("| ");
		
		if(type.equals("D")) {
			for(int i = 0; i < noFF; i++) {
				char a = (char)(65+i);
				System.out.print("D" + a + " ");
			}
		} else if(type.equals("T")) {
			for(int i = 0; i < noFF; i++) {
				char a = (char)(65+i);
				System.out.print("T" + a + " ");
			}
		} else if(type.equals("JK")) {
			for(int i = 0; i < noFF; i++) {
				char a = (char)(65+i);
				System.out.print("J" + a + " ");
				System.out.print("K" + a + " ");
			}
		} else if(type.equals("RS")) {
			for(int i = 0; i < noFF; i++) {
				char a = (char)(65+i);
				System.out.print("R" + a + " ");
				System.out.print("S" + a + " ");
			}
		}
		
		System.out.print("| ");
		
		for(int i = 0; i < noFF; i++) {
			char a = (char)(65+i);
			System.out.print(a + "  ");
		}

		if(noOut >0) {
			System.out.print("| ");
		}
		for(int i = 0; i < noOut; i++) {
			char a = (char)(120+noIn+i);
			System.out.print(a + "  ");
		}
		
		System.out.println();
		for(int i = 0; i < noRows; i++) {
			table[i].print();
		}
		System.out.println();
		System.out.println();
	}
	
	public static class Row {
		String type;
		int[] FFCurr;
		int[] input;
		int[] FFIn;
		String[] FFNext;
		int[] output;
		
		public Row(String type, int noFF, int noIn, int noOut){
			this.type = type;
			this.input = new int[noIn];
			this.output = new int[noOut];
			this.FFCurr = new int[noFF];
			this.FFNext = new String[noFF];
			this.FFIn = new int[noFF];
			if(type.equals("RS") || type.equals("JK")) {
				this.FFIn = new int[noFF*2];
			}
		}
		
		public void setInputsFromDecimal(int x) {
			String bin = Integer.toBinaryString(x);

			while(bin.length() < (FFCurr.length + input.length)) {
				bin = "0" + bin;
			}
//			System.out.println(bin);
			
			int index = 0;
			
			for(int i = 0; i < FFCurr.length; i++) {
				FFCurr[i] = Integer.parseInt(bin.charAt(index++) + "");
			}
			for(int i = 0; i < input.length; i++) {
				input[i] = Integer.parseInt(bin.charAt(index++) + "");
			}
		}
		
		public void setFFNext(int index, int value) {
			this.FFNext[index] = "" + value;
		}
		
		public void setOutput(int index, int value) {
			this.output[index] = value;
		}
		
		
		public void print() {
			for(int i = 0; i < FFCurr.length; i++) {
				System.out.print(FFCurr[i] + "  ");
			}
			for(int i = 0; i < input.length; i++) {
				System.out.print(input[i] + "  ");
			}
			System.out.print("| ");
			if(FFIn != null) {
				for(int i = 0; i < FFIn.length; i++) {
					System.out.print(FFIn[i] + "  ");
				}
				System.out.print("| ");
			}
			for(int i = 0; i < FFNext.length; i++) {
				System.out.print(FFNext[i] + "  ");
			}
			if(output.length > 0) {
				System.out.print("| ");
			}
			for(int i = 0; i < output.length; i++) {
				System.out.print(output[i] + "  ");
			}
			System.out.println();
		}
		
	}
}
