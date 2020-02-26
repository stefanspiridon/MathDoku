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
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;



public class Main extends Application {

/*    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }*/


    @Override
    public void start(Stage primaryStage) throws Exception{

        int n=6;

        primaryStage.setTitle("MathDoku");
        VBox vbox = new VBox();

        StackPane stackpane = new StackPane();
        stackpane.setPadding(new Insets(10, 10 , 10, 10));

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

        ArrayList<Line> hlines = new ArrayList<Line>();
        ArrayList<Line> vlines = new ArrayList<Line>();

        for (int i = 0; i <= n; i++) {
            Line hline = new Line();
            hline.setStartX(0);
            hline.setStartY(i*100);
            hline.setEndX(n*100);
            hline.setEndY(i*100);
            //hline.setStrokeWidth(3);
            hlines.add(hline);
            //hline.setStroke(Color.TRANSPARENT);

            Line vline = new Line();
            vline.setStartX(i*100);
            vline.setStartY(0);
            vline.setEndX(i*100);
            vline.setEndY(n*100);
            //vline.setStrokeWidth(3);
            vlines.add(vline);
            //vline.setStroke(Color.TRANSPARENT);

            canvas.getChildren().addAll(hline, vline);
        }

        hlines.get(0).setStrokeWidth(3);
        vlines.get(0).setStrokeWidth(3);

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
                rec.setX(j*100);
                rec.setY(i*100);
                gridPane.getChildren().addAll(rec);
            }
        }
        //Rectangle rec = (Rectangle) getNodeFromGridPane(gridPane, 2,2);
/*        for (int id : cage){
            Rectangle rec=(Rectangle) gridPane.getChildren().get(id-1);
            rec.setStroke(Color.BLACK);
        }*/

        ArrayList<ArrayList<Integer>> cageList = new ArrayList<ArrayList<Integer>>();

        File inputFile = new File("D:\\labs\\MathDoku\\src\\sample\\input.txt");
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()){
            //System.out.println(scanner.next());
            ArrayList<Integer> cage = new ArrayList<Integer>();
            String line = scanner.next();
            String[] nodes = line.split(",");

            for (String node : nodes){
                cage.add(Integer.parseInt(node));
            }

            cageList.add(cage);
        }

/*        ArrayList<Integer> cage = new ArrayList<Integer>();
        cage.add(1 - 1);
        cage.add(7 - 1);*/

        for (ArrayList<Integer> cage : cageList) {
            for (int i = 0; i < n * n; i++) {
                if (i < cage.size() - 1 && (cage.get(i) == (cage.get(i + 1)) - 1)) {
                    System.out.println("skip");
                } else {
                    Rectangle rec = (Rectangle) gridPane.getChildren().get(i);

                    int x1 = (int) rec.getX();
                    int y1 = (int) rec.getY();

                    Line line1 = new Line();
                    line1.setStartX(x1 + 100);
                    line1.setStartY(y1);
                    line1.setEndX(x1 + 100);
                    line1.setEndY(y1 + 100);
                    line1.setStroke(Color.RED);
                    line1.setStrokeWidth(3);
                    canvas.getChildren().addAll(line1);
                }

                if (i < cage.size() - 1 && (cage.get(i) == (cage.get(i + 1)) - n)) {
                    System.out.println("skip");
                } else {
                    Rectangle rec = (Rectangle) gridPane.getChildren().get(i);

                    int x1 = (int) rec.getX();
                    int y1 = (int) rec.getY();

                    Line line1 = new Line();
                    line1.setStartX(x1);
                    line1.setStartY(y1 + 100);
                    line1.setEndX(x1 + 100);
                    line1.setEndY(y1 + 100);
                    line1.setStroke(Color.BLUE);
                    line1.setStrokeWidth(3);
                    canvas.getChildren().addAll(line1);
                }
            }
        }

//DRAWS A RED LINE BETWEEN 2 HORIZONTAL CONSECUTIVE CAGE RECTANGLES
/*
        for (int i = 0; i<cage.size(); i++) {
            if(i==cage.size()-1) {
                break;
            } else {
                if (cage.get(i) == cage.get(i + 1) - 1) {
                    Rectangle rec1 = (Rectangle) gridPane.getChildren().get(cage.get(i)-1);
                    Rectangle rec2 = (Rectangle) gridPane.getChildren().get(cage.get(i + 1)-1);

                    int x1 = (int) rec1.getX();
                    int y1 = (int) rec1.getY();

                    Line line1 = new Line();
                    line1.setStartX(x1 + 100);
                    line1.setStartY(y1);
                    line1.setEndX(x1 + 100);
                    line1.setEndY(y1 + 100);
                    line1.setStroke(Color.RED);
                    line1.setStrokeWidth(3);
                    canvas.getChildren().addAll(line1);
                }
            }
        }*/

        hbox.getChildren().addAll(undo, redo, clear, file, input, mistake);
        vbox.getChildren().addAll(stackpane,hbox);
        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
