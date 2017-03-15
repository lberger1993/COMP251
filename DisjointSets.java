/*LUCIA BERGER 
260473661 
Assignment 2
*/


 
import java.io.*;
import java.util.*;
 
 
/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*
*****************************/
 
 
public class DisjointSets {
 
    private int[] par;
     
    /* contructor: creates a partition of n elements. */
    /* Each element is a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
     
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
     
    /* calling recursively--> path convergence method */
    public int find(int i) {
        if (par[i] == i) {
            return i;
        } else {
            return find(par[i]);
        }
    }
 
        /* Fill this method (The statement return 0 is here only to compile) */
    //    return 0
         
 
    /* merge sets containing elements i and j */
    public int union(int i, int j) {
        if(find(i) != find(j)) {
            par[find(i)] = find(j);
        }
        /* Fill this method (The statement return 0 is here only to compile) */
        return 0;
         
    }
     
    public static void main(String[] args) {
         
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
         
    }
 
}