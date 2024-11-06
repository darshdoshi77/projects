package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {

    private NGramMap ngm;

    // Constructor
    public HistoryHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        // Collect TimeSeries for the words provided in the query
        ArrayList<TimeSeries> timeSeriesList = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            TimeSeries wordData = ngm.weightHistory(word, startYear, endYear);
            if (!wordData.isEmpty()) {
                timeSeriesList.add(wordData);
                labels.add(word); // Use the word as the label for the plot
            }
        }

        if (timeSeriesList.isEmpty()) {
            return "No data available for the selected words and time period.";
        }

        // Generate the chart
        XYChart chart = Plotter.generateTimeSeriesChart(labels, timeSeriesList);
        // Encode the chart as a base-64 string
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;  // Return the base-64 encoded chart image
    }
}
