package kg.musabaev.cluserizator.view

import de.saxsys.mvvmfx.JavaView
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.util.Duration
import kg.musabaev.cluserizator.util.removeStyle
import kg.musabaev.cluserizator.util.setStyle
import kg.musabaev.cluserizator.viewmodel.*
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolBarView() : HBox(), Initializable, JavaView<ToolBarViewModel> {

    private val regexInput = TextField("regex")
    private val addNodeBtn = Button("Создать")

    private lateinit var toolBarViewModel: ToolBarViewModel
    private lateinit var seoKeywordTableViewModel: SeoKeywordTableViewModel
    private lateinit var graphClusterMap: GraphClusterMap
    private lateinit var graphViewModel: GraphViewModel

    @Inject
    constructor(
        toolBarViewModel: ToolBarViewModel,
        seoKeywordTableViewModel: SeoKeywordTableViewModel,
        graphClusterMap: GraphClusterMap,
        graphViewModel: GraphViewModel
    ) : this() {
        this.toolBarViewModel = toolBarViewModel
        this.seoKeywordTableViewModel = seoKeywordTableViewModel
        this.graphClusterMap = graphClusterMap
        this.graphViewModel = graphViewModel
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        super.getChildren().addAll(regexInput, addNodeBtn)
        initListeners()
    }

    private fun initListeners() {
        toolBarViewModel.btnCurrentColorBgProperty().addListener { _, _, newVal ->
            if (newVal == null) addNodeBtn.removeStyle("-fx-background-color")
            else addNodeBtn.setStyle("-fx-background-color", getRgbStringByColor(newVal))
        }
        toolBarViewModel.btnCurrentColorFgProperty().addListener { _, _, newVal ->
            if (newVal == null) addNodeBtn.removeStyle("-fx-text-fill")
            else addNodeBtn.setStyle("-fx-text-fill", getRgbStringByColor(newVal))
        }

        initAddNodeBtnListeners()
    }

    private fun initAddNodeBtnListeners() {
        // Есть нода не выбрана, то кнопка должна быть отключена
        addNodeBtn.disableProperty().bind(
            Bindings.isEmpty(graphViewModel.selectedGraphIdProperty())
        )

        // При нажатии на кнопку, изменять содержимое таблицы
        addNodeBtn.setOnAction {
            val clusterId = regexInput.text
            // Ищем все ключи в соответствии regex
            val matchedKeywords = seoKeywordTableViewModel
                .keywords
                .filter { it.getKeyword().contains(Regex(regexInput.text)) }
            val parentClusterId = graphViewModel.getSelectedGraphId()

            // В глобальное хранилище добавляем новую ноду - кластеризуем ключи
            graphClusterMap.map[clusterId] = GraphClusterValue(parentClusterId, clusterId, matchedKeywords)

            // Раз кластеризовали ключи, удаляем из старого кластера и добавляем дочернюю ноду в список из родительской ноды
            val parentCluster = graphClusterMap.map[parentClusterId]!!
            parentCluster.seoKeywords().removeAll(matchedKeywords)
            parentCluster.neighborClusterIds().add(clusterId)
        }

        graphViewModel.selectedGraphIdProperty().addListener { _, _, newVal ->
            val colors = getBgAndFgColorByString(newVal)

//            animateAddNodeBtnFill(colors.first, colors.second)

            toolBarViewModel.setBtnCurrentBgColor(colors.first)
            toolBarViewModel.setBtnCurrentFgColor(colors.second)
        }
    }

    private fun getBgAndFgColorByString(input: String): Pair<Color, Color> {
        var hash = 0
        for (c in input.toCharArray()) {
            hash = c.code + ((hash shl 5) - hash)
        }

        // Преобразуем хэш в RGB
        val r = (hash and 0xFF0000) shr 16
        val g = (hash and 0x00FF00) shr 8
        val b = hash and 0x0000FF

        // Генерируем контрастный цвет
        val bgColor: Color = Color.rgb(r, g, b)

        // Проверяем яркость для обеспечения читаемости текста
        val brightness: Double =
            0.2126 * bgColor.red +
                    0.7152 * bgColor.green +
                    0.0722 * bgColor.blue

        // Если фон слишком тёмный, делаем текст белым, иначе - чёрным
        val fgColor = if (brightness < 0.5) Color.WHITE else Color.BLACK

        return Pair(bgColor, fgColor)
    }

    private fun getRgbStringByColor(color: Color): String {
        return "rgb(${color.red * 255},${color.green * 255},${color.blue * 255})"
    }

    private fun animateAddNodeBtnFill(bgColor: Color, fgColor: Color) {
        val timeline = Timeline(
            KeyFrame(Duration.seconds(0.5), KeyValue(toolBarViewModel.btnCurrentColorBgProperty(), bgColor)),
            KeyFrame(Duration.seconds(0.5), KeyValue(toolBarViewModel.btnCurrentColorFgProperty(), fgColor)))
        timeline.play()
    }
}
