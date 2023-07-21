import arduino.Arduino;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;

// https://habr.com/ru/articles/340630/

public class Controller extends Component {

    private  static final String CURRENTDIRECTORY = "user.dir";
    private String comPortNumber;

    @FXML
    private Button button1, button2, button3, button7, button8;

    @FXML
    private Label myLabel1;

    @FXML
    private TextField textField1;

    @FXML
    void initialize() {

// Проверка существования каталога database
        File theDir0 = new File(System.getProperty(CURRENTDIRECTORY),"database");
        if (!theDir0.exists())
            new File(System.getProperty(CURRENTDIRECTORY), "database").mkdir();

// Проверка существования нужных файлов в папке database
        File theDir1 = new File(System.getProperty(CURRENTDIRECTORY),"database/setting.properties");

        if (!theDir1.exists()){
            createFile1();
        }

// Проверим есть ли файл setting.properties  в папке database
        checkingFile();

// Загружаем setting.properties из папки database
        File theDir = new File(System.getProperty(CURRENTDIRECTORY),"database/setting.properties");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(theDir));
        } catch (IOException e) {
            e.printStackTrace();
        }

// Получить значение comPortNumber из database/setting.properties"
        comPortNumber = appProps.getProperty("comPortNumber", "COM4");
// Установить их в textField
        textField1.setText(String.valueOf(comPortNumber));

        //Arduino arduino = new Arduino("COM4", 9600);
        Arduino arduino = new Arduino(comPortNumber, 9600);

//Нажатие на кнопку button1 - начало
        button1.setOnAction(event -> {
            boolean connected = arduino.openConnection();
            System.out.println("Соединение установлено: " + connected);
            myLabel1.setText("Соединение установлено");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//Нажатие на кнопку button1 - конец

//Нажатие на кнопку button2 - начало
        button2.setOnAction(event -> {
                    arduino.serialWrite('0');
                    arduino.closeConnection();
            System.out.println("Соединение разорвано");
            myLabel1.setText("Соединение разорвано");
        });
//Нажатие на кнопку button2 - конец

//Нажатие на кнопку button3 - начало
        button3.setOnAction(event -> {
            saveToPropertiesSetting();
            comPortNumber = textField1.getText();
        });
//Нажатие на кнопку button3 - конец



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

    void createFile1(){
        File file1 = null;
        String resource = "/setting.properties";
        URL res = getClass().getResource(resource);
        if (res.getProtocol().equals("jar")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                file1 = File.createTempFile("setting", ".properties");
                OutputStream out = new FileOutputStream(file1);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.close();
                file1.deleteOnExit();
            } catch (IOException ex) {
                //Exceptions.printStackTrace(ex);
                System.out.println("Exceptions.printStackTrace(ex);");
            }
        } else {
            file1 = new File(res.getFile());
        }

        if (file1 != null && !file1.exists()) {
            throw new RuntimeException("Error: File " + file1 + " not found!");
        }

        File file2 = new File(System.getProperty(CURRENTDIRECTORY),"database/setting.properties");
        try {
            copyFileUsingStream(file1, file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Как скопировать файл в Java? 4 способа — примеры и код
// Способ 1: Используем потоки для копирования файла
// https://javadevblog.com/kak-skopirovat-fajl-v-java-4-sposoba-primery-i-kod.html
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    // Проверка существования файла setting.properties
    void checkingFile(){
        File theDir = new File(System.getProperty(CURRENTDIRECTORY),"database/setting.properties");
        if (!theDir.exists())
            createFileAppProperties();
    }

    // Метод создания файла setting.properties из папки с ресурсами в рабочую папку с программой
    void createFileAppProperties(){
// Создаем файл setting.properties в папке с программой
        File dest = new File(System.getProperty(CURRENTDIRECTORY),"setting.properties");
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("setting.properties");
             OutputStream out = new FileOutputStream(dest)) {
            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    // Метод сохранения в properties
    void saveToPropertiesSetting() {
// Загружаем  setting.properties из папки database
        File theDir17 = new File(System.getProperty(CURRENTDIRECTORY),"database/setting.properties");
        Properties appProps = new Properties();
        try {
            appProps.load(new BufferedReader(new InputStreamReader(new FileInputStream(theDir17), "UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        comPortNumber = textField1.getText();

        appProps.setProperty("comPortNumber", String.valueOf(comPortNumber));

// Сохраним в setting.properties внесенные изменения из текстовых полей
        String newAppProps = "database/setting.properties";
        try {
            appProps.store(new FileWriter(newAppProps), "store");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }









