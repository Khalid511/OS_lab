import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TransferQueue;

public class PRWithArrivalTime {

    static class Triple {
        String process;
        int start, end;
        Triple(String process, int start, int end) {
            this.process = process;
            this.start = start;
            this.end = end;
        }
    }

    static void findWaitingTime(String[] process, int[] burstTime, int[] priority, int[] arrivalTime) {
        int len = process.length;;
        int[] waitingTime = new int[len];
        int[] remainingTime = Arrays.copyOf(burstTime, len);
        ArrayList<Triple> ganttChart = new ArrayList<>();

        int time = 0, finishTime = 0, completeProcess = 0, highPriority = Integer.MAX_VALUE;
        int highIndex = 0;
        boolean findHighPriority = false;
        int previousTime = 0;
        while (completeProcess<len) {
            for (int i = 0; i < len; i++) {
                if(arrivalTime[i]<=time && remainingTime[i]>0 && priority[i]<highPriority) {
                    highPriority = priority[i];
                    highIndex = i;
                    findHighPriority = true;
                }
            }

            if(!findHighPriority) {
                time++;
                continue;
            }

            //System.out.println("Index "+highIndex);
            if(ganttChart.size()>0) {
                String getProcess = ganttChart.get(ganttChart.size()-1).process;
                if(getProcess.equals(process[highIndex]))
                    ganttChart.get(ganttChart.size()-1).end++;
                else {
                    ganttChart.add(new Triple(process[highIndex], time, time+1));
                }
            }
            else
                ganttChart.add(new Triple(process[highIndex], time, time+1));
            remainingTime[highIndex]--;
            if(remainingTime[highIndex]==0) {
                completeProcess++;
                highPriority = Integer.MAX_VALUE;
                finishTime = time+1;
                waitingTime[highIndex] = finishTime - burstTime[highIndex]; // - arrivalTime[highIndex] + previousTime;
                previousTime = finishTime;
            }
            time++;
        }

        int totalWaitingTime = 0;


        for (int i = 0; i < len; i++) {
            totalWaitingTime+=waitingTime[i];
        }
        System.out.printf("Average waiting time = %.2f\n", 1.0*totalWaitingTime/len);

        for (int i = 0; i < len; i++) {
            System.out.println(process[i]+" "+waitingTime[i]+" "+(waitingTime[i]+burstTime[i]));
        }

        for (Triple triple: ganttChart) {
            System.out.print(" | "+triple.start+" ---- "+triple.end+" |");
        }
        System.out.println();
        for (Triple triple: ganttChart) {
            System.out.print("      " +triple.process+"     ");
        }
    }
    public static void main(String[] args) {
        String[] process = {"P1", "P2", "P3", "P4", "P5"};
        int[] burstTime = {10, 1, 2, 1, 5};
        int[] priority = {3, 1, 4, 5, 2};
        int[] arrivalTime = {1, 0, 2, 1, 2};
        findWaitingTime(process, burstTime, priority, arrivalTime);
    }
}
