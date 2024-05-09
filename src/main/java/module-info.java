module org.example.riikajatrinityjasten {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.riikajatrinityjasten to javafx.fxml;
    exports org.example.riikajatrinityjasten;
}