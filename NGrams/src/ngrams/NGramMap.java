package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;


/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private HashMap<Integer, Double> tot = new HashMap<>();
    private HashMap<String, TimeSeries> ind = new HashMap<>();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {

        In in = new In(countsFilename);

        while (in.hasNextLine()) {

            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            tot.put(Integer.valueOf(splitLine[0]), Double.valueOf(splitLine[1]));
        }
        In in2 = new In(wordsFilename);
        while (in2.hasNextLine()) {

            String nextLine = in2.readLine();
            String[] splitLine = nextLine.split("\t");
            String word = splitLine[0];
            Integer year = Integer.valueOf(splitLine[1]);
            Integer count = Integer.valueOf(splitLine[2]);

            TimeSeries ts;
            if (ind.containsKey(word)) {
                ts = ind.get(word);
            } else {
                ts = new TimeSeries();
            }
            ts.put(year, count.doubleValue());
            ind.put(word, ts);
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {

        if (!ind.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = ind.get(word);
        return new TimeSeries(original, startYear, endYear);
    }


    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!ind.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = ind.get(word);
        return new TimeSeries(original, original.firstKey(), original.lastKey());
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        if (tot == null || tot.isEmpty()) {
            return new TimeSeries();
        }
        TimeSeries ts = new TimeSeries();
        for (Integer year : tot.keySet()) {
            ts.put(year, tot.get(year));
        }
        return ts;

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {

        TimeSeries ts = totalCountHistory();
        if (!ind.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries t = new TimeSeries(ind.get(word), startYear, endYear);
            return t.dividedBy(ts);
        }

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {

        TimeSeries ts = totalCountHistory();
        if (!ind.containsKey(word)) {
            return new TimeSeries();
        } else {
            TimeSeries t = new TimeSeries(ind.get(word), ind.get(word).firstKey(), ind.get(word).lastKey());
            return t.dividedBy(ts);
        }

    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {

        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            ts = ts.plus(weightHistory(word, startYear, endYear));
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {

        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            ts = ts.plus(weightHistory(word));
        }
        return ts;
    }

}
