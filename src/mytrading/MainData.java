package mytrading;
import com.ib.client.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class MainData implements EWrapper{
    private EClientSocket client = null;
    double controlPrice =0.0, average, stdev, control=0.0;
    int i =0;

    double [][] histPrices = new double[13][30];                                      // first is no of tickers, second no of days
    double [] lastPrice = new double [13];                                            // size is no of tickers
    String symbol;
    int tickerID = 0;
    String [] tickers = {"AAPL","IBM","JPM", "ORCL", "AMZN", "FB", "TWTR","NFLX", "TSLA","BABA","DIS","MCD","GE",};   // ticker entry here

    public double getData(){

        EClientSocket client = new EClientSocket(this);         //standard process
        client.eConnect(null, 7496, 0);                         //creates connection
        try
        {
            Thread.sleep (2000);                                //break 2.0sec to confirm connection
        } catch (Exception e) {
        }

        long startTime, endTime;
        double time;
        startTime= System.currentTimeMillis();

        dataReq(client);                                        //method to get the data

        endTime= System.currentTimeMillis();
        time = (endTime - startTime)/1000.0;
        return time;
    }

    public void dataReq(EClientSocket client){

        String date = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());

        // defining the contract
        for (int j=0; j<tickers.length; j++) {
            symbol = tickers[j];

            Contract contract = new Contract();
            contract.m_symbol = symbol;
            contract.m_secType = "STK";
            contract.m_exchange = "SMART";
            contract.m_currency = "USD";
            Vector<TagValue> XYZ = new Vector<TagValue>();
            client.reqHistoricalData(tickerID, contract, date, "30 D", "1 day", "TRADES", 1, 1, XYZ);   // gets historical data

            Vector mktDataOptions = new Vector();
            client.reqMktData(tickerID, contract, null, true, mktDataOptions);      // gets the most recent prices

            tickerID=tickerID+1;
        }

        // waits until data has been received
        for (double i = 0; i < 350; i++) {
            try {
                if (control== 0.0 || controlPrice == 0.0) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
            }
        }
    }

    public void arvutused2() {
        Arvutused arvutused = new Arvutused();
        arvutused.strategy1(histPrices,lastPrice);   //takes on data arrays
    }

    public void arvutused3() {
        Arvutused arvutused = new Arvutused();
        arvutused.strategy2(histPrices);            //takes on data arrays
    }

    //  Historical prices arrive in this method
    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
        if (close!= -1.0){              // the last price in the dataset is always -1.0
            histPrices[reqId][i]= close;
            i++;}
        else {
            i=0;
        }

        if (reqId == tickers.length-1 && close== -1.0){         // the dataset is downloaded, if the last price of the last ticker is here
            controlPrice = 1.0;
            System.out.println("ajalugu käes");}
    }

    //Current prices arrive in this method
    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {

        if (field==4){                              //field 4 is current price
            lastPrice[tickerId]=price;
        }
        if (lastPrice[tickerId]==0 && field==9){        //field 9 is last close
            lastPrice[tickerId]=price;
        }
        if (tickerId == tickers.length-1&& field==9){       // if the last ticker is received control = 1.0
            control = 1.0;
            System.out.println("hinnad käes");
        }
    }

    public void contractDetails(int reqId, ContractDetails contractDetails) {

    }
    public void contractDetailsEnd(int reqId)
    {

    }

    @Override
    public void tickSize(int tickerId, int field, int size) {

    }

    @Override
    public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {

    }

    @Override
    public void tickGeneric(int tickerId, int tickType, double value) {

    }

    @Override
    public void tickString(int tickerId, int tickType, String value) {

    }

    @Override
    public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {

    }

    @Override
    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {

    }

    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {

    }

    @Override
    public void openOrderEnd() {

    }

    @Override
    public void updateAccountValue(String key, String value, String currency, String accountName) {

    }

    @Override
    public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {

    }

    @Override
    public void updateAccountTime(String timeStamp) {

    }

    @Override
    public void accountDownloadEnd(String accountName) {

    }

    @Override
    public void nextValidId(int orderId) {

    }



    @Override
    public void bondContractDetails(int reqId, ContractDetails contractDetails) {

    }



    @Override
    public void execDetails(int reqId, Contract contract, Execution execution) {

    }

    @Override
    public void execDetailsEnd(int reqId) {

    }

    @Override
    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {

    }

    @Override
    public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {

    }

    @Override
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {

    }

    @Override
    public void managedAccounts(String accountsList) {

    }

    @Override
    public void receiveFA(int faDataType, String xml) {

    }



    @Override
    public void scannerParameters(String xml) {

    }

    @Override
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {

    }

    @Override
    public void scannerDataEnd(int reqId) {

    }

    @Override
    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {

    }

    @Override
    public void currentTime(long time) {

    }

    @Override
    public void fundamentalData(int reqId, String data) {

    }

    @Override
    public void deltaNeutralValidation(int reqId, UnderComp underComp) {

    }

    @Override
    public void tickSnapshotEnd(int reqId) {

    }

    @Override
    public void marketDataType(int reqId, int marketDataType) {

    }

    @Override
    public void commissionReport(CommissionReport commissionReport) {

    }

    @Override
    public void position(String account, Contract contract, int pos, double avgCost) {

    }

    @Override
    public void positionEnd() {

    }

    @Override
    public void accountSummary(int reqId, String account, String tag, String value, String currency) {

    }

    @Override
    public void accountSummaryEnd(int reqId) {

    }

    @Override
    public void verifyMessageAPI(String apiData) {

    }

    @Override
    public void verifyCompleted(boolean isSuccessful, String errorText) {

    }

    @Override
    public void displayGroupList(int reqId, String groups) {

    }

    @Override
    public void displayGroupUpdated(int reqId, String contractInfo) {

    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public void error(String str) {

    }

    @Override
    public void error(int id, int errorCode, String errorMsg) {

    }

    @Override
    public void connectionClosed() {

    }


}
