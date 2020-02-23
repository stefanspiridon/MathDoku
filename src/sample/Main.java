package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("MathDoku");
        FlowPane flowpane = new FlowPane();
        HBox hbox = new HBox(10);
        flowpane.setVgap(10);
        flowpane.setHgap(10);
        flowpane.setPrefWrapLength(600);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button clear = new Button("Clear");
        TextField file = new TextField();
        TextField input = new TextField();
        CheckBox mistake = new CheckBox();


        hbox.getChildren().addAll(undo, redo, clear, file, input, mistake);
        flowpane.getChildren().add(hbox);
        primaryStage.setScene(new Scene(flowpane));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
