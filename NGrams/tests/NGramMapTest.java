import ngrams.NGramMap;
import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.HashSet;

import static utils.Utils.*;
import static com.google.common.truth.Truth.assertThat;

/**
 * Unit Tests for the NGramMap class.
 *
 * @author Josh Hug
 */
public class NGramMapTest {
    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(2005, 2006, 2007, 2008));
        List<Double> expectedCounts = new ArrayList<>
                (Arrays.asList(646179.0, 677820.0, 697645.0, 795265.0));

        TimeSeries request2005to2008 = ngm.countHistory("request");
        assertThat(request2005to2008.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2005to2008.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }

        expectedYears = new ArrayList<>
                (Arrays.asList(2006, 2007));
        expectedCounts = new ArrayList<>
                (Arrays.asList(677820.0, 697645.0));

        TimeSeries request2006to2007 = ngm.countHistory("request", 2006, 2007);

        assertThat(request2006to2007.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2006to2007.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }
    }

    @Test
    public void testOnShortFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(SHORTER_WORDS_FILE,
                TOTAL_COUNTS_FILE);

        // returns the count of the number of occurrences of economically per year between 2000 and 2010.
        TimeSeries econCount = ngm.countHistory("economically", 2000, 2010);
        assertThat(econCount.get(2000)).isWithin(1E-10).of(294258.0);
        assertThat(econCount.get(2010)).isWithin(1E-10).of(222744.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1999)).isWithin(1E-10).of(22668397698.0);

        // returns the relative weight of the word academic in each year between 1999 and 2010.
        TimeSeries academicWeight = ngm.weightHistory("academic", 1999, 2010);
        assertThat(academicWeight.get(1999)).isWithin(1E-7).of(969087.0 / 22668397698.0);
    }

    @Test
    public void testOnLargeFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(TOP_14337_WORDS_FILE,
                TOTAL_COUNTS_FILE);

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
        assertThat(fishCount.get(1865)).isWithin(1E-10).of(136497.0);
        assertThat(fishCount.get(1922)).isWithin(1E-10).of(444924.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);
        assertThat(fishWeight.get(1865)).isWithin(1E-7).of(136497.0 / 2563919231.0);

        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);
        assertThat(dogCount.get(1865)).isWithin(1E-10).of(75819.0);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("fish");
        fishAndDog.add("dog");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;
        assertThat(fishPlusDogWeight.get(1865)).isWithin(1E-10).of(expectedFishPlusDogWeight1865);
    }


    @Test
    public void testWeightHistoryWithInvalidWord() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        TimeSeries invalidWeightHistory = ngm.weightHistory("nonexistentword");

        // Ensure that the TimeSeries for an invalid word is empty
        assertThat(invalidWeightHistory.years()).isEmpty();
        assertThat(invalidWeightHistory.data()).isEmpty();
    }

    @Test
    public void testSummedWeightHistoryMultipleWords() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        Collection<String> words = new HashSet<>(Arrays.asList("request", "airport"));
        TimeSeries summedWeights = ngm.summedWeightHistory(words, 2005, 2008);

        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(2005, 2006, 2007, 2008));
        assertThat(summedWeights.years()).isEqualTo(expectedYears);

        // Ensure the summed data is reasonable (values are not null and within expected range)
        List<Double> summedData = summedWeights.data();
        for (Double data : summedData) {
            assertThat(data).isNotNull();
            assertThat(data).isAtLeast(0.0);
        }
    }

    @Test
    public void testCountHistoryWithInvalidWord() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        TimeSeries invalidWordHistory = ngm.countHistory("nonexistentword");

        // Ensure that the TimeSeries for an invalid word is empty
        assertThat(invalidWordHistory.years()).isEmpty();
        assertThat(invalidWordHistory.data()).isEmpty();
    }

    @Test
    public void testSummedWeightHistoryWithInvalidWord() {
        NGramMap ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);

        Collection<String> words = new HashSet<>(Arrays.asList("request", "nonexistentword"));

        TimeSeries summedWeights = ngm.summedWeightHistory(words);

        TimeSeries validRequestWeights = ngm.weightHistory("request");

        assertThat(summedWeights.years()).isEqualTo(validRequestWeights.years());
        for (int i = 0; i < summedWeights.data().size(); i++) {
            assertThat(summedWeights.data().get(i)).isWithin(1E-10).of(validRequestWeights.data().get(i));
        }
    }
}