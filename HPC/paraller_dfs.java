import java.util.Stack;

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

    public void SEQdfs(int src) {
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        stack.push(src);
        visited[src] = true;
        while (!stack.isEmpty()) {
            int node = stack.pop();
            for (int i = 0; i < n; i++) {
                if (adj[node][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    stack.push(i);
                }
            }
        }
    }

    public void PARdfs(int src) {
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        stack.push(src);
        visited[src] = true;
        while (!stack.isEmpty()) {
            Stack<Integer> localStack = new Stack<>();
            while (!stack.isEmpty()) {
                int node = stack.pop();
                visited[node] = true;
                localStack.push(node);
            }
            for (int i = 0; i < n; i++) {
                while (!localStack.isEmpty()) {
                    int node = localStack.pop();
                    if (adj[node][i] == 1 && !visited[i]) {
                        visited[i] = true;
                        stack.push(i);
                    }
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int n = 10000;
        Graph g = new Graph(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) g.addEdge(i, j);
            }
        }

        long stseq = System.currentTimeMillis();
        g.SEQdfs(0);
        long enseq = System.currentTimeMillis();
        double duration = (enseq - stseq) / 1000.0;
        System.out.println("Sequential DFS Execution Time: " + duration + " seconds");

        long stpar = System.currentTimeMillis();
        g.PARdfs(0);
        long enpar = System.currentTimeMillis();
        duration = (enpar - stpar) / 1000.0;
        System.out.println("Parallel DFS Execution Time: " + duration + " seconds");
    }
}
