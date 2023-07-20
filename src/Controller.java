import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;


public class Controller extends Component {

    @FXML
    private Button button7, button8;

    @FXML
    private TextField textField1;

    @FXML
    private Label myLabel1, myLabel2;

    @FXML
    void initialize() {

//Нажатие на кнопку button7 - начало
        button7.setOnAction(event -> {

            myLabel1.setText("нажата кнопка Открыть порт");

        });
//Нажатие на кнопку button7 - конец

//Нажатие на кнопку button8 - начало
        button8.setOnAction(event -> {

            myLabel1.setText("нажата кнопка закрыть порт");
            myLabel1.setText("Порт закрыт");


        });
//Нажатие на кнопку button8 - конец

    }


    }









