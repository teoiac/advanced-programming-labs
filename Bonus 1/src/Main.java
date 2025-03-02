import java.util.ArrayList;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        int n, k, i, j;
        boolean flag = false;
        if (args.length != 2) {
            System.out.println("Incorrect number of arguments");
            return;
        } else {
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
        }
        int[][] graph = new int[n][n];

        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                graph[i][j] = -1;

        for (i = 0; i < n; i++)
            graph[i][i] = 0;

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (graph[i][j] == -1) {
                    graph[i][j] = (int) (Math.random() * (2 - 0) + 0);
                    graph[j][i] = graph[i][j];
                }
            }
        }

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        for (i = 0; i < n; i++){
            List<Integer> clique = new ArrayList<Integer>();
            int degreeCount = 1;
            for (j = 0; j < n; j++){
                if (graph[i][j] == 1){
                    degreeCount++;
                }
            }
            if(degreeCount >= k){
                clique.add(i);
                for(j = 0; j < n; j++){
                    if(graph[i][j] == 1){
                        clique.add(j);
                    }
                }
                int t1, t2;
                boolean isClique = true;
                for(t1=1;t1< clique.size()-1;t1++){
                    for(t2=t1+1;t2< clique.size();t2++){
                        if(graph[clique.get(t1)][clique.get(t2)] == 0){
                            isClique = false;
                            break;
                        }
                    }
                    if(!isClique){
                        break;
                    }
                }
                if(isClique){
                    System.out.println("Found a clique of size: " + clique.size());
                    System.out.println("The clique is " + clique.toString());
                    flag = true;
                }
            }
        }
        if(flag == false){
            System.out.println("No cliques of size gte k");
        }
    }


}