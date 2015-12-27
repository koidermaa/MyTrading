package mytrading;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class StockData {

    public StockData(String stockTicker) throws IOException {

        Stage stage = new Stage();
        stage.setTitle(stockTicker);
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setStyle("-fx-background-color: #F8F8F8;");
        pane.setHgap(10);
        pane.setVgap(5);

        // Read data from finviz url:
        String dataSource = "http://www.finviz.com/quote.ashx?t="+stockTicker;
        URL stock = new URL(dataSource);
        URLConnection gc = stock.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(gc.getInputStream()));
        String line;
        String []linesArray = new String [199];
        int i = 0;
        int l = 0;

        // Read lines 101-299 to array linesArray
        while ((line = in.readLine()) != null) {
            if (i>100 && i<300){
                linesArray[l]=line;
                l++;
            }
            i++;
        }

        in.close();

        // Setting the starting line  with integer k
        String proov ="<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" bgcolor=\"#ffffff\" class=\"fullview-title\">";
        int k=0;
        for (int j = 0; j <199; j++) {
            if (proov.equals(linesArray[j])){
                k=j;
            }
        }

        // Read data from linesArray and add to pane
        String[] companyName = linesArray[k+2].split("<b>|\\</b>");
        Text compName = new Text(companyName[1]);
        compName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        pane.add(compName, 1, 1,2,1);

        Text emptyRow = new Text();
        pane.add(emptyRow, 1, 2);

        String[] companyIndustry = linesArray[k+3].split("\"tab-link\">|\\</a>");
        Text industry = new Text("Industry");
        industry.setFont(Font.font("Verdana", 12));
        pane.add(industry, 1, 3);
        Text compIndustry = new Text(companyIndustry[1]);
        compIndustry.setFont(Font.font("Verdana", 12));
        pane.add(compIndustry, 2, 3);

        String[] marketCap = linesArray[k+33].split("<b>|\\</b>");
        Text mcap = new Text("Market cap");
        mcap.setFont(Font.font("Verdana", 12));
        pane.add(mcap, 1, 4);
        Text mCap = new Text(marketCap[1]);
        mCap.setFont(Font.font("Verdana", 12));
        pane.add(mCap, 2, 4);

        String[] pe = linesArray[k+26].split("<b>|\\</b>");
        String[]peError = pe[1].split("=\"color:#008800;\">|\\</span>");
        String[]peError2 = pe[1].split("=\"color:#aa0000;\">|\\</span>");
        Text pe1 = new Text("P/E");
        pe1.setFont(Font.font("Verdana", 12));
        pane.add(pe1, 4, 4);
        if (peError[0].equals("<span style")){
            Text pe2 = new Text(peError[1]);
            pe2.setFont(Font.font("Verdana", 12));
            pane.add(pe2, 5, 4);
        } else if(peError2[0].equals("<span style")){
            Text pe2 = new Text(peError2[1]);
            pe2.setFont(Font.font("Verdana", 12));
            pane.add(pe2, 5, 4);
        }else {
            Text pe2 = new Text(pe[1]);
            pe2.setFont(Font.font("Verdana", 12));
            pane.add(pe2, 5, 4);
        }

        String[] roe = linesArray[k+68].split("<b>|\\</b>");
        String[]roeError = roe[1].split("=\"color:#008800;\">|\\</span>");
        String[]roeError2 = roe[1].split("=\"color:#aa0000;\">|\\</span>");
        Text roe1 = new Text("ROE");
        roe1.setFont(Font.font("Verdana", 12));
        pane.add(roe1, 1, 5);
        if (roeError[0].equals("<span style")){
            Text roe2 = new Text(roeError[1]);
            roe2.setFont(Font.font("Verdana", 12));
            pane.add(roe2, 2, 5);
        } else if (roeError2[0].equals("<span style")) {
            Text roe2 = new Text(roeError2[1]);
            roe2.setFont(Font.font("Verdana", 12));
            pane.add(roe2, 2, 5);
        } else {
            Text roe2 = new Text(roe[1]);
            roe2.setFont(Font.font("Verdana", 12));
            pane.add(roe2, 2, 5);
        }

        String[] forwardPE = linesArray[k+34].split("<b>|\\</b>");
        String[]pefError = forwardPE[1].split("=\"color:#008800;\">|\\</span>");
        String[]pefError2 = forwardPE[1].split("=\"color:#aa0000;\">|\\</span>");
        Text forwardPE1 = new Text("Forward PE");
        forwardPE1.setFont(Font.font("Verdana", 12));
        pane.add(forwardPE1, 4, 5);
        if (pefError[0].equals("<span style")){
            Text forwardPE2 = new Text(pefError[1]);
            forwardPE2.setFont(Font.font("Verdana", 12));
            pane.add(forwardPE2, 5, 5);
        } else if(pefError2[0].equals("<span style")){
            Text forwardPE2 = new Text(pefError2[1]);
            forwardPE2.setFont(Font.font("Verdana", 12));
            pane.add(forwardPE2, 5, 5);
        }else {
            Text forwardPE2 = new Text(forwardPE[1]);
            forwardPE2.setFont(Font.font("Verdana", 12));
            pane.add(forwardPE2, 5, 5);
        }

        String[] sales5Y = linesArray[k+83].split("<b>|\\</b>");
        String[]salesError = sales5Y[1].split("=\"color:#008800;\">|\\</span>");
        String[]salesError2 = sales5Y[1].split("=\"color:#aa0000;\">|\\</span>");
        Text sales5Y1 = new Text("5Y Sales growth");
        sales5Y1.setFont(Font.font("Verdana", 12));
        pane.add(sales5Y1, 1, 6);
        if (salesError[0].equals("<span style")){
            Text sales5Y2 = new Text(salesError[1]);
            sales5Y2.setFont(Font.font("Verdana", 12));
            pane.add(sales5Y2, 2, 6);
        } else if(salesError2[0].equals("<span style")){
            Text sales5Y2 = new Text(salesError2[1]);
            sales5Y2.setFont(Font.font("Verdana", 12));
            pane.add(sales5Y2, 2, 6);
        } else {
            Text sales5Y2 = new Text(sales5Y[1]);
            sales5Y2.setFont(Font.font("Verdana", 12));
            pane.add(sales5Y2, 2, 6);
        }

        String[] ps = linesArray[k+50].split("<b>|\\</b>");
        String[]psError2 = ps[1].split("=\"color:#aa0000;\">|\\</span>");
        Text ps1 = new Text("P/S");
        ps1.setFont(Font.font("Verdana", 12));
        pane.add(ps1, 4, 6);
        if (psError2[0].equals("<span style")){
            Text ps2 = new Text(psError2[1]);
            ps2.setFont(Font.font("Verdana", 12));
            pane.add(ps2, 5, 6);
        } else {
            Text ps2 = new Text(ps[1]);
            ps2.setFont(Font.font("Verdana", 12));
            pane.add(ps2, 5, 6);
        }

        String[] eDate = linesArray[k+107].split("<b>|\\</b>");
        Text eDate1 = new Text("Earnings date:");
        eDate1.setFont(Font.font("Verdana", 12));
        pane.add(eDate1, 1, 7);
        Text eDate2 = new Text(eDate[1]);
        eDate2.setFont(Font.font("Verdana", 12));
        pane.add(eDate2, 2, 7);

        String[] pfcf = linesArray[k+74].split("b>|\\</b>");
        String[]pfcfError = pfcf[1].split("=\"color:#008800;\">|\\</span>");
        String[]pfcfError2 = pfcf[1].split("=\"color:#aa0000;\">|\\</span>");
        Text pfcf1 = new Text("P/FCF");
        pfcf1.setFont(Font.font("Verdana", 12));
        pane.add(pfcf1, 4, 7);
        if (pfcfError[0].equals("<span style")){
            Text pfcf2 = new Text(pfcfError[1]);
            pfcf2.setFont(Font.font("Verdana", 12));
            pane.add(pfcf2, 5, 7);
        } else if (pfcfError2[0].equals("<span style")){
            Text pfcf2 = new Text(pfcfError2[1]);
            pfcf2.setFont(Font.font("Verdana", 12));
            pane.add(pfcf2, 5, 7);
        } else {
            Text pfcf2 = new Text(pfcf[1]);
            pfcf2.setFont(Font.font("Verdana", 12));
            pane.add(pfcf2, 5, 7);
        }

        String[] perfYtd = linesArray[k+70].split(";\">|\\</span>");
        Text perfYtd1 = new Text("Perf YTD");
        perfYtd1.setFont(Font.font("Verdana", 12));
        pane.add(perfYtd1, 1, 8);
        Text perfYtd2 = new Text(perfYtd[1]);
        perfYtd2.setFont(Font.font("Verdana", 12));
        pane.add(perfYtd2, 2, 8);

        String[] divYield = linesArray[k+81].split("<b>|\\</b>");
        Text divYield1 = new Text("Div yield");
        divYield1.setFont(Font.font("Verdana", 12));
        pane.add(divYield1, 4, 8);
        Text divYield2 = new Text(divYield[1]);
        divYield2.setFont(Font.font("Verdana", 12));
        pane.add(divYield2, 5, 8);

        // Get the graph from finviz
        String picSource = "http://www.finviz.com/chart.ashx?t="+stockTicker+"&ty=c&ta=1&p=d&s=l";
        Image img = new Image(picSource);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(340);
        imgView.setFitWidth(700);
        pane.add(imgView, 1, 11, 20, 1);

        Scene stseen = new Scene(pane, 740,550);
        stage.setScene(stseen);
        stage.show();
    }
}
