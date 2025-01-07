package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.collections.MapChangeListener
import javafx.concurrent.Worker
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebEngine
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
                    val parentClusterNodeId = clusterNode.getParentClusterId()


                    // Если нода не имеет соседей, то просто создаем его
                    if (clusterNode.neighborClusterIds().isEmpty()) {
                        webEngine.addNode(clusterNodeId)
                        if (parentClusterNodeId.isNotEmpty()) {
                            webEngine.addEdge(parentClusterNodeId, clusterNodeId)
                        }
                    } else {
                        addNodesRecursively(clusterNodeId)
                        webEngine.addNode(clusterNodeId)
                    }
                }
                change.wasRemoved() -> { TODO() }
            }
        })
    }

    private fun addNodesRecursively(nodeId: String) {
        if (graphClusterMap.map[nodeId] == null) return
        for (neighborClusterId in graphClusterMap.map[nodeId]!!.neighborClusterIds()) {
            addNodesRecursively(neighborClusterId)
            webEngine.addNode(neighborClusterId)
            webEngine.addEdge(nodeId, neighborClusterId)
        }
    }

    @JsFunction
    fun selectNode(id: String) {
        graphViewModel.setSelectedGraphId(id)
    }

    @JsFunction
    fun deselectNode() {
        graphViewModel.setSelectedGraphId("")
    }

    private fun WebEngine.addNode(id: String) {
        this.executeScriptSafely("GraphViewJs.addNode('$id')")
    }

    private fun WebEngine.addEdge(from: String, to: String) {
        this.executeScriptSafely("GraphViewJs.addEdge('$from', '$to')")
    }
}
