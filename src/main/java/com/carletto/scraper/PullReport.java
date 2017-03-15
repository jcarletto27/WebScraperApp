package com.carletto.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcarlett on 3/3/2017.
 */
public class PullReport {

    private static final Logger logger = LoggerFactory.getLogger(PullReport.class);

    public List<String[]> report(Document doc) {
        List<String[]> outputLines = new ArrayList<>();

        //Top Line of CSV
        outputLines.add(new String[]{"Location", "Total Copy Count"});

        //need to remove unnecessary nested tables
        doc.getElementsByTag("table").forEach(Node::remove);

        //Select the correct table, could also use .First();
        Element table = doc.select("table").get(0);

        //collect the rows
        Elements rows = table.select("tr");

        //iterate through the rows, could also use an Iterator, but this needs less code and allows for skipping certain lines.
        for (int i = 1; i < rows.size() - 2; i++) {
            Element row = rows.get(i);

            logger.info(row.text());
            String text = row.text().replace("Impressions", "").replace(".premierinc.com ", "#").replace("c3-workcentre7855-", "").replace("c3-workcentre7970-", "")
                    .replace(" ", "").replace("DNSName", "Location#").replace("PageCountDifference", "Copy Count");

            String[] textArray = text.split("#");

            logger.info(textArray[0] + " " + textArray[1]);
            outputLines.add(textArray);
        }

        //return List<String[]> for writing to CSV.
        return outputLines;
    }
}
