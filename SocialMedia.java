import java.util.*;

public class SocialMedia{
    public static void main(String[] args) {
        SocialMediaManager smm = new SocialMediaManager();

        smm.addUser(1, "Navi", 22);
        smm.addUser(2, "Madhav", 25);
        smm.addUser(3, "Dhruv", 21);
        smm.addUser(4, "Pranav", 20);

        smm.addFriend(1, 2);
        smm.addFriend(1, 3);
        smm.addFriend(2, 3);
        smm.addFriend(2, 4);

        smm.displayFriends(1);
        smm.displayFriends(2);

        smm.findMutualFriends(1, 2);

        smm.removeFriend(1, 2);

        smm.displayFriends(1);
        smm.displayFriends(2);

        smm.searchUser("Navi");
        smm.searchUser("3");

        smm.countFriends();
    }
}

class FriendNode {
    int friendId;
    FriendNode next;

    public FriendNode(int friendId) {
        this.friendId = friendId;
        this.next = null;
    }
}

class UserNode {
    int userId;
    String name;
    int age;
    FriendNode friendList;
    UserNode next;

    public UserNode(int userId, String name, int age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.friendList = null;
        this.next = null;
    }
}

class SocialMediaManager {
    private UserNode head;

    // Add new user
    public void addUser(int userId, String name, int age) {
        UserNode newUser = new UserNode(userId, name, age);
        newUser.next = head;
        head = newUser;
    }

    // Add a friend connection between two users
    public void addFriend(int userId1, int userId2) {
        UserNode user1 = findUser(userId1);
        UserNode user2 = findUser(userId2);

        if (user1 != null && user2 != null) {
            addFriendToList(user1, userId2);
            addFriendToList(user2, userId1);
        }
    }

    private void addFriendToList(UserNode user, int friendId) {
        if (!isFriend(user.friendList, friendId)) {
            FriendNode newFriend = new FriendNode(friendId);
            newFriend.next = user.friendList;
            user.friendList = newFriend;
        }
    }

    private boolean isFriend(FriendNode friendList, int friendId) {
        FriendNode temp = friendList;
        while (temp != null) {
            if (temp.friendId == friendId) return true;
            temp = temp.next;
        }
        return false;
    }

    // Remove friend connection
    public void removeFriend(int userId1, int userId2) {
        UserNode user1 = findUser(userId1);
        UserNode user2 = findUser(userId2);

        if (user1 != null && user2 != null) {
            user1.friendList = removeFromFriendList(user1.friendList, userId2);
            user2.friendList = removeFromFriendList(user2.friendList, userId1);
        }
    }

    private FriendNode removeFromFriendList(FriendNode head, int friendId) {
        if (head == null) return null;
        if (head.friendId == friendId) return head.next;
        head.next = removeFromFriendList(head.next, friendId);
        return head;
    }

    // Find mutual friends
    public void findMutualFriends(int userId1, int userId2) {
        UserNode user1 = findUser(userId1);
        UserNode user2 = findUser(userId2);

        if (user1 != null && user2 != null) {
            System.out.print("Mutual friends between " + userId1 + " and " + userId2 + ": ");
            FriendNode temp1 = user1.friendList;
            while (temp1 != null) {
                if (isFriend(user2.friendList, temp1.friendId)) {
                    System.out.print(temp1.friendId + " ");
                }
                temp1 = temp1.next;
            }
            System.out.println();
        }
    }

    // Display all friends of a user
    public void displayFriends(int userId) {
        UserNode user = findUser(userId);
        if (user != null) {
            System.out.print("Friends of User " + userId + ": ");
            FriendNode temp = user.friendList;
            while (temp != null) {
                System.out.print(temp.friendId + " ");
                temp = temp.next;
            }
            System.out.println();
        }
    }

    // Search user by Name or ID
    public void searchUser(String nameOrId) {
        UserNode temp = head;
        while (temp != null) {
            if (String.valueOf(temp.userId).equals(nameOrId) || temp.name.equalsIgnoreCase(nameOrId)) {
                System.out.println("User Found: ID = " + temp.userId + ", Name = " + temp.name + ", Age = " + temp.age);
                return;
            }
            temp = temp.next;
        }
        System.out.println("User not found.");
    }

    // Count number of friends for each user
    public void countFriends() {
        UserNode temp = head;
        while (temp != null) {
            int count = 0;
            FriendNode friendTemp = temp.friendList;
            while (friendTemp != null) {
                count++;
                friendTemp = friendTemp.next;
            }
            System.out.println("User " + temp.userId + " (" + temp.name + ") has " + count + " friends.");
            temp = temp.next;
        }
    }

    // Helper method to find a user by ID
    private UserNode findUser(int userId) {
        UserNode temp = head;
        while (temp != null) {
            if (temp.userId == userId) return temp;
            temp = temp.next;
        }
        return null;
    }
}


