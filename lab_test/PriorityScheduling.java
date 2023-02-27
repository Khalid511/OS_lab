import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class PriorityScheduling {

    static class Triple implements Comparable<Triple> {

        String process;
        int burstTime, priority;
        Triple(String process, int burstTime, int priority) {
            this.process = process;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        @Override
        public int compareTo(Triple o) {
            return this.priority - o.priority;
        }
    }

    static void findWaitingTime(Triple[] triples, int len) {
        int[] waitingTime = new int[len];
        int[] turnAroundTime = new int[len];
        for (int i=1; i<len; i++) {
            waitingTime[i] = waitingTime[i-1] + triples[i-1].burstTime;
        }

        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        System.out.println("Gantt Chart:");
        for (int i = 0; i < len; i++) {
            turnAroundTime[i] = waitingTime[i]+triples[i].burstTime;
            System.out.print(" | "+waitingTime[i]+" ---- "+turnAroundTime[i]+" |");
            totalWaitingTime+=waitingTime[i];
            totalTurnAroundTime+=turnAroundTime[i];
        }
        System.out.println();
        for (int i = 0; i < len; i++) {
            System.out.print("      "+triples[i].process+"      ");
        }
        System.out.println();
        System.out.printf("Average waiting time = %.2f\n", 1.0*totalWaitingTime/len);
        System.out.printf("Average turn around time = %.2f\n", 1.0*totalTurnAroundTime/len);

    }
    public static void main(String[] args){
        String[] process = {"P1", "P2", "P3", "P4", "P5"};
        int[] burstTime = {10, 1, 2, 1, 5};
        int[] priority = {3, 1, 4, 5, 2};
        int len = process.length;
        Triple[] triples = new Triple[len];
        for (int i = 0; i < len; i++) {
            triples[i] = new Triple(process[i], burstTime[i], priority[i]);
        }
        Arrays.sort(triples);
        findWaitingTime(triples, len);
    }
}

