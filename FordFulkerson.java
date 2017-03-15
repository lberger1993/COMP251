/*LUCIA BERGER 
 * 260473661 
 * 
 * sources include: 
 * Wikipedia algorithm, stack overflow 
 * 
 */


/*pathDFS: returns paths found by DFS using arraylists of Stack and Q
 *that behave as Stacks and queues in the below 
 * 
 */


import java.io.*;
import java.util.*;

public class FordFulkerson
{
    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph)
    {
        ArrayList<Integer> Stack = new ArrayList<Integer>();
        ArrayList<Integer> Q = new ArrayList<Integer>();
        Q.add(source);
        Stack.add(source);
        while (Q.size() > 0)
        {
            Integer v = Q.remove(0);
            if (v == destination)
            {
                break;
            }
            Integer u;
            for (Edge e : graph.listOfEdgesSorted())
            {
                if (v == e.nodes[0])
                {
                    u = e.nodes[1];
                    if (!Stack.contains(u) && e.weight != 0)
                    {
                        Q.add(u);
                        Stack.add(u);
                        break;
                    }
                }
            }
        }
        return Stack;
    }

    
  /*AUGMENTING PATH: 
   * @parameters: stack, source, graph 
   * @ returns if there is an augmenting path  
   */
    

    static boolean augmentingPath(ArrayList<Integer> Stack, Integer source, WGraph graph)
    {
        boolean augmentingPath = true;
        if (Stack.contains(graph.getSource()) && Stack.size() == 1)
        {
            augmentingPath = false;
        }
        return augmentingPath;
    }

 
    public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath)
    {
        String answer = "";
        String mcgill_id = "260473661";
        int max_flow = 0;

        WGraph residualGraph = new WGraph(graph);
        for (Edge e : graph.getEdges()){
            e.weight = 0;}

        ArrayList<Integer> paths = pathDFS(source, destination, residualGraph);

        while (augmentingPath(paths, residualGraph.getSource(), residualGraph))
        {
            if (paths.contains(residualGraph.getDestination()))
            {
                int minimum = residualGraph.getEdge(paths.get(0), paths.get(1)).weight;
                for (int i = 0; i < paths.size() - 1; i++)
                {
                  if (residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight < minimum) /*&& residualGraph.getEdge(paths.get(i), paths.get(i).weight!= 0)))*/
                    {
                        minimum = residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight;
                    }
                }
                max_flow += minimum;
                for (int i = 0; i < paths.size() - 1; i++)
                {
                    graph.getEdge(paths.get(i), paths.get(i + 1)).weight += minimum;
                    residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight -= minimum;
                }
            }

            if (!paths.contains(residualGraph.getDestination()) && augmentingPath(paths, residualGraph.getSource(), residualGraph))
            {

                //change all the nodes of the incomplete path to zero 
                for (int i = 0; i < paths.size() - 1; i++)
                {
                    residualGraph.getEdge(paths.get(i), paths.get(i + 1)).weight = 0;
                }

            }

            paths = pathDFS(source, destination, residualGraph);
        }

        System.out.println("\nresidualGraph Graph");
        System.out.println(residualGraph.toString());

        System.out.println("---------------------------------");
        System.out.println("Resulting Graph\n");

        answer += max_flow + "\n" + graph.toString();
        writeAnswer(filePath + mcgill_id + ".txt", answer);
        System.out.println(answer);
    }

    public static void writeAnswer(String path, String line)
    {
        BufferedReader br = null;
        File file = new File(path);
        // if file doesnt exists, then create it

        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line + "\n");
            bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        args = new String[]
        {
            "../ff2.txt"
        };
        String file = args[0];
        File f = new File(file);
        WGraph g = new WGraph(file);
        fordfulkerson(g.getSource(), g.getDestination(), g, f.getAbsolutePath().replace(".txt", ""));
    }
}
