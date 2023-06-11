import java.util.*;
import java.io.*;

/**
 *
 * Amanuel Tenaw
 * ITSC 1213 
 * University of North Carolina at Charlotte
 */

public class FastFoodKitchen {

    private ArrayList<BurgerOrder> orderList = new ArrayList();  // Array for storing orders.
    private ArrayList<BurgerOrder> completedOrderList = new ArrayList(); // Array for storing uncompleted orders.

    private static int nextOrderNum = 1;
   
    FastFoodKitchen() {
        /*
        orderList.add(new BurgerOrder(3, 15, 4, 10, false, getNextOrderNum()));
        incrementNextOrderNum();
        orderList.add(new BurgerOrder(10, 10, 3, 3, true, getNextOrderNum()));
        incrementNextOrderNum();
        orderList.add(new BurgerOrder(1, 1, 1, 2, false, getNextOrderNum()));
        incrementNextOrderNum();

*/

         
    loadOrderList(orderList); // Calls the loadOrderList method to populate orderList.
   
          
    }

    public static int getNextOrderNum() {
        return nextOrderNum;
    }

    private void incrementNextOrderNum() {
        nextOrderNum++;
    }

    public int addOrder(int ham, int cheese, int veggie, int soda, boolean toGo) {
        int orderNum = getNextOrderNum();
        orderList.add(new BurgerOrder(ham, cheese, veggie, soda, toGo, orderNum));
        incrementNextOrderNum();
        orderCallOut(orderList.get(orderList.size() - 1));
        return orderNum;

    }

    public boolean isOrderDone(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                return false;
            }
        }
        return true;
    }

    public boolean cancelOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                orderList.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getNumOrdersPending() {
        return orderList.size();
    }

    public boolean cancelLastOrder() {

        if (!orderList.isEmpty()) { // same as  if (orderList.size() > 0) 
            orderList.remove(orderList.size() - 1);
            orderList.remove(orderList.size() - 1);
            return true;
        }

        return false;
    }

    private void orderCallOut(BurgerOrder order) {
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumHamburger() + " hamburgers");
        }
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumCheeseburgers() + " cheeseburgers");
        }
        if (order.getNumVeggieburgers() > 0) {
            System.out.println("You have " + order.getNumVeggieburgers() + " veggieburgers");
        }
        if (order.getNumSodas() > 0) {
            System.out.println("You have " + order.getNumSodas() + " sodas");
        }

    }

    public void completeSpecificOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                System.out.println("Order number " + orderID + " is done!");
                if (orderList.get(i).isOrderToGo()) {
                    orderCallOut(orderList.get(i));
                }
                completedOrderList.add(orderList.get(i)); // Adds the completed orders to completedOrderList.
                orderList.remove(i);
            }
        }

    }

    public void completeNextOrder() {
        int nextOrder = orderList.get(0).getOrderNum();
        completeSpecificOrder(nextOrder);

    }

    // Part 2
    public ArrayList<BurgerOrder> getOrderList() {
        return orderList;
    }

/**
 * This method returns the completedOrderList.
 * @return completedOrderList 
 */
public ArrayList<BurgerOrder> getCompletedOrderList() { 
        return completedOrderList;
    }



    public int findOrderSeq(int whatWeAreLookingFor) {
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getOrderNum() == whatWeAreLookingFor) {
                return j;
            }
        }
        return -1;
    }


    

//    public int findOrderBin(int whatWeAreLookingFor) {
//        int left = 0;
//        int right = orderList.size() - 1;
//        while (left <= right) {
//            int middle = (left + right) / 2;
//            if (whatWeAreLookingFor < orderList.get(middle).getOrderNum()) {
//                right = middle - 1;
//            } else if (whatWeAreLookingFor > orderList.get(middle).getOrderNum()) {
//                left = middle + 1;
//            } else {
//                return middle;
//            }
//        }
//        return -1;
//    }

  public int findOrderBin(int orderID){
        int left = 0;
        int right = orderList.size()-1;
        while (left <= right){
            int middle = ((left + right)/2);
            if (orderID < orderList.get(middle).getOrderNum()){
                right = middle-1;
            }
            else if(orderID > orderList.get(middle).getOrderNum()){
                left = middle +1;
            }
            else{
                return middle;
            }
        }
        return -1;
        
    }
    public void selectionSort(){
        for (int i = 0; i< orderList.size()-1; i++){
            int minIndex = i;
            for (int k = i+1; k < orderList.size(); k++){
                if (orderList.get(minIndex).getTotalBurgers() > orderList.get(k).getTotalBurgers()){
                    minIndex = k;
                }
            }
            BurgerOrder temp = orderList.get(i);
            orderList.set(i , orderList.get(minIndex));
            orderList.set(minIndex, temp);
        }
    }

    public void insertionSort() {
        for (int j = 1; j < orderList.size(); j++) {
            BurgerOrder temp = orderList.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.getTotalBurgers() < orderList.get(possibleIndex - 1).getTotalBurgers()) {
                orderList.set(possibleIndex, orderList.get(possibleIndex - 1));
                possibleIndex--;
            }
            orderList.set(possibleIndex, temp);
        }
    }



/**
   * This method is used to populate orderList by reading external file.
   * @param orderList
   */
    public  void loadOrderList(ArrayList<BurgerOrder> orderList) {
        try {
            Scanner fileScanner = new Scanner(new File("burgerOrders.csv")); // Reads from burgerOrders.csv file. 
            while(fileScanner.hasNext()){
                String line = fileScanner.nextLine();
                String[] parsedLine = line.split(","); // Separates words between a comma.  
                int orderNum = Integer.valueOf(parsedLine[0]);
                incrementNextOrderNum();
                int numHamburgers = Integer.valueOf(parsedLine[1]);
                int numCheeseburgers = Integer.valueOf(parsedLine[2]);
                int numVeggieburgers = Integer.valueOf(parsedLine[3]);
                int numSodas = Integer.valueOf(parsedLine[4]);
                boolean orderToGo = Boolean.parseBoolean(parsedLine[5]);

                orderList.add(new BurgerOrder(numHamburgers, numCheeseburgers, numVeggieburgers, numSodas, orderToGo, orderNum)); 
            }
            System.out.println("Finished Loading Order list");
          fileScanner.close();
        } 

        catch (FileNotFoundException x){ // Catchs FileNotFoundException. 
            System.out.println("Please check the locations of the File and try again.");
        } 
        
        
    }

   


    
//    public void selectionSort() { //weird method!
//
//        for (int j = 0; j < orderList.size() - 1; j++) {
//            int minIndex = j;
//            for (int k = j + 1; k < orderList.size(); k++) {
//
//                 if (orderList.get(minIndex).getTotalBurgers() > orderList.get(j).getTotalBurgers()){
//                    minIndex = k;
//                }
//            }
//            BurgerOrder temp = orderList.get(j);
//            orderList.set(j, orderList.get(minIndex));
//            orderList.set(minIndex, temp);
//
//        }
//    }

}
