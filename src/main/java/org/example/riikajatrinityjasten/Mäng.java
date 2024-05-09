package org.example.riikajatrinityjasten;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Mäng extends Application {
    private StackPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new StackPane();
        näitaMänguAlgust();

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Interface");
        primaryStage.show();
    }

    private void näitaMänguAlgust() {
        root.getChildren().clear();
        Text title = new Text("🤩 Tere tulemast Riikide Äraarvamisemängu! 🤩");
        title.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));

        Button button1 = new Button("Juhend");
        Button button2 = new Button("Mängima");
        button1.getStyleClass().add("default-button");
        button2.getStyleClass().add("success-button");
        button1.setOnAction(event -> näitaJuhendit());

        HBox buttonBox = new HBox(10, button1, button2);
        buttonBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(20, title, buttonBox);
        centerBox.setAlignment(Pos.CENTER);

        centerBox.setPadding(new Insets(20));

        root.getChildren().add(centerBox);
    }

    private void näitaJuhendit() {
        root.getChildren().clear();
        Text title = new Text("\uD83D\uDCD6 Juhend \uD83D\uDCD6");
        title.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));

        Text text = new Text("Ekraanile kuvatakse vihje, nt \"See riik on kaardi peal jalakujuline, mis järel pead sina pakkuma, mis riigiga on tegemist. 😎 Kui vastasid õigesti, liidetakse sinu puntkiskoorile punkt.");
        text.setFont(Font.font("Arial", 14));
        text.setWrappingWidth(250);
        text.setTextAlignment(TextAlignment.CENTER);

        Button backButton = new Button("Tagasi");
        backButton.getStyleClass().add("default-button");

        backButton.setOnAction(event -> näitaMänguAlgust());

        VBox centerBox = new VBox(20, title, text, backButton);
        centerBox.setAlignment(Pos.CENTER);

        centerBox.setPadding(new Insets(20));

        root.getChildren().add(centerBox);
    }

    public void näitaNimeSisestust() {
        // TODO
    }

    public void näitaRiikideValikut() {
        // TODO
    }

    public void näitaSoovitudKüsimusteArvu() {
        // TODO
    }

    public void näitaKüsimust(String küsimus) {
        // veel mingid argumendid
        // TODO
    }
    public void näitaEdetabelit() {
        // TODO
    }




    public static void main(String[] args) {
        launch(args);
    }
}
