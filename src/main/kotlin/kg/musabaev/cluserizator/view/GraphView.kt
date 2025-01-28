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
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.GraphClusterItem
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
    private lateinit var graphClusters: GraphClusters
    private lateinit var menuViewModel: MenuViewModel

    @Inject
    constructor(graphViewModel: GraphViewModel, keywordTableViewModel: SeoKeywordTableViewModel, graphClusters: GraphClusters, menuViewModel: MenuViewModel) : this() {
        this.graphViewModel = graphViewModel
        this.keywordTableViewModel = keywordTableViewModel
        this.graphClusters = graphClusters
        this.menuViewModel = menuViewModel
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        webEngine.load(this::class.java.getResource("graphView.html")!!.toString())
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
        graphClusters.getMap().addListener(MapChangeListener { change ->
            when {
                change.wasAdded() -> {
                    val clusterNodeId: String = change.key
                    val clusterNode: GraphClusterItem = change.valueAdded
                    val parentClusterNodeId: String = clusterNode.getParentId()

                    // Если мапа заграужется из сейва
                    if (menuViewModel.getIsLoadingFromSave()) {
                        if (clusterNodeId == "root") {
                            addNodesRecursively(clusterNode)
                            webEngine.addNode(clusterNodeId)
                            webEngine.redraw() // не понятно почему, но тут явно надо писать
                        }
                    } // Если добавляется просто нода - редактирует граф
                    else {
                        webEngine.addNode(clusterNodeId)
                        webEngine.addEdge(parentClusterNodeId, clusterNodeId)
                    }
                    webEngine.redraw()
                }
                change.wasRemoved() -> {
                    if (change.map.isEmpty()) {
                        webEngine.reload()
                    } else {
                        webEngine.deleteSelected()
                    }
                    webEngine.redraw()
                }
            }
        })
    }

    private fun addNodesRecursively(cluster: GraphClusterItem) {
        if (cluster.neighbors().isEmpty()) return
        for (neighbor in cluster.neighbors()) {
            addNodesRecursively(neighbor)
            webEngine.addNode(neighbor.getId())
            webEngine.addEdge(cluster.getId(), neighbor.getId())
        }
    }

    @JsFunction
    fun selectNode(id: String) {
        graphViewModel.setSelectedClusterId(id)
    }

    @JsFunction
    fun deselectNode() {
        graphViewModel.setSelectedClusterId("")
    }

    private fun WebEngine.addNode(id: String) {
        this.executeScriptSafely("GraphViewJs.addNode('$id')")
    }

    private fun WebEngine.addEdge(from: String, to: String) {
        this.executeScriptSafely("GraphViewJs.addEdge('$from', '$to')")
    }

    private fun WebEngine.deleteSelected() {
        this.executeScriptSafely("graph.deleteSelected()") // TODO в интерфейс
    }

    private fun WebEngine.redraw() {
        this.executeScriptSafely("graph.redraw()") // TODO в интерфейс
    }
}
