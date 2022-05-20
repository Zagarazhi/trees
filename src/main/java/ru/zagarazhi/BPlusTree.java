package ru.zagarazhi;

public class BPlusTree {
    private class Node {
        public boolean isLeaf;
        public int level;
        public int[] keys;
        public Node[] child;
        public Node next;

        public Node(boolean isLeaf, int level) {
            this.isLeaf = isLeaf;
            this.level = level;
        }

        public void print() {
            for(int i = 0; i < 2 * level; i++) {
                System.out.print("\t");
            }
            System.out.println(stringKeys());
            if(child != null){
                for(int i = 0; i < child.length; i++) {
                    child[i].print();
                }
            }
        }

        public int search(int number){
            if(isLeaf) {
                int ans = keys[0];
                for(int i = 1; i < keys.length; i++) {
                    if(keys[i] <= number){
                        ans = keys[i];
                    }
                }
                return ans;
            }
            if(number > keys[keys.length - 1]) return child[child.length - 1].search(number);
            int bias = 0;
            while(keys[bias] < number) {
                bias++;
            }
            return child[bias].search(number);
        }

        public String stringKeys() {
            StringBuilder sb = new StringBuilder("[");
            for(int i = 0; i < keys.length; i++) {
                if(i != 0) {
                    sb.append(", " + keys[i]);
                } else {
                    sb.append(keys[i]);
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    private Node root;
    private int degree;

    private void sort(int[] array) {
        int n = array.length;  
        int temp = 0;  
        for(int i = 0; i < n; i++){  
            for(int j = 1; j < (n-i); j++){  
                if(array[j-1] > array[j]){
                    temp = array[j-1];  
                    array[j-1] = array[j];  
                    array[j] = temp;  
                }  
            }  
        }
    }

    public BPlusTree(int[] keys, int degree) {
        this.degree = degree;
        sort(keys);

        root = new Node(false, 0);
        int[] tempK = new int[1];
        tempK[0] = keys[keys.length / 2 - 2];
        root.keys = tempK;

        Node[] temp = new Node[2 * keys.length / degree + 1];
        for(int i = 0; i < temp.length - 1; i++) {
            temp[i] = new Node(true, 2);
            temp[i].keys = new int[2];
            temp[i].keys[0] = keys[2 * i];
            temp[i].keys[1] = keys[2 * i + 1];
            if(i != 0) {
                temp[i - 1].next = temp[i];
            }
        }
        temp[temp.length - 1] = new Node(true, 2);
        temp[temp.length - 1].keys = new int[4];
        temp[temp.length - 2].next = temp[temp.length - 1];
        for(int i = 0; i < 4; i++) {
            temp[temp.length - 1].keys[i] = keys[i + 12];
        }

        root.child = new Node[2];
        root.child[0] = new Node(false, 1);
        root.child[0].keys = new int[2];
        root.child[0].child = new Node[3];
        for(int i = 0; i < root.child[0].child.length; i++){
            root.child[0].child[i] = temp[i];
            if(i != 0){
                root.child[0].keys[i - 1] = temp[i].keys[0];
            }
        }

        int bias = root.child[0].child.length;
        root.child[1] = new Node(false, 1);
        root.child[1].keys = new int[3];
        root.child[1].child = new Node[4];
        for(int i = 0; i < root.child[1].child.length; i++){
            root.child[1].child[i] = temp[i + bias];
            if(i != 0){
                root.child[1].keys[i - 1] = temp[i + bias].keys[0];
            }
        }
    }

    public void tour() {
        Node temp = root;
        while(!temp.isLeaf){
            temp = temp.child[0];
        }
        while(temp != null) {
            System.out.println(temp.stringKeys());
            temp = temp.next;
        }
    }

    public void print() {
        root.print();
    }

    public int search(int number) {
        return root.search(number);
    }
}
