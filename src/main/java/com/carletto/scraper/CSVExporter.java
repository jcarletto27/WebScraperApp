package com.carletto.scraper;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by jcarlett on 3/3/2017.
 */
public class CSVExporter {

    public void write(List<String[]> entries, File fileLocation) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileLocation))) {
            for (String[] sa : entries) {
                System.out.println(sa[0] + " " + sa[1]);
            }

            writer.writeAll(entries);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
