package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.collections.MapChangeListener
import javafx.concurrent.Worker
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebView
import kg.musabaev.cluserizator.util.JsFunction
import kg.musabaev.cluserizator.util.Utils
import kg.musabaev.cluserizator.util.executeScriptSafely
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphViewModel
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
    private lateinit var graphClusterMap: GraphClusterMap

    @Inject
    constructor(graphViewModel: GraphViewModel, keywordTableViewModel: SeoKeywordTableViewModel, graphClusterMap: GraphClusterMap) : this() {
        this.graphViewModel = graphViewModel
        this.keywordTableViewModel = keywordTableViewModel
        this.graphClusterMap = graphClusterMap
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        webEngine.load(Utils.getResourcePath("graph/graphView.html"))
        super.setCenter(webView)

        webView.engine.loadWorker.stateProperty().addListener {_, _, newVal ->
            if (newVal == Worker.State.SUCCEEDED) {
                val windowJs: JSObject = webEngine.executeScript("window") as JSObject
                windowJs.setMember(this.javaClass.simpleName, this)
            }
        }

        initListeners()
    }

    private fun initListeners() {
        graphClusterMap.map.addListener(MapChangeListener { change ->
            when {
                change.wasAdded() -> {
                    val clusterNodeId = change.key
                    val clusterNode = change.valueAdded

//                    if (graphClusterMap.map.contains(clusterNodeId)) return@MapChangeListener
                    // Если нода не имеет соседей, то просто создаем его
                    if (clusterNode.neighbors().isEmpty()) {
                        webEngine.executeScriptSafely("addNode('${clusterNodeId}')")
                    } else {
                        addNodesRecursively(clusterNodeId)
                        webEngine.executeScriptSafely("addNode('$clusterNodeId')")
                    }
                }
                change.wasRemoved() -> { TODO() }
            }
        })
    }

    private fun addNodesRecursively(nodeId: String) {
        // тут априори не может быть null, но потом все равно добавить проверку
        if (graphClusterMap.map[nodeId] == null) {
            return
        }
        for (neighbor in graphClusterMap.map[nodeId]!!.neighbors()) {
            addNodesRecursively(neighbor.getClusterId())
            webEngine.executeScriptSafely("addNode('${neighbor.getClusterId()}')")
        }
    }

    @JsFunction
    fun selectEdge(id: String) {
        graphViewModel.setSelectedGraphId(id)
    }
}
