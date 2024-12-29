package kg.musabaev.cluserizator;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DynamicTableViewExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        TableView<Student> tableView = new TableView<>();

        // Колонка для имени
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Колонка для возраста
        TableColumn<Student, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        // Колонка для курса
        TableColumn<Student, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        // Добавляем колонки в таблицу
        tableView.getColumns().addAll(nameColumn, ageColumn, courseColumn);

        // Данные для таблицы
        ObservableList<Student> studentList = FXCollections.observableArrayList(
                new Student("Alice", 20, "Math"),
                new Student("Bob", 22, "Physics")
        );

        tableView.setItems(studentList);

        // Поля ввода для добавления новых строк
        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");

        TextField ageInput = new TextField();
        ageInput.setPromptText("Age");

        TextField courseInput = new TextField();
        courseInput.setPromptText("Course");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            // Получаем данные из текстовых полей
            String name = nameInput.getText();
            int age = Integer.parseInt(ageInput.getText());
            String course = courseInput.getText();

            // Добавляем нового студента в список
            studentList.add(new Student(name, age, course));

            // Очищаем текстовые поля
            nameInput.clear();
            ageInput.clear();
            courseInput.clear();
        });

        // Layout для ввода данных
        HBox inputBox = new HBox(10, nameInput, ageInput, courseInput, addButton);
        VBox root = new VBox(10, tableView, inputBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Dynamic TableView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Student {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty course;

    public Student(String name, int age, String course) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.course = new SimpleStringProperty(course);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }
}
