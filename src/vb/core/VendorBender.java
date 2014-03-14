package vb.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.*;

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

        // Start by sorting the list.
        Collections.sort(values);

        // Then reverse it to descending order.
        Collections.reverse(values);

        // Create a list of all the possible combinations of our values.
        Set<Set<Integer>> sets = getSetFromValues();

        int sumTo = 40;
        while (!sets.isEmpty()) {
            Set<Set<Integer>> setsThatSumTo = getSetsThatSumTo(sets, sumTo);
            while (!setsThatSumTo.isEmpty()) {
                Set<Integer> set = setsThatSumTo.iterator().next();
                results.add(new Result(set));
                for (Integer i : set) {
                    values.remove(i);
                }
                sets = getSetFromValues();
                setsThatSumTo = getSetsThatSumTo(sets, sumTo);
            }
            sumTo++;
            sets = getSetFromValues();
        }

        // Let everyone else know we've calculated the result
        isCalculated = true;
    }

    /**
     * Creates a power set from the current values. This is essentially
     * a list of all possible combinations of numbers.
     *
     * @return a power set of the values.
     */
    private Set<Set<Integer>> getSetFromValues() {
        // Create a power set of all possible combinations of our values.
        ImmutableSet.Builder<Integer> builder = ImmutableSet.builder();
        for (Integer i : values)
            builder.add(i);
        Set<Set<Integer>> sets = Sets.powerSet(builder.build());

        // Remove all options that do not sum to 40-50
        return trimExcessive(sets);
    }

    /**
     * Removes from the specified list all entries that
     * do not sum to 40-50.
     *
     * @param sets list of sets to trim
     */
    private Set<Set<Integer>> trimExcessive(Set<Set<Integer>> sets) {
        HashSet<Set<Integer>> newSets = Sets.newHashSet();
        for (Set<Integer> set : sets) {
            int sum = getSumOf(set);
            if (sum >= 40 && sum <= 50)
                newSets.add(set);
        }
        return newSets;
    }

    /**
     * Finds a subset of the specified set of sets
     * for which all sets sum to the specified value.
     *
     * @param sets list of all sets
     * @param sum minimum value of returned sets
     * @return all sets that sum to at least the specified value
     */
    private Set<Set<Integer>> getSetsThatSumTo(Set<Set<Integer>> sets, int sum) {
        HashSet<Set<Integer>> newSets = Sets.newHashSet();
        for (Set<Integer> set : sets) {
            int setSum = getSumOf(set);
            if (setSum == sum)
                newSets.add(set);
        }
        return newSets;
    }

    /**
     * Returns the sum of the specified set of integers.
     *
     * @param set to sum
     * @return the sum of the set
     */
    private int getSumOf(Set<Integer> set) {
        int sum = 0;
        for (Integer i : set)
            sum += i;
        return sum;
    }

    /**
     * This is an older version of the calculate method. This performs the
     * same task as the new version, but less accurately. It is,
     * however, considerably faster.
     */
    public void calculateLegacy() {

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
//
//    public static void main(String[] args) {
//        VendorBender vb = new VendorBender();
//        vb.add(5);
//        vb.add(14);
//        vb.add(10);
//        vb.add(10);
//        vb.add(20);
//        vb.add(15);
//        vb.add(19);
//        vb.add(19);
//        vb.add(11);
//        vb.add(7);
//
//        vb.calculate();
//
//        LinkedList<Result> results = vb.getResults();
//
//        for (Result r : results) {
//            System.out.print("Result (" + r.getSum() + "): ");
//            for (Integer i : r.getValues()) {
//                System.out.print(i + " ");
//            }
//            System.out.println();
//        }
//    }

}
