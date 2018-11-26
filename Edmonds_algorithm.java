/*

Group Members:

Hana'a Al-Lohibi            1675753
Bashair Atif Abdulrazak     1505459  
Maha Osama  Assagran        1605216

Algorithms and Data Structures
Section CH 
Project 6 - Implementing Edmonds Karp's Algorithm

 */
package edmonds_algorithm;

import java.util.*;

/**
 *
 * @author Lenovo
 */
public class Edmonds_algorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int[][] Graph = {
            {0, 3, 0, 3, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 0},
            {3, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 0, 2, 6, 0},
            {0, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 9},
            {0, 0, 0, 0, 0, 0, 0}

        };

        int src = 0;
        int sink = 6;
//        public static int num_paths;
        ArrayList AugPaths = new ArrayList();

//        AugPaths.add(src, src);
//        for (int i = 0; i < AugPaths.size(); i++) {
//            int sourceValue = Graph[src][src];
//            AugPaths.set(i, sourceValue);
//            
//        }
        int[][] result = MaxFlow(Graph, src, sink, AugPaths);
//
//        System.out.println("Paths : ");

        int max_flow = result[0][0];
        int number_of_paths = result[0][1];

        System.out.println("~~~Edmonds-Karp Algorithm~~~" + "\n-----------------------------");

        System.out.println("Number of Paths: " + number_of_paths);
        System.out.println("Paths: ");

        //    for (int i = 0; i < number_of_paths; i++) {
        System.out.println("From " + src + " To " + sink + "\n---------------");

//        for (int j = 0; j < AugPaths.size(); j++) {
//
//            System.out.println(AugPaths.get(j));
//            // AugPaths.add(j, src);
//        }
AugPaths.add(src,src);
        for (int i = 0; i < AugPaths.size(); i++) {
            
            int nextINDX = i + 1;
            if (nextINDX < AugPaths.size()) {
                int thisValue = (int) AugPaths.get(i);
                if (thisValue == sink) {
                    AugPaths.add(nextINDX, src);
                    continue;
                }

            }

        }

        for (int j = 0; j < AugPaths.size(); j++) {

            System.out.print(AugPaths.get(j));
            if ((int) AugPaths.get(j) == sink) {
                System.out.println("\n");
            } else {
                System.out.print(" -> ");
            }

        }

        System.out.println("The Final Maximum Flow: " + max_flow);
        // }
//        for (int i = 0; i < AugPaths.size(); i++) {
//            
//            System.out.print(AugPaths.get(i));
//         
//            if ((int) AugPaths.get(i) == sink) {
//                System.out.println("");
//            } else {
//                System.out.print("->");
//            }
//            
//        }

        System.out.println("");
        //   System.out.println("Max Flow " + max);

    } // main 

    public static boolean isThereAugPath(int capacity[][], int src, int sink, int parent_map[]) {

        boolean label[] = new boolean[capacity.length];

        int[][] flow = new int[capacity.length][capacity.length];

        // initials 
        for (int i = 0; i < label.length; ++i) {
            label[i] = false;
        }

        for (int i = 0; i < flow.length; i++) {

            for (int j = 0; j < flow[i].length; j++) {
                flow[i][j] = 0;
            }
        }

        int res_capacity = 0;
        // Create a queue, enqueue source vertex and mark 
        // source vertex as visited 
        QueueLL Queue = new QueueLL();

        // label src 
        label[src] = true;
        Queue.enqueue(src);

        // Standard BFS Loop 
        while (!Queue.isEmpty()) {
            // pick the front (i)
            int front = Queue.peek();

            for (int j = 0; j < capacity.length; j++) {
                // if j unlabled 
                if (label[j] != true) {
                    res_capacity = capacity[front][j] - flow[front][j];

                    if (res_capacity > 0) {

                        Queue.enqueue(j);
                        parent_map[j] = front;
                        label[j] = true;

                    }

                }
            }

            //////////////// decide if we want to keep it 
            for (int j = 0; j < flow.length; j++) {

                if (label[j] != true) {
                    if (flow[front][j] > 0) {
                        int min = Math.min(flow[front][j], capacity[front][j]);
                        Queue.enqueue(j);
                    }
                }
            }

            Queue.dequeue();
        }
        //  AugPaths.add(src);

        //Collections.reverse(AugPaths);
        // AugPaths.add(AugPaths);
//         for (int i = 0; i < AugPaths.size(); i++) {
//            //System.out.print("Path " + i + ": ");
//           
//            System.out.print(AugPaths.get(i)+ " ");
//           // System.out.println("");
//        }
//        if (visited[sink] !=src)
//        System.out.println("");
        // we reached the sink so there is augmented path 
        return (label[sink] == true);
    }

    // Returns tne maximum flow from s to t in the given graph 
    public static int[][] MaxFlow(int capacity[][], int src, int sink, ArrayList<Integer> AugPaths) {

        int u, v;
        int number_paths = 0;
        int max_flow = 0;  // There is no flow initially 
        int[][] result = new int[1][2];
        // Create a residual graph and fill the residual graph 
        // with given capacities in the original graph as 
        // residual capacities in residual graph 
        // Residual graph where rGraph[i][j] indicates 
        // residual capacity of edge from i to j (if there 
        // is an edge. If rGraph[i][j] is 0, then there is 
        // not) 
        int residualGraph[][] = new int[capacity.length][capacity.length];

        int parent_map[] = new int[capacity.length];
        // int n = sink;
        int INF = 9999;

        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity.length; j++) {
                // initialize it same as the capacity 
                residualGraph[i][j] = capacity[i][j];
            }
        }

        // This array is filled by BFS and to store path 
        while (isThereAugPath(residualGraph, src, sink, parent_map)) {

            // initially inifint from src 
            int path_flow = INF;

            for (v = sink; v != src; v = parent_map[v]) {

//                System.out.println(v); 
                AugPaths.add(v);
                u = parent_map[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);

            }

            // update residual capacities of the edges and 
            // reverse edges along the path 
            for (v = sink; v != src; v = parent_map[v]) {

                u = parent_map[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;

            }

            // Add path flow to overall flow 
            max_flow += path_flow;
            number_paths++;
        }

        result[0][0] = max_flow;
        result[0][1] = number_paths;

        Collections.reverse(AugPaths);

        // System.out.println(number_paths);
        return result;
    }

}// class
