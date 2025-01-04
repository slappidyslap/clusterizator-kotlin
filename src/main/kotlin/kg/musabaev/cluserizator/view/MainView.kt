package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.SplitPane
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.menu.MenuView
import kg.musabaev.cluserizator.viewmodel.SeoKeywordTableViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView() : BorderPane(), JavaView<SeoKeywordTableViewModel>, Initializable {

    private val splitPane = SplitPane()

    private lateinit var menuView: MenuView
    private lateinit var graphView: GraphView
    private lateinit var seoKeywordTableView: SeoKeywordTableView

    @Inject
    constructor(menuView: MenuView, graphView: GraphView, seoKeywordTableView: SeoKeywordTableView) : this() {
        this.menuView = menuView
        this.graphView = graphView
        this.seoKeywordTableView = seoKeywordTableView
    }

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        super.setTop(menuView)

        splitPane.items.addAll(graphView, seoKeywordTableView)
        super.setCenter(splitPane)
    }
}