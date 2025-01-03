package kg.musabaev.cluserizator.graph

import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.SplitPane
import javafx.scene.layout.BorderPane
import kg.musabaev.cluserizator.SeoKeywordTableViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainView() : BorderPane(), JavaView<SeoKeywordTableViewModel>, Initializable {

    private val splitPane: SplitPane = SplitPane()
    private lateinit var graphView: GraphView
    private lateinit var seoKeywordTableView: SeoKeywordTableView

    @Inject
    constructor(graphView: GraphView, seoKeywordTableView: SeoKeywordTableView) : this() {
        this.graphView = graphView
        this.seoKeywordTableView = seoKeywordTableView
    }

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        splitPane.items.addAll(graphView, seoKeywordTableView)
        super.setCenter(splitPane)
    }
}