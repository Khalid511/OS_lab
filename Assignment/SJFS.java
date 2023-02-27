package practice;


import java.util.Arrays;

public class SJFS {

    static class Pair implements Comparable<Pair> {

        String process;
        int burstTime;

        Pair(String process, int burstTime) {
            this.process = process;
            this.burstTime = burstTime;
        }

        @Override
        public int compareTo(Pair pair) {
            return this.burstTime - pair.burstTime;
        }
    }

    static void findAverageTime(Pair[] pairs, int len) {
        int[] waitingTime = new int[len];
        for (int i = 1; i < len; i++) {
            waitingTime[i] = waitingTime[i-1] + pairs[i-1].burstTime;
        }

        for (int i = 0; i < len; i++) {
            System.out.print(" | "+waitingTime[i]+" ---- "+(waitingTime[i]+pairs[i].burstTime)+" |");
        }

        System.out.println();
        int totalWaitingTime = 0;
        for (int i = 0; i < len; i++) {
            System.out.print("      "+pairs[i].process+"     ");
            totalWaitingTime+=waitingTime[i];
        }
        System.out.println();
        System.out.printf("Average waiting time = %.2f\n", 1.0*totalWaitingTime/len);
    }

    public static void main(String[] args) {
        String[] process = { "P1", "P2", "P3", "P4" };
        int[] burstTime = {6, 8, 7, 3};
        int len = process.length;
        Pair[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = new Pair(process[i], burstTime[i]);
        }
        Arrays.sort(pairs);
        for (int i = 0; i < len; i++) {
            System.out.println(pairs[i].process+" "+pairs[i].burstTime);
        }
        findAverageTime(pairs, len);
    }
}
