package com.carletto.scraper;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by jcarlett on 3/3/2017.
 */
public class CSVExporter {

    private static final Logger logger = LoggerFactory.getLogger(CSVExporter.class);

    public void write(List<String[]> entries, File fileLocation) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileLocation))) {

            // Debug
            entries.stream()
              .map(sa -> sa[0] + " " + sa[1])
              .forEach(logger::info);

            writer.writeAll(entries);
            writer.close();
        } catch (Exception e) {
            logger.error("Failed to write entries to " + fileLocation, e);
        }

    }
}
