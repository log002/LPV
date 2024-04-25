import java.util.concurrent.ThreadLocalRandom;

public class Main {

    // Sequential implementations
    static long seqMin(final long[] arr) {
        long minVal = Long.MAX_VALUE;
        for (long num : arr) {
            if (num < minVal) {
                minVal = num;
            }
        }
        return minVal;
    }

    static long seqMax(final long[] arr) {
        long maxVal = Long.MIN_VALUE;
        for (long num : arr) {
            if (num > maxVal) {
                maxVal = num;
            }
        }
        return maxVal;
    }

    static long seqSum(final long[] arr) {
        long sum = 0;
        for (long num : arr) {
            sum += num;
        }
        return sum;
    }

    static double seqAverage(final long[] arr) {
        long sum = seqSum(arr);
        return (double) sum / arr.length;
    }

    public static void main(final String[] args) {
        final int N = 70000; // Size of the array
        final long[] arr = new long[N];

        // Initialize array with random values
        for (int i = 0; i < N; ++i) {
            arr[i] = ThreadLocalRandom.current().nextLong(1000); // Random values between 0 and 999
        }

        // Measure execution time for sequential operations
        long seqStartTime, seqEndTime;
        double seqDuration;

        seqStartTime = System.currentTimeMillis();
        long minSeq = seqMin(arr);
        seqEndTime = System.currentTimeMillis();
        seqDuration = (seqEndTime - seqStartTime) / 1000.0;
        System.out.println("Sequential Min Execution Time: " + seqDuration + " seconds");

        seqStartTime = System.currentTimeMillis();
        long maxSeq = seqMax(arr);
        seqEndTime = System.currentTimeMillis();
        seqDuration = (seqEndTime - seqStartTime) / 1000.0;
        System.out.println("Sequential Max Execution Time: " + seqDuration + " seconds");

        seqStartTime = System.currentTimeMillis();
        long sumSeq = seqSum(arr);
        seqEndTime = System.currentTimeMillis();
        seqDuration = (seqEndTime - seqStartTime) / 1000.0;
        System.out.println("Sequential Sum Execution Time: " + seqDuration + " seconds");

        seqStartTime = System.currentTimeMillis();
        double avgSeq = seqAverage(arr);
        seqEndTime = System.currentTimeMillis();
        seqDuration = (seqEndTime - seqStartTime) / 1000.0;
        System.out.println("Sequential Average Execution Time: " + seqDuration + " seconds");
    }
}
