package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.JavaView
import javafx.beans.binding.Bindings
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import kg.musabaev.cluserizator.viewmodel.*
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolBarView() : HBox(), Initializable, JavaView<ToolBarViewModel> {

    private val regexInput = TextField("regex")
    private val addNodeBtn = Button("Создать")

    private lateinit var seoKeywordTableViewModel: SeoKeywordTableViewModel
    private lateinit var graphClusterMap: GraphClusterMap
    private lateinit var graphViewModel: GraphViewModel

    @Inject
    constructor(seoKeywordTableViewModel: SeoKeywordTableViewModel, graphClusterMap: GraphClusterMap, graphViewModel: GraphViewModel) : this() {
        this.seoKeywordTableViewModel = seoKeywordTableViewModel
        this.graphClusterMap = graphClusterMap
        this.graphViewModel = graphViewModel
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        super.getChildren().addAll(regexInput, addNodeBtn)
        initListeners()
    }

    private fun initListeners() {
        // Есть нода не выбрана, то кнопка должна быть отключена
        addNodeBtn.disableProperty().bind(
            Bindings.isEmpty(graphViewModel.selectedGraphIdProperty()))

        // При нажатии на кнопку
        addNodeBtn.setOnAction { e ->
            val clusterId = regexInput.text
            // Ищем все ключи в соответствии regex
            val matchedKeywords = seoKeywordTableViewModel
                .keywords
                .filter { it.getKeyword().matches(Regex(regexInput.text)) }

            // В глобальное хранилище добавляем новую ноду - кластеризуем ключи
            graphClusterMap.map[clusterId] = GraphClusterValue(clusterId, matchedKeywords)

            // Раз кластеризовали ключи, удаляем из старого кластера
            graphClusterMap.map[graphViewModel.getSelectedGraphId()]!!
                .seoKeywords()
                .removeAll(matchedKeywords)
        }
    }
}