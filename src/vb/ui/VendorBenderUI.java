package vb.ui;

import vb.core.Result;
import vb.core.VendorBender;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Justin on 3/11/14.
 */
public class VendorBenderUI {

    private VendorBender vb;

    public VendorBenderUI() {
        vb = new VendorBender();
        displayMenu();
    }

    public void displayMenu() {
        System.out.println("Enter values, or 0 to calculate.");
        Scanner in = new Scanner(System.in);
        int input = -1;
        while (input != 0) {
            input = in.nextInt();
            if (input != 0) vb.add(input);
        }
        in.close();
        System.out.println();

        LinkedList<Result> results = vb.getResults();

        for (Result r : results) {
            System.out.print("Result (" + r.getSum() + "): ");
            for (Integer i : r.getValues()) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        new VendorBenderUI();
    }

}
