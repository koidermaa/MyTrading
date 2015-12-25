package mytrading;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserInterface {

    Stage layout = new Stage();
    BorderPane border = new BorderPane();

    public UserInterface(){

        layout.setTitle("MyTrading 2.0");
        GridPane pane = addGrid1();
        border.setTop(pane);

        Scene stseen = new Scene(border,300,200);
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
           maindata.arvutused2();
        });

        Button strategy2 = new Button("   RSI   ");
        pane.add(strategy2, 2, 9);
        strategy2.setOnAction(event -> {
           maindata.arvutused3();
        });

        Button strategy3 = new Button("VIX curve");
        pane.add(strategy3,3, 9);
        strategy3.setOnAction(event ->  {
            try {
                new Futuurid();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return pane;
    }


}
