package kg.musabaev.cluserizator.graph

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebView
import kg.musabaev.cluserizator.GraphViewModel
import kg.musabaev.cluserizator.MainViewModel
import java.net.URL
import java.util.*
import javax.inject.Inject

class GraphView : BorderPane, JavaView<GraphViewModel>, Initializable {
    private val webView = WebView()

    @InjectViewModel
    private lateinit var model: GraphViewModel

    @Inject
    constructor(model: GraphViewModel) : this() {
        this.model = model
    }

    constructor() : super() {
        webView.engine.load(ClassLoader.getSystemClassLoader().getResource("graph.html").toURI().toString())
        super.setCenter(webView)
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        println("Yepta")


    }
}