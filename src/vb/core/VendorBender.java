package vb.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
            Result r = new Result();

            int total = r.getSum();
            for (int i = 0; ; ) {
                int cur = values.get(i);
                if (total + cur < 40) {
                    r.add(values.remove(i));
                    total += cur;
                } else {
                    break;
                }
            }

//            // Add the first (largest) item to the result
//            r.add(values.remove(0));
//
//            // Add more (large) items while the total is under 40
//            int total = r.getSum();
//            Iterator<Integer> it = values.iterator();
//            while (it.hasNext()) {
//                int cur = it.next();
//                if (total + cur < 40) {
//                    it.remove();
//                    r.add(cur);
//                    total += cur;
//                } else {
//                    break;
//                }
//            }
//
            // Repeatedly add the largest small item that fits
            while (r.getSum() < 40) {
                for (int i = values.size() - 1; i >= 0; i--) {
                    if (r.getSum() + values.get(i) < 40)
                        continue;
                    else {
                        if (i == values.size() - 1) {
                            r.add(values.remove(i));
                            break;
                        }
                        r.add(values.remove(i+1));
                        break;
                    }
                }
            }

            results.add(r);
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
