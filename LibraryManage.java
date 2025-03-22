public class LibraryManage {
    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();

        lms.addBookAtEnd("The Alchemist", "Paulo Coelho", "Fiction", 101, true);
        lms.addBookAtBeginning("Clean Code", "Robert C. Martin", "Programming", 102, true);
        lms.addBookAtPosition("1984", "George Orwell", "Dystopian", 103, false, 2);

        System.out.println("\n--- Display Forward ---");
        lms.displayForward();

        System.out.println("\n--- Display Reverse ---");
        lms.displayReverse();

        System.out.println("\n--- Search by Title: 1984 ---");
        lms.searchByTitle("1984");

        System.out.println("\n--- Search by Author: Paulo Coelho ---");
        lms.searchByAuthor("Paulo Coelho");

        System.out.println("\n--- Update Availability for Book ID 101 ---");
        lms.updateAvailability(101, false);

        System.out.println("\n--- Remove Book with ID 103 ---");
        lms.removeBookByID(103);

        System.out.println("\n--- Display Forward After Deletion ---");
        lms.displayForward();

        System.out.println("\n--- Count Total Books ---");
        lms.countBooks();
    }
}

class BookNode {
    String title;
    String author;
    String genre;
    int bookID;
    boolean isAvailable;
    BookNode next;
    BookNode prev;

    public BookNode(String title, String author, String genre, int bookID, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.next = null;
        this.prev = null;
    }
}

class LibraryManagementSystem {
    BookNode head = null;
    BookNode tail = null;

    // Add book at the beginning
    public void addBookAtBeginning(String title, String author, String genre, int bookID, boolean isAvailable) {
        BookNode newNode = new BookNode(title, author, genre, bookID, isAvailable);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Add book at the end
    public void addBookAtEnd(String title, String author, String genre, int bookID, boolean isAvailable) {
        BookNode newNode = new BookNode(title, author, genre, bookID, isAvailable);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Add book at a specific position (1-based index)
    public void addBookAtPosition(String title, String author, String genre, int bookID, boolean isAvailable, int position) {
        if (position <= 1) {
            addBookAtBeginning(title, author, genre, bookID, isAvailable);
            return;
        }

        BookNode temp = head;
        int index = 1;
        while (temp != null && index < position - 1) {
            temp = temp.next;
            index++;
        }

        if (temp == null || temp.next == null) {
            addBookAtEnd(title, author, genre, bookID, isAvailable);
        } else {
            BookNode newNode = new BookNode(title, author, genre, bookID, isAvailable);
            newNode.next = temp.next;
            newNode.prev = temp;
            temp.next.prev = newNode;
            temp.next = newNode;
        }
    }

    // Remove book by Book ID
    public void removeBookByID(int bookID) {
        BookNode temp = head;
        while (temp != null) {
            if (temp.bookID == bookID) {
                if (temp == head) {
                    head = head.next;
                    if (head != null) head.prev = null;
                } else if (temp == tail) {
                    tail = tail.prev;
                    if (tail != null) tail.next = null;
                } else {
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                }
                System.out.println("Book with ID " + bookID + " removed.");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Book with ID " + bookID + " not found.");
    }

    // Search by Book Title
    public void searchByTitle(String title) {
        BookNode temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) {
                displayBook(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No book found with title: " + title);
    }

    // Search by Author
    public void searchByAuthor(String author) {
        BookNode temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.author.equalsIgnoreCase(author)) {
                displayBook(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No books found by author: " + author);
    }

    // Update book availability by Book ID
    public void updateAvailability(int bookID, boolean isAvailable) {
        BookNode temp = head;
        while (temp != null) {
            if (temp.bookID == bookID) {
                temp.isAvailable = isAvailable;
                System.out.println("Availability updated for Book ID " + bookID);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Book with ID " + bookID + " not found.");
    }

    // Display all books forward
    public void displayForward() {
        BookNode temp = head;
        if (temp == null) {
            System.out.println("No books to display.");
            return;
        }
        while (temp != null) {
            displayBook(temp);
            temp = temp.next;
        }
    }

    // Display all books reverse
    public void displayReverse() {
        BookNode temp = tail;
        if (temp == null) {
            System.out.println("No books to display.");
            return;
        }
        while (temp != null) {
            displayBook(temp);
            temp = temp.prev;
        }
    }

    // Count total books
    public void countBooks() {
        BookNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        System.out.println("Total number of books in library: " + count);
    }

    private void displayBook(BookNode node) {
        System.out.println("ID: " + node.bookID + ", Title: " + node.title + ", Author: " + node.author + ", Genre: " + node.genre + ", Available: " + (node.isAvailable ? "Yes" : "No"));
    }
}


