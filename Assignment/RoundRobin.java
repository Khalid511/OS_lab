package practice;

import java.util.ArrayList;
import java.util.Arrays;

public class RoundRobin {

    static void findAverageTime(String[] process, int[] burstTime, int timeQuantum) {
        int len = process.length;
        int[] waitingTime = new int[len];
        int[] turnAroundTime = new int[len];
        int[] remainingTime = Arrays.copyOf(burstTime, len);
        int time = 0;
        ArrayList<String> processList = new ArrayList<>();
        while (true) {
            boolean check = true;
            for (int i = 0; i < len; i++) {
                if(remainingTime[i] > 0) {
                    System.out.print(" | " + time + " ");
                    check = false;
                    if (remainingTime[i] > timeQuantum) {
                        System.out.print("----");
                        processList.add(process[i]);
                        time += timeQuantum;
                        remainingTime[i] -= timeQuantum;
                        System.out.print(" " + time + " |");
                    }

                    else {
                        //System.out.print(" | " + time + " ");
                        System.out.print("----");
                        processList.add(process[i]);
                        time += remainingTime[i];
                        remainingTime[i] = 0;
                        waitingTime[i] = time-burstTime[i];
                        System.out.print(" " + time + " |");
                    }
                }

            }
            if(check)
                break;
        }

        for(String str: processList) {
            System.out.print("       "+str+"      ");
        }
        System.out.println();

        int totalWaitingTime = 0, totalTurnAroundTime = 0;
        for(int i=0; i<len; i++) {
            turnAroundTime[i] = waitingTime[i]+burstTime[i];
            totalTurnAroundTime+=turnAroundTime[i];
            totalWaitingTime+=waitingTime[i];
        }

        System.out.println("Average waiting time = "+String.format("%.2f",1.0*totalWaitingTime/len)+"\nAverage Turn Around Time = " +
                String.format("%.2f",1.0*totalTurnAroundTime/len));
    }

    public static void main(String[] args) {
        String[] process = { "P1", "P2", "P3" };
        int[] burstTime = {24, 3, 3};
        int timeQuantum = 4;
        findAverageTime(process, burstTime, timeQuantum);
    }
}
