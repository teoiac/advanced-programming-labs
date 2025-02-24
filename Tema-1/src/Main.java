//Iacob Teodora - 2B1 - Tema 1
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i, j, edgesCount = 0;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[][] graph = new int[n][n];
        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                graph[i][j] = -1;


        for (i = 0; i < k; i++)
            for (j = 0; j < k; j++) {
                graph[i][j] = 1;
                graph[j][i] = 1;
            }

        if (n - k >= k) {
            for (i = k; i < n; i++) {
                for (j = k; j < n; j++) {
                    graph[i][j] = 0;
                    graph[j][i] = 0;
                }
            }
        } else {
            System.out.println("Nu putem avea si multime stabila");
        }
        for (i = 0; i < n; i++) {
            graph[i][i] = 0;
        }
        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++) {
                if (graph[i][j] == -1) {
                    graph[i][j] = (int) (Math.random() * (2 - 0) + 0);
                    graph[j][i] = graph[i][j];
                }
            }
        int minDegree = Integer.MAX_VALUE;
        int maxDegree = Integer.MIN_VALUE;
        for (i = 0; i < n; i++) {
            int degree = 0;
            for (j = 0; j < n; j++) {
                System.out.print(graph[i][j] + " ");
                if (graph[i][j] == 1) {
                    edgesCount++;
                    degree++;
                }

            }
            if (degree < minDegree)
                minDegree = degree;
            if (degree > maxDegree)
                maxDegree = degree;
            System.out.println();
        }
        edgesCount = edgesCount / 2;
        System.out.println("Numarul de muchii = " + edgesCount);

        System.out.println("Gradul maxim = " + maxDegree);

        System.out.println("Gradul minim = " + minDegree);

    }
}