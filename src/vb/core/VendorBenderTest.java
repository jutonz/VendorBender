package vb.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Justin on 3/10/14.
 */
public class VendorBenderTest {

    private VendorBender vb;
    private List<Result> results;

    @Before
    public void setUp() throws Exception {
        vb = new VendorBender();
    }

    @Test
    public void testSumTo40() throws Exception {
        vb.add(20);
        vb.add(20);
        Result r = vb.getResults().get(0);
        Assert.assertEquals(40, r.getSum());
        Assert.assertEquals(20, (int) r.getValues().get(0));
        Assert.assertEquals(20, (int) r.getValues().get(1));
    }

    @Test
    public void testSumToLessThan40() throws Exception {
        // Empty test
        Assert.assertTrue(vb.getResults().isEmpty());

        // Sums to 39
        vb.add(20);
        vb.add(19);
        Assert.assertTrue(vb.getResults().isEmpty());
    }

    @Test
    public void testSumToMoreThan40() throws Exception {
        // Sums to 43
        vb.add(20);
        vb.add(20);
        vb.add(3);
        Result r43 = vb.getResults().get(0);
        Assert.assertEquals(43, r43.getSum());
//        Assert.assertEquals(20, (int)r43.getValues().get(0));
//        Assert.assertEquals(20, (int)r43.getValues().get(1));
//        Assert.assertEquals(3, (int) r43.getValues().get(2));
        vb.clear();

        // Sums to 43 (extra values)
        vb.add(5);
        vb.add(5);
        vb.add(6);
        vb.add(11);
        vb.add(12);
        vb.add(14);
        vb.add(18);
        Result r = vb.getResults().get(0);
        Assert.assertEquals(43, r.getSum());
        Assert.assertEquals(18, (int)r.getValues().get(0));
        Assert.assertEquals(14, (int)r.getValues().get(1));
        Assert.assertEquals(6, (int)r.getValues().get(2));
        Assert.assertEquals(5, (int)r.getValues().get(3));
        vb.clear();

        vb.add(15);
        vb.add(15);
        vb.add(15);
        vb.add(5);
        Result r45 = vb.getResults().get(0);
        Assert.assertEquals(45, r45.getSum());
        vb.clear();
    }
}
