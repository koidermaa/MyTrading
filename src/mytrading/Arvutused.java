package mytrading;

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
        UserInterface user = new UserInterface();
        user.populate(buyTickers, buyPrices, sellTickers, sellPrices);

    }

    public void strategy2(double [][]histPrices){
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
            //System.out.println(symbol + " RSI on " + RSI+"  "+ summa3 + "  "+summa4);
            if (RSI< 40) {
                System.out.println("osta "+symbol +" RSI on ju "+ RSI);
            } else if (RSI> 60) {
                System.out.println("müü "+symbol +" RSI on ju " + RSI);
            }
        }
    }

}
