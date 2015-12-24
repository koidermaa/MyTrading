package mytrading;

public class Arvutused extends MainData {

    public void strategy1(double [][]histPrices, double []lastPrice) {
        System.out.println("algavad arvutused");
        double pikkus= histPrices[1][1];
        System.out.println("prooi number 2 on..."+pikkus);

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
            //System.out.println("keskmine on "+ keskmine);

            for (int k = 0; k <histPrices[0].length ; k++) {
                double element = (histPrices [tickerID][k] - keskmine)*(histPrices [tickerID][k] - keskmine);
                summa2 += element;
            }

            stdev = Math.sqrt(summa2 / (histPrices[0].length-1 ));

            double maxLimit = keskmine + 1.5*stdev;
            double minLimit = keskmine - 1.5*stdev;

            if (lastPrice[tickerID]< minLimit){
                System.out.println("Osta "+ symbol + " aktsiat hinnaga "+ lastPrice[tickerID]);
            } else if (lastPrice[tickerID]> maxLimit){
                System.out.println("Müü "+ symbol + " aktsiat hinnaga "+ lastPrice[tickerID]);
            }

            //System.out.println("Keskmine hind aktsial " + symbol + " on " + keskmine + " ja stdev on "+ stdev + " viimane hind on " + lastPrice[tickerID]);
        }
    }


}
