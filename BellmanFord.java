/*LUCIA BERGER 
 * 260473661 
 * 
 * sources include: 
 * Wikipedia algorithm, stack overflow 
 * 
 */




import java.util.*;

public class BellmanFord{

    private int[] distances; //distance to shortest path s=> v 
    private int[] predecessors; //last edge on shortest path
    private int source;
    final int eve = -1; //to indicate no predecesor
   
    
    BellmanFord(WGraph g, int source) throws Exception{
       int numberofNodes = g.getNbNodes();     
       distances = new int [numberofNodes]; //distance to shortest path s=> v 
       predecessors  = new int [numberofNodes];
      
      
        ArrayList<Edge> listofEdges = g.getEdges();
        for (int i = 0; i < numberofNodes; i++){

            distances[i]=Integer.MAX_VALUE;
            predecessors[i]=eve; 
            //-1 to indicate no predicessors
        }   
       
     
        distances[source] = 0; 
                // The distance from the source to itself is zero.
        int i, j;
        

        //relaxes the edges
        for (i = 0; i<numberofNodes-1; ++i){
            for (j= 0; j<listofEdges.size(); j++){ //for the number of edges
                
                int u = g.getEdges().get(j).nodes[0];
                int v = g.getEdges().get(j).nodes[1];
                int weight = g.getEdges().get(j).weight;
                
                if (distances[u] + weight < distances[v]){
                distances[v] = distances[u] + weight;
                predecessors[v] = u;
              }
            }
        }
     
      
        for (j= 0; j<listofEdges.size(); j++){ 
              int u = g.getEdges().get(j).nodes[0];
                int v = g.getEdges().get(j).nodes[1];
                int weight = g.getEdges().get(j).weight;
                if (distances[u] + weight  < distances[v]) {
                    throw new Exception("Negative cycle Found!");

                
                }
                
    
            }
      }

        
  public int[] shortestPath(int destination) throws Exception{
    ArrayList<Integer> reversePath = new ArrayList<Integer>();
      reversePath.add(destination);
      int predecessor = this.predecessors[destination];


      while ( predecessor != source) {
          reversePath.add(predecessor);
          predecessor = this.predecessors [predecessor];
          if(predecessor == -1) {
              throw new Exception("There is no path");
          }

      }
      

      reversePath.add(predecessor);
      int [] shortPathestPath = new int [reversePath.size()];
      int j = 0;
      
      for (int i = reversePath.size()-1; i>=0; i--){

        shortPathestPath[j] = reversePath.get(i);
        j++;

      }

     //System.out.println(shortPathestPath);
    
      //move to reversepath 
  return shortPathestPath;
}
       
  public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        
        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}