package kg.musabaev.cluserizator

import javafx.event.EventHandler
import javafx.scene.control.TreeCell
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class TextFieldTreeCellImpl : TreeCell<SeoKeywordModel?>() {
    private var graphicImpl: TreeCellGraphic? = null

    override fun startEdit() {
        super.startEdit()

        if (graphicImpl == null) {
            createTextField()
        }
        text = null
        graphic = graphicImpl
        graphicImpl?.input?.requestFocus()
        //        textField.selectPositionCaret(textField.getText().length());
    }

    override fun cancelEdit() {
        super.cancelEdit()
        text = item!!.getKeyword()
        graphic = treeItem.graphic
        this.requestFocus()
    }

    override fun updateItem(item: SeoKeywordModel?, empty: Boolean) {
        super.updateItem(item, empty)

        if (empty) {
            text = null
            graphic = null
        } else {
            if (isEditing) {
                if (graphicImpl != null) {
                    graphicImpl!!.input.text = string
                }
                text = null
                graphic = graphicImpl
            } else {
                graphic = treeItem.graphic
            }
        }
    }

    private fun createTextField() {
        graphicImpl = TreeCellGraphic(item!!.getId(), item!!.getKeyword(), item!!.otherMetas[1])
        graphicImpl!!.input.onKeyReleased = EventHandler { t: KeyEvent ->
            if (t.code == KeyCode.ENTER) {
                item!!.setKeyword(graphicImpl!!.input.text)
                commitEdit(item)
            } else if (t.code == KeyCode.ESCAPE) {
                cancelEdit()
            }
        }
    }

    private val string: String
        get() = if (item == null) "<<null>>" else item!!.getKeyword()
}