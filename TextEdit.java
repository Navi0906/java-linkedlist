public class TextEdit {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        editor.addState("Hello");
        editor.addState("Hello World");
        editor.addState("Hello World!");
        editor.displayCurrentState();

        editor.undo();
        editor.displayCurrentState();

        editor.undo();
        editor.displayCurrentState();

        editor.redo();
        editor.displayCurrentState();

        editor.addState("Hello Java!");
        editor.displayCurrentState();

        editor.undo();
        editor.displayCurrentState();


    }
}

class TextStateNode {
    String textContent;
    TextStateNode next;
    TextStateNode prev;

    public TextStateNode(String textContent) {
        this.textContent = textContent;
        this.next = null;
        this.prev = null;
    }
}

class TextEditor {
    private TextStateNode head = null;
    private TextStateNode tail = null;
    private TextStateNode current = null;
    private int size = 0;
    private final int MAX_HISTORY = 10;

    // Add a new text state (simulating typing or an action)
    public void addState(String newText) {
        TextStateNode newNode = new TextStateNode(newText);

        // If user types after undo, discard all "redo" states
        if (current != tail) {
            TextStateNode temp = current.next;
            while (temp != null) {
                TextStateNode nextNode = temp.next;
                temp = null;
                temp = nextNode;
                size--;
            }
            current.next = null;
            tail = current;
        }

        // Append new state
        if (head == null) {
            head = tail = current = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            current = tail;
        }

        size++;

        // If exceeds max history, remove the oldest
        if (size > MAX_HISTORY) {
            head = head.next;
            head.prev = null;
            size--;
        }
    }

    // Undo functionality
    public void undo() {
        if (current != null && current.prev != null) {
            current = current.prev;
            System.out.println("Undo performed.");
        } else {
            System.out.println("No more undo available.");
        }
    }

    // Redo functionality
    public void redo() {
        if (current != null && current.next != null) {
            current = current.next;
            System.out.println("Redo performed.");
        } else {
            System.out.println("No more redo available.");
        }
    }

    // Display current state of the text
    public void displayCurrentState() {
        if (current != null) {
            System.out.println("Current Text: " + current.textContent);
        } else {
            System.out.println("Text editor is empty.");
        }
    }


    public void displayHistory() {
        TextStateNode temp = head;
        System.out.println("\n--- History ---");
        while (temp != null) {
            System.out.println(temp.textContent + (temp == current ? " <-- Current" : ""));
            temp = temp.next;
        }
    }
}


