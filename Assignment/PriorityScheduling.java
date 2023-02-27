package practice;

import java.util.ArrayList;
import java.util.Arrays;

public class PriorityScheduling {

    static class Tuple implements Comparable<Tuple> {

        String process;
        int burstTime, priority;

        Tuple(String process, int burstTime, int priority) {
            this.process = process;
            this.burstTime = burstTime;
            this.priority = priority;
        }

        @Override
        public int compareTo(Tuple tuple) {
            return this.priority - tuple.priority;
        }
    }

    static void findAverageTime(Tuple[] tuples, int len) {
        int[] waitingTime = new int[len];
        int[] turnAroundTime = new int[len];
        waitingTime[0] = 0;
        for (int i = 1; i < len; i++) {
            System.out.print(" | "+waitingTime[i-1]);
            waitingTime[i] = waitingTime[i-1]  + tuples[i-1].burstTime;
            System.out.print(" ---- "+waitingTime[i]+" |");
        }
        System.out.print(" | "+waitingTime[len-1]);
        System.out.print(" ---- "+(waitingTime[len-1]+tuples[len-1].burstTime)+" |");
        System.out.println();


        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        for (int i = 0; i < len; i++) {
            turnAroundTime[i] = waitingTime[i] + tuples[i].burstTime;
            totalTurnAroundTime+=turnAroundTime[i];
            totalWaitingTime+=waitingTime[i];
            System.out.print("      "+tuples[i].process+"     ");
        }

        System.out.println();
        System.out.println("Average waiting time = "+String.format("%.2f",1.0*totalWaitingTime/len)+"\nAverage Turn Around Time = " +
                String.format("%.2f",1.0*totalTurnAroundTime/len));

    }

    public static void main(String[] args) {
        String[] process = {"P1", "P2", "P3", "P4", "P5"};
        int[] burstTime = {10, 1, 2, 1, 5};
        int[] priority = {3, 1, 4, 5, 2};
        int len = process.length;
        Tuple[] tuples = new Tuple[len];
        for(int i=0; i<len; i++) {
            tuples[i] = new Tuple(process[i], burstTime[i], priority[i]);
        }
        Arrays.sort(tuples);
        findAverageTime(tuples, len);
    }
}
