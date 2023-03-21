package converter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static apiCurrencies.ApiRequest.getCurrencies;
import static apiCurrencies.ApiRequest.getPrice;

public class ConvertController implements Initializable {

    @FXML
    private ChoiceBox<String> choice_1= new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> choice_2= new ChoiceBox<>();

    @FXML
    private TextField text_1;

    @FXML
    private Label resultConvert;

    @FXML
    private Label lblResultConvert;

    private final String[] currencies = getCurrencies();

    public ConvertController() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoice(choice_1,currencies,0);
        loadChoice(choice_2,currencies,1);
    }
    public void onclickButtonConvert(MouseEvent e) throws Exception {
        String currency_1 = choice_1.getValue().substring(0,choice_1.getValue().indexOf("-"));
        String currency_2 = choice_2.getValue().substring(0,choice_2.getValue().indexOf("-"));
        DecimalFormat df= new DecimalFormat("#.####");
        Double price= getTotalPriceCurrency(currency_1,currency_2);
        lblResultConvert.setText(currency_1+" / "+currency_2);
        resultConvert.setText(df.format(price.doubleValue())+" "+currency_2);
    }

    private void loadChoice(ChoiceBox<String> choice,String[] datos,int pos){
        for (int i = 0; i < datos.length; i++) {
            choice.getItems().add(datos[i]);
        }
        choice.setValue(datos[pos]);
    }


    private Double getTotalPriceCurrency(String currency_1, String currency_2) throws Exception {
        Double value_1= getPrice(currency_1);
        Double value_2= getPrice(currency_2);
        Double inputValue= numberVerify(text_1);
        return (value_2/value_1)*inputValue;
    }

    private Double numberVerify(TextField text) {
        try {
            Double number = Double.parseDouble(text.getText());
            if (number < 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El dato ingresado es inválido");
            alert.showAndWait();
            return 0.0;
        }
    }
}

