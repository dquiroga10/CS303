import java.util.*;
import java.text.*;
import java.lang.*;

// used JAVA SE 11

public class PA2 {
	public static void main(String[] args){
		Scanner array = new Scanner(System.in);
		System.out.println("Input an array of at least 2 elements: ");
		String str = array.nextLine();
		// System.out.println(str);

		str = str.replace("[", "");
		str = str.replace("]", "");
		str = str.replace(" ", "");
		// System.out.println(str);

		String[] arr = str.split(","); //where i created a list of the numbers in the array

		// System.out.println(arr.length);
//making sure that the list has more than one element
		while (arr.length <= 1){
			System.out.println("Input an array of at least 2 elements: ");
			str = array.nextLine();
			// System.out.println(str);

			str = str.replace("[", "");
			str = str.replace("]", "");
			str = str.replace(" ", "");
			// System.out.println(str);

			arr = str.split(",");

			// System.out.println(arr.length);
		}

		// for (String s : arr){
		// 	System.out.println(s);
		// }
		List a = new ArrayList();
		for (String i : arr){
			//makes sure that the things inside the list are integers
			a.add(Integer.parseInt(i));
		}
		// System.out.println(a);

		Scanner num = new Scanner(System.in);
		System.out.println("What is your k?");
		int k = (int) num.nextDouble();
		//making sure that k is a valid index
		while (k < 1 || k > arr.length) {
			System.out.println("What is your k?");
			k = (int) num.nextDouble();
		}

		//create an object of sort and then call its functions
		Sort s = new Sort();
		System.out.println("Select: " + s.select((ArrayList) a, k));
		System.out.println("Det_select: " + s.det_select((ArrayList) a, k));
		System.out.println("Quicksort: " + s.quicksort((ArrayList) a, k));
	}
}

class Sort {
	Sort(){
		int i = 0;
		List A = new ArrayList();
		Random random = new Random();

		while (i != 10000000){
			A.add(random.nextInt(100000));
			i++; 
		}

		System.out.println("Testing my sorting alogirthms with an enormous array with random numbers inputted");

		long startSelect = System.nanoTime();
		int select = select((ArrayList) A, (int) Math.ceil(((double) i) / 2));
		long endSelect = System.nanoTime();
		System.out.println("Select: " + select + " in " + ((endSelect - startSelect)/1000000000) + " seconds.");
		
		long startDet = System.nanoTime();
		int det = det_select((ArrayList) A, (int) Math.ceil(((double) i) / 2));
		long endDet = System.nanoTime();
		System.out.println("Det_select: " + det + " in " + ((endDet - startDet)/1000000000) + " seconds. ");
		
		long startQuick = System.nanoTime();
		int quick = quicksort((ArrayList) A, (int) Math.ceil(((double) i) / 2));
		long endQuick = System.nanoTime();
		System.out.println("Quicksort: " + quick + " in " + ((endQuick - startQuick)/1000000000) + " seconds. " );
	}


	// Algorithm 1
	public int select(ArrayList S, int k){
		Random rand = new Random();
		int split = rand.nextInt(S.size());
		int splitter = (int) S.get(split);
		
		List lower = new ArrayList();
		List upper = new ArrayList();
		List equal = new ArrayList();

		for (int count = 0 ; count < S.size(); count++){
			if ((int) S.get(count) < splitter){
				lower.add((int) S.get(count));
			}
			else if ((int) S.get(count) > splitter){
				upper.add((int) S.get(count));
			}
			else if ((int) S.get(count) == splitter){
				equal.add((int) S.get(count));
			}
			
		}

		if (lower.size() == k - 1){
			return splitter;
		}

		else if (lower.size() >= k){
			return select((ArrayList) lower, k); 
		}

		// else if (lower.size() < k - 1){
		else{
			if (lower.size() + equal.size() >= k){
				return splitter;
			}
			return select((ArrayList) upper, k - lower.size() - equal.size());
		}

	}



	// Algorithm 2
	public int det_select(ArrayList S, int k){
		if (S.size() <= 10) {
			return quicksort(S, k);
		}
		List x = new ArrayList();
		List array = new ArrayList();

		for (int i = 0; i < S.size(); i++){
			if(array.size() < 5 && i != S.size() - 1){
				array.add(S.get(i));
			}

			if (array.size() == 5){
				x.add(array);
				array = new ArrayList();
			}
			else if (i == S.size() - 1){
				array.add(S.get(i));
				if (S.size() != 0){
					x.add(array);
				}
			}
		}

		int count;
		List X = new ArrayList();
		for(count = 0; count < x.size(); count++){
			if (((ArrayList) x.get(count)).size() == 5){
				X.add(det_select((ArrayList) x.get(count), 3));
			}
			else{
				// System.out.println(Math.ceil((double)((ArrayList)x.get(count)).size() / 2));
				// System.out.println(((ArrayList)x.get(count)).size() / 2);
				X.add(det_select((ArrayList) x.get(count), (int) Math.ceil((double)((ArrayList)x.get(count)).size() / 2)));
			}
		}

		// System.out.println(S + " "+  k);

		// System.out.println("x " + X);

		int m = det_select((ArrayList) X, (int) Math.ceil(S.size()/10));

		// System.out.println("M " + m);

		List lower = new ArrayList();
		List upper = new ArrayList();
		List equal = new ArrayList();


		for (count = 0 ; count < S.size(); count++){
			if ((int) S.get(count) < m){
				lower.add((int) S.get(count));
			}
			else if ((int) S.get(count) > m){
				upper.add((int) S.get(count));
			}
			else if ((int) S.get(count) == m){
				equal.add((int) S.get(count));
			}
		
		}

		// System.out.println("lower: " + lower );
		// System.out.println("upper: " + upper );
		// System.out.println("equal: " + equal );

		if (k <= lower.size()){
			return det_select((ArrayList) lower, k);
		}

		else if (k > lower.size() + equal.size()){
			// System.out.println((lower.size() + equal.size()));
			return det_select((ArrayList) upper, k - lower.size() - equal.size());
		}
		else{
			return m;
		}

	}



	// Algorithm 3
	public int quicksort(ArrayList S, int k){
		List q = quicksortAlgo(S);
		return (int) q.get(k-1);
	}

	private ArrayList quicksortAlgo(ArrayList S){
		List lower = new ArrayList();
		List upper = new ArrayList();
		List equal = new ArrayList();

		if (S.size() <= 3){
			Collections.sort(S);
			return S;
		}
		else{
			Random rand = new Random();
			int split = rand.nextInt(S.size());
			int splitter = (int) S.get(split);
			for (int count = 0 ; count < S.size(); count++){
				if ((int) S.get(count) < splitter){
					lower.add((int) S.get(count));
				}
				else if ((int) S.get(count) > splitter){
					upper.add((int) S.get(count));
				}
				else if ((int) S.get(count) == splitter){
					equal.add((int) S.get(count));
				}
			
			}
			List combine = new ArrayList();
			combine.addAll(quicksortAlgo((ArrayList)lower));
			combine.addAll(equal);
			combine.addAll(quicksortAlgo((ArrayList)upper));
			// System.out.println(combine);
			return (ArrayList)combine;
			// return quicksortAlgo((ArrayList)lower), equal, quicksortAlgo((ArrayList)upper);
		}
	}



}



