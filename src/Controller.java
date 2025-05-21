import arduino.Arduino;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;

// https://habr.com/ru/articles/340630/

// 21.05.2025 - парсинг сайта
// Вначале нужно скачать 3 файла библиотеки Jsoup (jsoup-1.20.1.jar, jsoup-1.20.1-javadoc.jar, jsoup-1.20.1-sources.jar )и положить в папку lib своего проекта

public class Controller extends Component {

    private  static final String CURRENTDIRECTORY = "user.dir";
    private String comPortNumber;
    Arduino arduino;

    @FXML
    private Button button1, button2, button3, button7, button8;

    @FXML
    private Label myLabel1;

    @FXML
    private TextField textField1;

    @FXML
    void initialize() {


// Образец кода
// https://goparse.ru/java-jsoup

        try {
            // String url = "https://example.com";
            // String url = "https://goparse.ru/java-jsoup";
            // String url = "https://www.satbeams.com/satellites?status=active"; //OK
            //String url = "https://www.flysat.com"; // Не работает
            // String url = "https://www.lyngsat.com/Express-AM7.html"; // OK


// OK
//            String url = "https://www.lyngsat.com/Turksat-4A.html";
//            Document doc = Jsoup.connect(url).userAgent("Chrome").ignoreHttpErrors(true).timeout(5000).get();
//            System.out.println(doc);


            File file = new File("D:/07/AM7.htm");
            Document doc = Jsoup.parse(file, "UTF-8", "hh.ru");
            //System.out.println(doc);


// OK
//            Elements links = doc.select("a");
//            for (Element link : links) {
//                System.out.println(link.attr("href"));
//            }


// ОК - извлечение всего текста после параграфа
//            Elements paragraphs = doc.select("p");
//            for (Element paragraph : paragraphs) {
//                System.out.println(paragraph.text());
//            }


// ОК - из тега h1
//            Elements h1 = doc.select("h1");
//            System.out.println(h1);


//////////////////////////////////////////////////////////
// https://javarush.com/groups/posts/2767-parsing-html-bibliotekoy-jsoup-



//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(3) > b:nth-child(1)
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(11) > td:nth-child(3) > b:nth-child(1)
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(17) > td:nth-child(3) > b:nth-child(1)
//
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(4)
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(11) > td:nth-child(4)
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(17) > td:nth-child(4)
//
//            это самый последний элемент - 410
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(410) > td:nth-child(3) > b:nth-child(1)
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(410) > td:nth-child(4)
//
//            это самый первый элемент - 4
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(3) > b:nth-child(1)
//            body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(4)


// OK - Получить теги title. Знак > выбирает теги title вложенные в тег head
//            Elements titleElem = doc.select("head > title");
//            System.out.println(titleElem);

// OKOK
//            Elements titleElem = doc.select("body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(3) > b:nth-child(1)");
//            Elements titleElem2 = doc.select("body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(4)");
//            System.out.println(titleElem);
//            System.out.println(titleElem2);
// OKOK



            for (int i = 2; i < 2000; i++) {

// Выберем из HTML страницы строчку, содержащую частоту и поляризацию
                Elements titleElem = doc.select("body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child("+ i +") > td:nth-child(3) > b:nth-child(1)");
                String s1 = String.valueOf(titleElem).replace("<b>", "").replace("</b>", "");
// удалить последний символ в строке java, т.е. поляризацию, заодно проверим на то чтобы строка не была пустой
                String s2 = (s1 != null && !s1.isEmpty()) ? s1.substring(0, s1.length() - 1) : null;
// Проверим как выводится частота
                //System.out.println(s2);

                int j = 0;

               if(s1 != null && !s1.isEmpty()){
                   j = i;
                   //System.out.println("i = " + i + " j = " + j);


// Выберем из HTML страницы строчку, содержащую символьную скорость и FEC
                   //body > table:nth-child(6) > tbody > tr:nth-child(5) > td:nth-child(4)
                   //body > table:nth-child(6) > tbody > tr:nth-child(11) > td:nth-child(4)
                   //body > table:nth-child(6) > tbody > tr:nth-child(17) > td:nth-child(4)
                   Elements titleElem2 = doc.select("body > table:nth-child(6) > tbody:nth-child(1) > tr:nth-child("+ j +") > td:nth-child(4)");
                   //System.out.println(titleElem2);
                   //Elements titleElem2 = doc.select("body > table:nth-child(6) > tbody > tr:nth-child(5) > td:nth-child(4)");
                   //String m1 = String.valueOf(titleElem2).replace("<td rowspan=\"6\" align=\"center\">", "").replace("<td rowspan=\"16\" align=\"center\">", "").replace("<td rowspan=\"12\" align=\"center\">", "").replace("<td rowspan=\"2\" align=\"center\">", "").replace("<td rowspan=\"7\" align=\"center\">", "").replace("<td rowspan=\"15\" align=\"center\">", "");
                   String m1 = String.valueOf(titleElem2).replace("<td rowspan=\"1\" align=\"center\"></td>", "").replace("<td rowspan=\"6\" align=\"center\">", "").replace("<td rowspan=\"16\" align=\"center\">", "").replace("<td rowspan=\"12\" align=\"center\">", "").replace("<td rowspan=\"2\" align=\"center\">", "").replace("<td rowspan=\"7\" align=\"center\">", "").replace("<td rowspan=\"15\" align=\"center\">", "").replace("<td rowspan=\"1\" align=\"center\">", "").replace("<td rowspan=\"59\" align=\"center\">", "").replace("A", "");
                   //System.out.println(m1);
                   String m2 = (m1 != null && !m1.isEmpty()) ? m1.substring(0, m1.length() - 14) : null;
                   //System.out.println(m2);
// OK
                if((s1 != null && !s1.isEmpty()) && (m1 != null && !m1.isEmpty())){
                    System.out.println("i = " + i + " " + s2 + " " + m2);
                }



                }






            }






// OK
//            Elements titleElem = doc.select("td > span");
//            System.out.println(titleElem);

// Получить первый тег span вложенный в td
            // Elements firstDiv = doc.select("td > span:nth-child(1)");
            //System.out.println(firstDiv);

// Получить тег div c классом "content", вложенный в body
// Elements contentElem = document.select("body > div.content");



// Далее по этой инструкции
// https://javarush.com/groups/posts/2767-parsing-html-bibliotekoy-jsoup-

// Получить теги c id "123"
// Elements idElem = document.select("#123");

// Получить теги div c классом "header" и "main", вложенные в body,  но без тегов h1
// Elements divHeader = document.select("body > div.header.main :not(h1)");

//            Elements divHeader = doc.select("b > href");
//            System.out.println(divHeader);

            //Elements idElem = doc.select("bigtable > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(15) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > font:nth-child(1) > font:nth-child(1) > b:nth-child(1) > a:nth-child(1)");
            //Elements idElem = doc.select("bigtable > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(15) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > font:nth-child(1) > font:nth-child(1) > b:nth-child(1) > a:nth-child(1)");
            //System.out.println(idElem);


//css selector
// .bigtable > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(15) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > font:nth-child(1) > font:nth-child(1) > b:nth-child(1) > a:nth-child(1)



        } catch (IOException e) {
            e.printStackTrace();
        }




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
        arduino = new Arduino(comPortNumber, 9600);

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
            arduino = new Arduino(comPortNumber, 9600);
        });
//Нажатие на кнопку button3 - конец



//Нажатие на кнопку button7 - начало
        button7.setOnAction(event -> {
            arduino.serialWrite('1');
            myLabel1.setText("Питание выключено");
        });
//Нажатие на кнопку button7 - конец

//Нажатие на кнопку button8 - начало
        button8.setOnAction(event -> {
            arduino.serialWrite('0');
            myLabel1.setText("Питание включено");
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









