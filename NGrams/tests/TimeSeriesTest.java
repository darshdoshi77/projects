import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {

    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        // Expected years: 1991, 1992, 1994, 1995
        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1991, 1992, 1994, 1995));
        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        // Expected values: 0.0, 100.0, 600.0, 500.0
        List<Double> expectedTotal = new ArrayList<>(Arrays.asList(0.0, 100.0, 600.0, 500.0));
        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    // Additional test: Checking division operation
    @Test
    public void testDivision() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1990, 10.0);
        ts1.put(1995, 20.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(1990, 2.0);
        ts2.put(1995, 4.0);

        TimeSeries result = ts1.dividedBy(ts2);

        // Expected values: 10/2 = 5.0, 20/4 = 5.0
        List<Double> expectedResult = new ArrayList<>(Arrays.asList(5.0, 5.0));
        for (int i = 0; i < expectedResult.size(); i += 1) {
            assertThat(result.data().get(i)).isWithin(1E-10).of(expectedResult.get(i));
        }
    }


    // Additional test: Handling mismatched years
    @Test
    public void testMismatchedYears() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1990, 10.0);
        ts1.put(1995, 20.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(1990, 2.0);  // No entry for 1995

        // Assert that IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> ts1.dividedBy(ts2));
    }

    // Additional test: Summing time series with different years
    @Test
    public void testSumWithDifferentYears() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1990, 10.0);
        ts1.put(1995, 20.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(1990, 5.0);
        ts2.put(2000, 30.0);  // Year 2000 only in ts2

        TimeSeries result = ts1.plus(ts2);

        // Expected years: 1990, 1995, 2000
        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1990, 1995, 2000));
        assertThat(result.years()).isEqualTo(expectedYears);

        // Expected values: 10 + 5 = 15 (1990), 20 (1995), 30 (2000)
        List<Double> expectedTotal = new ArrayList<>(Arrays.asList(15.0, 20.0, 30.0));
        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(result.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    // Additional test: Creating a TimeSeries with a specific year range
    @Test
    public void testCreateWithYearRange() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1990, 10.0);
        ts1.put(1992, 15.0);
        ts1.put(1995, 20.0);

        TimeSeries tsWithRange = new TimeSeries(ts1, 1990, 1992);

        // Expected years: 1990, 1992
        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1990, 1992));
        assertThat(tsWithRange.years()).isEqualTo(expectedYears);

        // Expected values: 10.0, 15.0
        List<Double> expectedData = new ArrayList<>(Arrays.asList(10.0, 15.0));
        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(tsWithRange.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }
}
