import java.util.Arrays;
import java.util.Random;

class Main {
    static int[] merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; i++) {
            left[i] = arr[l + i];
        }

        for (int j = 0; j < n2; j++) {
            right[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }

        return arr;
    }

    static int[] seqMergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            arr = seqMergeSort(arr, l, m);
            arr = seqMergeSort(arr, m + 1, r);
            arr = merge(arr, l, m, r);
        }

        return arr;
    }

    static int[] parMergeSort(int[] arr, int l, int r) {
        if (l < r) {
            if ((r - l) > 1000) {
                int m = (l + r) / 2;
                int[] arrLeft = Arrays.copyOfRange(arr, l, m + 1);
                int[] arrRight = Arrays.copyOfRange(arr, m + 1, r + 1);
                Thread t1 = new Thread(() -> parMergeSort(arrLeft, 0, arrLeft.length - 1));
                Thread t2 = new Thread(() -> parMergeSort(arrRight, 0, arrRight.length - 1));
                t1.start();
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                merge(arr, l, m, r);
            } else {
                int m = (l + r) / 2;
                seqMergeSort(arr, l, m);
                seqMergeSort(arr, m + 1, r);
                merge(arr, l, m, r);
            }
        }

        return arr;
    }

    public static void main(String[] args) {
        int n = 50000;
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];

        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr1[i] = rand.nextInt(1000);
            arr2[i] = rand.nextInt(1000);
        }

        long seqStartTime = System.currentTimeMillis();
        seqMergeSort(arr1, 0, n - 1);
        long seqEndTime = System.currentTimeMillis();
        double seqDuration = (seqEndTime - seqStartTime) / 1000.0;
        System.out.println("Sequential Merge Sort Execution Time: " + seqDuration + " seconds");

        long parStartTime = System.currentTimeMillis();
        parMergeSort(arr2, 0, n - 1);
        long parEndTime = System.currentTimeMillis();
        double parDuration = (parEndTime - parStartTime) / 1000.0;
        System.out.println("Parallel Merge Sort Execution Time: " + parDuration + " seconds");
    }
}
