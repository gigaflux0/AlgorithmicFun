import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TestGround {

//	Find the most frequent integer in an array
	private static class FreqIntArr {			
		private int[][] freq;
		private int[] arrCop;
		
		FreqIntArr() {
			freq = new int[2][2];
		}
		
		public int mostFreq(int[] arr){
			//if (arr.length == 0) return -1;
			arrCop = new int[arr.length];
			System.arraycopy(arr, 0, arrCop, 0, arr.length);
			Arrays.sort(arrCop);
			
			int i = 0;
			freq[0][0] = arrCop[0];
			boolean secNum = false;
			for(; i < arrCop.length; i++) {
				if(arrCop[i] == freq[0][0]) {
					freq[0][1]++;
				}
				else if(!secNum) {
					freq[1][0] = arrCop[i];
					freq[1][1]++;
					secNum = !secNum;
				}
				else if(arrCop[i] == freq[1][0]) {
					freq[1][1]++;
				}
				else {
					break;
				}
			}
			if(freq[1][1] > freq[0][1]) System.arraycopy(freq[1], 0, freq[0], 0, freq[0].length);
			for(; i < arrCop.length; i++) {
				if(arrCop[i] == freq[1][0]) {
					freq[1][1]++;
				}
				else {
					if(freq[1][1] > freq[0][1]) System.arraycopy(freq[1], 0, freq[0], 0, freq[0].length);
					freq[1][0] = arrCop[i];
					freq[1][1] = 1;
				}
			}
			if(freq[1][1] > freq[0][1]) System.arraycopy(freq[1], 0, freq[0], 0, freq[0].length);
			return freq[0][0];
		}			
	}
	
//	Find pairs in an integer array whose sum is equal to 10 (bonus: do it in linear time)
	private static class PairsTen {
		private Map<Integer, List<Integer>> map;
		private List<Integer> ans;
		
		PairsTen() {
			map = new HashMap<>();
			ans = new LinkedList<Integer>();
		}
		
		private void setAns (int[] arr) {
			for(int i = 0; i < arr.length; i++) {
				if(map.get(arr[i]) != null) {
					List<Integer> temp = map.get(arr[i]);
					temp.add(i);
					map.put(arr[i], temp);
				}
				else {
					List<Integer> temp = new LinkedList<Integer>();
					temp.add(i);
					map.put(arr[i], temp);
				}
				if(arr[i] == 5 && map.get(5).size() == 1) {
					continue;
				}
				if(map.get(10 - arr[i]) != null) {
					ans.add(arr[i]);
					ans.add(10 - arr[i]);
					List<Integer> temp = map.get(arr[i]);
					temp.remove(0);
					if(temp.isEmpty()) temp = null;
					map.put(arr[i], temp);
					temp = map.get(10 - arr[i]);
					temp.remove(0);
					if(temp.isEmpty()) temp = null;
					map.put(10 - arr[i], temp);
				}
			}
			return;
		}
		
		public void print(int[] arr) {
			setAns(arr);
			System.out.println(ans.toString());
			map.clear();
			ans.clear();
		}
	}
	
//	Given 2 integer arrays, determine if the 2nd array is a rotated version of the 1st array. Ex. Original Array A={1,2,3,5,6,7,8} Rotated Array B={5,6,7,8,1,2,3}
	private static class ArrRot {
		//
		
		public boolean isRot(int[] arrA, int[] arrB) {
			if(arrA.length != arrB.length) return false;
			for(int i = 0; i < arrB.length; i++) {
				boolean eq = true;
				for(int j = 0; j < arrB.length; j++) {
					int temp = j + i;
					if(j + i >= arrB.length) temp = j + i - arrB.length;
					if(arrA[j] != arrB[temp]) {
						eq = !eq;
						break;
					}
				}
				if(eq) return true;
			}
			return false;
		}
	}
	
//	Write fibbonaci iteratively and recursively (bonus: use dynamic programming)
	private static class Fib {
		//
		
		private void iter(int places) {
			if (places < 1) System.out.println("Value must be greater than 0");
			int currVal = 2;
			int lastVal = 1;
			for(int i = 0; i < places; i++) {
				System.out.println(currVal);
				int temp = currVal;
				currVal += lastVal;
				lastVal = temp;				
			}
		}
		
		private void recur(int places) {
			if (places < 1) System.out.println("Value must be greater than 0");
			recur(places, 2, 1);
		}
		private void recur(int places, int currVal, int lastVal) {
			if(places > 0) {
				System.out.println(currVal);
				recur(--places, currVal+lastVal, currVal);
			}
		}
	}
	
//	Find the only element in an array that only occurs once.
	private static class OnlyOnce {
		//
		
		private void print(Object[] arr) {
			Map<Object, List<Integer>> map = new HashMap<>();
			for(int i = 0; i < arr.length; i++) {
				List<Integer> list;
				if(map.get(arr[i]) == null) list = new LinkedList<>();
				else list = map.get(arr[i]);
				list.add(i);				
				map.put(arr[i], list);
			}
			Iterator it = map.entrySet().iterator();
			Object ans = null;
			while(it.hasNext()) {
				Map.Entry<Integer, List<Integer>> pair = (Map.Entry)it.next();
				if(pair.getValue().size() == 1) ans = pair.getKey();
				it.remove();
			}
			System.out.println(ans.toString());
		}
	}
	
//  Playing with threads for fun
	private static class ThreadFun extends Thread{
		//
		
		public void run() {
			System.out.println("Thread running " + Math.random() * 10);
		}
	}
	
//  Card game
	private static class CardGame {
		List<Card> deck;
		
		CardGame(int numDecks) {
			if(numDecks <= 0) { throw new IllegalArgumentException("Number of decks cant be 0 or less");}
			deck = new LinkedList<>();
			for(int i = 0; i < numDecks; i++) {
				for(int j = 0; j < 52; j++) {
					if(j < 13) { deck.add(new Card("Hearts", j+1)); }
					else if(j < 26) { deck.add(new Card("Diamonds", (j%13)+1)); }
					else if(j < 39) { deck.add(new Card("Clubs", (j%13)+1)); }
					else { deck.add(new Card("Spades", (j%13)+1)); }
				}
			}
		}
		
		public void draw() {
			if(!deck.isEmpty()) {
				System.out.println(deck.get(0));
				deck.remove(0);
			}
			else { System.out.println("Deck is empty"); }
		}
		
		public void shuffle() {
			Collections.shuffle(deck);
		}
		
		private class Card {
			private String face;
			private int value;
			
			Card(String face, int value) {
				this.face = face;
				this.value = value;
			}
			
			public String getFace() {
				return face;
			}
			
			public int getValue() {
				return value;
			}
			
			public String toString() {
				return(face+"   "+value);
			}
			
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		
//		Find the most frequent integer in an array
		//int[] tempArr = {2, 2, 9, 2, 3, 4, 5, 6, 4, 7, 8, 1, 4, 4};
		//FreqIntArr tempCla = new FreqIntArr();
		//System.out.println(tempCla.mostFreq(tempArr));
		
//		Find pairs in an integer array whose sum is equal to 10 (bonus: do it in linear time)
		//int[] tempArr = {5, 5, 5, 7, -12, 22, 0, 0, 10, 5, 6, 7, 4, 3};
		//PairsTen tempCla = new PairsTen();
		//tempCla.print(tempArr);
		
//		Given 2 integer arrays, determine if the 2nd array is a rotated version of the 1st array. Ex. Original Array A={1,2,3,5,6,7,8} Rotated Array B={5,6,7,8,1,2,3}
		//ArrRot tempCla = new ArrRot();
		//int[] tempArrA = {1, 2, 3, 4, 5};
		//int[] tempArrB = {2, 3, 4, 5, 1};
		//System.out.println(tempCla.isRot(tempArrA, tempArrB));
		
//		Write fibbonaci iteratively and recursively (bonus: use dynamic programming)
		//Fib tempCla = new Fib();
		//tempCla.iter(8);
		//tempCla.recur(8);
		
//		Find the only element in an array that only occurs once.
		//OnlyOnce tempCla = new OnlyOnce();
		//Object[] tempArr = {2, 2, 9, 5, 9, 3, 5, 2, 5};
		//tempCla.print(tempArr);
		
//		Thread Fun
		/*ThreadFun thread = new ThreadFun();
		ThreadFun thread2 = new ThreadFun();
		thread.start();
		thread2.start();
		while (true) {
			try {
				thread.join();
				thread2.join();
				break;
			} catch (InterruptedException e) {e.printStackTrace();}
		}*/
		
//		Card game		
		//CardGame game = new CardGame(1);
		//game.draw();
		//game.draw();
		//game.shuffle();
		//game.draw();
		
	}
	
}





//	Find the common elements of 2 int arrays
//	Implement binary search of a sorted array of integers
//	Implement binary search in a rotated array (ex. {5,6,7,8,1,2,3})
//	Use dynamic programming to find the first X prime numbers
//	Write a function that prints out the binary form of an int
//	Implement parseInt
//	Implement squareroot function
//	Implement an exponent function (bonus: now try in log(n) time)
//	Write a multiply function that multiples 2 integers without using *
//	HARD: Given a function rand5() that returns a random int between 0 and 5, implement rand7()
//	HARD: Given a 2D array of 1s and 0s, count the number of "islands of 1s" (e.g. groups of connecting 1s)