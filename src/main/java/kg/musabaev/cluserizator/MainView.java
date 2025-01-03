package kg.musabaev.cluserizator;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainView extends BorderPane implements JavaView<MainViewModel>, Initializable {

    private final TreeItem<SeoKeywordModel> root = new TreeItem<>();
    private final TreeView<SeoKeywordModel> treeView = new TreeView<>(root);

    @InjectViewModel
    private MainViewModel mainViewModel;
    @Inject
    private TestService testService;

    @Inject
    public MainView(MainViewModel mainViewModel, TestService testService) {
        this.testService = testService;
    }

    public MainView() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        treeView.setEditable(true);
        treeView.setCellFactory(p -> new TextFieldTreeCellImpl());
        treeView.setShowRoot(false);


        super.setCenter(treeView);
        testService.consoleLog();

        FileHandler testCsvFileHandler = new TestCsvFileHandler();

        AtomicReference<Integer> i = new AtomicReference<>(1);
        testCsvFileHandler.getLinesCsv().forEach(line -> {
            String[] values = line.split(",");

            String[] otherMetas = new String[values.length - 1];
            System.arraycopy(values, 1, otherMetas, 0, values.length - 1);
            Integer id = i.get();
            String keyword = values[0];

            SeoKeywordModel seoKeyword = new SeoKeywordModel(id, keyword, otherMetas);
            i.getAndSet(i.get() + 1);

            root.getChildren().add(new TreeItem<>(seoKeyword));
            mainViewModel.getSeoKeywords().add(seoKeyword);
        });
//        initStyles();
    }

//    public void initStyles() {
//        tableView.getColumns().get(1)
//                .prefWidthProperty().bind(Bindings.subtract(
//                        getScene().getWindow().getWidth(),
//                        tableView.getColumns().get(0).widthProperty()));
//    }
}