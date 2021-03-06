package com.carletto.scraper.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by jcarlett on 3/9/2017.
 */
public class FleetPrinter {
    private final StringProperty SYSTEMNAME;
    private final StringProperty DIFFERENCE;
    private ObservableList<FleetPrinter> fleetPrinters = FXCollections.observableArrayList();

    public FleetPrinter() {
        this(null, null);

    }

    public FleetPrinter(String systemName, String difference) {
        this.SYSTEMNAME = new SimpleStringProperty(systemName);
        this.DIFFERENCE = new SimpleStringProperty(difference);
    }

    public ObservableList<FleetPrinter> getFleetPrinters() {
        return fleetPrinters;
    }

    public ObservableList<FleetPrinter> getFleetPrinterList(List<String[]> list) {
        //System.out.println(list.get(0)[0] + " " + list.get(1)[0]);


        for (int x = 1; x < list.size(); x++) {
            String[] arr = list.get(x);
            fleetPrinters.add(new FleetPrinter(arr[0], String.valueOf(Integer.valueOf(arr[1]))));
        }

        return fleetPrinters;

    }


}
