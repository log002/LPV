import java.util.Random;

class Main {

    static void SeqBubbleSort(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void ParBubbleSort(int arr[]) {
        int n = arr.length;
        Thread threads[] = new Thread[n - 1];
        for (int i = 0; i < n - 1; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < n - index - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < n - 1; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int n = 50000;
        int[] arr = new int[n];

        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000);
        }

        long seqStart = System.currentTimeMillis();
        SeqBubbleSort(arr);
        long seqEnd = System.currentTimeMillis();
        System.out.println("Sequential Time: " + (seqEnd - seqStart) + " ms");

        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000);
        }

        long parStart = System.currentTimeMillis();
        ParBubbleSort(arr);
        long parEnd = System.currentTimeMillis();
        System.out.println("Parallel Time: " + (parEnd - parStart) + " ms");
    }
}
