/*LUCIA BERGER*/

import java.util.*;
 
//you will need the classes DisjointSets and WGraph to excecute Kruskal
//import WGraph.java;
// import DisjointSets.java;
 
public class Kruskal{
 
    public static WGraph kruskal(WGraph g){
 
        /* Fill this method (The statement return null is here only to compile) */
        //graph is originally empty - we will add edges 1 by 1.
        WGraph mst = new WGraph();
        int numberOfNodes = g.getNbNodes();
        DisjointSets djSets = new DisjointSets(numberOfNodes);
 
 
        ArrayList<Edge> listEdgesSorted = g.listOfEdgesSorted();
 
        for (int i = 0; i < listEdgesSorted.size(); i++){
 
            Edge e = listEdgesSorted.get(i);
            int v = e.nodes[0];
            int w = e.nodes[1];
 
            if (IsSafe(djSets, e)){
                djSets.union(v,w);
                mst.addEdge(e);
             
            }
        }
        return mst;
    }
 
    public static Boolean IsSafe(DisjointSets p, Edge e){
        int v = e.nodes[0];
        int w = e.nodes[1];
 
        if (p.find(v) == p.find(w)){
            return false;
        } else {
            return true;
        }
 
 
        /* Fill this method (The statement return 0 is here only to compile) */
     
    }
 
    public static void main(String[] args){
 
        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);
 
   }
}
