package kg.musabaev.cluserizator;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TreeCellGraphic extends HBox {

    private final Label id;
    private final TextField input;
    private final Label freq;

    public TreeCellGraphic(Integer id, String input, String freq) {
        this.id = new Label(id.toString());
        this.input = new TextField(input);
        this.freq = new Label(freq);

        super.getChildren().add(this.id);
        super.getChildren().add(this.input);
        super.getChildren().add(this.freq);
    }

    public Label getIdNode() {
        return id;
    }

    public TextField getInput() {
        return input;
    }

    public Label getFreq() {
        return freq;
    }
}
