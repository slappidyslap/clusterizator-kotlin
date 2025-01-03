package kg.musabaev.cluserizator;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

public class Launcher extends MvvmfxEasyDIApplication {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void startMvvmfx(Stage stage) throws Exception {
        ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.javaView(MainView.class).load();
        stage.setTitle("Hello!");
        Scene scene = new Scene(viewTuple.getView());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        ScenicView.show(scene);
    }
}