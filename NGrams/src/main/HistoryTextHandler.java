package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap ngm;

    // Constructor
    public HistoryTextHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();

        for (String word : words) {
            // Get the TimeSeries for the word
            TimeSeries wordHistory = ngm.weightHistory(word, startYear, endYear);

            // Check if the word exists in the dataset
            if (wordHistory.isEmpty()) {
                response.append(word).append(": {}\n");
            } else {
                response.append(word).append(": ").append(wordHistory.toString()).append("\n");
            }
        }

        return response.toString();  // Ensure the formatting matches the exact requirement.
    }
}
