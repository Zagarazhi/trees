package ru.zagarazhi;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class App {
    public static void main( String[] args ) {
        /*
        //30 - тестовый
        Random random = new Random();
        int[] ra = new int[16];
        for(int i = 0; i < ra.length; i++) {
            ra[i] = random.nextInt(151);
        }
        BinaryTree bt = new BinaryTree(ra[0], 0);
        for(int i = 1; i < ra.length; i++) {
            bt.addElement(ra[i]);
        }
        bt.print();
        //bt.tour();
        System.out.println("Ближайшее число, меньше или равное искомому: " + bt.search(123).toString());
        */
		BPlusTree<Integer, String> myTree = new BPlusTree<Integer, String>(8);  
              
        int max = 1000000;  
        long start = System.currentTimeMillis();  
        for(int i = 0; i < max; i++) {  
            myTree.set(i, String.valueOf(i));  
        }  
        System.out.println("time cost with BPlusTree: " + (System.currentTimeMillis() - start));  
        System.out.println("Data has been inserted into tree");  
            
        System.out.println("height: " + myTree.height());  
            
        start = System.currentTimeMillis();  
        Map<Integer, String> hashMap = new HashMap<Integer, String>();  
        for (int i = 0; i < max; i++) {  
            hashMap.put(i, String.valueOf(i));  
        }  
        System.out.println("time cost with HashMap: " + (System.currentTimeMillis() - start));  
            
        for (int i = 0; i < max; i++) {  
            if (!String.valueOf(i).equals(myTree.get(i))) {  
                System.err.println("error for: " + i);  
            }  
        }  
            
        System.out.println("Success"); 
        /*
        Random random = new Random();
        for(int i = 15000; i <= 150000; i+=15000){
            int[] ra = new int[i];
            for(int j = 0; j < ra.length; j++){
                ra[j] = random.nextInt(i+1);
            }
            BinaryTree bt = new BinaryTree(ra[0], 0);
            for(int j = 1; j < ra.length; j++){
                bt.addElement(ra[j]);
            }
            long start = System.nanoTime();
            BinaryTree ans = bt.search(-1);
            long end = System.nanoTime();
            System.out.println(ans + " " + (end - start));
        }
        */
    }
}
