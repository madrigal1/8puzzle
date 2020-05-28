package ai;

public class MinPQ<Key extends Comparable<Key>> {
        Key[] arr;
        private int n;
        MinPQ() {
            arr =(Key[]) new Comparable[1];
            n=0;
        }

        public void insert(Key item) {
            if(n == arr.length-1) resize(arr.length*2);
            arr[++n] = item;
            swim(n);
        }

        public Key delMin() {
            Key min = arr[1];
            exch(n--,1);
            sink(1);
            arr[n+1] = null;
            if(n == (arr.length-1)/4) resize(arr.length/2);
            return min;
        }

        private void resize(int capacity) {
            Key[] clone = (Key[]) new Comparable[capacity+1];
            for(int i=1;i<=n;i++)
                clone[i] = arr[i];
             arr=clone;
        }

        private void show() {
            for(Key item:arr)
                System.out.print(item);
            System.out.print('\n');
        }


        private void sink(int k) {
            while(2*k <=n) {
                int j= 2*k;
                if(j<n && greater(j,j+1))j++;
                exch(j,k);
                k = j;
            }
        }
        private void swim(int k) {
            while (k>1 && greater(k/2,k)) {
                exch(k/2,k);
                k/=2;
            }
        }


        private boolean greater(int i,int j) {
            return arr[i].compareTo(arr[j]) >0;
        }

        private void exch(int i,int j) {
            Key temp = arr[i];
            arr[i] =  arr[j];
            arr[j] = temp;
        }
        private int arrlength() {
            return arr.length;
        }

        public Key min() {
            return arr[1];
        }

        public static void main(String[] args) {
            MinPQ<String> pq = new MinPQ<>();
            System.out.println(pq.arrlength());
//            pq.insert("test");
//           pq.insert("zebra" );
           pq.insert("19BBS0152");
           pq.insert("19BBS0151");
            pq.insert("19BBS001");
            System.out.print(pq.delMin());
        }
}
