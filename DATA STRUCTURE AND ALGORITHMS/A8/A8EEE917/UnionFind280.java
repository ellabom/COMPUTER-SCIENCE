//Name: EMMANUELLA EYO
//Student ID: 11291003
//NSID: EEE917

import lib280.graph.*;


public class UnionFind280 {
	GraphAdjListRep280<Vertex280, Edge280<Vertex280>> G;
	
	/**
	 * Create a new union-find structure.
	 * 
	 * @param numElements Number of elements (numbered 1 through numElements, inclusive) in the set.
	 * @postcond The structure is initialized such that each element is in its own subset.
	 */
	public UnionFind280(int numElements) {
		G = new GraphAdjListRep280<Vertex280, Edge280<Vertex280>>(numElements, true);
		G.ensureVertices(numElements);		
	}
	
	/**
	 * Return the representative element (equivalence class) of a given element.
	 * @param id The elements whose equivalence class we wish to find.
	 * @return The representative element (equivalence class) of the element 'id'.
	 */
	public int find(int id) {
		// TODO - Write this method
		GraphAdjListRep280<Vertex280, Edge280<Vertex280>> graphPos = G;

		//set the vertex cursor to v
		graphPos.goIndex(id);
		//set the edge cursor to the first edge of v
		graphPos.eGoFirst(graphPos.item());

		while(graphPos.eItemExists()){
			graphPos.goVertex(graphPos.eItemAdjacentVertex());
			graphPos.eGoFirst(graphPos.item());
		}

		Vertex280 result = graphPos.item();
		return result.index();
	}
	
	/**
	 * Merge the subsets containing two items, id1 and id2, making them, and all of the other elemnets in both sets, "equivalent".
	 * @param id1 First element.
	 * @param id2 Second element.
	 */
	public void union(int id1, int id2) {
		// TODO - Write this method.

		int root_v1 = find(id1);
		int root_v2 = find(id2);

		if(root_v1 != root_v2){
			G.addEdge(root_v1, root_v2);
		}
		
	}
}
