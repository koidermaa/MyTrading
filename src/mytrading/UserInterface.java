package mytrading;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserInterface {

    Stage layout = new Stage();
    BorderPane border = new BorderPane();

    public UserInterface(){

        layout.setTitle("MyTrading 2.0");
        GridPane pane = addGrid1();
        border.setTop(pane);

        Scene stseen = new Scene(border, 500,500);
        layout.setScene(stseen);

        layout.show();
        layout.setOnCloseRequest(event -> System.exit(0));
    }

    private GridPane addGrid1() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(10);
        pane.setVgap(5);
        MainData maindata = new MainData();

        Button logOut = new Button("Log out");
        pane.add(logOut, 3, 1);
        Button getData = new Button("Get data from IB");
        pane.add(getData, 1, 1, 2,1);
        getData.setOnAction(event -> {
            maindata.getData();
        });

        Button strategy1 = new Button("Bollinger");
        pane.add(strategy1, 1, 9);
        strategy1.setOnAction(event -> {
            layout.close();
            maindata.arvutused2();
        });

        Button strategy2 = new Button("   RSI   ");
        pane.add(strategy2, 2, 9);
        strategy2.setOnAction(event -> {
            maindata.arvutused3();
        });

        Button strategy3 = new Button("VIX curve");
        pane.add(strategy3,3, 9);

        return pane;
    }

    public void populate(ArrayList<String> buyTickers,ArrayList<Double> buyPrices,ArrayList<String> sellTickers,ArrayList<Double> sellPrices) {
        int buySize = buyTickers.size();
        int sellSize = sellTickers.size();
        int i =0;

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(10);
        pane.setVgap(1);
        pane.setStyle("-fx-background-color: #F8F8F8;");

        Text results = new Text("Results:");
        pane.add(results,0,0);

        if (buySize> 0){
            Text buy = new Text("Buy the following stocks (latest price):");
            pane.add(buy,0,i+2,5,1);
            for (i = 0; i < buySize; i++) {

                String aktsia = buyTickers.get(i);
                Hyperlink link = new Hyperlink(aktsia);
                link.setOnAction(event -> {
                    new StockData(aktsia);
                });
                pane.add(link, 0, i + 3);
                double price = buyPrices.get(i);
                String price2 = Double.toString(price);
                Text hind = new Text(price2);
                pane.add(hind, 1, i+3);
                ;
            }
        }  else {
            Text buy = new Text("No stocks to buy!!");
            pane.add(buy,0,i+2,3,1);
        }

        if (sellSize> 0){
            Text sell = new Text("Sell the following stocks (latest price):");
            pane.add(sell,0,i+5,5,1);

            for (int j = 0; j < sellSize; j++) {

                String aktsia = sellTickers.get(i);
                Hyperlink link = new Hyperlink(aktsia);
                link.setOnAction(event -> {
                    System.out.println(aktsia);
                });
                pane.add(link, 0, i + 6+ j);
                ;
            }
        }  else {
            Text sell = new Text("No stocks to sell!!");
            pane.add(sell,0,i+5,3,1);
        }

        border.setCenter(pane);
    }
}
