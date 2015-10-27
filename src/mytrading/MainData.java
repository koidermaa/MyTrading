package mytrading;

import com.ib.client.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Arrays;
import java.util.Vector;

public class MainData implements EWrapper{
    private int orderId = 0;
    private EClientSocket client = null;
    private double lastPrice =0.0;     // siia saab muutujaid, mis võtavad meetoditest datat
    int i =0;
    double [] a = new double[6];

    public MainData (){
        EClientSocket client = new EClientSocket(this);
        client.eConnect(null, 7496, 0);

        try
        {
            Thread.sleep (2000);
            while (! (client.isConnected()));
        } catch (Exception e)
        {

        }

        String [] tickerid = {"AAPL","IBM","GOOGL"};
        String symbol;
        for (int j=0; j<3; j++) {
            symbol = tickerid[j];

            Contract contract = new Contract();
            contract.m_symbol = symbol;
            contract.m_secType = "STK";
            contract.m_exchange = "SMART";
            contract.m_currency = "USD";
            Vector<TagValue> XYZ = new Vector<TagValue>();
            int tickerID = 10000001;
            client.reqHistoricalData(tickerID, contract, "20151022 22:30:00", "5 D", "1 day", "TRADES", 1, 1, XYZ);
            //int tickerID = 10000002;
            // client.reqContractDetails(tickerID, contract);

            try {
                if (lastPrice != -1.0) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
            }

            arvutused(symbol);
            i=0;
            lastPrice=0.0;
        }
        System.out.println("protsessi lõpp");

        client.eDisconnect();



    }
    public void arvutused(String symbol){
        double keskmine;
        keskmine =( a[0]+a[1]+a[2]+a[3]+a[4])/5;
        System.out.println("Keskmine hind aktsial "+symbol+" on "+keskmine);

    }

    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {

        a[i]= close;
        i++;
        lastPrice = close;
    }



    public void contractDetails(int reqId, ContractDetails contractDetails) {
        System.out.println(reqId);
        System.out.println("sain katte");

    }
    public void contractDetailsEnd(int reqId)
    {
        System.out.println("thats all");

    }


    @Override
    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {

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
    public static void main (String args[])
    {
        try
        {
            MainData myData = new MainData();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }

        System.out.println("protsessi lopp2");

    }


}
