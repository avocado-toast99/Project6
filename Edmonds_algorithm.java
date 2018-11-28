/*

Group Members:

Hana'a Al-Lohibi            
Bashair Atif Abdulrazak       
Maha Osama          

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

        int[][] SlidesGraph = {
            //  1  2  3  4  5  6  
            {0, 2, 0, 3, 0, 0},
            {0, 0, 5, 0, 3, 0},
            {0, 0, 0, 0, 0, 2},
            {0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 0}

        };

        int[][] GraphEX2 = {
            {0, 3, 0, 3, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 0},
            {3, 0, 0, 1, 2, 0, 0},
            {0, 0, 0, 0, 2, 6, 0},
            {0, 1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 9},
            {0, 0, 0, 0, 0, 0, 0}

        };
        System.out.println("~~~Edmonds-Karp Algorithm~~~" + "\n-----------------------------");
        System.out.println("Now running on the sample graph in the book... ");
        System.out.println("\n\n");
        int[][] PathsEX1 = EdmondsKarp(SlidesGraph);

      // int[][] PathsEX2 = EdmondsKarp(GraphEX2);
    } // main 

    public static int[][] EdmondsKarp(int[][] Graph) {
        /*
        
        Description:
        this method
        
        Parameters:
        
        
        Output:
        
        
         */

        int src = 0;
        int sink = Graph.length - 1;

        ArrayList AugPaths = new ArrayList();

        int[][] result = MaxFlow(Graph, src, sink, AugPaths);

        int max_flow = result[0][0];
        int number_of_paths = result[0][1];

        int[] nodes = new int[Graph.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = i + 1;

        }

        System.out.println("Number of Paths: " + number_of_paths);
        System.out.println("Paths: ");

        System.out.println("From " + nodes[src] + " To " + nodes[sink] + "\n---------------");

        AugPaths.add(src, src);
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
        int[][] Paths = new int[Graph.length][Graph[0].length];
        for (int i = 0; i < Paths.length; i++) {
            for (int j = 0; j < Paths[i].length; j++) {
                Paths[i][j] = 100;

            }

        }

        int stoppedAt = 0;

        for (int i = Paths.length - 1; i >= 0; i--) {
            for (int j = 0; j < Paths[i].length; j++) {
                for (int l = stoppedAt; l < AugPaths.size(); l++) {
                    int indx = (int) AugPaths.get(l);
                    Paths[i][j] = nodes[indx];

                    if (indx == sink) {
                        Paths[i][j] = nodes[indx];
                        stoppedAt = l + 1;
                        break;
                    } else {
                        stoppedAt = l + 1;

                        break;
                    }
                }

            }

        }
        for (int i = 0; i < Paths.length; i++) {
            for (int j = 0; j < Paths[i].length; j++) {

                if (Paths[i][j] != 100) {

                    System.out.print(Paths[i][j]);
                    if (j + 1 == Paths[i].length) {
                        System.out.println();

                        break;

                    } else {
                        System.out.print(" -> ");

                    }

                }
            }

            System.out.println("");

        }

        System.out.println("The Final Maximum Flow: " + max_flow);

        System.out.println("");
        return Paths;
    }

    public static boolean isThereAugPath(int capacity[][], int src, int sink, int parent_map[]) {

        int[] nodes = new int[6];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = i + 1;

        }

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

        // we reached the sink so there is augmented path 
        return (label[sink] == true);
    }

    // Returns tne maximum flow from s to t in the given graph 
    public static int[][] MaxFlow(int capacity[][], int src, int sink, ArrayList<Integer> AugPaths) {

        int[] nodes = new int[6];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = i + 1;

        }

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
