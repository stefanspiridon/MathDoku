package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.event.EventHandler;
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
QUESTIONS:
DO I NEED TO CLEAR A CELL BEFORE I ENTER ANOTHER VALUE?

*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Main extends Application {

    //NUMBER TO FILL THE BOXES WITH
    String num;

    //BOX CLASS
    public class Box{
        Rectangle rec = new Rectangle();
        Text text = new Text();
        boolean flag=false;

        Box(){
            Text number = new Text();
        }

        public Rectangle getRec() {
            return rec;
        }

        public void setRec(Rectangle rec) {
            this.rec = rec;
        }

        public boolean getFlag(){
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public Text getText() {
            return text;
        }

        public void setText(Text text) {
            this.text = text;
        }
    }

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

        //HBOX TO STORE DIFFERENT BUTTONS
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        Button undo = new Button("Undo");
        Button redo = new Button("Redo");
        Button clear = new Button("Clear");
        TextField file = new TextField();
        TextField input = new TextField();
        CheckBox mistake = new CheckBox();

        //HBOX TO STORE NUMBER GUI BUTTONS
        HBox numberBox = new HBox(5);
        numberBox.setPadding(new Insets(10, 10, 10, 10));

        //LIST TO STORE BOXES
        ArrayList<Box> boxList = new ArrayList<Box>();

        //NUMBER GUI BUTTONS CREATION AND EVENT HANDLING
        for (int i =1; i<=n; i++){
            Button button = new Button(String.valueOf(i));
            int finalI = i;

            button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                num=String.valueOf(finalI);
                for(Box box : boxList){
                    if(box.getFlag()){
                        int x = (int) ((int) box.getRec().getX() + box.getRec().getWidth()/2);
                        int y = (int) ((int) box.getRec().getY()+box.getRec().getHeight()/2);

                        //Text number = new Text(x-13, y+19,num);
                        box.getText().setX(x-13);
                        box.getText().setY(y+19);
                        box.getText().setFont(Font.font ("Comic Sans MS", 50));
                        box.getText().setText(num);
                        canvas.getChildren().add(box.getText());
                        //box.setText(box.getText());

                        box.setFlag(false);
                        box.getRec().setStroke(Color.TRANSPARENT);
                    }
                }
            });
            numberBox.getChildren().add(button);
        }

        //BACKSPACE GUI BUTTON
        Button backspace = new Button("⌫");
        backspace.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            for(Box box : boxList){
                if(box.getFlag()){
                    canvas.getChildren().remove(box.getText());
                    box.getText().setText(null);
                    box.setFlag(false);
                    box.getRec().setStroke(Color.TRANSPARENT);
                }
            }
        });
        numberBox.getChildren().addAll(backspace);

        //DEFAULT BORDER LINES
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

        //CREATES THE BOXES WITH THE RECTANGLES
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(99);
                rec.setHeight(99);
                rec.setFill(Color.TRANSPARENT);
                //rec.setStroke(Color.YELLOW);
                rec.setStrokeWidth(1);
                //StackPane miniStack = new StackPane(rec);
                GridPane.setRowIndex(rec, i);
                GridPane.setColumnIndex(rec, j);
                rec.setX(j * 100);
                rec.setY(i * 100);
                gridPane.getChildren().addAll(rec);

                Box box = new Box();
                box.setRec(rec);
                boxList.add(box);
            }
        }

        //EVENT HANDLER FOR CHANGING THE SELECTED FLAG
        for(Box box : boxList){
            box.getRec().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                box.setFlag(true);
                box.getRec().setStroke(Color.PINK);
            });
        }

        //EVENT HANDLER FOR DIGIT KEYS
        vbox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //System.out.println(keyEvent.getCode());
                for (int i = 1; i <= n; i++) {
                    if ((String.valueOf(keyEvent.getCode())).equals("DIGIT"+String.valueOf(i))) {
                        num = String.valueOf(i);
                        for (Box box : boxList) {
                            if (box.getFlag()) {

                                int x = (int) ((int) box.getRec().getX() + box.getRec().getWidth() / 2);
                                int y = (int) ((int) box.getRec().getY() + box.getRec().getHeight() / 2);

                                box.getText().setX(x - 13);
                                box.getText().setY(y + 19);
                                box.getText().setFont(Font.font("Comic Sans MS", 50));
                                box.getText().setText(num);
                                canvas.getChildren().add(box.getText());
                                //box.setText(box.getText());

                                box.setFlag(false);
                                box.getRec().setStroke(Color.TRANSPARENT);
                            }
                        }
                    }

                    if(keyEvent.getCode()==KeyCode.BACK_SPACE){
                        for(Box box : boxList){
                            if(box.getFlag()){
                                canvas.getChildren().remove(box.getText());
                                box.getText().setText(null);
                                box.setFlag(false);
                                box.getRec().setStroke(Color.TRANSPARENT);
                            }
                        }
                    }
                }
            }
        });


        //READING FROM FILE AND CREATING THE GRID
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
            text.setFont(Font.font ("Comic Sans MS", 20));
            canvas.getChildren().add(text);
        }

        hbox.getChildren().addAll(undo, redo, clear, file, input, mistake);
        vbox.getChildren().addAll(stackpane, hbox, numberBox);
        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
