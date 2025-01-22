package kg.musabaev.cluserizator.domain.component

import javafx.scene.control.Alert

class NewProjectConfirmationDialog(type: Type) : Alert(AlertType.CONFIRMATION) {

    init {
        title = "Окно подтверждения"
        headerText = "После ${if (type == Type.ON_LOAD) "загрузки" else "импортирования"}, " +
                "текущий процесс будет утерян. " +
                "Вы правда хотите ${if (type == Type.ON_LOAD) "загрузить" else "импортировать"}?"
    }

    enum class Type {
        ON_LOAD,
        ON_IMPORT
    }
}