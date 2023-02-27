import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BankersAlgorithm {

    static void resourceAllocation(int[][] allocation,  int[] available, int[][] need, int[] request,
                                   int threadNumber){
        int row = allocation.length;
        int col = request.length;
        boolean possibleAllocation = true;
        for(int i=0; i<col; i++) {
            if(request[i]>available[i]) {
                possibleAllocation = false;
                break;
            }
        }
        //System.out.println("Possible "+possibleAllocation);
        if(possibleAllocation) {
            for(int i=0; i<col; i++) {
                available[i]-=request[i];
                allocation[threadNumber][i]+=request[i];
                need[threadNumber][i]-=request[i];
            }
            //System.out.println("Available ss: "+Arrays.toString(available));
            ArrayList<Integer> list = safeStateSequence(allocation, available, need, request, 0);
            if(list.size()<row) {
                System.out.println("Request: "+Arrays.toString(request)+"Unsafe state");
            }
            else
                System.out.println("Request: "+Arrays.toString(request)+"Safe state. Sequence: "+list);
        }
        else
            System.out.println("Request: "+Arrays.toString(request)+"Unsafe state");

    }

    static ArrayList<Integer> safeStateSequence(int[][] allocation,  int[] available, int[][] need, int[] request,
                                                int startIndex) {
        ArrayList<Integer> list = new ArrayList<>();
        int row = allocation.length, col = available.length;
        int[] work = Arrays.copyOf(available, col);
        boolean[] finish = new boolean[row];

        int highestPossibleLoop = 0;

        for(int i=startIndex; i<row; i++) {
//            if(highestPossibleLoop>row)
//                break;
            boolean findIndex = false;
            if(!finish[i]) {
                boolean find = false;
                for(int j=0; j<col; j++) {
                    if(need[i][j]>work[j]) {
                        find = true;
                        //break;
                    }
                }
                if(!find) {
                    for(int j=0; j<col; j++) {
                        work[j] += allocation[i][j];

                    }
                    finish[i] = true;
                    list.add(i);
                    findIndex = true;
                }
            }

            if(i==row-1) {
                for (int j = 0; j < row; j++) {
                    if (!finish[j]) {
                        i = j-1;
                        //System.out.println("Index j " + j);
                        highestPossibleLoop++;
                        break;
                    }
                }
            }

        }
        return list;
        //System.out.println(list);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("resource_input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int n =5, m = 3;
        int[][] allocation = new int[n][m];
        int[][] maxNeed = new int[n][m];
        int[] available = new int[m];
        int[][] need = new int[n][m];
        int[] request = new int[m];
        int i=0, totalFiles = 0;
        String input;
        while ((input = br.readLine())!=null) {
            String[] values = input.trim().split(" ");
            if(values.length<3) {
                totalFiles++;
                i = 0;
                continue;
            }
           // System.out.println(values.length);

            if(totalFiles==0) {
                for (int j = 0; j < values.length; j++) {
                    allocation[i][j] = Integer.parseInt(values[j]);
                }
                i++;
            }
            else if(totalFiles==1) {
                for(int j=0; j<values.length; j++) {
                    maxNeed[i][j] = Integer.parseInt(values[j]);
                }
                i++;
            }
            else if(totalFiles==2) {
                for(int j=0; j<values.length; j++)
                    request[j] = Integer.parseInt(values[j]);
            }
            else {
                for(int j=0; j<values.length; j++)
                    available[j] = Integer.parseInt(values[j]);
            }
        }

        for(i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                need[i][j] = maxNeed[i][j] - allocation[i][j];
            }
        }

        ArrayList<Integer> list = safeStateSequence(allocation, available, need, new int[]{0, 1, 0}, 0);
        if(list.size()<n) {
            System.out.println("Request: "+Arrays.toString(new int[]{0, 1, 0})+" Unsafe state");
        }
        else
            System.out.println("Request: "+Arrays.toString(new int[]{0, 1, 0})+"Safe state. Sequence: "+list);
        //System.out.println("Available: "+Arrays.toString(available));
        resourceAllocation(allocation, available, need, request, 1);
        //System.out.println("Available: "+Arrays.toString(available));
        resourceAllocation(allocation, available, need, new int[]{0, 0, 2}, 0);
        //System.out.println("Available: "+Arrays.toString(available));
    }
}
