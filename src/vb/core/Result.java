package vb.core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Justin on 3/10/14.
 */
public class Result {
    private ArrayList<Integer> values;

    public Result(ArrayList<Integer> values) {
        this.values = values;
    }

    public Result() {
        this(new ArrayList<Integer>());
    }

    public void add(int value) {
        values.add(value);
    }

    public ArrayList<Integer> getValues() {
        // Sort (accordingly)
        Collections.sort(values);

        // Reverse to descending
        Collections.reverse(values);

        return values;
    }

    public int getSum() {
        int sum = 0;
        for (Integer i : values)
            sum += i;
        return sum;
    }
}
