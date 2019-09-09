package progproject5;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	
	//variables
	static HashMap<String, String> focusMap = new HashMap<String, String>();
	static ArrayList<String> books = new ArrayList<String>();
	static ArrayList<String> focusArray = new ArrayList<String>();
	static ArrayList<String> comparisonArray = new ArrayList<String>();
	static ArrayList<String> wordsArray = new ArrayList<String>();
	static String focusBook;
	static String focusBookName;
	static String comparisonBook;
	static String comparisonBookName;

	public static void main(String[] args) throws IOException{
		
		//Read first and second file paths
		String FIRSTFILE = args[0]; //list of books
		String SECONDFILE = args[1]; //list of vocabulary words
		
		//String FIRSTFILE = "/Users/natashasharma/Desktop/data/BooksExample.txt";
		//String SECONDFILE = "/Users/natashasharma/Desktop/data/words.txt";

		
		File firstFILE = new File(FIRSTFILE);
		Scanner sc = new Scanner(firstFILE);
		ArrayList<String> books = new ArrayList<String>();
		while (sc.hasNext()){
			String file = sc.next();
			books.add(file);
		}		

		System.out.println("");
		
		//Initialize the focus book array and Hash Map
		focusBook = books.get(0);
		focusBookName = books.get(0).replaceAll(".txt", "");
		System.out.println("");
		String filename = focusBook;
		createFocusArray(filename);
		createFocusHashMap(focusArray);
		
		//Initialize comparison books and compare with focus book
		for (int i=1; i<books.size(); i++){
			comparisonBook = books.get(i);
			comparisonBookName = books.get(i).replaceAll(".txt", "");
			String fileName = books.get(i);
			System.out.println(">> COMPARING FOCUS BOOK \"" + focusBookName + "\" WITH \"" + comparisonBookName + "\" << \n");
			createComparisonArray(fileName);
			compareTwoBooks(focusMap, comparisonArray);
			comparisonArray.clear();
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
		}

		//Read in second file path and execute method
		System.out.println(">> COMPARING FOCUS BOOK \"" + focusBookName + "\" WITH VOCAB LIST << \n");
		compareVocabulary(focusMap, SECONDFILE);
		System.out.println("\n");
		
	}
	
	/*
	 * method to compare the focus book with the following comparison books
	 * outputs the assignment requirements
	 * uses a Merge Sort algorithm
	 */
	public static void compareTwoBooks(HashMap<String,String> focusBookMap, ArrayList<String> array){
		
		ArrayList<String> wordListFocus = new ArrayList<String>();
		ArrayList<String> wordListComparison = new ArrayList<String>();
		
		//create comparison hash map
		HashMap<String,String> comparisonMap = new HashMap<String,String>();
		for (String word: array){
			if (!comparisonMap.containsKey(word)){
				if (Character.isUpperCase(word.charAt(0))){
					comparisonMap.put(word, "(P)");
				}else{
					comparisonMap.put(word, "(R)");
				}
			}
		}
		
		int wordsInBoth = 0;
		for (String item : focusBookMap.keySet()){
			if (comparisonMap.containsKey(item)){
				wordsInBoth++;
			}
		}
		int wordsInFocusOnly=0;;
		for (String word : focusBookMap.keySet()){
			if (!comparisonMap.containsKey(word)){
				wordsInFocusOnly++;
				wordListFocus.add(word);
			}
		}
		int wordsInComparisonOnly=0;
		for (String word : comparisonMap.keySet()){
			if (!focusBookMap.containsKey(word)){
				wordsInComparisonOnly++;
				wordListComparison.add(word);
			}
		}

		//Sort both arrays
		mergeSort(wordListFocus);
		mergeSort(wordListComparison);
		
		System.out.println("Focus Book: " + focusBookName + "\t Total (Unique) Words: " + focusBookMap.size() + "\t Proper Nouns: " + countProperNouns(focusMap) +"\n");
		System.out.println("Comparison Book: " + comparisonBookName + "\t Total (Unique) Words: " + comparisonMap.size() + "\t Proper Nouns: " + countProperNouns(comparisonMap) +"\n");
		System.out.println("Words in both: " + wordsInBoth + "\t Words in " + focusBookName + " only: " + wordsInFocusOnly + "\t Words in " + comparisonBookName + " only: " + wordsInComparisonOnly +"\n");
		
		while(true){
			System.out.print("Word List " + focusBookName + " only: ");
			for (String word : wordListFocus){System.out.print(word +", ");}
			break;
		}
		System.out.println("\n");
		while(true){
			System.out.print("Word List " + comparisonBookName + " only: ");
			for (String word : wordListComparison){System.out.print(word +", ");}
			break;
		}
		System.out.println("\n");
	}
	
	/*
	 * method to compare the focus book with the list of vocabulary words
	 */
	public static void compareVocabulary(HashMap<String,String> focusBookMap, String fileName) throws FileNotFoundException{
		
 		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		while(sc.hasNext()){
			String word = sc.next();
			String newWord = word.replaceAll("^[^A-Za-z]*|[^A-Za-z]*$", "");
			if (newWord != ""){
				wordsArray.add(newWord);
			} 
		}
		wordsArray.removeAll(Arrays.asList("", null));
		
		HashMap<String, String> vocabMap = new HashMap<String, String>();
		for (String word : wordsArray){
			if (!vocabMap.containsKey(word)){
				if (Character.isUpperCase(word.charAt(0))){
					vocabMap.put(word, "(P)");
				}else{
					vocabMap.put(word, "(R)");
				}
			}
		}
		
		ArrayList<String> wordsIn = new ArrayList<String>();
		ArrayList<String> wordsNotIn = new ArrayList<String>();
		
		for(String word : vocabMap.keySet()){
			if (focusBookMap.containsKey(word)){
				wordsIn.add(word);
			} else if (!focusBookMap.containsKey(word)){
				wordsNotIn.add(word);
			}
		}
		mergeSort(wordsIn);
		mergeSort(wordsNotIn);
		
		while (true){
			System.out.print("Words in " + focusBookName + " also in Vocabulary List: ");
			for (String word : wordsIn){System.out.print(word + ", ");}
			break;
		}
		System.out.println("\n");
		while (true){
			System.out.print("Words in Vocabulary List not in " + focusBookName + ": ");
			for (String word : wordsNotIn){System.out.print(word + ", ");}
			break;
		}
	}
	
	/*
	 * method to create hash map of unique words in focus book
	 * Value indicates whether it is regular or proper noun
	 */
	public static void createFocusHashMap(ArrayList<String> array){
		for (String word: array){
			if (!focusMap.containsKey(word)){
				if (Character.isUpperCase(word.charAt(0))){
					focusMap.put(word, "(P)");
				}else{
					focusMap.put(word, "(R)");
				}
			}
		}
	}
	
	/*
	 * method to create an array of words in the focus book
	 * uses regular expressions to eliminate punctuation at beginning/end of word, and removes "-ed" or "-ing" suffix
	 */
	public static void createFocusArray(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		while(sc.hasNext()){
			String word = sc.next();
			//String newWord = word.replaceAll("^[^A-Za-z]*|[^A-Za-z]*$|ed$|ing$", "");
			String newWord = word.replaceAll("^[^A-Za-z]*|[^A-Za-z]*$", "");
			focusArray.add(newWord);
		}
		focusArray.removeAll(Arrays.asList("", null));
	}
	
	/*
	 * method to create an array of words in the comparison books
	 * uses regular expressions to eliminate punctuation at beginning/end of word, and removes "-ed" or "-ing" suffix
	 */
	public static void createComparisonArray(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		while(sc.hasNext()){
			String word = sc.next();
			//String newWord = word.replaceAll("^[^A-Za-z]*|[^A-Za-z]*$|ed$|ing$", "");
			String newWord = word.replaceAll("^[^A-Za-z]*|[^A-Za-z]*$", "");
			comparisonArray.add(newWord);	 
		}
		comparisonArray.removeAll(Arrays.asList("", null));
	}
	
	/*
	 * method to count the proper nouns in a book using the key-value labels
	 */
	public static int countProperNouns(HashMap<String,String> map){
		int properNouns = Collections.frequency(map.values(), "(P)");
		return properNouns;
	}
	
	/*
	 * method to print key-value pair
	 */
	public static void printHashMap(HashMap<String,String> map){
		for (String key : map.keySet()) {
		    System.out.println(key + ", " + map.get(key));
		}
		System.out.println("Print of Hash Map Key-Value Pairings");	
	}
	
	/*
	 * method to print the array
	 */
	public static void printArray(ArrayList<String> array){
		for (String word : array){
			System.out.println(word + "");
		}
	}
	
	/*
	 * merge sort method to sort the word lists
	 * recursive methods
	 */
	public static ArrayList<String> mergeSort(ArrayList<String> array) {
        ArrayList<String> l = new ArrayList<String>();
        ArrayList<String> r = new ArrayList<String>();
        int middle;
        if (array.size() == 1) {    
            return array;
        } else {
            middle = array.size()/2;
            for (int i=0; i<middle; i++) {
                    l.add(array.get(i));
            }
            for (int i=middle; i<array.size(); i++) {
                    r.add(array.get(i));
            }
            l  = mergeSort(l);
            r = mergeSort(r);
            merge(array, l, r);
        }
        return array;
    }
 
    private static void merge(ArrayList<String> array, ArrayList<String> l, ArrayList<String> r) {
        int lIndex = 0;
        int rIndex = 0;
        int arrayIndex = 0;
        while (lIndex < l.size() && rIndex < r.size()) {
            if ( (l.get(lIndex).compareTo(r.get(rIndex))) < 0) {
                array.set(arrayIndex, l.get(lIndex));
                lIndex++;
            } else {
                array.set(arrayIndex, r.get(rIndex));
                rIndex++;
            }
            arrayIndex++;
        }
        ArrayList<String> temp;
        int tempIndex;
        if (lIndex >= l.size()) {
            temp = r;
            tempIndex = rIndex;
        } else {
            temp = l;
            tempIndex = lIndex;
        }
        for (int i=tempIndex; i<temp.size(); i++) {
            array.set(arrayIndex, temp.get(i));
            arrayIndex++;
        }
    }

}
