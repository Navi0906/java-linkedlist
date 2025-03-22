public class InventorySystem {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addItem("Laptop", 103, 12, 75000, 0);
        inventory.addItem("CPU", 105, 6, 20000, 1);
        inventory.addItem("Headphones", 203, 9, 23000, 2);

        System.out.println("Inventory List: ");
        inventory.display();

        System.out.println("Searching for Item with ID 105:");
        inventory.searchItem(105, "");

        System.out.println("Updating Quantity of 203: ");
        inventory.updateQuantity(203, 20);
        inventory.display();

        System.out.println("Total Inventory Value: Rs."+inventory.calculateTotalValue());

        System.out.println("Sorting by Item Name: ");
        inventory.sortByName();
        inventory.display();

        System.out.println("Sorting by Price: ");
        inventory.sortByPrice();
        inventory.display();

        System.out.println("Removing Item with ID 203");
        inventory.removeItem(203);
        inventory.display();
    }
}

class Node{
    String itemName;
    int itemId;
    int quantity;
    double price;

    Node next;

    public Node(String itemName, int itemId, int quantity, double price){
        this.itemName = itemName;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.next = null;
    }
}

class Inventory{
    private Node head;

    public void addItem(String itemName, int itemId, int quantity, double price, int position){
        Node newItem = new Node(itemName,itemId,quantity,price);
        if(position==0 || head==null){
            newItem.next = head;
            head = newItem;
        }
        else {
            Node temp = head;
            int index = 0;
            while(temp.next != null && index < position - 1){
                temp = temp.next;
                index++;
            }
            newItem.next = temp.next;
            temp.next = newItem;
        }
    }

    public void removeItem(int itemId){
        if(head == null){
            return;
        }
        if(head.itemId == itemId){
            head = head.next;
            return;
        }
        Node temp = head;
        while(temp.next!=null && temp.next.itemId != itemId){
            temp = temp.next;
        }
        if(temp.next != null){
            temp.next = temp.next.next;
        }
    }

    public void updateQuantity(int itemId, int newQuantity){
        Node temp = head;
        while(temp != null){
            if(temp.itemId == itemId){
                temp.quantity = newQuantity;
                return;
            }
            temp = temp.next;
        }
    }

    public void searchItem(int itemId, String itemName){
        Node temp = head;
        while(temp!=null){
            if(temp.itemId == itemId || temp.itemName.equalsIgnoreCase(itemName)){
                System.out.println(temp.itemId + " | " + temp.itemName + " | "+temp.quantity+" | "+temp.price);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Item not found.");
    }

    public double calculateTotalValue(){
        double totalValue = 0;
        Node temp = head;
        while(temp!=null){
            totalValue = totalValue + (temp.quantity* temp.price);
            temp = temp.next;
        }
        return totalValue;
    }

    public void sortByName(){
        if(head==null || head.next == null)return;
        boolean swapped;
        do{
            swapped = false;
            Node temp = head;
            while(temp.next!=null){
                if(temp.itemName.compareToIgnoreCase(temp.next.itemName)>0){
                    swap(temp, temp.next);
                    swapped = true;
                }
                temp = temp.next;
            }
        }while(swapped);
    }

    public void sortByPrice(){
        if(head==null || head.next==null) return;
        boolean swapped;
        do{
            swapped = false;
            Node temp = head;
            while(temp.next!=null){
                if(temp.price>temp.next.price){
                    swap(temp,temp.next);
                    swapped = true;
                }
                temp = temp.next;
            }
        }while(swapped);
    }

    private void swap(Node a, Node b){
        String tempName = a.itemName;
        int tempId = a.itemId;
        int tempQuantity = a.quantity;
        double tempPrice = a.price;

        a.itemName = b.itemName;
        a.itemId = b.itemId;
        a.quantity = b.quantity;
        a.price = b.price;

        b.itemName = tempName;
        b.itemId = tempId;
        b.quantity = tempQuantity;
        b.price = tempPrice;
    }

    public void display(){
        Node temp = head;
        while(temp!=null){
            System.out.println(temp.itemId + " | " + temp.itemName + " | "+ temp.quantity+" | "+temp.price);
            temp = temp.next;
        }
    }
}






