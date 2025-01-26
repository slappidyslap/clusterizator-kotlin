package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.FluentViewLoader
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication
import eu.lestard.easydi.EasyDI
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import kg.musabaev.cluserizator.domain.GraphClusters
import kg.musabaev.cluserizator.domain.component.ErrorDialog
import kg.musabaev.cluserizator.menu.MenuView
import kg.musabaev.cluserizator.menu.MenuViewModel
import kg.musabaev.cluserizator.menu.SaveLoadTestMenuView
import kg.musabaev.cluserizator.menu.SimpleMenuView
import kg.musabaev.cluserizator.view.*
import kotlin.system.exitProcess

class Launcher : MvvmfxEasyDIApplication() {
    @Throws(Exception::class)
    override fun startMvvmfx(stage: Stage) {
        val viewTuple = FluentViewLoader.javaView(MainView::class.java).load()
        // MenuView создается вручную снизу с конкретной реализацией
        FluentViewLoader.javaView(ToolBarView::class.java).load()
        FluentViewLoader.javaView(ContentView::class.java).load()
        FluentViewLoader.javaView(SaveLoadTestMenuView::class.java).load()
        FluentViewLoader.javaView(GraphView::class.java).load()
        FluentViewLoader.javaView(SeoKeywordTableView::class.java).load()

        stage.title = "Hello!"
        val scene = Scene(viewTuple.view)
        stage.scene = scene
        stage.isMaximized = true
        stage.setOnCloseRequest {
            Platform.exit()
            exitProcess(0)
        }
        stage.show()
//        ScenicView.show(scene)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            try {
                launch(Launcher::class.java)
            } catch (e: Exception) {
                Platform.runLater { ErrorDialog(e).show() }
            }
            Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
                Platform.runLater { ErrorDialog(throwable).show() }
            }

        }
        // fun main() = launch(Launcher::class.java)
    }

    override fun initEasyDi(context: EasyDI) {
        context.bindProvider(MenuView::class.java) {
            SimpleMenuView(
                context.getInstance(GraphClusters::class.java),
                context.getInstance(MenuViewModel::class.java))
        }
    }
}
