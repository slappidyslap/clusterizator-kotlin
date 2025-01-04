package kg.musabaev.cluserizator;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestFoodApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Модель данных
        ObservableList<Food> foodList = FXCollections.observableArrayList(
                new Food("Pizza", "Cheese", "Tomato", "Dough"),
                new Food("Salad", "Lettuce", "Tomato", "Cucumber")
        );

        // ViewModel
        FoodViewModel viewModel = new FoodViewModel();

        // Компоненты
        FoodListView foodListView = new FoodListView(foodList, viewModel);
        IngredientsView ingredientsView = new IngredientsView(viewModel);

        // Интерфейс
        HBox root = new HBox(foodListView.getView(), ingredientsView.getView());
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Food App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public class Food {
        private final StringProperty name = new SimpleStringProperty();
        private final ObservableList<String> ingredients = FXCollections.observableArrayList();

        public Food(String name, String... ingredients) {
            this.name.set(name);
            this.ingredients.addAll(ingredients);
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public ObservableList<String> getIngredients() {
            return ingredients;
        }
    }
    public class FoodViewModel {
        private final ObjectProperty<Food> selectedFood = new SimpleObjectProperty<>();

        public ObjectProperty<Food> selectedFoodProperty() {
            return selectedFood;
        }

        public Food getSelectedFood() {
            return selectedFood.get();
        }

        public void setSelectedFood(Food food) {
            this.selectedFood.set(food);
        }
    }

    public class FoodListView {
        private final ListView<Food> listView = new ListView<>();
        private final FoodViewModel viewModel;

        public FoodListView(ObservableList<Food> foodList, FoodViewModel viewModel) {
            this.viewModel = viewModel;
            listView.setItems(foodList);

            listView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Food item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getName());
                }
            });

            // Связываем выбор с ViewModel
            listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                viewModel.setSelectedFood(newSelection);
            });
        }

        public ListView<Food> getView() {
            return listView;
        }
    }

    public class IngredientsView {
        private final ListView<String> ingredientsListView = new ListView<>();
        private final FoodViewModel viewModel;

        public IngredientsView(FoodViewModel viewModel) {
            this.viewModel = viewModel;

            // Связываем состав с выбранной едой
            viewModel.selectedFoodProperty().addListener((obs, oldFood, newFood) -> {
                if (newFood != null) {
                    ingredientsListView.setItems(newFood.getIngredients());
                } else {
                    ingredientsListView.setItems(FXCollections.observableArrayList());
                }
            });
        }

        public ListView<String> getView() {
            return ingredientsListView;
        }
    }
}
