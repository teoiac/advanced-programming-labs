import java.util.Random;

public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        int i, j, n, k, edgesCount = 0;
        if (args.length != 2) {
            System.out.println("Incorrect number of arguments");
            return;
        } else {
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
        }
        int[][] adjacencyMatrix = new int[n][n];

        // generating a random array with k vertices - those are the clique vertices
        int[] randomValues = new int[k];
        Random random = new Random();
        int count = 0;
        while (count < k) {
            int randomValue = random.nextInt(n);
            boolean duplicate = false;
            for (j = 0; j < count; j++) {
                if (randomValues[j] == randomValue) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                randomValues[count] = randomValue;
                count++;
            }
        }
        /*
        System.out.println("Random values for clique are:");
        for (int value : randomValues) {
            System.out.print(value + " ");
        }
        System.out.println();

         */

        // initialising the matrix with the default value  o f -1
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = -1;
            }
        }

        // building the clique

        for (i = 0; i < k - 1; i++) {
            for (j = i + 1; j < k; j++) {
                adjacencyMatrix[randomValues[i]][randomValues[j]] = 1;
                adjacencyMatrix[randomValues[j]][randomValues[i]] = 1;
            }
        }


        if (n - k < k) {
            System.out.println("Not possible for a stable set to exist");
        } else {

            // picking k random vertices to form a stable set

            int[] randomValues2 = new int[k];
            count = 0;
            Random random2 = new Random();
            while (count < k) {
                boolean duplicate = false;
                int randomValue2 = random2.nextInt(n);
                for (i = 0; i < count; i++) {
                    if (randomValues2[i] == randomValue2) {
                        duplicate = true;
                        break;
                    }
                }

                boolean stableSet = true;
                for (i = 0; i < count; i++) {

                    if (adjacencyMatrix[randomValue2][randomValues2[i]] == 1) {
                        stableSet = false;
                        break;
                    }
                }
                if (!duplicate && stableSet) {
                    randomValues2[count] = randomValue2;
                    count++;
                }
            }
            /*
            System.out.println("Random values for stable set are:");
            for (int value : randomValues2) {
                System.out.print(value + " ");
            }
            System.out.println("\n");
            */
            for (i = 0; i < k - 1; i++) {
                for (j = i + 1; j < k; j++) {
                    adjacencyMatrix[randomValues2[i]][randomValues2[j]] = 0;
                    adjacencyMatrix[randomValues2[j]][randomValues2[i]] = 0;

                }
            }
        }


        for (i = 0; i < n; i++)
            adjacencyMatrix[i][i] = 0;

        //filling the rest of the matrix with random edges

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == -1) {
                    adjacencyMatrix[i][j] = (int) (Math.random() * (2 - 0) + 0);
                    adjacencyMatrix[j][i] = adjacencyMatrix[i][j];
                }
            }
        }

        int minDegree = Integer.MAX_VALUE;
        int maxDegree = Integer.MIN_VALUE;
        int degreeCount = 0;
        for (i = 0; i < n; i++) {
            int degree = 0;
            for (j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    degree++;
                    if (adjacencyMatrix[j][i] == 1) {
                        edgesCount++;
                    }
                }
            }
            if (degree < minDegree)
                minDegree = degree;
            if (degree > maxDegree)
                maxDegree = degree;
            degreeCount += degree;
        }
        edgesCount = edgesCount / 2;
        if (degreeCount == 2 * edgesCount)
            System.out.println("Sum of degree equals the double of edges number");
        else
            System.out.println("Sum of degree does not  equal the double of edges number");

        System.out.println("Number of edges = " + edgesCount);
        System.out.println("\u0394(G)= " + maxDegree);
        System.out.println("\u03B4(G)= " + minDegree);

        //pretty representation of the matrix
        long endTime = System.nanoTime();
        if(n<30_000) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    System.out.print(" \u2501");
                System.out.println();
                for (j = 0; j < n; j++)
                    System.out.printf("\u2503%d", adjacencyMatrix[i][j]);
                System.out.print("\u2503");
                System.out.println();
            }
            for (i = 0; i < n; i++)
                System.out.print(" \u2501");
        }
        else{
            System.out.printf("Application running time : %d", (endTime-startTime));
        }
    }
}
