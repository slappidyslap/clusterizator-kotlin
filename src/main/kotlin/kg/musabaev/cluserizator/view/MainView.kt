package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.menu.MenuView
import kg.musabaev.cluserizator.viewmodel.MainViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView() : BorderPane(), JavaView<MainViewModel>, Initializable {

    private lateinit var menuView: MenuView
    private lateinit var contentView: ContentView

    @Inject
    constructor(menuView: MenuView, contentView: ContentView) : this() {
        this.menuView = menuView
        this.contentView = contentView
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        super.setTop(menuView)
        super.setCenter(contentView)
    }
}