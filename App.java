import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    private double firstNumber = 0;
    private String operator = "";

    @Override
    public void start(Stage stage) {

        // Display field
        TextField display = new TextField();
        display.setEditable(false);
        display.setPrefHeight(60);
        display.setFont(Font.font(24));
        display.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: center-right;");

        // Grid for buttons
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: #202124;");

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        int row = 1;
        int col = 0;

        for (String text : buttons) {
            Button btn = new Button(text);
            btn.setPrefSize(60, 60);
            btn.setFont(Font.font(18));
            btn.setTextFill(Color.WHITE);

            // Style buttons
            if (text.matches("[0-9]")) {
                btn.setStyle("-fx-background-color: #4a4a4a;");
            } else if (text.matches("[+\\-*/]")) {
                btn.setStyle("-fx-background-color: #ff9500;");
            } else if (text.equals("=")) {
                btn.setStyle("-fx-background-color: #34c759;");
            } else if (text.equals("C")) {
                btn.setStyle("-fx-background-color: #ff3b30;");
            }

            // Button action
            btn.setOnAction(e -> {
                switch (text) {
                    case "C" -> {
                        display.clear();
                        firstNumber = 0;
                        operator = "";
                    }
                    case "=" -> {
                        try {
                            double secondNumber = Double.parseDouble(display.getText());
                            double result = calculate(firstNumber, secondNumber, operator);
                            display.setText(String.valueOf(result));
                        } catch (NumberFormatException ex) {
                            display.setText("Error");
                        }
                    }
                    case "+", "-", "*", "/" -> {
                        try {
                            firstNumber = Double.parseDouble(display.getText());
                            operator = text;
                            display.clear();
                        } catch (NumberFormatException ex) {
                            display.setText("Error");
                        }
                    }
                    default -> display.appendText(text);
                }
            });

            grid.add(btn, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        // Add display to grid
        grid.add(display, 0, 0, 4, 1);

        // Scene and Stage
        Scene scene = new Scene(grid, 280, 350);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    private double calculate(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b == 0 ? 0 : a / b;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
