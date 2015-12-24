package mytrading;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface {

    public UserInterface(){

        Stage layout = new Stage();
        BorderPane border = new BorderPane();

        GridPane pane = addGrid1();
        border.setTop(pane);

        GridPane pane2 = addGrid2();
        border.setCenter(pane2);

        Scene stseen = new Scene(border, 500,500);
        layout.setScene(stseen);

        layout.show();
        layout.setOnCloseRequest(event -> System.exit(0));
    }

    private GridPane addGrid2() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(1);
        pane.setStyle("-fx-background-color: #F8F8F8;");

        Text results = new Text("Results:");
        pane.add(results,1,1);

        String [][] data = new String[2][2];
        data[0][0]= "GOOGL";
        data[0][1]= "30";
        data[1][0]= "AAPL";
        data[1][1]= "75";

        for (int i = 0; i <2 ; i++) {

            String aktsia = data[i][0];
            String rsivalue = data[i][1];
            Hyperlink link = new Hyperlink(aktsia);
            link.setOnAction(event -> {
                printimine(aktsia, rsivalue);
            });
            pane.add(link, 1, i+3);

            Text rsi = new Text(rsivalue);
            pane.add(rsi, 2, i + 3);

        }

        return pane;

    }

    private void printimine(String aktsia, String rsivalue) {
        System.out.println(aktsia+"  "+rsivalue);
    }

    private GridPane addGrid1() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        MainData maindata = new MainData();
        Arvutused arvutused = new Arvutused();

        Button logOut = new Button("Log out");
        pane.add(logOut, 4, 3);
        Button getData = new Button("Get data from IB");
        pane.add(getData, 1, 3, 2,1);
        getData.setOnAction(event -> {
            maindata.getData();
        });


        Button strategy1 = new Button("Bollinger");
        pane.add(strategy1, 1, 9);
        strategy1.setOnAction(event -> {
            maindata.arvutused2();
        });


        Button strategy2 = new Button("   RSI   ");
        pane.add(strategy2, 2, 9);
        Button strategy3 = new Button("VIX curve");
        pane.add(strategy3, 3, 9);

        return pane;

    }
}
