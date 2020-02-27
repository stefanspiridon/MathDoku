package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.*;

import java.io.File;
import java.util.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        int n = 6;

        primaryStage.setTitle("MathDoku");
        VBox vbox = new VBox();

        StackPane stackpane = new StackPane();
        stackpane.setPadding(new Insets(10, 10, 10, 10));

        GridPane gridPane = new GridPane();
        Pane canvas = new Pane();

        stackpane.getChildren().addAll(canvas, gridPane);

        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button clear = new Button("Clear");
        TextField file = new TextField();
        TextField input = new TextField();
        CheckBox mistake = new CheckBox();

        Line hline = new Line();
        hline.setStartX(0);
        hline.setStartY(0);
        hline.setEndX(n * 100);
        hline.setEndY(0);
        hline.setStrokeWidth(6);

        Line vline = new Line();
        vline.setStartX(0);
        vline.setStartY(0);
        vline.setEndX(0);
        vline.setEndY(n * 100);
        vline.setStrokeWidth(6);

        canvas.getChildren().addAll(hline, vline);


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(99);
                rec.setHeight(99);
                rec.setFill(Color.TRANSPARENT);
                //rec.setStroke(Color.YELLOW);
                //rec.setStrokeWidth(3);
                GridPane.setRowIndex(rec, i);
                GridPane.setColumnIndex(rec, j);
                rec.setX(j * 100);
                rec.setY(i * 100);
                gridPane.getChildren().addAll(rec);
            }
        }

        ArrayList<ArrayList<Integer>> cageList = new ArrayList<ArrayList<Integer>>();

        ArrayList<String> strings = new ArrayList<String>();

        File inputFile = new File("D:\\labs\\MathDoku\\src\\sample\\input.txt");
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            ArrayList<Integer> cage = new ArrayList<Integer>();
            String string = scanner.nextLine();

            String number = string.substring(0, string.indexOf(' '));
            strings.add(number);
            String line = string.substring(string.indexOf(' ')+1);
            //System.out.println(number);

            String[] nodes = line.split(",");
            for (String node : nodes) {
                cage.add(Integer.parseInt(node));
            }
            cageList.add(cage);



        }

        ArrayList<Line> horizontalLines = new ArrayList<Line>();
        ArrayList<Line> verticalLines = new ArrayList<Line>();

        for (int i = 0; i < n * n; i++) {
            Rectangle rec = (Rectangle) gridPane.getChildren().get(i);

            int x1 = (int) rec.getX();
            int y1 = (int) rec.getY();

            Line line1 = new Line();
            line1.setStartX(x1 + 100);
            line1.setStartY(y1);
            line1.setEndX(x1 + 100);
            line1.setEndY(y1 + 100);
            //line1.setStroke(Color.RED);
            line1.setStrokeWidth(6);
            canvas.getChildren().addAll(line1);
            verticalLines.add(line1);

            Line line2 = new Line();
            line2.setStartX(x1);
            line2.setStartY(y1 + 100);
            line2.setEndX(x1 + 100);
            line2.setEndY(y1 + 100);
            //line2.setStroke(Color.BLUE);
            line2.setStrokeWidth(6);
            canvas.getChildren().addAll(line2);
            horizontalLines.add(line2);



        }

        for (ArrayList<Integer> cage : cageList) {

            for(int i=0; i<cage.size(); i++){

                if (i < cage.size() - 1 && cage.get(i) == (cage.get(i + 1)) - 1) {
                    System.out.println("skip");
                    verticalLines.get(cage.get(i)-1).setStrokeWidth(1);
                }

                if (i < cage.size() - 1 &&cage.get(i) == (cage.get(i + 1)) - n) {
                    System.out.println("skip");
                    horizontalLines.get(cage.get(i)-1).setStrokeWidth(1);
                }

////////////////////////////////////////////////////////////////////////////////////////////
                if (i < cage.size() - 2 &&cage.get(i) == (cage.get(i + 2)) - n) {
                    System.out.println("skip");                                                 ///THIS IS REALLY BAD BUT WORKS FOR NOW///
                    horizontalLines.get(cage.get(i)-1).setStrokeWidth(1);
                }
//////////////////////////////////////////////////////////////////////////////////////////////
            }
        }

        for (int i=0; i<cageList.size(); i++){
            Rectangle rec = (Rectangle) gridPane.getChildren().get(cageList.get(i).get(0)-1);
            int x = (int) rec.getX();
            int y = (int) rec.getY();

            Text text = new Text(x+5, y+20, strings.get(i));
            text.setFont(Font.font ("Verdana", 20));
            canvas.getChildren().add(text);
        }

        hbox.getChildren().addAll(undo, redo, clear, file, input, mistake);
        vbox.getChildren().addAll(stackpane,hbox);
        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
