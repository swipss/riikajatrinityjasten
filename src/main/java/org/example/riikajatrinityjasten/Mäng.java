package org.example.riikajatrinityjasten;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Mäng extends Application {
    private StackPane juur;
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

    public static void näitaViga(String title, String content) {
        Alert teade = new Alert(Alert.AlertType.ERROR);
        teade.setTitle(title);
        teade.setHeaderText(null); // No header text
        teade.setContentText(content);
        teade.showAndWait();
    }

    @Override
    public void start(Stage lava) throws Exception {
        juur = new StackPane();
        näitaMänguAlgust();

        Scene stseen = new Scene(juur, 400, 300);
        stseen.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        lava.setScene(stseen);
        lava.setTitle("JavaFX Interface");
        lava.show();
    }

    private void näitaMänguAlgust() {
        juur.getChildren().clear();
        Text pealkiri = new Text("🤩 Tere tulemast Riikide Äraarvamisemängu! 🤩");
        pealkiri.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));

        Button nupp1 = new Button("Juhend");
        Button nupp2 = new Button("Mängima");
        nupp1.getStyleClass().add("default-button");
        nupp2.getStyleClass().add("success-button");
        nupp1.setOnAction(event -> näitaJuhendit());
        nupp2.setOnAction(event -> näitaNimeSisestust());

        HBox nupuKast = new HBox(10, nupp1, nupp2);
        nupuKast.setAlignment(Pos.CENTER);

        VBox keskmineKast = new VBox(20, pealkiri, nupuKast);
        keskmineKast.setAlignment(Pos.CENTER);

        keskmineKast.setPadding(new Insets(20));

        juur.getChildren().add(keskmineKast);
    }

    private void näitaJuhendit() {
        juur.getChildren().clear();
        Text pealkiri = new Text("\uD83D\uDCD6 Juhend \uD83D\uDCD6");
        pealkiri.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));

        Text tekst = new Text("Ekraanile kuvatakse vihje, nt \"See riik on kaardi peal jalakujuline, mis järel pead sina pakkuma, mis riigiga on tegemist. 😎 Kui vastasid õigesti, liidetakse sinu puntkiskoorile punkt.");
        tekst.setFont(Font.font("Arial", 14));
        tekst.setWrappingWidth(250);
        tekst.setTextAlignment(TextAlignment.CENTER);

        Button tagasiNupp = new Button("Tagasi");
        tagasiNupp.getStyleClass().add("default-button");

        tagasiNupp.setOnAction(event -> näitaMänguAlgust());

        VBox keskmineKast = new VBox(20, pealkiri, tekst, tagasiNupp);
        keskmineKast.setAlignment(Pos.CENTER);

        keskmineKast.setPadding(new Insets(20));

        juur.getChildren().add(keskmineKast);
    }

    public void näitaNimeSisestust() {
        juur.getChildren().clear();

        Label silt = new Label("Enne, kui asud mängu kallale, sisesta oma nimi:");
        silt.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField tekstiSisend = new TextField();
        tekstiSisend.setPromptText("Nimi");

        Button edasiNupp = new Button("Jätka");
        Button tagasiNupp = new Button("Tagasi");
        tagasiNupp.getStyleClass().add("default-button");
        edasiNupp.getStyleClass().add("success-button");

        edasiNupp.setOnAction(event -> {
            String mängijaNimi = tekstiSisend.getText();

            if (!mängijaNimi.isEmpty()) {
                Mängija mängija = new Mängija(mängijaNimi, 0);
                haldur.lisaMängija(mängijaNimi, mängija);
                määraPraeguneMängija(mängijaNimi);
                näitaRiikideValikut();
            } else {
               näitaViga("Input Error", "Nimi ei tohi olla tühi!");
            }

        });

        tagasiNupp.setOnAction(event -> näitaMänguAlgust());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        HBox nupuKast = new HBox(10, tagasiNupp, edasiNupp);
        nupuKast.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(silt, tekstiSisend, nupuKast);
        layout.setPadding(new Insets(20));

        juur.getChildren().add(layout);
    }

    public void näitaRiikideValikut() {
        juur.getChildren().clear();

        Label silt = new Label("Milliseid riike soovid arvata?");
        silt.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button euroopaNupp = new Button("Euroopa");
        Button maailmaNupp = new Button("Maailm");

        euroopaNupp.getStyleClass().add("success-button");
        maailmaNupp.getStyleClass().add("success-button");

        euroopaNupp.setOnAction(e -> {
            try {
                riigid = new Riigid("euroopaliit.txt");
                näitaSoovitudKüsimusteArvu();
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
        HBox nupuKast = new HBox(10, euroopaNupp, maailmaNupp);
        nupuKast.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(silt, nupuKast);
        layout.setPadding(new Insets(20));

        juur.getChildren().add(layout);


    }

    public void näitaSoovitudKüsimusteArvu() {
        juur.getChildren().clear();

        Label silt = new Label("Mitmele küsimusele soovid vastata (1-27):");
        silt.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField teksiSisend = new TextField();
        teksiSisend.setPromptText("Küsimusi");
        Button edasiNupp = new Button("Alusta");
        edasiNupp.getStyleClass().add("success-button-disabled");
        teksiSisend.textProperty().addListener(((observableValue, old, newVal) -> {
            int küsimusi = 0;
            if (!newVal.isEmpty()) {
                küsimusi = Integer.parseInt(newVal);
            }
            if (küsimusi < 27 && küsimusi > 0) {
                edasiNupp.getStyleClass().add("success-button");
                edasiNupp.getStyleClass().remove("success-button-disabled");
            }

        }));

        edasiNupp.setOnAction(event -> {
           küsimusteArv = Integer.parseInt(teksiSisend.getText());
            if (küsimusteArv < 27 && küsimusteArv > 0) {
                alustaMängu();
            }
        });


        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        HBox nupuKast = new HBox(10, edasiNupp);
        nupuKast.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(silt, teksiSisend, nupuKast);
        layout.setPadding(new Insets(20));

        juur.getChildren().add(layout);
    }

    public void alustaMängu() {
        näitaKüsimust();
    }

    public void näitaKüsimust() {
        if (küsimusteArv > 0) {
            String[] rida = riigid.getRandomQuestion();
            String vastus = rida[0];
            String küsimus = rida[1];
            System.out.println(vastus);

            juur.getChildren().clear();

            Text tekst = new Text("Vihje: " + küsimus);
            tekst.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            tekst.setWrappingWidth(250);
            tekst.setTextAlignment(TextAlignment.CENTER);

            TextField tekstiSisend = new TextField();
            tekstiSisend.setPromptText("Vastus");

            Button edasiNupp = new Button("Edasi");
            edasiNupp.getStyleClass().add("success-button");
            edasiNupp.setOnAction(event -> {
                String mängijaVastus = tekstiSisend.getText();
                if (mängijaVastus.trim().isEmpty()) {
                    näitaViga("Input Error", "Vastus ei tohi olla tühi!");
                    return;
                }
                küsimusteArv--;
                if (vastus.equalsIgnoreCase(mängijaVastus)) {
                    haldur.getMängija(praeguneMängija.getNimi()).lisaPunkte(1);
                }
                if (küsimusteArv > 0) {
                    näitaKüsimust();
                } else {
                    näitaEdetabelit();
                }
            });

            VBox layout = new VBox(10);
            layout.setAlignment(Pos.CENTER);
            HBox nupuKast = new HBox(10, edasiNupp);
            nupuKast.setAlignment(Pos.CENTER);
            layout.getChildren().addAll(tekst, tekstiSisend, nupuKast);
            layout.setPadding(new Insets(20));

            juur.getChildren().add(layout);
        }
    }
    public void näitaEdetabelit() {
        juur.getChildren().clear();

        TableView<Mängija> tabel = new TableView<>();
        TableColumn<Mängija, String> nimeVeerg = new TableColumn<>("Mängija Nimi");
        nimeVeerg.setCellValueFactory(new PropertyValueFactory<>("nimi"));

        TableColumn<Mängija, Number> skooriVeerg = new TableColumn<>("Punktid");
        skooriVeerg.setCellValueFactory(new PropertyValueFactory<>("skoor"));

        tabel.getColumns().add(nimeVeerg);
        tabel.getColumns().add(skooriVeerg);
        tabel.setItems(FXCollections.observableArrayList(haldur.getMängijad().values().stream()
                .map(m -> new Mängija(m.getNimi(), m.getSkoor())).sorted(Comparator.comparingInt(Mängija::getSkoor)).collect(Collectors.toList())));

        Button lõpetaNupp = new Button("Lõpeta mängimine");
        lõpetaNupp.getStyleClass().add("default-button");

        lõpetaNupp.setOnAction(event -> Platform.exit());

        Button edasiNupp = new Button("Jätka mängimist");
        edasiNupp.getStyleClass().add("success-button");

        edasiNupp.setOnAction(event -> {
            näitaMänguAlgust();
        });

        VBox nupuKast = new VBox(10, edasiNupp, lõpetaNupp);
        nupuKast.setAlignment(Pos.CENTER);

        HBox mainLayout = new HBox(10, tabel, nupuKast);
        mainLayout.setAlignment(Pos.CENTER);

        juur.getChildren().add(mainLayout);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
