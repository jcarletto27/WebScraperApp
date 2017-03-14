package com.carletto.scraper;

import com.carletto.scraper.utils.Converter;
import com.carletto.scraper.utils.DateList;
import com.carletto.scraper.utils.FleetPrinter;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private TableView<FleetPrinter> tableView_Report;
    @FXML
    private TableColumn tableColumn_SystemName;
    @FXML
    private TableColumn tableColumn_Difference;
    @FXML
    private Button button_PullReport;
    @FXML
    private DatePicker datepicker_Start;
    @FXML
    private DatePicker datepicker_End;
    @FXML
    private MenuItem menuItem_SaveAs;
    @FXML
    private MenuItem menuItem_Quit;

    private Document doc;
    //this url is unlikely to change.
    private String url = "http://10.32.137.215/XeroxCentreWareWeb/AccessControl/Reports/ReportUser/reportDisplay.aspx?reportID=100";
    private PullReport pullReport = new PullReport();
    private List<String[]> outputLines = null;
    private FleetPrinter fleetPrinter = new FleetPrinter();
    private File fileLocation;
    private EditReport editReport = new EditReport();
    private Converter converter = new Converter();
    private ObservableList<FleetPrinter> data = FXCollections.observableArrayList();
    private String editReportUrl = "http://10.32.137.215/XeroxCentreWareWeb/AccessControl/Reports/reportsSettings.aspx?reportID=100";
    private CSVExporter csvExporter = new CSVExporter();

    public Controller() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        button_PullReport.setOnAction(event -> {
            try {

                DateList.changedDates(datepicker_Start.getValue().toString(), datepicker_End.getValue().toString());
                editReport.editSettings(editReportUrl);

                if (datepicker_End.getValue() != null && datepicker_Start.getValue() != null) {


                    DateList.changedDates(datepicker_Start.getValue().toString(), datepicker_End.getValue().toString());

//                        System.out.println(DateList.startDate());
//                        System.out.println(DateList.endDate());


                    for (HtmlSelect select : editReport.getSelectList()) {
                        for (String s : editReport.getElementList()) {
                            if (select.getId().equals(s)) {
                                editReport.setField(select, DateList.getCurrentDates().get(s));
                                //System.out.println(select.getDefaultValue());
                            }
                        }
                    }

                    editReport.submit();

                    Thread.sleep(100);

                    doc = Jsoup.connect(url).timeout(20000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").followRedirects(true).get();
                    outputLines = pullReport.report(doc);
                    data = fleetPrinter.getFleetPrinterList(outputLines);
                    tableView_Report.setItems(data);
                    tableView_Report.refresh();

                    //fleetPrinter.setFleetPrinters(fleetPrinter.getFleetPrinterList(outputLines));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        datepicker_End.setOnAction(event -> {
            System.out.println("Changing end Dates!");
            DateList.changedDates(datepicker_Start.getValue().toString(), datepicker_End.getValue().toString());
            data.clear();

        });

        datepicker_Start.setOnAction(event -> {
            System.out.println("Changing start dates!");
            DateList.changedDates(datepicker_Start.getValue().toString(), datepicker_End.getValue().toString());
            data.clear();
        });

        tableColumn_SystemName.setCellValueFactory(new PropertyValueFactory<FleetPrinter, String>("SYSTEMNAME"));
        tableColumn_Difference.setCellValueFactory(new PropertyValueFactory<FleetPrinter, Integer>("DIFFERENCE"));


        menuItem_SaveAs.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV to:");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV - Comma Delimited", "*.csv")
            );
            fileLocation = fileChooser.showSaveDialog(null);

            if (fileLocation != null) {
                // System.out.println(fileLocation.getAbsolutePath());
                csvExporter.write(outputLines, fileLocation);
                try {
                    if (!fileLocation.exists() && !fileLocation.canWrite()) {
                        Thread.sleep(1000);
                    } else {
                        Desktop.getDesktop().open(fileLocation);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });


        try {
            editReport.editSettings(editReportUrl);


            datepicker_Start.setConverter(converter);
            datepicker_End.setConverter(converter);

            datepicker_Start.setValue(DateList.localDateParser(DateList.startDate()));
            datepicker_End.setValue(DateList.localDateParser(DateList.endDate()));


            DateList.changedDates(datepicker_Start.getValue().toString(), datepicker_End.getValue().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

        menuItem_Quit.setOnAction(event -> Platform.exit());
    }
}
