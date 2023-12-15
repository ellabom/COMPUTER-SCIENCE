import lib280.graph.Vertex280;
import lib280.graph.WeightedEdge280;
import lib280.graph.WeightedGraphAdjListRep280;
import lib280.tree.ArrayedMinHeap280;

public class Kruskal {

	/**
	 *
	 * @param G A weighted, undirected graph
	 * @return an undirected, weighted graph with the same node as G;
	 * 			but no edges.
	 */
	public static WeightedGraphAdjListRep280<Vertex280> minSpanningTree(WeightedGraphAdjListRep280<Vertex280> G) {

		WeightedGraphAdjListRep280<Vertex280> minST = new WeightedGraphAdjListRep280<>(G.capacity(), false);

		UnionFind280 UF = new UnionFind280(G.numVertices());

		//sort the edge of G from smallest to largest weight
		ArrayedMinHeap280<WeightedEdge280<Vertex280>> sortEdge = new ArrayedMinHeap280<>(G.numVertices() * G.numVertices());

		G.goFirst();
		while(G.itemExists()){
			G.eGoFirst(G.item());
			while(G.eItemExists()) {
				sortEdge.insert(G.eItem());
				G.eGoForth();
			}
			G.goForth();
		}


		while (!sortEdge.isEmpty()) {

			WeightedEdge280<Vertex280> edge280 = sortEdge.item();
			Vertex280 a = edge280.firstItem();
			Vertex280 b = edge280.secondItem();
			sortEdge.deleteItem();
			if (UF.find(a.index()) != UF.find(b.index())){
				minST.addEdge(a,b);
				minST.setEdgeWeight(a, b, G.getEdgeWeight(a,b));
				UF.union(a.index(),b.index());
			}
		}
		return minST;
	}
	
	
	public static void main(String[] args) {
		WeightedGraphAdjListRep280<Vertex280> G = new WeightedGraphAdjListRep280<>(1, false);
		G.initGraphFromFile("/Users/emmanuellaeyo/Desktop/My280Project/KruskalTemplate/mst.graph");
		System.out.println(G);
		
		WeightedGraphAdjListRep280<Vertex280> minST = minSpanningTree(G);
		
		System.out.println(minST);
	}
}


