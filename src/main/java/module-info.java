module com.example.conversor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens apiCurrencies to com.fasterxml.jackson.databind;
    opens converter to javafx.fxml;
    exports converter;
    exports apiCurrencies;

}