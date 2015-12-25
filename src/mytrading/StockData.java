package mytrading;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockData {

    public StockData(String aktsia){

        Stage stage = new Stage();
        stage.setTitle(aktsia);
        GridPane pane = new GridPane();

        Text hind = new Text(aktsia);
        pane.add(hind, 1, 1);

        Scene stseen = new Scene(pane, 300,300);
        stage.setScene(stseen);

        stage.show();
    }
}
