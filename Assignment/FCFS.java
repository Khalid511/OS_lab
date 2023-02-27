package practice;

public class FCFS {

    static void findWaitingTime(String[] process, int[] burstTime) {
        int len = process.length;
        int[] waitingTime = new int[len];
        int[] turnAroundTime = new int[len];


        for (int i = 1; i < len; i++) {
            waitingTime[i] = waitingTime[i-1] + burstTime[i-1];
        }

        int totalWaitingTime = 0;
        for (int i=0; i<len; i++) {
            System.out.print(" | "+waitingTime[i]+" ---- "+(waitingTime[i]+burstTime[i])+" | ");
            totalWaitingTime+=waitingTime[i];
            //System.out.print((waitingTime[i]+burstTime[i])+" | ");
        }

        System.out.println();
        for (int i = 0; i < len; i++) {
            System.out.print("        "+process[i]+"      ");
        }

        System.out.printf("\nAverage waiting time = %.2f", 1.0*totalWaitingTime/len);

    }

    public static void main(String[] args) {
        String[] process = { "P1", "P2", "P3" };
        int[] burstTime = {24, 3, 3};
        findWaitingTime(process, burstTime);
    }
}
