package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.InjectViewModel
import de.saxsys.mvvmfx.JavaView
import javafx.fxml.Initializable
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import javafx.util.Callback
import java.net.URL
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class MainView : BorderPane, JavaView<MainViewModel>, Initializable {
    private val root = TreeItem<SeoKeywordModel>()
    private val treeView = TreeView(root)

    @InjectViewModel
    private lateinit var mainViewModel: MainViewModel

    @Inject
    private lateinit var testService: TestService

    @Inject
    constructor(mainViewModel: MainViewModel, testService: TestService) {
        this.mainViewModel = mainViewModel
        this.testService = testService
    }

    constructor()

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        treeView.isEditable = true
        treeView.cellFactory = Callback { TextFieldTreeCellImpl() }
        treeView.isShowRoot = false


        super.setCenter(treeView)
        testService.consoleLog()

        val testCsvFileHandler: FileHandler = TestCsvFileHandler()

        val i = AtomicReference(1)
        testCsvFileHandler.getLinesCsv().forEach { line: String ->
            val values = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val otherMetas = values.copyOfRange(1, values.lastIndex - 1)
            val id = i.get()
            val keyword = values[0]

            val seoKeyword = SeoKeywordModel(id, keyword, otherMetas)
            i.getAndSet(i.get() + 1)

            root.children.add(TreeItem(seoKeyword))
            mainViewModel.seoKeywords.add(seoKeyword)
        }
    }
}