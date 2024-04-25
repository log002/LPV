import java.util.LinkedList;
import java.util.Queue;

class Graph {
    private int[][] adj;
    private int n;

    public Graph(int v) {
        n = v;
        adj = new int[n][n];
    }

    public void addEdge(int v, int w) {
        adj[v][w] = 1;
    }

    public void seqBFS(int src) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        visited[src] = true;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int i = 0; i < n; i++) {
                if (adj[node][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    public void parBFS(int src) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        visited[src] = true;
        while (!queue.isEmpty()) {
            Queue<Integer> localQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int i = 0; i < n; i++) {
                    if (adj[node][i] == 1 && !visited[i]) {
                        visited[i] = true;
                        localQueue.add(i);
                    }
                }
            }
            for (int node : localQueue) {
                queue.add(node);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int n = 5000;
        Graph g = new Graph(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) g.addEdge(i, j);
            }
        }

        long seqStartTime = System.currentTimeMillis();
        g.seqBFS(0);
        long seqEndTime = System.currentTimeMillis();
        double seqDuration = (seqEndTime - seqStartTime) / 1000.0;
        System.out.println("Sequential BFS Execution Time: " + seqDuration + " seconds");

        long parStartTime = System.currentTimeMillis();
        g.parBFS(0);
        long parEndTime = System.currentTimeMillis();
        double parDuration = (parEndTime - parStartTime) / 1000.0;
        System.out.println("Parallel BFS Execution Time: " + parDuration + " seconds");
    }
}
