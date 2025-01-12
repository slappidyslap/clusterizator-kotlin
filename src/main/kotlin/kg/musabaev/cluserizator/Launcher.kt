package kg.musabaev.cluserizator

import de.saxsys.mvvmfx.FluentViewLoader
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication
import eu.lestard.easydi.EasyDI
import javafx.scene.Scene
import javafx.stage.Stage
import kg.musabaev.cluserizator.menu.MenuView
import kg.musabaev.cluserizator.menu.MenuViewModel
import kg.musabaev.cluserizator.menu.SaveLoadTestMenuView
import kg.musabaev.cluserizator.view.*
import kg.musabaev.cluserizator.domain.GraphClusters

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
        stage.show()
//        ScenicView.show(scene)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Launcher::class.java)
        }
        // fun main() = launch(Launcher::class.java)
    }

    override fun initEasyDi(context: EasyDI) {
        context.bindProvider(MenuView::class.java) {
            SaveLoadTestMenuView(
                context.getInstance(GraphClusters::class.java),
                context.getInstance(MenuViewModel::class.java))
        }
    }
}
