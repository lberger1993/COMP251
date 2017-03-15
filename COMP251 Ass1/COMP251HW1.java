/*
Lucia BERGER 

COllaborated with Nic Yanzi, Dedie Kahn, & Kalvin Hao 


*/


import java.io.*;
import java.util.*;

/**
 * 
 * ATTENTION: ANY CHANGES IN INITIAL CODE (INCLUDING FILE NAME, METHODS, CONSTRUCTORS etc) WILL CAUSE NOT POSITIVE MARK! 
 * HOWEVER YOU CAN CREATE YOUR OWN METHODS WITH CORRECT NAME. ONLY THIS FILE WILL BE GRADED, that is NO EXTERNAL CLASSES are allowed.
 *
 * TO STUDENT: ALL IMPORTANT PARTS ARE SELECTED "TO STUDENT" AND WRITTEN IN HEADERS OF METHODS. * 
 * @author AlexanderButyaev
 *
 */
public class COMP251HW1 {
	//Example for 10%, 30%, 50%, 70%, 80%, 90%, 100%, 120%, 150%, 200% --> This has to be updated
	public double[] ns = {0.1, 0.2, 0.3, 0,4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0};
	/*Fields / methods for grading BEGIN*/
	private HashMap<Integer,String> pathMap;
	
	public HashMap<Integer, String> getPaths() {
		return pathMap;
	}

	public void setPaths(HashMap<Integer, String> pathMap) {
		this.pathMap = pathMap;
	}
	/*Fields / methods for grading END*/
	
	/**
	 * method generateRandomNumbers generates array of random numbers (double primitive) of size = "size" and w, which limits generated random number by 2^w-1 
	 * @param w - integer number, which limits generated random number by 2^w-1
	 * @param size - size of the resulting array
	 * @return double[]
	 */
	public double[] generateRandomNumbers(int w, int size) {
		double[] resultArray = new double[size];
		if (getPaths() != null) {	//THIS PART WILL BE USED FOR GRADING
			String path = getPaths().get(size);
			File file = new File (path);
			Scanner scanner;
			try {
				scanner = new Scanner(file);
				int i = 0;
				while (scanner.hasNextLine() && i < resultArray.length) {
					resultArray[i] = Double.parseDouble(scanner.nextLine());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			
			for (int i = 0; i < size; i++) {
				resultArray[i] = Math.floor(Math.random()*(Math.pow(2, w)-1)); //cast to int to make it Integer
			} 
		}
		return resultArray;
	}

	public double generateRandomNumberiInRange(double min, double max) {
		double res = min;
		while (res == min) {
			res = Math.floor(min + Math.random() * (max - min));
		}
		return res;
	}
	
	/**
	 * method generateCSVOutputFile generates CSV File which contains row of x (first element is identificator "X"), 
	 * and one row for every experiment (ys - id with set of values)
	 * Looks like this:
	 * ================
	 *  X,1,2,3
	 *  E1,15,66,34
	 *  E2,16,15,14
	 *  E3,99,88,77
	 * ================
	 *  
	 * @param filePathName - absolute path to the file with name (it will be rewritten or created)
	 * @param x - values along X axis, eg 1,2,3,4,5,6,7,8
	 * @param ys - values for Y axis with the name of the experiment for different plots.
	 */
	public void generateCSVOutputFile(String filePathName, double[] x, HashMap<String, double[]> ys) {
		File file = new File(filePathName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.append("X");
			for (double r: x) {
				fw.append("," + r);
			}
			fw.append("\n");
			for (Map.Entry<String, double[]> entry: ys.entrySet()) {
				fw.append(entry.getKey());
				double[] dTemp = entry.getValue();
				for (int i = 0, len = dTemp.length;i < len; i++) {
					fw.append(","+dTemp[i]);
				}
				fw.append("\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * divisionMethod is the main method for division method in hashing problem. It creates specific array ys, 
	 * iterates over the set of n (defined by ns field) and for every n adds in ys array particular number of collisions
	 * 
	 * It requires arguments:
	 * r - division factor, m - number of slots, w - integer number (required for random number generator)  
	 * 
	 * It returns an array of number of collisions (for all n in range from 10% to 200% of m) - later in plotting phase - it is Y values for divisionMethod
	 * @param r
	 * @param m
	 * @param w
	 * @return ys for division method {double[]}
	 */
	public double[] divisionMethod (int r, int m, int w) {
		double[] ys = new double[ns.length];
		for (int it = 0, len = ns.length; it < len; it++) {
			int n = (int)(ns[it]*m);
			ys[it] = divisionMethodImpl (r, n, w);
		}
		return ys;
	}
	
	/**
	 * divisionMethodImpl is the particular implementation of the division method.
	 * 
	 * It requires arguments:
	 * r - division factor, n - number of key to insert, w - integer number (required for random number generator)
	 * @param r
	 * @param n
	 * @param w
	 * @return number of collision for particular configuration {double}
	 */
	public double divisionMethodImpl (int r, int n, int w) {
		
		/*TO STUDENT: WRITE YOUR CODE HERE*/

		// r is the divison factor, 
		// n is the number of keys to insert
		// w is the integer number

	
		double randomNumbers [] = generateRandomNumbers(w,n);

		//create array to hold keys created
		int hashTable[] = new int[n];

		//generate D within the range allowed 2^r-1 <= D <= 2^r.
		int D = (int)generateRandomNumberiInRange(Math.pow(2,r-1),Math.pow(2,r));

		//counter for number of collisions, set at one, that will be changed
		int collisionCount = 0;

	

		for(int i = 0; i <hashTable.length; i++) {
			hashTable[i] = (int)(randomNumbers[i])% D;
		}

		

		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] == -1){
				continue;
			}
			else{
				for(int j = i+1; j<hashTable.length; j++){
			
					if(hashTable[i] == hashTable[j]) {
					
						hashTable[j] = -1;
					
						collisionCount++; 
					}
				}
		
				hashTable[i] = -1;
			}
		}

		return (double)collisionCount;
	}
	
	
	
	/**
	 * multiplicationMethod is the main method for multiplication method in hashing problem. It creates specific array ys, specifies A under with some validations, iterates over the set of n (defined by ns field) and for every n adds in ys array particular number of collisions
	 * 
	 * It requires arguments:
	 * m - number of slots, r and w - are such integers, that w > r
	 * 
	 * It returns an array of number of collisions (for all n in range from 10% to 200% of m) - later in plotting phase - it is Y values for multiplicationMethod
	 * @param r
	 * @param m
	 * @param w
	 * @return ys for multiplication method {double[]}
	 */
	public double[] multiplicationMethod (int r, int m, int w) {
		double[] ys = new double[ns.length];
		double y;
		double A = generateRandomNumberiInRange(Math.pow(2,w-1),Math.pow(2,w));
		for (int it = 0, len = ns.length; it < len; it++) {
			int n = (int)(ns[it]*m);
			y = multiplicationMethodImpl (r, n, w, A);
			if (y < 0) return null;
			ys[it] = y;
		}
		return ys;
	}
	
	
	/**
	 * multiplicationMethodImpl is the particular implementation of the multiplication method.
	 * 
	 * It requires arguments:
	 * n - number of key to insert, r and w - are such integers, that w > r, A is a factor
	 * @param r
	 * @param n
	 * @param w
	 * @param A
	 * @return number of collisions for particular configuration {double}
	 */
	public double multiplicationMethodImpl (int r, int n, int w, double A) {
		
		
		/*TO STUDENT: WRITE YOUR CODE HERE*/

	
		double randomNumbers [] = generateRandomNumbers(w,n);

		//create array to hold keys created
		int hashTable[] = new int[n];

		//counter for number of collisions. 
		int collisionCount = 0;

		//initialize hashTable array with hashed values

		for(int i = 0; i <hashTable.length; i++) {
			hashTable[i] = (int)(A*(randomNumbers[i])) % (int)(Math.pow(2,w));
		}

		//

		for(int i = 0; i < hashTable.length; i++){
			if(hashTable[i] == -1){
				continue;
			}
			else{
				for(int j = i+1; j<hashTable.length; j++){
					//check if values at i and j are equal
					if(hashTable[i] == hashTable[j]) {
						//mark as checked
						hashTable[j] = -1;
						//increment collision counter
						collisionCount++; 
					}
				}
				//mark value at i as checked as well once out of inner loop
				hashTable[i] = -1;
			}
		}

		return (double)collisionCount;
	}
	
	/**
	 * TO STUDENT: MAIN method - WRITE/CHANGE code here (it should be compiled anyway!)
	 * TO STUDENT: NUMBERS ARE RANDOM! 
	 * @param args
	 */
	public static void main(String[] args) {
		int w = 0, r = 0, m = 0;
		if (args!= null && args.length>2) {
			w = Integer.parseInt(args[0]);
			r = Integer.parseInt(args[1]);
			m = Integer.parseInt(args[2]);
		} else {
			System.err.println("Input should be w r m (integers). Exit(-1).");
			System.exit(-1);
		}
		
		if (w<=r) {
			System.err.println("Input should contain w r (integers) such that w>r. Exit(-1).");
			System.exit(-1);
		}
		
		COMP251HW1 hf = new COMP251HW1();
		double[] yTemp;
		
		HashMap<String, double[]> ys = new HashMap<String, double[]>();
		System.out.println("===Division=Method==========");
		yTemp = hf.divisionMethod(r, m, w);
		if (yTemp == null) {
			System.out.println("Something wrong with division method. Check your implementation, formula and all its parameters.");
			System.exit(-1);
		}
		ys.put("divisionMethod", yTemp);
		for (double y: ys.get("divisionMethod")) {
			System.out.println(y);
		}
		
		System.out.println("============================");
		System.out.println("===Multiplication=Method====");
		yTemp = hf.multiplicationMethod(r, m, w);
		if (yTemp == null) {
			System.out.println("Something wrong with division method. Check your implementation, formula and all its parameters.");
			System.exit(-1);
		}
		ys.put("multiplicationMethod", yTemp);
		
		for (double y: ys.get("multiplicationMethod")) {
			System.out.println(y);
		}
		
		double[] x = new double[hf.ns.length];
		for (int it = 0, len = hf.ns.length; it < len; it++) {
			x[it] = (int)(hf.ns[it]*m);
		}
		
		hf.generateCSVOutputFile("hashFunctionProblem.csv", x, ys);
	}
}
