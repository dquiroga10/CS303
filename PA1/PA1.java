import java.util.*;
import java.text.*;

// used JAVA SE 11

public class PA1 {
	public static void main(String[] args){
		Scanner n = new Scanner(System.in);
		System.out.println("What is your n? ");
		int nodes = (int) n.nextDouble();
		while (nodes < 2) {
			System.out.println("Sorry choose a n > 1 and a whole number. What is your n? ");
			nodes = (int) n.nextDouble();
		}

		Scanner p = new Scanner(System.in);
		System.out.println("Chose a p between [0,1]. What is your p? ");
		Double prob = p.nextDouble();
		while ((prob < 0) || (prob > 1)) {
			System.out.println(" Sorry choose a p greater inside [0,1]. What is your p? ");
			prob = p.nextDouble();
		}

		Scanner t = new Scanner(System.in);
		System.out.println("Pick a whole number t greater than 1. What is your t? ");
		int t_val = (int) t.nextDouble();
		while(t_val < 1){
			System.out.println("Sorry pick a whole number t greater than 1. What is your t? ");
			t_val = (int) t.nextDouble();
		}

		System.out.println(main(nodes, prob, t_val));
		mainTest();
	}

	public static int main(int nodes, Double p, int t_val){
		List visited = new ArrayList();
		List vis_search;
		int n = nodes;
		int[][] adj_list = generate_graph(n, p);
		for(int k = 0; k < n; k++){
			if(visited.contains(k) == false){
				vis_search = BFS(k, adj_list);
				for(int m = 0; m < vis_search.size(); m++){
					// add the error message
					visited.add(vis_search.get(m));
				}
				if(vis_search.size() >= t_val){
					return 1;
				}
			}
		}
		return 0;
	}

	public static void mainTest(){
		System.out.println("Testing.. for c [.2,3] increment by .2. 500 random graphs for each c");
		Double c = .2;
		DecimalFormat df = new DecimalFormat("#.#");
		while(c <= 3.199999){
			Double percentage = testing(c);
			System.out.println("c-value: "+ df.format(c) + "  percentage: " + percentage + " of conncections out of 500 graphs");
			c += .2;
		}
		System.out.println("Ran 7500 randomly generated graphs. 500 gaphs for each c");
	}

	public static Double testing(Double c){
		Double n = 40.0000;
		Double p = c/n;
		int i = 1;
		int count = 0;
		while(i <= 500){
			int ret_value = main(40, p, 30);
			if(ret_value == 1){
				count += 1;
			}
			i++;
		}
		return count/500.00;
	}

	public static List BFS(int s, int[][] adj){
		HashMap level = new HashMap();
		HashMap parent = new HashMap();
		List vis = new ArrayList();
		List frontier = new ArrayList();
		level.put(s, 0);
		parent.put(s, -1); // did this because there is no null and there will never be a node that is less than 0
		vis.add(s);
		frontier.add(s);
		int i = 1;
		while (frontier.size() != 0) {
			List next_up = new ArrayList();
			for (Object ob :  frontier){
				int u = (int) ob;
				for (int v : adj[u]){
					if (level.containsKey(v) == false){
						level.put(v, i);
						parent.put(v, u);
						next_up.add(v);
						vis.add(v);
					}
				}
			}
			frontier = next_up;
			i++;
		}
		return vis;
	}

	public static int[][] generate_graph(int n, Double p) {
		Random rand = new Random();
		Double num;
		ArrayList<Integer>[] connect = new ArrayList[n];
		for(int index = 0; index < n; index++){
			connect[index] = new ArrayList<Integer>();
		}
		int[][] graph = new int[n][];
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){
				num = rand.nextDouble();
				if((num < p) && (x != y) && (connect[x].contains(y) == false)){
					connect[x].add(y);
					connect[y].add(x);
				}
			}
		}
		for(int index = 0; index < n; index++){
			int size = connect[index].size();
			graph[index] = new int[size];
			for(int i = 0; i < size; i++){
				graph[index][i] = connect[index].get(i);
			}
		}

	return graph;
	}
}