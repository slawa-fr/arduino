import arduino.Arduino;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.util.Scanner;

// https://habr.com/ru/articles/340630/

public class Controller extends Component {

    @FXML
    private Button button7, button8;

    @FXML
    private TextField textField1;

    @FXML
    private Label myLabel1, myLabel2;

    @FXML
    void initialize() {

        Scanner scanner = new Scanner(System.in);
        Arduino arduino = new Arduino("COM4", 9600);

        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        label_1:
//        while (scanner.hasNext()) {
//
//            String s = scanner.nextLine();
//
//            switch (s) {
//                case "on":
//                    arduino.serialWrite('1');
//                    break;
//                case "off":
//                    arduino.serialWrite('0');
//                    break;
//                case "exit":
//                    arduino.serialWrite('0');
//                    arduino.closeConnection();
//                    break label_1;
//                default:
//                    System.out.println(s + " - не является командой");
//                    break;
//            }
//        }


//Нажатие на кнопку button7 - начало
        button7.setOnAction(event -> {

            arduino.serialWrite('1');
            myLabel1.setText("Питание включено");

        });
//Нажатие на кнопку button7 - конец

//Нажатие на кнопку button8 - начало
        button8.setOnAction(event -> {

            arduino.serialWrite('0');
            myLabel1.setText("Питание выключено");


        });
//Нажатие на кнопку button8 - конец




    }


    }









