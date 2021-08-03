
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*
 *	Brandon Aaron Bustamante 
 * 	Elementary Data Structures and Algorithms
 * 
 *  DESCRIPTION 
 *  
 *  In this lab, I will be creating a program to validate Latin Squares. A Latin square
 *  is an N-by-N array filled with N different Latin letters, each occurring exactly
 *  once in each row and once in each column. Before I began solving the main problem,
 *  I had to implement some helper methods for reading text files, populating arrays, 
 *  and displaying them. Next, to solve the main problem, I broke it down into smaller
 *  steps, four to be exact. I checked the rows first, then the columns, and finally
 *  performed two separate checks for the N unique characters. Lastly, I generated text
 *  files for my own testing and validation. I provided five of them needed for the
 *  program to run correctly. 
 *
 */

public class Main {
	static Scanner scnr = new Scanner(System.in);

	/*
	 * getSize() will prompt the user for the size of the N x N array, then return
	 * the value to whichever method called it. The program will crash if anything
	 * other than an integer is input since no input validation was required.
	 */
	public static int getSize() {
		System.out.println("Enter the desired size of the NxN array");
		int sizeOfArr = scnr.nextInt();
		return sizeOfArr;
	}

	/*
	 * pickPopulate(char[][] arr) will let the user select the method of populating
	 * the array. I made use of a switch statement to accomplish this. Once
	 * completed, the array is returned. The default case will handle invalid
	 * integer inputs and exit the program.
	 */
	public static char[][] pickPopulate(char[][] arr) {
		System.out.println("Select an option.\n" + "1. Manual Entry\n" + "2. File Entry\n");
		int userInput = scnr.nextInt();
		switch (userInput) {
		case 1:
			arr = manPopulateArr(arr);
			break;

		case 2:
			System.out.println("Enter the file path.");
			System.out.println();
			String fileName = scnr.next();
			arr = filePopulateArr(arr, fileName);
			break;

		default:
			System.out.println("Invalid input, Goodbye.");
			System.exit(0);
		}
		return arr;
	}

	/*
	 * manPopulateArr(char[][] arr) will allow the user the enter each character at
	 * each index in the array. I accomplished this with nested loops. If the user
	 * enters more than a single character, only the character stored at index 0 is
	 * stored. The populated array is then returned.
	 */
	public static char[][] manPopulateArr(char[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.println("Pleae enter the character at position at " + i + " and " + j);
				arr[i][j] = scnr.next().charAt(0);
			}
		}
		System.out.println("Array Populated.");
		return arr;
	}

	/*
	 * filePopulateArr(char[][] arr, String fileName) will automatically populate
	 * the array from the provided text file. The row and column size of the array
	 * and file must match. There is exception handling for the file not being
	 * found. The method for populating the array is similar to the one above,
	 * although an outer while loop is used.
	 */
	public static char[][] filePopulateArr(char[][] arr, String fileName) {
		try {
			File filePath = new File(fileName);
			Scanner readFile = new Scanner(filePath);
			// i is iterating through the rows, while j iterates through the columns of the
			// array.
			int i = 0;
			while (readFile.hasNextLine()) {
				String data = readFile.nextLine();
				// Parsing the line of text for individual characters
				for (int j = 0; j < data.length(); j++) {
					// Storing the character into the array
					arr[i][j] = data.charAt(j);
				}
				i++;
			}
			readFile.close();
			System.out.println("Array Populated.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found, Goodbye!");
			System.exit(0);
		}
		return arr;
	}

	/*
	 * isLatinSquare(char[][] arr) will validate the given array. There are four
	 * checks performed. If any of them fail, then the array is not a Latin Square,
	 * and a boolean value of false is returned, true if all pass. Each check is
	 * explained in the comments below.
	 */
	public static boolean isLatinSquare(char[][] arr) {
		printArr(arr);
		/*
		 * This is a sample run of the program checks that will be performed
		 * 
		 * Checking rows for the array | a b c d | | e f g h | | i j k l | | m n o p |
		 * 
		 * The checks that will be performed are
		 * 
		 * Row 0 a == b ? a == c ? a == d ? b == c ? b == d ? c == d ?
		 * 
		 * Row 1 e == f ? e == g ? e == h ? f == g ? f == h ? g == h ?
		 * 
		 * Row 2 i == j ? i == k ? i == l ? j == k ? j == l ? k == l ?
		 * 
		 * Row 3 m == n ? m == o ? m == p ? n == o ? n == p ? o == p ?
		 * 
		 */
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				for (int k = j + 1; k < arr.length; k++) {
					if (arr[i][j] == arr[i][k]) {
						System.out.println("Checking rows : Failed \n");
						return false;
					}
				}
			}
		}
		System.out.println("Checking rows : Passed \n");
		/*
		 * This is a sample run of the program checks that will be performed
		 * 
		 * Checking columns for the array | a b c d | | e f g h | | i j k l | | m n o p|
		 * 
		 * The checks that will be performed are
		 * 
		 * Column 0 a == e ? a == i ? a == m ?
		 * 
		 * Column 1 b == f ? b == j ? b == n ?
		 * 
		 * Column 2 c == g ? c == k ? c == o ?
		 * 
		 * Column 3 d == h ? d == l ? d == p ?
		 * 
		 * Repeat from the next letter in the column
		 * 
		 * Remainder of column 0 e == i ? e == m ?
		 * 
		 * Remainder of column 1 f == j ? f == n ?
		 * 
		 * Remainder of column 2 g == k ? g == o ?
		 * 
		 * Remainder of column 3 h == l ? h == p ?
		 * 
		 * The steps are repeated from above.
		 * 
		 * Remainder of column 0 i == m ?
		 * 
		 * Remainder of column 1 j == n ?
		 * 
		 * Remainder of column 2 k == o ?
		 * 
		 * Remainder of column 3 l == p ?
		 * 
		 */

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				for (int k = i + 1; k < arr.length; k++) {
					if (arr[i][j] == arr[k][j]) {
						System.out.println("Checking Columns : Failed \n");
						return false;
					}
				}
			}
		}
		System.out.println("Checking Columns : Passed \n");
		/*
		 * Now, I have confirmed none of the characters in the rows or columns are the
		 * same. Therefore row 0 will have the N unique characters I need to check for.
		 */
		Set<String> chars = new HashSet<String>();
		for (int i = 0; i < arr.length; i++) {
			chars.add(Character.toString(arr[0][i]));
		}
		/*
		 * I used a Set to store the N unique characters. A set does not allow for
		 * duplicate data. Therefore the length of row 0 and the set chars should be the
		 * same size.
		 */
		if (chars.size() != arr[0].length) {
			System.out.println("Checking N unique characters : Failed \n");
			return false;
		}

		/*
		 * Finally, if any characters in the array are not found in the set, there are
		 * not N unique characters.
		 */
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (!chars.contains(Character.toString(arr[i][j]))) {
					System.out.println("Checking N unique characters : Failed \n");
					return false;
				}
			}
		}
		System.out.println("Checking N unique characters : Passed \n");
		System.out.println("This above array is a Latin Square. \n");
		return true;
	}

	/*
	 * printArr(char[][] arr) prints the array. I added in some spacing and lines
	 * for easier reading.
	 */
	public static void printArr(char[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.print("| " + arr[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.println();
	}

	/*
	 * The program runs five test cases before giving the user control. The user can
	 * then test the program on their own.
	 */
	public static void main(String[] args) {
		// Uncomment below to see some of my test cases with the files I provided
//		// Testing a valid Latin Square of length three.
		char[][] arr = new char[3][3];
//		arr = filePopulateArr(arr, "txtFiles/valid3.txt");
//		isLatinSquare(arr);
//		// Testing a valid Latin Square of length four.
//		arr = new char[4][4];
//		arr = filePopulateArr(arr, "txtFiles/valid4.txt");
//		isLatinSquare(arr);
//		// Testing an invalid Latin Square of length three.
//		arr = new char[3][3];
//		arr = filePopulateArr(arr, "txtFiles/invalid3.txt");
//		isLatinSquare(arr);
//		// Testing an invalid Latin Square of length four.
//		arr = new char[4][4];
//		arr = filePopulateArr(arr, "txtFiles/invalid4.txt");
//		isLatinSquare(arr);
//		// Testing an invalid Latin Square without N unique characters.
//		arr = new char[4][4];
//		arr = filePopulateArr(arr, "txtFiles/invalid4chars.txt");
//		isLatinSquare(arr);
		// User input
		int size = getSize();
		arr = new char[size][size];
		arr = pickPopulate(arr);
		isLatinSquare(arr);
		scnr.close();
	}

}
