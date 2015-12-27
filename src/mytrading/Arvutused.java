package mytrading;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Arvutused extends MainData {

    public void strategy1(double [][]histPrices, double []lastPrice) {
        ArrayList<String> buyTickers = new ArrayList<String>();
        ArrayList<Double> buyPrices = new ArrayList<Double>();
        ArrayList<String> sellTickers = new ArrayList<String>();
        ArrayList<Double> sellPrices = new ArrayList<Double>();

        for (int j = 0; j <tickers.length ; j++) {
            double sum = 0;
            double sum2= 0;
            symbol = tickers[j] ;
            tickerID = j;

            // calculates historical average for each stock
            for (int k = 0; k <histPrices[0].length ; k++) {
                double element = histPrices [tickerID][k];
                sum += element;
            }

            average = sum / histPrices[0].length;

            // calculates standard deviation for each stock
            for (int k = 0; k <histPrices[0].length ; k++) {
                double element = (histPrices [tickerID][k] - average)*(histPrices [tickerID][k] - average);
                sum2 += element;
            }

            stdev = Math.sqrt(sum2 / (histPrices[0].length-1 ));

            // here you can modify the conditions (average +- 1.5stdev)
            double maxLimit = average + 1.5*stdev;
            double minLimit = average - 1.5*stdev;

            // build new Arraylist of stock symbols if conditions met
            if (lastPrice[tickerID]< minLimit){
                buyTickers.add(symbol);
                buyPrices.add(lastPrice[tickerID]);

            } else if (lastPrice[tickerID]> maxLimit){
                sellTickers.add(symbol);
                sellPrices.add(lastPrice[tickerID]);
            }
        }
       String bollinger = new String ("latest price");
       // Go to results presentation method
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

            // calculates RSI level for each stock
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

            // build new Arraylist of stock symbols if conditions met
            if (RSI< 40) {
                buyTickers.add(symbol);
                rsiLevelsBuy.add(RSI);

            } else if (RSI> 60) {
                sellTickers.add(symbol);
                rsiLevelsSell.add(RSI);
            }
        }
        String rsi = new String ("rsi");
        // Go to results presentation method
        populate(buyTickers, rsiLevelsBuy, sellTickers, rsiLevelsSell, rsi);
    }

    public void populate(ArrayList<String> buyTickers,ArrayList<Double> buyValues,ArrayList<String> sellTickers,ArrayList<Double> sellValues, String dataType) {
        int buySize = buyTickers.size();
        int sellSize = sellTickers.size();
        int i =0;

        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,50,30,10));
        pane.setHgap(10);
        pane.setVgap(3);
        pane.setStyle("-fx-background-color: #F8F8F8;");
        DecimalFormat df = new DecimalFormat("####0.00");

        // set title according to datatype
        if (dataType.equals("rsi")){
            stage.setTitle("RSI");
        } else if (dataType.equals("latest price")){
            stage.setTitle("Bollinger");
        }

        Text results = new Text("Results:");
        results.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        pane.add(results,0,0);
        Text emptyRow = new Text();
        pane.add(emptyRow, 0, 1);

        // If there are stocks in buyTickers array
        if (buySize> 0){
            String sentence = "Buy the following stocks ("+ dataType +"):";
            Text buy = new Text(sentence);
            buy.setFont(Font.font("Verdana", 12));
            pane.add(buy,0,i+2,5,1);
            for (i = 0; i < buySize; i++) {

                //Prints a hyperlink for each stock and adds required values to second column
                String stockSymbol = buyTickers.get(i);
                Hyperlink link = new Hyperlink(stockSymbol);
                link.setOnAction(event -> {
                    try {
                        new StockData(stockSymbol);   // links to StockData class
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                link.setFont(Font.font("Verdana", 12));
                pane.add(link, 0, i + 3);
                double value = buyValues.get(i);
                String value2 = df.format(value);
                Text value3 = new Text(value2);
                value3.setFont(Font.font("Verdana", 12));
                pane.add(value3, 1, i+3);
            }
        }  else {
            Text buy = new Text("No stocks to buy!!");
            buy.setFont(Font.font("Verdana", 12));
            pane.add(buy,0,i+2,3,1);
        }

        Text emptyRow2 = new Text();
        pane.add(emptyRow2, 0, i+4);

        // If there are stocks in sellTickers array
        if (sellSize> 0){
            Text sell = new Text("Sell the following stocks ("+dataType+"):");
            sell.setFont(Font.font("Verdana", 12));
            pane.add(sell,0,i+5,5,1);

            //Prints a hyperlink for each stock and adds required values to second column
            for (int j = 0; j < sellSize; j++) {

                String aktsia = sellTickers.get(j);
                Hyperlink link = new Hyperlink(aktsia);
                link.setOnAction(event -> {
                    try {
                        new StockData(aktsia);          // links to StockData class
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                link.setFont(Font.font("Verdana", 12));
                pane.add(link, 0, i + 6+j);
                double value = sellValues.get(j);
                String value2 = df.format(value);
                Text value3 = new Text(value2);
                value3.setFont(Font.font("Verdana", 12));
                pane.add(value3, 1, i+6+j);
            }
        }  else {
            Text sell = new Text("No stocks to sell!!");
            sell.setFont(Font.font("Verdana", 12));
            pane.add(sell, 0, i + 5, 3, 1);
        }

        Scene stseen = new Scene(pane);
        stage.setScene(stseen);

        stage.show();
    }
}
