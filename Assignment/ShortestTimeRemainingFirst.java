package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ShortestTimeRemainingFirst {

//    static class Tuple implements Comparable<Tuple> {
//
//        String process;
//        int burstTime, arrivalTime, remainingTime;
//
//        Tuple(String process, int burstTime, int arrivalTime) {
//            this.process = process;
//            this.burstTime = burstTime;
//            this.arrivalTime = arrivalTime;
//            this.remainingTime = burstTime;
//        }
//
//        @Override
//        public int compareTo(Tuple tuple) {
//            if(tuple.remainingTime == this.remainingTime)
//                return this.arrivalTime - tuple.arrivalTime;
//            return this.remainingTime - tuple.remainingTime;
//        }
//    }

    static class Triple {
        String process;
        int start, end;
        Triple(String process, int start, int end) {
            this.process = process;
            this.start = start;
            this.end = end;
        }
    }

    static void findAverageWaitingTime(String[] process, int[] burstTime, int[] arrivalTime) {
        int len = process.length;
        int[] waitingTime = new int[len];
        int[] turnAroundTime = new int[len];
        int[] remainingTime = Arrays.copyOf(burstTime, len);

        int completeProcess = 0, time = 0, shortestIndex = 0;
        int minTime = Integer.MAX_VALUE, finishTime = 0;
        boolean findMinVal = false;
        ArrayList<String> processList = new ArrayList<>();
        ArrayList<Triple> timeWithProcess = new ArrayList<>();
        //timeWithProcess.add(new Triple("", 0, 0));
        int startTime = 0;
        while (completeProcess<len) {
            for (int i = 0; i < len; i++) {
                if(arrivalTime[i]<=time && remainingTime[i]>0 && remainingTime[i]<minTime) {
                    minTime = remainingTime[i];
                    shortestIndex = i;
                    findMinVal = true;
                }
            }

            if(!findMinVal) {
                time++;
                //System.out.print("-");
                continue;
            }


            //System.out.print(" | "+(waitingTime[shortestIndex]+time)+" --- "+(time+1)+" |");
            if(timeWithProcess.size()>0) {
                String getProcess = timeWithProcess.get(timeWithProcess.size()-1).process;
                if(getProcess.equals(process[shortestIndex])) {
                    timeWithProcess.get(timeWithProcess.size()-1).end++;
                }
                else {
                    startTime = waitingTime[shortestIndex]+time;
                    timeWithProcess.add(new Triple(process[shortestIndex], startTime, time+1));
                }
            }
            else {
                startTime = waitingTime[shortestIndex]+time;
                timeWithProcess.add(new Triple(process[shortestIndex], startTime, time+1));
            }
            remainingTime[shortestIndex]--;
            minTime = remainingTime[shortestIndex];
            //processList.add(process[shortestIndex]);
            if(minTime==0) {
                minTime = Integer.MAX_VALUE;
                finishTime = time+1;
                waitingTime[shortestIndex] = finishTime - burstTime[shortestIndex] - arrivalTime[shortestIndex];
                completeProcess++;
                if(waitingTime[shortestIndex]<0)
                    waitingTime[shortestIndex] = 0;
            }

            time++;
        }

//        System.out.println();
//
//        for(String str: processList) {
//            System.out.print("   "+str+"   ");
//        }
        System.out.println();
        System.out.println("Array = "+timeWithProcess.size());
        for (Triple triple: timeWithProcess) {
            System.out.print(" | "+triple.start+" ---- "+triple.end+" |");
        }
        System.out.println();
        for (Triple triple: timeWithProcess) {
            System.out.print("        "+triple.process+"   ");
        }
        System.out.println();

    }

    public static void main(String[] args) {
        String[] process = { "P1", "P2", "P3", "P4" };
        int[] burstTime = {8, 4, 9, 5};
        int[] arrivalTime = {0, 1, 2, 3};
        int len = process.length;
        findAverageWaitingTime(process, burstTime, arrivalTime);
    }
}
