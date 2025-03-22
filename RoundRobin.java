public class RoundRobin {
    public static void main(String[] args) {
        RoundRobinScheduler scheduler = new RoundRobinScheduler();

        scheduler.addProcess(1, 10, 1);
        scheduler.addProcess(2, 5, 2);
        scheduler.addProcess(3, 8, 1);

        scheduler.simulate();
    }
}

class ProcessNode {
    int processID;
    int burstTime;
    int priority;
    int remainingTime;
    int waitingTime = 0;
    int turnaroundTime = 0;
    ProcessNode next;

    public ProcessNode(int processID, int burstTime, int priority) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}

class RoundRobinScheduler {
    private ProcessNode head = null;
    private ProcessNode tail = null;
    private final int TIME_QUANTUM = 4;

    // Add a new process at the end
    public void addProcess(int id, int burst, int priority) {
        ProcessNode newNode = new ProcessNode(id, burst, priority);
        if (head == null) {
            head = tail = newNode;
            tail.next = head;
        } else {
            tail.next = newNode;
            newNode.next = head;
            tail = newNode;
        }
    }

    // Remove a process after execution
    private void removeProcess(ProcessNode process) {
        if (head == null) return;

        ProcessNode temp = head;
        ProcessNode prev = tail;
        do {
            if (temp == process) {
                if (temp == head) {
                    head = head.next;
                    tail.next = head;
                } else {
                    prev.next = temp.next;
                    if (temp == tail) {
                        tail = prev;
                    }
                }
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);
    }

    // Simulate Round Robin Scheduling
    public void simulate() {
        if (head == null) {
            System.out.println("No processes to schedule.");
            return;
        }

        int time = 0;
        ProcessNode current = head;
        int totalProcesses = countProcesses();

        while (totalProcesses > 0) {
            if (current.remainingTime > 0) {
                int execTime = Math.min(TIME_QUANTUM, current.remainingTime);
                current.remainingTime -= execTime;
                time += execTime;

                // Update turnaround and waiting time for all active processes
                ProcessNode temp = head;
                do {
                    if (temp != current && temp.remainingTime > 0) {
                        temp.waitingTime += execTime;
                    }
                    temp = temp.next;
                } while (temp != head);

                if (current.remainingTime == 0) {
                    current.turnaroundTime = time;
                    removeProcess(current);
                    totalProcesses--;
                }
            }
            current = current.next;
            displayQueue();
        }
        displayMetrics();
    }

    // Display processes in queue
    private void displayQueue() {
        if (head == null) return;
        System.out.println("Current Queue:");
        ProcessNode temp = head;
        do {
            System.out.print("[P" + temp.processID + "|Remaining:" + temp.remainingTime + "] ");
            temp = temp.next;
        } while (temp != head);
        System.out.println("\n");
    }

    // Count total processes
    private int countProcesses() {
        if (head == null) return 0;
        int count = 0;
        ProcessNode temp = head;
        do {
            count++;
            temp = temp.next;
        } while (temp != head);
        return count;
    }

    // Display average waiting and turnaround time
    private void displayMetrics() {
        int totalWT = 0, totalTAT = 0, count = 0;
        ProcessNode temp = head;
        do {
            totalWT += temp.waitingTime;
            totalTAT += temp.turnaroundTime;
            count++;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Average Waiting Time: " + (totalWT / (double) count));
        System.out.println("Average Turnaround Time: " + (totalTAT / (double) count));
    }
}


