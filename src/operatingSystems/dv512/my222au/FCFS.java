package operatingSystems.dv512.my222au;//package operatingSystems.dv512.my222au;/*
// * File:	operatingSystems.dv512.my222au.Process.java
// * Course: 	Operating Systems
// * Code: 	1DV512
// * Author: 	Suejb Memeti
// * Date: 	November, 2016
// */

import java.util.ArrayList;

public class FCFS {
    private int mWatingTime;
    private int mCompleteTime;
    private int mTATTime;
    private int mStartTime;


    // The list of processes to be scheduled
    public ArrayList<Process> processes;


    // Class constructor
    public FCFS(ArrayList<Process> processes) {
        this.processes = processes;
    }


    public void run() {
        mWatingTime = 0;
        mCompleteTime = 0;
        mTATTime = 0;
        mStartTime = 0;  // this time gets increased  with the brustTime and is the startTime for every procsess depending on
        // last procces burstTime


        for (int i = 0; i < processes.size(); i++) {
            // since  in  some tests  like Test 5 for some processes the arival time
            // is greater than mStarttime so this case is handled here. We are increasing by diffrence
            if (SortArrivalTime(processes).get(i).getArrivalTime() > mStartTime) {

                mStartTime += (SortArrivalTime(processes).get(i).getArrivalTime() - mStartTime);
            }

            mWatingTime = mStartTime - SortArrivalTime(processes).get(i).getArrivalTime();
            // for cases when the waitingtime is negativ
            if (mWatingTime < 0) {
                mWatingTime = 0;
            }

            mCompleteTime = ((processes.get(i).getArrivalTime() + processes.get(i).getBurstTime()) + mWatingTime);

            mTATTime = mCompleteTime - processes.get(i).getArrivalTime();

            //System.out.println(" starttime: " + mStartTime + " At: " + SortArrivalTime(processes).get(i).getArrivalTime() + " Bt " +
            //processes.get(i).getBurstTime());

            processes.get(i).setCompletedTime(mCompleteTime);
            processes.get(i).setTurnaroundTime(mTATTime);
            processes.get(i).setWaitingTime(mWatingTime);
            mStartTime += processes.get(i).getBurstTime();
        }

    }


    // Selection sort to sort by Arival time by order

    private ArrayList<Process> SortArrivalTime(ArrayList<Process> arry) {
        for (int i = 0; i < arry.size() - 1; i++) {
            for (int j = i + 1; j < arry.size(); j++) {
                if (arry.get(i).getArrivalTime() > arry.get(j).getArrivalTime()) {
                    Process temp = arry.get(j);
                    arry.set(j, arry.get(i));
                    arry.set(i, temp);
                }
            }

        }
        return arry;
    }


    public void printTable() {
        System.out.format("-----------------------------------------------------------------------------------------%n");
        System.out.format("| Process ID  | Arival Time | Brust Time | Complete Time | Trunaround Time | Wating Time |%n");
        System.out.format("-----------------------------------------------------------------------------------------%n");

        for (int i = 0; i < processes.size(); i++) {
            System.out.format("|  %-11s| %-11s | %-11s| %-11s|    %-16s| %-12s|%n",
                    processes.get(i).getProcessId(), processes.get(i).getArrivalTime(),
                    processes.get(i).getBurstTime(), processes.get(i).getCompletedTime(),
                    processes.get(i).getTurnaroundTime(), processes.get(i).getWaitingTime());
        }
        System.out.format("-----------------------------------------------------------------------------------------%n");


    }

    public void printGanttChart() {
        int brustTime = 0;
        System.out.printf("[");
        for (Process p : processes) {
            System.out.print("--------------");
        }
        System.out.print("]");
        System.out.println("");


        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < processes.size(); i++) {
            brustTime = processes.get(i).getBurstTime();
            sb.setLength(0);
            for (int s = 0; s < brustTime; s++) {
                sb.append(" ");
            }
            System.out.printf(sb.toString());
            System.out.format("%s|", "P" + processes.get(i).getProcessId());


        }

        System.out.println("");
        System.out.printf("[");

        for (Process p : processes) {
            System.out.print("--------------");
        }

        System.out.printf("]");
        System.out.println(" ");

        StringBuilder sb2 = new StringBuilder();
        for (int j = 0; j < processes.size(); j++) {
            brustTime = processes.get(j).getBurstTime();
            sb2.setLength(0);
            for (int s = 0; s < brustTime; s++) {  // add spaces to the chart with brustTime
                sb2.append(" ");
            }

            int excTime;  // exctuion time
            if (j == 0) {
                System.out.printf("%d", 0);
            }

            System.out.printf(" %s", sb2.toString());
            if (j > 0) {
                excTime = (processes.get(j - 1).completedTime + processes.get(j).getBurstTime());

            } else {
                excTime = processes.get(j).getCompletedTime();
            }
            System.out.printf("%d ", excTime);
        }
        System.out.println(" ");
    }

}


