package mytrading;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class Futuurid {
    public Futuurid () throws IOException {
        String [] urlList = {"http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_F16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_G16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_H16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_J16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_K16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_M16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_N16_VX.csv",
                "http://cfe.cboe.com/Publish/ScheduledTask/MktData/datahouse/CFE_Q16_VX.csv",
        };
        String []lastPrice = new String[8];

        // Read the csv files into a string array
        for (int i = 0; i <8; i++) {
            URL cboe = new URL(urlList[i]);
            URLConnection gc = cboe.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(gc.getInputStream()));
            String line;
            LinkedList<String[]> rows = new LinkedList<String[]>();

            while ((line = in.readLine()) != null) {
                rows.add(line.split(","));              // split each row by comma (,)
            }

            int length =rows.size();
            String[] lastRow=rows.get(length-1);          // read the last row to a new string
            lastPrice [i]= lastRow [5];                   // the 6th column in csv file was last price
            in.close();
        }
        // Draw a graph of last prices
        drawGraph(lastPrice);
    }

    private void drawGraph(String[] lastPrice) {

        // put string data into double values
        double [] value = new double[8];
        for (int i = 0; i <8; i++) {
            value[i] = Double.parseDouble(lastPrice[i]);
        }

        Stage stage = new Stage();
        stage.setTitle("VIX futures");

        // graph example from   https://docs.oracle.com/javafx/2/charts/line-chart.htm
        final CategoryAxis xAxis = new CategoryAxis();
        double minSize= Math.round(value[1]-2.0);
        double maxSize=Math.round(value[0]+1.0);
        final NumberAxis yAxis = new NumberAxis(minSize,maxSize,1);
        xAxis.setLabel("Month");

        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("VIX Term Structure");
        lineChart.setLegendVisible(false);
        XYChart.Series series = new XYChart.Series();

        String [] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July","Aug"};

        // Put data into graph series
        for (int i = 0; i <8; i++) {
            series.getData().add(new XYChart.Data(months[i], value[i]));

        }
        lineChart.getData().add(series);

        Scene scene  = new Scene(lineChart,600,400);
        scene.getStylesheets().add(Futuurid.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();

    }
}
