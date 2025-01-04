package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.concurrent.Worker
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebView
import kg.musabaev.cluserizator.util.JsFunction
import kg.musabaev.cluserizator.util.Utils
import kg.musabaev.cluserizator.viewmodel.GraphViewModel
import kg.musabaev.cluserizator.viewmodel.SeoClusterViewModel
import kg.musabaev.cluserizator.viewmodel.SeoKeywordTableViewModel
import netscape.javascript.JSObject
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphView() : BorderPane(), JavaView<GraphViewModel>, Initializable {
    private val webView = WebView()
    private val webEngine = webView.engine

    @InjectViewModel
    private lateinit var graphViewModel: GraphViewModel
    private lateinit var keywordTableViewModel: SeoKeywordTableViewModel
    private lateinit var clusterViewModel: SeoClusterViewModel

    @Inject
    constructor(graphViewModel: GraphViewModel, keywordTableViewModel: SeoKeywordTableViewModel, clusterViewModel: SeoClusterViewModel) : this() {
        this.graphViewModel = graphViewModel
        this.keywordTableViewModel = keywordTableViewModel
        this.clusterViewModel = clusterViewModel
    }

//        webEngine.getLoadWorker().stateProperty()
//            .addListener { _, _, newValue ->
//                if (newValue == Worker.State.SUCCEEDED) {
//                    val firebugLibPath = Utils.getFilePathFromRoot("lib", "firebug", "firebug.js")
//                    webEngine.executeScript("var firebug=document.createElement('script');firebug.setAttribute('src','$firebugLibPath');document.body.appendChild(firebug);(function(){if(window.firebug.version){firebug.init();}else{setTimeout(arguments.callee);}})();void(firebug);")
//                }
//            }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        webEngine.load(Utils.getResourcePath("graph/graphView.html"))
        super.setCenter(webView)

        webView.engine.loadWorker.stateProperty().addListener {_, _, newVal ->
            if (newVal == Worker.State.SUCCEEDED) {
                val windowJs: JSObject = webEngine.executeScript("window") as JSObject
                windowJs.setMember(this.javaClass.simpleName, this)
//                println(webView.engine.executeScript("window.graph.body.nodes"))
            }
        }
    }

    @JsFunction
    fun selectEdge(id: String) {
        println(id)
        graphViewModel.selectedGraphId.set(id)
    }
}
