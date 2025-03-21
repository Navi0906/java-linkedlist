//Node class
class Node{
    int roll;
    int age;
    String name;
    char grade;
    Node next;

    public Node(int roll, int age, String name, char grade){
        this.roll = roll;
        this.age = age;
        this.name = name;
        this.grade = grade;

        this.next = null;
    }
}

class Student{
    private Node head;

    public Student(){
        head=null;
    }

    //Method to add student at specific position
    public void addStudent(int roll, int age, String name, char grade, int position){
        Node newNode = new Node(roll, age, name, grade);
        if(position==0 || head==null){
            newNode.next = head;
            head=newNode;

        }
        else{
            Node temp = head;
            int index = 0;
            while(temp.next!=null && index<position-1){
                temp = temp.next;
                index++;
            }
            newNode.next = temp.next;
            temp.next = newNode;
        }
    }

    //To display all students
    public void display(){
        Node temp = head;
        while(temp!=null){
            System.out.println(temp.name+" "+temp.roll+" "+temp.age+" "+temp.grade);
            temp = temp.next;
        }
    }

    //Delete record of a student
    public void deleteStudent(int roll){
        if(head == null){
            return;
        }
        if (head.roll == roll){
            head = head.next;
            return;
        }

        Node temp = head;
        while(temp.next != null && temp.next.roll != roll){
            temp=temp.next;
        }
        if(temp.next != null){
            temp.next = temp.next.next;
        }
    }

    //Traversing the list to search
    public void searchStudent(int roll){
        Node temp = head;
        while (temp!=null){
            if(temp.roll==roll){
                System.out.println(temp.name+" "+temp.roll+" "+temp.age+" "+temp.grade);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Student with Roll Number "+roll+" not found.");
    }

    //Traversing the list
    public void upgradeGrade(int roll, char newGrade){
        Node temp = head;
        while(temp != null){
            if(temp.roll==roll){
                temp.grade = newGrade;
                return;
            }
            temp = temp.next;
        }
    }
}



public class StudentRecord {
    public static void main(String[] args) {
        Student s1 = new Student();

        s1.addStudent(200, 21, "Navi", 'A', 0);
        s1.addStudent(201, 23, "Dhruv", 'D', 1);
        s1.addStudent(202, 19, "Madhav", 'B', 1);

        System.out.println("Students Record: ");
        s1.display();

        s1.deleteStudent(202);
        System.out.println("After deleting 202: ");
        s1.display();

        s1.upgradeGrade(200, 'O');
        System.out.println("After updating grade of 200");
        s1.searchStudent(200);
    }
}
