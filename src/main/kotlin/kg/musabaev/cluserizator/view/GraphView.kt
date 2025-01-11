package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.collections.MapChangeListener
import javafx.concurrent.Worker
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import kg.musabaev.cluserizator.menu.MenuViewModel
import kg.musabaev.cluserizator.util.JsFunction
import kg.musabaev.cluserizator.util.Utils
import kg.musabaev.cluserizator.util.executeScriptSafely
import kg.musabaev.cluserizator.viewmodel.GraphClusterMap
import kg.musabaev.cluserizator.viewmodel.GraphClusterValue
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
    private lateinit var menuViewModel: MenuViewModel

    @Inject
    constructor(graphViewModel: GraphViewModel, keywordTableViewModel: SeoKeywordTableViewModel, graphClusterMap: GraphClusterMap, menuViewModel: MenuViewModel) : this() {
        this.graphViewModel = graphViewModel
        this.keywordTableViewModel = keywordTableViewModel
        this.graphClusterMap = graphClusterMap
        this.menuViewModel = menuViewModel
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
                    val clusterNodeId: String = change.key
                    val clusterNode: GraphClusterValue = change.valueAdded
                    val parentClusterNodeId: String = clusterNode.getParentClusterId()

                    // Если мапа заграужется из сейва
                    if (menuViewModel.getIsLoadingFromSave()) {
                        if (clusterNodeId == "root") {
                            addNodesRecursively(clusterNode)
                            webEngine.addNode(clusterNodeId)
                        }
                    } // Если добавляется просто нода - редактирует граф
                    else {
                        webEngine.addNode(clusterNodeId)
                        webEngine.addEdge(parentClusterNodeId, clusterNodeId)
                    }
                }
                change.wasRemoved() -> { TODO() }
            }
        })
    }

    private fun addNodesRecursively(cluster: GraphClusterValue) {
        if (cluster.neighborClusters().isEmpty()) return
        for (neighbor in cluster.neighborClusters()) {
            addNodesRecursively(neighbor)
            webEngine.addNode(neighbor.getClusterId())
            webEngine.addEdge(cluster.getClusterId(), neighbor.getClusterId())
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
