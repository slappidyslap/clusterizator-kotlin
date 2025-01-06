package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.SplitPane
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.menu.MenuView
import kg.musabaev.cluserizator.viewmodel.ContentViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentView() : BorderPane(), JavaView<ContentViewModel>, Initializable {

    private lateinit var toolBarView: ToolBarView
    private val splitPane = SplitPane()
    private lateinit var graphView: GraphView
    private lateinit var seoKeywordTableView: SeoKeywordTableView

    @Inject
    constructor(toolBarView: ToolBarView, graphView: GraphView, seoKeywordTableView: SeoKeywordTableView) : this() {
        this.toolBarView = toolBarView
        this.graphView = graphView
        this.seoKeywordTableView = seoKeywordTableView
    }

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        super.setTop(toolBarView)

        splitPane.items.addAll(graphView, seoKeywordTableView)
        super.setCenter(splitPane)
    }
}