package vb.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Justin on 10th March 2014.
 *
 * @author Justin Toniazzo
 */
public class VendorBender {

    private ArrayList<Integer> values;
    private LinkedList<Result> results;
    private boolean isCalculated;

    public VendorBender() {
        values = new ArrayList<Integer>();
        results = new LinkedList<Result>();
        isCalculated = false;
    }

    public void add(int value) {
        values.add(value);
        isCalculated = false;
    }

    public LinkedList<Result> getResults() {
        if (!isCalculated)
            calculate();
        return results;
    }

    public int getSumOfValues() {
        int sum = 0;
        for (Integer i : values)
            sum += i;
        return sum;
    }

    public void clear() {
        values.clear();
        results.clear();
        isCalculated = false;
    }


    public void calculate() {

        // Let everyone else know we've calculated the results
        isCalculated = true;

        // Start by sorting the list.
        Collections.sort(values);

        // Then reverse it to descending order.
        Collections.reverse(values);

        while (getSumOfValues() >= 40) {

            // Create a new result to populate.
            Result result = new Result();

            int total = result.getSum();
            for (int i = 0; ; ) {
                int cur = values.get(i);
                if (total + cur < 40) {
                    result.add(values.remove(i));
                    total += cur;
                } else {
                    break;
                }
            }

            // Now we want to add small values so we get as close as possible to 40.
            // Loop while we have less than 40.
            while (result.getSum() < 40) {
                // Iterate through values until we get one that gets us 40.
                for (int i = values.size() - 1; i >= 0; i--) {
                    int value = values.get(i);
                    int newTotal = result.getSum() + value;
                    if (newTotal >= 40) {
                        // If the value gets us to over 40, add it and stop.
//                        result.add(values.remove(i == values.size() - 1 ? i : i + 1));
                        values.remove((Integer) value);
                        result.add(value);
                        break;
                    }
                }
            }

            // Add what we have found to the list of solutions and loop again.
            results.add(result);
        }
    }

    public static void main(String[] args) {
        VendorBender vb = new VendorBender();
        vb.add(16);
        vb.add(16);
        vb.add(14);

        vb.calculate();

        LinkedList<Result> results = vb.getResults();

        for (Result r : results) {
            System.out.print("Result (" + r.getSum() + "): ");
            for (Integer i : r.getValues()) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

}
