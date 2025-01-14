package kg.musabaev.cluserizator.domain

import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.util.Callback

class AutoIncrementTableCellFactory<S, T> : Callback<TableColumn<S, T>, TableCell<S, T>> {

    override fun call(param: TableColumn<S, T>?): TableCell<S, T> {
        return AutoIncrementTableCell(1)
    }

    class AutoIncrementTableCell<S, T>(private val startNumber: Int) : TableCell<S, T>() {

        override fun updateItem(item: T, empty: Boolean) {
            super.updateItem(item, empty)
            text = if (empty) "" else (startNumber + index).toString()
        }
    }
}