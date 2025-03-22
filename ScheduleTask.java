public class ScheduleTask {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        scheduler.addTaskAtEnd(1, "Task A", 2, "2025-03-25");
        scheduler.addTaskAtBeginning(2, "Task B", 1, "2025-03-21");
        scheduler.addTaskAtPosition(3, "Task C", 3, "2025-03-30", 2);

        System.out.println("\n--- All Tasks ---");
        scheduler.displayAllTasks();

        System.out.println("\n--- View Current & Move Next ---");
        scheduler.viewCurrentAndMoveNext();
        scheduler.viewCurrentAndMoveNext();

        System.out.println("\n--- Search by Priority 1 ---");
        scheduler.searchByPriority(1);

        System.out.println("\n--- Remove Task with ID 3 ---");
        scheduler.removeTaskByID(3);

        System.out.println("\n--- All Tasks After Deletion ---");
        scheduler.displayAllTasks();
    }
}

class TaskNode {
    int taskID;
    String taskName;
    int priority;
    String dueDate;
    TaskNode next;

    public TaskNode(int taskID, String taskName, int priority, String dueDate) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
        this.next = null;
    }
}

class TaskScheduler {
    private TaskNode head = null;
    private TaskNode tail = null;
    private TaskNode currentTask = null;

    // Add task at the beginning
    public void addTaskAtBeginning(int id, String name, int priority, String dueDate) {
        TaskNode newNode = new TaskNode(id, name, priority, dueDate);
        if (head == null) {
            head = tail = newNode;
            tail.next = head;
        } else {
            newNode.next = head;
            head = newNode;
            tail.next = head;
        }
        currentTask = head;
    }

    // Add task at the end
    public void addTaskAtEnd(int id, String name, int priority, String dueDate) {
        TaskNode newNode = new TaskNode(id, name, priority, dueDate);
        if (head == null) {
            head = tail = newNode;
            tail.next = head;
        } else {
            tail.next = newNode;
            newNode.next = head;
            tail = newNode;
        }
        currentTask = head;
    }

    // Add task at a specific position (1-based index)
    public void addTaskAtPosition(int id, String name, int priority, String dueDate, int position) {
        if (position <= 1 || head == null) {
            addTaskAtBeginning(id, name, priority, dueDate);
            return;
        }

        TaskNode temp = head;
        int index = 1;
        while (temp.next != head && index < position - 1) {
            temp = temp.next;
            index++;
        }

        TaskNode newNode = new TaskNode(id, name, priority, dueDate);
        newNode.next = temp.next;
        temp.next = newNode;
        if (temp == tail) {
            tail = newNode;
        }
        currentTask = head;
    }

    // Remove task by Task ID
    public void removeTaskByID(int id) {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }

        TaskNode temp = head, prev = tail;
        do {
            if (temp.taskID == id) {
                if (temp == head) {
                    head = head.next;
                    tail.next = head;
                } else {
                    prev.next = temp.next;
                    if (temp == tail) {
                        tail = prev;
                    }
                }
                System.out.println("Task with ID " + id + " removed.");
                if (currentTask == temp) {
                    currentTask = head;
                }
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != head);

        System.out.println("Task with ID " + id + " not found.");
    }

    // View current task and move to the next
    public void viewCurrentAndMoveNext() {
        if (currentTask != null) {
            displayTask(currentTask);
            currentTask = currentTask.next;
        } else {
            System.out.println("No tasks available.");
        }
    }

    // Display all tasks starting from head
    public void displayAllTasks() {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }

        TaskNode temp = head;
        do {
            displayTask(temp);
            temp = temp.next;
        } while (temp != head);
    }

    // Search tasks by Priority
    public void searchByPriority(int priority) {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }

        boolean found = false;
        TaskNode temp = head;
        do {
            if (temp.priority == priority) {
                displayTask(temp);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);

        if (!found) {
            System.out.println("No tasks found with priority " + priority);
        }
    }

    private void displayTask(TaskNode node) {
        System.out.println("ID: " + node.taskID + ", Name: " + node.taskName + ", Priority: " + node.priority + ", Due: " + node.dueDate);
    }
}


