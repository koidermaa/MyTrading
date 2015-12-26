package mytrading;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

    public StockData(String aktsia) throws IOException {

        Stage stage = new Stage();
        stage.setTitle(aktsia);
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(10);
        pane.setVgap(3);

        String dataSource = "http://www.finviz.com/quote.ashx?t="+aktsia;

        URL stock = new URL(dataSource);
        URLConnection gc = stock.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(gc.getInputStream()));
        String line;
        ArrayList<String> lines = new ArrayList<String>();
        int i = 0;

        while ((line = in.readLine()) != null) {
            if (i>100 && i<300){
                lines.add(line);
            }
            i++;
        }

        in.close();

        String[] linesArray = lines.toArray(new String[lines.size()]);
        String proov ="<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" bgcolor=\"#ffffff\" class=\"fullview-title\">";
        int k=0;
        for (int j = 0; j <199; j++) {
            if (proov.equals(linesArray[j])){
                k=j;
            }
        }

        String[] companyName = linesArray[k+2].split("<b>|\\</b>");
        Text compName = new Text(companyName[1]);
        pane.add(compName, 1, 1);

        String[] companyIndustry = linesArray[k+3].split("\"tab-link\">|\\</a>");
        Text industry = new Text("Industry");
        pane.add(industry, 1, 2);
        Text compIndustry = new Text(companyIndustry[1]);
        pane.add(compIndustry, 2, 2);

        String[] marketCap = linesArray[k+33].split("<b>|\\</b>");
        Text mcap = new Text("Market cap");
        pane.add(mcap, 1, 3);
        Text mCap = new Text(marketCap[1]);
        pane.add(mCap, 2, 3);

        String[] pe = linesArray[k+26].split("<b>|\\</b>");
        String[]peError = pe[1].split("=\"color:#008800;\">|\\</span>");
        String[]peError2 = pe[1].split("=\"color:#aa0000;\">|\\</span>");
        Text pe1 = new Text("P/E");
        pane.add(pe1, 4, 3);
        if (peError[0].equals("<span style")){
            Text pe2 = new Text(peError[1]);
            pane.add(pe2, 5, 3);
        } else if(peError2[0].equals("<span style")){
            Text pe2 = new Text(peError2[1]);
            pane.add(pe2, 5, 3);
        }else {
            Text pe2 = new Text(pe[1]);
            pane.add(pe2, 5, 3);
        }

        String[] roe = linesArray[k+68].split("<b>|\\</b>");
        String[]roeError = roe[1].split("=\"color:#008800;\">|\\</span>");
        String[]roeError2 = roe[1].split("=\"color:#aa0000;\">|\\</span>");
        Text roe1 = new Text("ROE");
        pane.add(roe1, 1, 4);

        if (roeError[0].equals("<span style")){
            Text roe2 = new Text(roeError[1]);
            pane.add(roe2, 2, 4);
        } else if (roeError2[0].equals("<span style")) {
            Text roe2 = new Text(roeError2[1]);
            pane.add(roe2, 2, 4);
        } else {
            Text roe2 = new Text(roe[1]);
            pane.add(roe2, 2, 4);
        }

        String[] forwardPE = linesArray[k+34].split("<b>|\\</b>");
        String[]pefError = forwardPE[1].split("=\"color:#008800;\">|\\</span>");
        String[]pefError2 = forwardPE[1].split("=\"color:#aa0000;\">|\\</span>");
        Text forwardPE1 = new Text("Forward PE");
        pane.add(forwardPE1, 4, 4);
        if (pefError[0].equals("<span style")){
            Text forwardPE2 = new Text(pefError[1]);
            pane.add(forwardPE2, 5, 4);
        } else if(pefError2[0].equals("<span style")){
            Text forwardPE2 = new Text(pefError2[1]);
            pane.add(forwardPE2, 5, 4);
        }else {
            Text forwardPE2 = new Text(forwardPE[1]);
            pane.add(forwardPE2, 5, 4);
        }

        String[] sales5Y = linesArray[k+83].split("<b>|\\</b>");
        String[]salesError = sales5Y[1].split("=\"color:#008800;\">|\\</span>");
        String[]salesError2 = sales5Y[1].split("=\"color:#aa0000;\">|\\</span>");
        Text sales5Y1 = new Text("5Y Sales growth");
        pane.add(sales5Y1, 1, 5);
        if (salesError[0].equals("<span style")){
            Text sales5Y2 = new Text(salesError[1]);
            pane.add(sales5Y2, 2, 5);
        } else if(salesError2[0].equals("<span style")){
            Text sales5Y2 = new Text(salesError2[1]);
            pane.add(sales5Y2, 2, 5);
        } else {
            Text sales5Y2 = new Text(sales5Y[1]);
            pane.add(sales5Y2, 2, 5);
        }

        String[] ps = linesArray[k+50].split("<b>|\\</b>");
        String[]psError2 = ps[1].split("=\"color:#aa0000;\">|\\</span>");
        Text ps1 = new Text("P/S");
        pane.add(ps1, 4, 5);
        if (psError2[0].equals("<span style")){
            Text ps2 = new Text(psError2[1]);
            pane.add(ps2, 5, 5);
        } else {
            Text ps2 = new Text(ps[1]);
            pane.add(ps2, 5, 5);
        }

        String[] eDate = linesArray[k+107].split("<b>|\\</b>");
        Text eDate1 = new Text("Earnings date:");
        pane.add(eDate1, 1, 6);
        Text eDate2 = new Text(eDate[1]);
        pane.add(eDate2, 2, 6);

        String[] pfcf = linesArray[k+74].split("b>|\\</b>");
        String[]pfcfError = pfcf[1].split("=\"color:#008800;\">|\\</span>");
        String[]pfcfError2 = pfcf[1].split("=\"color:#aa0000;\">|\\</span>");
        Text pfcf1 = new Text("P/FCF");
        pane.add(pfcf1, 4, 6);
        if (pfcfError[0].equals("<span style")){
            Text pfcf2 = new Text(pfcfError[1]);
            pane.add(pfcf2, 5, 6);
        } else if (pfcfError2[0].equals("<span style")){
            Text pfcf2 = new Text(pfcfError2[1]);
            pane.add(pfcf2, 5, 6);
        } else {
            Text pfcf2 = new Text(pfcf[1]);
            pane.add(pfcf2, 5, 6);
        }

        String[] perfYtd = linesArray[k+70].split(";\">|\\</span>");
        Text perfYtd1 = new Text("Perf YTD");
        pane.add(perfYtd1, 1, 7);
        Text perfYtd2 = new Text(perfYtd[1]);
        pane.add(perfYtd2, 2, 7);

        String[] divYield = linesArray[k+81].split("<b>|\\</b>");
        Text divYield1 = new Text("Div yield");
        pane.add(divYield1, 4, 7);
        Text divYield2 = new Text(divYield[1]);
        pane.add(divYield2, 5, 7);

        String picSource = "http://www.finviz.com/chart.ashx?t="+aktsia+"&ty=c&ta=1&p=d&s=l";

        Image img = new Image(picSource);
        ImageView imgView = new ImageView(img);

        imgView.setFitHeight(340);
        imgView.setFitWidth(700);
        pane.add(imgView, 1, 11, 20, 1);

        Scene stseen = new Scene(pane, 720,500);
        stage.setScene(stseen);

        stage.show();
    }
}
