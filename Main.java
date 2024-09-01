//Danielle Andal - 6/15/24

package Assignments.PA3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int n; // Number of nodes 
    public static int edges; // Number of edges
    public static int num_remove; // Number of edges to be disconnected
    public static Edges[] Alist;  // Array of edges
    public static long FA; // Final answer per disconnectivity
    public static ArrayList<Long> list_FA; // list of final answers

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Number of nodes, edges, and nodes to remove
        n = in.nextInt();
        edges = in.nextInt();
        num_remove = in.nextInt();

        // Array of edges
        Alist = new Edges[edges];

        // Add the edges
        for (int i = 0; i < edges; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            Edges E = new Edges(a, b);
            Alist[i] = E;
        }

        // Add the indexes of which edges to be disconnected 
        int[] Rlist = new int[num_remove];
        for (int i = 0; i < num_remove; i++) {
            Rlist[i] = in.nextInt() - 1;
        }

        //Boolean array for which edges is to connected and not
        boolean[] inGraph = new boolean[edges];

        //Initialize boolean array appropriately
        for (int i = 0; i < edges; i++) {
            inGraph[i] = true;
        }
        for (int i = 0; i < num_remove; i++) {
            inGraph[Rlist[i]] = false;
        }
   

        // Initialize disjoint set
        disjointSet Set = new disjointSet(n); // make set

        // Initalize the final answer arraylist
        list_FA = new ArrayList<>();

        // Connect the edges that will not be removed later one
        for (int i = 0; i < edges; i++) {
            if (inGraph[i]) {

                // the straregy to solve the project is at union method
                Set.union(Alist[i].getV1(), Alist[i].getV2());
            }
       
        }

        // Get the connectivity and store in final answer array
        FA = Set.connectivity();
        list_FA.add(FA);

        // Connect the edges that were originally going to be removed 
        for (int i = num_remove - 1; i >= 0; i--) {
            Set.union(Alist[Rlist[i]].getV1(), Alist[Rlist[i]].getV2());
  
            // Get the connectivity for each connection made
            FA = Set.connectivity();
            list_FA.add(FA);

        }

        // Print the final answer
        for (int i = list_FA.size() - 1; i >= 0; i--) {
            System.out.println(list_FA.get(i));
        }

        in.close();
    }
}

// DISJOINT SET DATA STRUCTURE
class disjointSet {

    private Pair[] parents;
    private long final_answer;

    // Constructor to make set
    public disjointSet(int n) {
        parents = new Pair[n];

        // Initialize each node, all heights are initially 0
        for (int i = 0; i < n; i++) {
            parents[i] = new Pair(i, 0);
        }
    }

     // Finds the parent of a node
     public int find(int id) {
        // if the ID it not itself, meaning it's not the root
        if (id != parents[id].getID()) {
            // traverse and once root is found, set the id to the root
            parents[id].setID(find(parents[id].getID())); // path compression
        }
        // returns the root
        return parents[id].getID();
    }

    // Merges two nodes, and keeps tracks of connectivity
    /* Since path compression is implemented, for every connectivity, there would always only be one root. For every number of nodes connected to the root, that is the connectivity.
    If the node has only one number of node, it means that it is a child node or a root itself. */ 
    public boolean union(int v1, int v2) {
        int d1 = find(v1);
        int d2 = find(v2);

        // Already connected
        if (d1 == d2) return false;

        // d1 height is greater that d2 so we merge there
        if (parents[d1].getHeight() > parents[d2].getHeight()) {
            parents[d2].setID(d1);

            // When a node is merge into another one, increase the num_nodes appropriately
            parents[d1].addNodes(parents[d2].getNodes());

            // Since we are merging, the number of nodes of d2 should be 1 again if its not already because of path compression logic
            parents[d2].setNode();

        }
        // d2 height is greather than d1 so we merge there
         else if (parents[d2].getHeight() > parents[d1].getHeight()) {
            parents[d1].setID(d2);

            // Adds the number of nodes appropriate and since we have merge, the reset number of nodes appropriately
            parents[d2].addNodes(parents[d1].getNodes());
            parents[d1].setNode();
        } 
        // d1 and d2 have the same height
        else {

            // Merge into whicheven has a lower root
            if (d1 < d2) {
                parents[d2].setID(d1);
                parents[d1].incHeight();

                 // Adds the number of nodes appropriate and since we have merge, the reset number of nodes appropriately
                parents[d1].addNodes(parents[d2].getNodes());
                parents[d2].setNode();
            } else {
                parents[d1].setID(d2);
                parents[d2].incHeight();

                // Adds the number of nodes appropriate and since we have merge, the reset number of nodes appropriately
                parents[d2].addNodes(parents[d1].getNodes());
                parents[d1].setNode();
            }
        }
        //Union is complete
        return true;
    }

    @Override // DEBUGGER , prints the number of nodes per pair node
    public String toString() {
        String ans = "";
        for (int i = 0; i < parents.length; i++) {
            
            ans += "parent:" + i + " num nodes:" + parents[i].getNodes() + "\n";
        }
        return ans;
    }


    // To check connectivity, number of nodes connected and calculate appropriately
    public long connectivity() {
        final_answer = 0;

        // Go through all the pair nodes
        for (int i = 0; i < parents.length; i++)
         {
                // If it's a root node, add its square to the final answer
                if (parents[i].getID() == i) {
                    final_answer += (parents[i].getNodes() * parents[i].getNodes());
                }
            
        }
        // Return the final answer
        return final_answer;
    }
    
}



// EDGE CLASS, tracks the edges to be connected and disconnected
class Edges {
    private int v1;
    private int v2;

    public Edges(int d1, int d2) {
        this.v1 = d1;
        this.v2 = d2;
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }
}

// PAIR CLASS
class Pair {
    private int ID;
    private int height;
    private long num_nodes; // Keeps track of nodes/children

    public Pair(int num, int height) {
        this.ID = num;
        this.height = height;
        this.num_nodes = 1; // Initialize with 1 node, because itself would be one node
    }

    public int getHeight() {
        return height;
    }

    public int getID() {
        return ID;
    }

    public void setID(int newID) {
        ID = newID;
    }

    public void incHeight() {
        height++;
    }

    public void decHeight() {
        height--;
    }

    // Adds the number of nodes connected
    public void addNodes(long nodes) {
        this.num_nodes += nodes;
    }

    // Returns the number of nodes connected
    public long getNodes() {
        return num_nodes;
    }

    // Resets the number of node
    public void setNode(){
        num_nodes = 1;
    }
}
