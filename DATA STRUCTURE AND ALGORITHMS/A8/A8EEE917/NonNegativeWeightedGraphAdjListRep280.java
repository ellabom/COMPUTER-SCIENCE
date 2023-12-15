//NAME: EMMANUELLA EYO
//STUDENT ID: 11291003
//NSID: EEE917

package lib280.graph;

//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;

 import lib280.base.Pair280;
 import lib280.exception.InvalidArgument280Exception;
 import lib280.list.LinkedList280;

 import java.util.Arrays;
 import java.util.InputMismatchException;
 import java.util.Scanner;


 public class NonNegativeWeightedGraphAdjListRep280<V extends Vertex280> extends
         WeightedGraphAdjListRep280<V> {

     public NonNegativeWeightedGraphAdjListRep280(int cap, boolean d,
                                                  String vertexTypeName) {
         super(cap, d, vertexTypeName);
     }

     public NonNegativeWeightedGraphAdjListRep280(int cap, boolean d) {
         super(cap, d);
     }


     @Override
     public void setEdgeWeight(V v1, V v2, double weight) {
         // Overriding this method to throw an exception if a weight is negative will cause
         // super.initGraphFromFile to throw an exception when it tries to set a weight to
         // something negative.

         // Verify that the weight is non-negative
         if(weight < 0) throw new InvalidArgument280Exception("Specified weight is negative.");

         // If it is, then just set the edge weight using the superclass method.
         super.setEdgeWeight(v1, v2, weight);
     }

     @Override
     public void setEdgeWeight(int srcIdx, int dstIdx, double weight) {
         // Get the vetex objects associated with each index and pass off to the
         // version of setEdgeWEight that accepts vertex objects.
         this.setEdgeWeight(this.vertex(srcIdx), this.vertex(dstIdx), weight);
     }


     /**
      * Implementation of Dijkstra's algorithm.
      * @param startVertex Start vertex for the single-source shortest paths.
      * @return An array of size G.numVertices()+1 in which offset k contains the shortest
      *         path from startVertex to k.  Offset 0 is unused since vertex indices start
      *         at 1.
      */
     public Pair280<double[], int[]> shortestPathDijkstra(int startVertex) {
         // the method works for input 9, but for 1, the paths always skip vertex 3
         //not sure why

         int isVisited = 0; //counter for visited node;
         int length = this.numVertices() + 1;

         //array of tentative distance
         double[] tentativeD = new double[length];
         Arrays.fill(tentativeD, Double.MAX_VALUE);

         //array of boolean values for visited nodes
         boolean[] visited  = new boolean[length];
         Arrays.fill(visited, false);

         //array to fill in node predecessors
         int[] predecessorNode = new int[length];
         Arrays.fill(predecessorNode, 0);

         tentativeD[startVertex] = 0;
         int cur = startVertex;
         int z = 0;

         while(isVisited < length) {
             visited[cur] = true;
             isVisited++;                   //increment rhe counter for visited nodes

             this.eGoFirst(this.vertex(cur));
             while (this.eItemExists()) {
                 z = this.eItemAdjacentIndex();


                 if (!visited[z] && tentativeD[z] > tentativeD[cur] +
                         this.getEdgeWeight(cur, z)) {
                     tentativeD[z] = tentativeD[cur] + this.getEdgeWeight(cur, z);
                     predecessorNode[z] = cur;
                 }
                 this.eGoForth();
             }

             // find the next node with the shortest distance
             for(int i =1; i  <= length-1; i++){
                 if(!visited[i] && tentativeD[i]  < tentativeD[0]){
                     cur = i;
                 }
             }
         }

         return new Pair280<double[], int[]>(tentativeD, predecessorNode);
     }

     // Given a predecessors array output from this.shortestPathDijkatra, return a string
     // that represents a path from the start node to the given destination vertex 'destVertex'.
     private static String extractPath(int[] predecessors, int destVertex) {
         //e.g path from 3 to 2 = 2 <- 4 <- 7 <- 3 ( 2 = destVertex)

         String path = "" + destVertex;

         int idx = destVertex;
         if(predecessors[destVertex] <= 0){
            return "Not reachable.";
         }
         else {
             while (predecessors[idx] != 0) {
                 path = predecessors[idx] + ", " + path;
                 idx = predecessors[idx];
             }

             return "The path to " + destVertex + " is: " + path;
         }
     }

     // Regression Test
     public static void main(String args[]) {
         NonNegativeWeightedGraphAdjListRep280<Vertex280> G = new NonNegativeWeightedGraphAdjListRep280<Vertex280>(1, false);

         if( args.length == 0)
             G.initGraphFromFile("/Users/emmanuellaeyo/Desktop/My280Assignment/lib280-asn8/src/lib280/graph/weightedtestgraph.gra");
         else
             G.initGraphFromFile(args[0]);

         System.out.println("Enter the number of the start vertex: ");
         Scanner in = new Scanner(System.in);
         int startVertex;
         try {
             startVertex = in.nextInt();
         }
         catch(InputMismatchException e) {
             in.close();
             System.out.println("That's not an integer!");
             return;
         }

         if( startVertex < 1 || startVertex > G.numVertices() ) {
             in.close();
             System.out.println("That's not a valid vertex number for this graph.");
             return;
         }
         in.close();


         Pair280<double[], int[]> dijkstraResult = G.shortestPathDijkstra(startVertex);
         double[] finalDistances = dijkstraResult.firstItem();
         //double correctDistances[] = {-1, 0.0, 1.0, 3.0, 23.0, 7.0, 16.0, 42.0, 31.0, 36.0};
         int[] predecessors = dijkstraResult.secondItem();

         for(int i=1; i < G.numVertices() +1; i++) {
             System.out.println("The length of the shortest path from vertex " + startVertex + " to vertex " + i + " is: " + finalDistances[i]);
 //			if( correctDistances[i] != finalDistances[i] )
 //				System.out.println("Length of path from to vertex " + i + " is incorrect; should be " + correctDistances[i] + ".");
 //			else {
                 System.out.println(extractPath(predecessors, i));
 //			}
         }
     }

 }
