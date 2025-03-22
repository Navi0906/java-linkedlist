public class MovieManage {
    public static void main(String[] args) {
        MovieManagementSystem mms = new MovieManagementSystem();

        mms.addMovieAtEnd("Inception", "Christopher Nolan", 2010, 8.8);
        mms.addMovieAtBeginning("The Godfather", "Francis Ford Coppola", 1972, 9.2);
        mms.addMovieAtPosition("Interstellar", "Christopher Nolan", 2014, 8.6, 2);

        System.out.println("\n--- Display Forward ---");
        mms.displayForward();

        System.out.println("\n--- Display Reverse ---");
        mms.displayReverse();

        System.out.println("\n--- Search by Director: Christopher Nolan ---");
        mms.searchByDirector("Christopher Nolan");

        System.out.println("\n--- Search by Rating: 9.2 ---");
        mms.searchByRating(9.2);

        System.out.println("\n--- Update Rating for Inception ---");
        mms.updateRating("Inception", 9.0);

        System.out.println("\n--- Remove Movie: Interstellar ---");
        mms.removeMovieByTitle("Interstellar");

        System.out.println("\n--- Display Forward After Deletion ---");
        mms.displayForward();
    }
}

class MovieNode {
    String title;
    String director;
    int year;
    double rating;
    MovieNode next;
    MovieNode prev;

    public MovieNode(String title, String director, int year, double rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.next = null;
        this.prev = null;
    }
}

class MovieManagementSystem {
    MovieNode head = null;
    MovieNode tail = null;

    // Add movie at the beginning
    public void addMovieAtBeginning(String title, String director, int year, double rating) {
        MovieNode newNode = new MovieNode(title, director, year, rating);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Add movie at the end
    public void addMovieAtEnd(String title, String director, int year, double rating) {
        MovieNode newNode = new MovieNode(title, director, year, rating);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Add movie at a specific position (1-based index)
    public void addMovieAtPosition(String title, String director, int year, double rating, int position) {
        if (position <= 1) {
            addMovieAtBeginning(title, director, year, rating);
            return;
        }

        MovieNode temp = head;
        int index = 1;
        while (temp != null && index < position - 1) {
            temp = temp.next;
            index++;
        }

        if (temp == null || temp.next == null) {
            addMovieAtEnd(title, director, year, rating);
        } else {
            MovieNode newNode = new MovieNode(title, director, year, rating);
            newNode.next = temp.next;
            newNode.prev = temp;
            temp.next.prev = newNode;
            temp.next = newNode;
        }
    }

    // Remove movie by title
    public void removeMovieByTitle(String title) {
        MovieNode temp = head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) {
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
                System.out.println("Movie \"" + title + "\" removed.");
                return;
            }
            temp = temp.next;
        }
        System.out.println("Movie \"" + title + "\" not found.");
    }

    // Search by Director
    public void searchByDirector(String director) {
        MovieNode temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.director.equalsIgnoreCase(director)) {
                displayMovie(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No movies found by Director: " + director);
    }

    // Search by Rating
    public void searchByRating(double rating) {
        MovieNode temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.rating == rating) {
                displayMovie(temp);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("No movies found with Rating: " + rating);
    }

    // Display all movies forward
    public void displayForward() {
        MovieNode temp = head;
        if (temp == null) {
            System.out.println("No movies to display.");
            return;
        }
        while (temp != null) {
            displayMovie(temp);
            temp = temp.next;
        }
    }

    // Display all movies reverse
    public void displayReverse() {
        MovieNode temp = tail;
        if (temp == null) {
            System.out.println("No movies to display.");
            return;
        }
        while (temp != null) {
            displayMovie(temp);
            temp = temp.prev;
        }
    }

    // Update movie rating by title
    public void updateRating(String title, double newRating) {
        MovieNode temp = head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) {
                temp.rating = newRating;
                System.out.println("Rating updated for \"" + title + "\" to " + newRating);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Movie \"" + title + "\" not found.");
    }

    private void displayMovie(MovieNode node) {
        System.out.println("Title: " + node.title + ", Director: " + node.director + ", Year: " + node.year + ", Rating: " + node.rating);
    }
}


