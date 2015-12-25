package mytrading;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Arvutused extends MainData {

    public void strategy1(double [][]histPrices, double []lastPrice) {
        ArrayList<String> buyTickers = new ArrayList<String>();
        ArrayList<Double> buyPrices = new ArrayList<Double>();
        ArrayList<String> sellTickers = new ArrayList<String>();
        ArrayList<Double> sellPrices = new ArrayList<Double>();

        for (int j = 0; j <tickers.length ; j++) {
            double summa = 0;
            double summa2= 0;
            symbol = tickers[j] ;
            tickerID = j;

            for (int k = 0; k <histPrices[0].length ; k++) {
                double element = histPrices [tickerID][k];
                summa += element;
            }

            keskmine = summa / histPrices[0].length;

            for (int k = 0; k <histPrices[0].length ; k++) {
                double element = (histPrices [tickerID][k] - keskmine)*(histPrices [tickerID][k] - keskmine);
                summa2 += element;
            }

            stdev = Math.sqrt(summa2 / (histPrices[0].length-1 ));

            double maxLimit = keskmine + 1.5*stdev;
            double minLimit = keskmine - 1.5*stdev;

            if (lastPrice[tickerID]< minLimit){

                buyTickers.add(symbol);
                buyPrices.add(lastPrice[tickerID]);

            } else if (lastPrice[tickerID]> maxLimit){

                sellTickers.add(symbol);
                sellPrices.add(lastPrice[tickerID]);

            }

        }
       String bollinger = new String ("latest price");
       populate(buyTickers, buyPrices, sellTickers, sellPrices, bollinger);

    }

    public void strategy2(double [][]histPrices){

        ArrayList<String> buyTickers = new ArrayList<String>();
        ArrayList<Double> rsiLevelsBuy = new ArrayList<Double>();
        ArrayList<String> sellTickers = new ArrayList<String>();
        ArrayList<Double> rsiLevelsSell = new ArrayList<Double>();

        for (int j = 0; j <tickers.length ; j++) {
            double summa3=0, summa4=0;
            symbol = tickers[j];
            tickerID = j;
            for (int k = 0; k < 14; k++) {
                int algusKoht = histPrices[0].length-14;
                double vahe = histPrices[tickerID][algusKoht+k]-histPrices[tickerID][algusKoht+k-1];
                if (vahe >= 0.0){
                    summa3 += vahe;
                }
                if (vahe < 0.0){
                    summa4 += vahe;
                }
            }
            double RSI = 100 - (100/(1-summa3/summa4));

            if (RSI< 40) {

                buyTickers.add(symbol);
                rsiLevelsBuy.add(RSI);

            } else if (RSI> 60) {

                sellTickers.add(symbol);
                rsiLevelsSell.add(RSI);
            }
        }
        String rsi = new String ("rsi");
        populate(buyTickers, rsiLevelsBuy, sellTickers, rsiLevelsSell, rsi);

    }

    public void populate(ArrayList<String> buyTickers,ArrayList<Double> buyPrices,ArrayList<String> sellTickers,ArrayList<Double> sellPrices, String dataType) {
        int buySize = buyTickers.size();
        int sellSize = sellTickers.size();
        int i =0;

        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(10);
        pane.setVgap(1);
        pane.setStyle("-fx-background-color: #F8F8F8;");
        DecimalFormat df = new DecimalFormat("####0.00");

        if (dataType.equals("rsi")){
            stage.setTitle("RSI");
        } else if (dataType.equals("latest price")){
            stage.setTitle("Bollinger");
        }

        Text results = new Text("Results:");
        pane.add(results,0,0);


        if (buySize> 0){

            String sentence = "Buy the following stocks ("+ dataType +"):";
            Text buy = new Text(sentence);
            pane.add(buy,0,i+2,5,1);
            for (i = 0; i < buySize; i++) {

                String aktsia = buyTickers.get(i);
                Hyperlink link = new Hyperlink(aktsia);
                link.setOnAction(event -> {
                    new StockData(aktsia);
                });
                pane.add(link, 0, i + 3);
                double price = buyPrices.get(i);
                String price2 = df.format(price);
                Text hind = new Text(price2);
                pane.add(hind, 1, i+3);
            }
        }  else {
            Text buy = new Text("No stocks to buy!!");
            pane.add(buy,0,i+2,3,1);
        }

        if (sellSize> 0){
            Text sell = new Text("Sell the following stocks ("+dataType+"):");
            pane.add(sell,0,i+5,5,1);

            for (int j = 0; j < sellSize; j++) {

                String aktsia = sellTickers.get(i);
                Hyperlink link = new Hyperlink(aktsia);
                link.setOnAction(event -> {
                    new StockData(aktsia);
                });
                pane.add(link, 0, i + 6+j);
                double price = sellPrices.get(i);
                String price2 = df.format(price);
                Text hind = new Text(price2);
                pane.add(hind, 1, i+6+j);
            }
        }  else {
            Text sell = new Text("No stocks to sell!!");
            pane.add(sell, 0, i + 5, 3, 1);
        }
        Scene stseen = new Scene(pane,500,500);
        stage.setScene(stseen);

        stage.show();

    }


}
