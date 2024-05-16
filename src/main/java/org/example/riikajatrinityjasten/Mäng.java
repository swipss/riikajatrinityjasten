package org.example.riikajatrinityjasten;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Arrays;

public class Mäng extends Application {
    private StackPane root;
    private Mängija praeguneMängija;
    private MängijateHaldur haldur = MängijateHaldur.getInstance();
    private Riigid riigid;
    private int küsimusteArv;


    public void määraPraeguneMängija(String mängijaNimi) {
        this.praeguneMängija = haldur.getMängija(mängijaNimi);
        if (this.praeguneMängija == null) {
            System.out.println("Mängijat ei leitud: " + mängijaNimi);
        }
    }

    public void lisaPraeguseleMängijalePunkte(int punktid) {
        if (praeguneMängija != null) {
            praeguneMängija.lisaPunkte(punktid);
        } else {
            System.out.println("Praegust mängijat ei eksisteeri.");
        }
    }

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
        button2.setOnAction(event -> näitaNimeSisestust());

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
        root.getChildren().clear();

        Label label = new Label("Enne, kui asud mängu kallale, sisesta oma nimi:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField textField = new TextField();
        textField.setPromptText("Nimi");

        Button continueButton = new Button("Jätka");
        Button backButton = new Button("Tagasi");
        backButton.getStyleClass().add("default-button");
        continueButton.getStyleClass().add("success-button");

        continueButton.setOnAction(event -> {
            String mängijaNimi = textField.getText();

            if (!mängijaNimi.isEmpty()) {
                Mängija mängija = new Mängija(mängijaNimi, 0);
                haldur.lisaMängija(mängijaNimi, mängija);
                määraPraeguneMängija(mängijaNimi);
                System.out.println(praeguneMängija.getNimi());
                System.out.println(haldur.getMängijad());
                näitaRiikideValikut();
            }
        });

        backButton.setOnAction(event -> näitaMänguAlgust());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        HBox buttonBox = new HBox(10, backButton, continueButton);
        buttonBox.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, textField, buttonBox);
        layout.setPadding(new Insets(20));

        // Add the layout to the root pane
        root.getChildren().add(layout);
    }

    public void näitaRiikideValikut() {
        root.getChildren().clear();

        Label label = new Label("Milliseid riike soovid arvata?");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button euroopaNupp = new Button("Euroopa");
        Button maailmaNupp = new Button("Maailm");

        euroopaNupp.getStyleClass().add("success-button");
        maailmaNupp.getStyleClass().add("success-button");

        euroopaNupp.setOnAction(e -> {
            try {
                riigid = new Riigid("euroopa.txt");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });
        maailmaNupp.setOnAction(e -> {
            try {
                riigid = new Riigid("maailm.txt");
                näitaSoovitudKüsimusteArvu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        HBox buttonBox = new HBox(10, euroopaNupp, maailmaNupp);
        buttonBox.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, buttonBox);
        layout.setPadding(new Insets(20));

        // Add the layout to the root pane
        root.getChildren().add(layout);


    }

    public void näitaSoovitudKüsimusteArvu() {
        root.getChildren().clear();

        Label label = new Label("Mitmele küsimusele soovid vastata (1-27):");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField textField = new TextField();
        textField.setPromptText("Küsimusi");
        Button continueButton = new Button("Alusta");
        continueButton.getStyleClass().add("success-button-disabled");
        textField.textProperty().addListener(((observableValue, old, newVal) -> {
            int küsimusi = 0;
            if (!newVal.isEmpty()) {

                küsimusi = Integer.parseInt(newVal);
            }
            if (küsimusi < 27 && küsimusi > 0) {
                continueButton.getStyleClass().add("success-button");
                continueButton.getStyleClass().remove("success-button-disabled");

            }
            System.out.println(küsimusi);
        }));

        continueButton.setOnAction(event -> {
           küsimusteArv = Integer.parseInt(textField.getText());
            System.out.println(Arrays.toString(riigid.getRandomQuestion()));
        });


        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        HBox buttonBox = new HBox(10, continueButton);
        buttonBox.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, textField, buttonBox);
        layout.setPadding(new Insets(20));

        // Add the layout to the root pane
        root.getChildren().add(layout);
    }

    public void alustaMängu() {
        // veel mingid argumendid
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
