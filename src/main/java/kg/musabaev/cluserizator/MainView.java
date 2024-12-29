package kg.musabaev.cluserizator;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainView extends VBox implements JavaView<MainViewModel>, Initializable {

    private final TableView<SeoKeywordModel> tableView = new TableView<>();

    @InjectViewModel
    private MainViewModel mainViewModel;

    public MainView() {
        super.getChildren().add(tableView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<SeoKeywordModel, Integer> idColumn = new TableColumn<>("");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<SeoKeywordModel, String> keyColumn = new TableColumn<>("Ключевое слово");
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("keyword"));
        tableView.getColumns().addAll(idColumn, keyColumn);

        FileHandler testCsvFileHandler = new TestCsvFileHandler();

        AtomicReference<Integer> i = new AtomicReference<>(1);
        testCsvFileHandler.getLinesCsv().forEach(line -> {
            String[] values = line.split(",");

            String[] otherMetas = new String[values.length - 1];
            System.arraycopy(values, 1, otherMetas, 0, values.length - 1);
            Integer id = i.get();
            String keyword = values[0];

            SeoKeywordModel seoKeyword = new SeoKeywordModel(id, keyword/*, otherMetas*/);
            i.getAndSet(i.get() + 1);

            mainViewModel.getSeoKeywords().add(seoKeyword);
        });
        tableView.setItems(mainViewModel.getSeoKeywords());
//        initStyles();
    }

//    public void initStyles() {
//        tableView.getColumns().get(1)
//                .prefWidthProperty().bind(Bindings.subtract(
//                        getScene().getWindow().getWidth(),
//                        tableView.getColumns().get(0).widthProperty()));
//    }
}