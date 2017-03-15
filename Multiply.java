/******************
/*  LUCIA BERGER  /*
/*  260473661
 * Collaborating with Nic Yanzti, Stack overflow, Class notes (CHAPTER 16) 
 */



import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        int maxval = (1 << size) - 1;
        return (int)(Math.random()*maxval);
    }
    
    public static int[] naive(int size, int x, int y) {
      
        int [] results; 
        results = new int [2]; //giving array size 2
        int n = size; //putting size into variable n
   
        
        
        if (n == 1) {         // in the base case ==> the cost is 1, return below 
            results[0] = x * y; 
            results [1] = 1;
            return results;
        }
        else {

        double sizeDouble = (double) n/2;    //casting to double
        int m = (int) (Math.ceil(sizeDouble));  //casting back to int
        
        //DIRECLTY TAKEN FROM THE SLIDES

        int a = (x / (int) Math.pow(2, m)); 
        int b = x % (int) Math.pow(2, m);
        int c = (y / (int) Math.pow(2, m)); 
        int d = y % (int) Math.pow(2, m);
                
                
                
              
        
        //recusive calls
        // cost --> 3*m for each recursive call
        
        
        int e[] = naive(m,a,c);
        int f[] = naive(m,b,d);
        int g[] = naive (m,b,c);
        int h [] = naive(m,a,d);
        
        int naiveAnswer =  (int) ((Math.pow(2, 2*m)*e[0]) + (Math.pow(2,m))*(g[0]+h[0]))+f[0];

        int naiveCost = (e[1]+f[1]+g[1]+h[1]+(3*m)); 
        //save values in results array
           results[0] = naiveAnswer;
           results[1] = naiveCost;
        
          return results;
        }
        
    }

    public static int[] karatsuba(int size, int x, int y) {
       //MAKING ARRAY FOR THE KARATSUBA ARRAY
        int [] kresults; 
        kresults = new int [2];
        int n = size;
        
        if (n == 1){
            kresults[0] = x*y;
            kresults [1] = 1;
        
            return kresults;
        }
        else {
            
        
         
        //DIRECLTY TAKEN FROM THE SLIDES

           double sizeDouble = (double) n/2;  
           int m = (int) (Math.ceil(sizeDouble));
           int a = x / (int) Math.pow(2,m);  
           int b = x % (int) Math.pow(2,m); 
           int c = y / (int) Math.pow(2,m);
           int d = y % (int) Math.pow(2,m);
           
      
           
           int e[] = karatsuba(m,a,c);
           int f[] = karatsuba(m,b,d);
           int g [] = karatsuba (m, a-b, c-d);
           
           //Math.pow(2,2m)*e + (Math.pow(2,m) (e+f-g)+f
                  
           int karaAnswer = (int)((Math.pow(2,2*m)*e[0]) + (Math.pow(2,m)*(e[0]+f[0]-g[0])) + f[0]);
          
           // cost --> 6*m for each recursive call
           int karaCost = (e[1]+f[1]+g[1]+(6*m));

           kresults[0] = karaAnswer;
           kresults[1] = karaCost;

           return kresults;
        
        }
           
        
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}