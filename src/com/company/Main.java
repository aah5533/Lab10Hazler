/*
Project: Lab9Hazler
Purpose Details: Pizza Shop, class, methods
Course: IST 242
Author: Adam Hazler
Date Developed: 6/9/19
Last Date Changed: 6/11/19
Revision: 6/11/19
*/



package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private int cCount = 1;

    static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {
        Main main = new Main();
        final char EXIT_CODE = 'E';
        final char CUST_CODE = 'C';
        final char MENU_CODE = 'M';
        final char ORDE_CODE = 'O';
        final char TRAN_CODE = 'T';
        final char CUST_PRNT = 'P';
        final char HELP_CODE = '?';
        char userAction;
        final String PROMPT_ACTION = "Add 'C'ustomer, 'P'rint Customer, List 'M'enu, Add 'O'rder, List 'T'ransaction or 'E'xit: ";
        ArrayList<Customer> cList = new ArrayList<>();
        ArrayList<Menu> mList = new ArrayList<>();
        ArrayList<Order> oList = new ArrayList<>();
        ArrayList<Transaction> tList = new ArrayList<>();
        Order order1 = new Order(1);
        Transaction trans1 = new Transaction(1, order1, PaymentType.cash);
        Menu menu1 = new Menu(1, "Plain", 8.0);
        Menu menu2 = new Menu(2, "Meat", 12.0);
        Menu menu3 = new Menu(3, "Extra", 2.0);
        Menu menu4 = new Menu(4, "Veg", 16.0);
        mList.add(menu1);
        mList.add(menu2);
        mList.add(menu3);
        mList.add(menu4);
        userAction = getAction(PROMPT_ACTION);
        while (userAction != EXIT_CODE) {
            switch (userAction) {
                case CUST_CODE:
                    Customer c = main.addCustomer(cList);
                    if (c.getCustomerId() < cList.size()) {
                        cList.remove(c.getCustomerId());
                    }
                    cList.add(main.addCustomer(cList));
                    break;
                case CUST_PRNT:
                    Customer.printCustomer(cList);
                    break;
                case MENU_CODE:
                    Menu.listMenu(mList);
                    break;
                case ORDE_CODE:
                    System.out.print("Enter customer ID : ");
                    int cid = scnr.nextInt();
                    if (cid < cList.size()) {
                        ArrayList<Menu> cMenu = selectMenu(mList);
                        Order.addOrders(order1, cList.get(cid), cMenu);
                        oList.add(order1);
                        trans1 = makePaymet(order1);
                        tList.add(trans1);
                    } else {
                        System.out.println("No such customer ID !");
                    }
                    break;
                case TRAN_CODE:
                    Transaction.listTransactions(tList);
                    break;
            }
            userAction = getAction(PROMPT_ACTION);
        }
    }

    private static Transaction makePaymet(Order order1) {
        double total = 0;
        double amount;
        System.out.println("Your bill is:");
        for (Menu menu : order1.getMenuItem()) {
            System.out.print(menu.getmenuItem());
            System.out.print(" quantity: ");
            System.out.println(menu.getQuantity());
            System.out.print(" amount: ");
            amount = menu.getQuantity() * menu.getitemPrice();
            total = total + amount;
            System.out.println(amount);
        }
        System.out.print("Total bill amount is : ");
        System.out.println(total);
        int option;
        Transaction t;
        while (true) {
            System.out.print("Select Payment Option: ");
            System.out.println("1. Cash");
            System.out.println("2. Credit");
            option = scnr.nextInt();
            if (option == 1) {
                t = new Transaction(order1.getorderId(), order1, PaymentType.cash);
                return t;
            } else if (option == 2) {
                t = new Transaction(order1.getorderId(), order1, PaymentType.credit);
                return t;
            }
        }
    }

    public static ArrayList<Menu> selectMenu(ArrayList<Menu> menus) {
        System.out.println("Select menu (by ID): (Press 0 to finalize)");
        for (Menu menu : menus)
            System.out.println("'" + menu.getmenuId() + "' for " + menu.getmenuItem());
        int flag;
        ArrayList<Menu> menus1 = new ArrayList<>();
        while (true) {
            flag = scnr.nextInt();
            if (flag == 0)
                break;
            System.out.print("Add quantity : ");
            int quantity = scnr.nextInt();
            Menu item = menus.get(flag - 1);
            item.setQuantity(quantity);
            menus1.add(item);
        }
        return menus1;
    }

    public static char getAction(String prompt) {
        //removed scanner object from here
        String answer = "";
        System.out.println(prompt);
        answer = scnr.nextLine().toUpperCase() + " ";
        char firstChar = answer.charAt(0);
        return firstChar;
    }

    public Customer addCustomer(ArrayList<Customer> cList) {
        Customer cust = null;

        {
            cust = new Customer(cCount++);
            //removed scanner object from here
            System.out.println("Please Enter your Name: ");
            cust.setCustomerName(scnr.nextLine());
            System.out.println("Please Enter your Phone: ");
            cust.setCustomerPhoneNumber(scnr.nextLine());
        }
        return cust;
    }
}




