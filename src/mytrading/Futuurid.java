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

        for (int i = 0; i <8; i++) {
            URL cboe = new URL(urlList[i]);
            URLConnection gc = cboe.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(gc.getInputStream()));
            String line;
            LinkedList<String[]> rows = new LinkedList<String[]>();

            while ((line = in.readLine()) != null) {
                rows.add(line.split(","));

            }

            int pikkus =rows.size();
            String[] proov=rows.get(pikkus-1);
            lastPrice [i]= proov [5];
            in.close();
        }

        System.out.println(lastPrice[0]+" "+ lastPrice[1]+" "+lastPrice[2]+" "+ lastPrice[3]+" "+lastPrice[4]+" "+ lastPrice[5]+" "+lastPrice[6]+" "+ lastPrice[7]);

        drawGraph(lastPrice);

    }

    private void drawGraph(String[] lastPrice) {
        Stage stage = new Stage();
        stage.setTitle("VIX futures");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(15,22,1);
        xAxis.setLabel("Month");

        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("VIX Term Structure");
        lineChart.setLegendVisible(false);
        XYChart.Series series = new XYChart.Series();

        String [] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July","Aug"};
        double [] value = new double[8];
        for (int i = 0; i <8; i++) {
            value[i] = Double.parseDouble(lastPrice[i]);
        }

        for (int i = 0; i <8; i++) {
            series.getData().add(new XYChart.Data(months[i], value[i]));

        }

        Scene scene  = new Scene(lineChart,600,400);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();



    }
}
