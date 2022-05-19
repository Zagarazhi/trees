package ru.zagarazhi;

public class BPlusTree {  

    private int factor;  
      
    private static final int DEFAULT_FACTOR = 5;
    private int MAX_CHILDREN_FOR_INTERNAL;
    private int MAX_FOR_LEAF;
      
    private Node root;  
      
    public BPlusTree() {  
        this(DEFAULT_FACTOR);  
    }  
      
    public BPlusTree(int factor) {  
        this.factor = factor;
        this.MAX_CHILDREN_FOR_INTERNAL = this.factor;
        this.MAX_FOR_LEAF = this.factor - 1;  
        this.root = new LeafNode();  
    }

    abstract class Node {  
          
        protected Node parent;
        protected int[] keys;
        protected int size;
        abstract Node insert(int key);
    }

    class InternalNode extends Node{  
        private Node[] pointers;  
          
        public InternalNode() {  
            this.size = 0;  
            this.pointers = new Node[MAX_CHILDREN_FOR_INTERNAL + 1];  
            this.keys = new int[MAX_CHILDREN_FOR_INTERNAL];  
        }  
          
        public Node insert(int key) {  
            int i = 0;  
            for (; i < this.size; i++) {  
                if (key < this.keys[i])  break;  
            }
            return this.pointers[i].insert(key);  
        }

        private Node insert(int key, Node leftChild, Node rightChild){  
            if (this.size == 0) {  
                this.size++;  
                this.pointers[0] = leftChild;  
                this.pointers[1] = rightChild;  
                this.keys[0] = key;  
                  
                leftChild.parent = this;  
                rightChild.parent = this;  
                  
                return this;  
            }
            int[] newKeys = new int[MAX_CHILDREN_FOR_INTERNAL + 1];  
            Node[] newPointers = new Node[MAX_CHILDREN_FOR_INTERNAL + 2];  
              
            int i = 0;  
            for(; i < this.size; i++) {  
                int curKey = this.keys[i];  
                if (curKey > key) break;  
            }
            System.arraycopy(this.keys, 0, newKeys, 0, i);  
            newKeys[i] = key;  
            System.arraycopy(this.keys, i, newKeys, i + 1, this.size - i);  
              
            System.arraycopy(this.pointers, 0, newPointers, 0, i + 1);  
            newPointers[i + 1] = rightChild;  
            System.arraycopy(this.pointers, i + 1, newPointers, i + 2, this.size - i);  
  
            this.size++;  
            if(this.size <= MAX_CHILDREN_FOR_INTERNAL) {  
                System.arraycopy(newKeys, 0, this.keys, 0, this.size);  
                System.arraycopy(newPointers, 0, this.pointers, 0, this.size + 1);  
                return null;  
            }  
              
            int m = (this.size / 2);  

            InternalNode newNode = new InternalNode();  
              
            newNode.size = this.size - m - 1;  
            System.arraycopy(newKeys, m + 1, newNode.keys, 0, this.size - m - 1);  
            System.arraycopy(newPointers, m + 1, newNode.pointers, 0, this.size - m);  
             
            for(int j = 0; j <= newNode.size; j++) {  
                newNode.pointers[j].parent = newNode;  
            }  
              
            this.size = m;  
            this.keys = new int[MAX_CHILDREN_FOR_INTERNAL];  
            this.pointers = new Node[MAX_CHILDREN_FOR_INTERNAL];  
            System.arraycopy(newKeys, 0, this.keys, 0, m);  
            System.arraycopy(newPointers, 0, this.pointers, 0, m + 1);  
              
            if (this.parent == null) {  
                this.parent = new InternalNode();  
            }  
            newNode.parent = this.parent;  
              
            return ((InternalNode)this.parent).insert(newKeys[m], this, newNode);  
        }  
    }
    
    class LeafNode extends Node {
          
        public LeafNode() {  
            this.size = 0;  
            this.keys = new int[MAX_FOR_LEAF]; 
            this.parent = null;  
        }  
          
        public Node insert(int key) {  
            Object[] newKeys = new Object[MAX_FOR_LEAF + 1];
              
            int i = 0;  
            for (; i < this.size; i++) {  
                int curKey = this.keys[i];  
                  
                if (curKey == key) {
                    return null;  
                }
                if (curKey > key) break;  
            }  
              
            System.arraycopy(this.keys, 0, newKeys, 0, i);  
            newKeys[i] = key;  
            System.arraycopy(this.keys, i, newKeys, i + 1, this.size - i);    
              
            this.size++;  
              
            if (this.size <= MAX_FOR_LEAF){  
                System.arraycopy(newKeys, 0, this.keys, 0, this.size);  
                return null;  
            }

            int m = this.size / 2;  
              
            this.keys = new int[MAX_FOR_LEAF];
            System.arraycopy(newKeys, 0, this.keys, 0, m);
              
            LeafNode newNode = new LeafNode();  
            newNode.size = this.size - m;  
            System.arraycopy(newKeys, m, newNode.keys, 0, newNode.size);
              
            this.size = m;  
              
            if (this.parent == null) {  
                this.parent = new InternalNode();  
            }  
            newNode.parent = this.parent;  
              
            return ((InternalNode)this.parent).insert(newNode.keys[0], this, newNode);  
        }
    }

    public void print(){

    }
}  
