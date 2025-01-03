package kg.musabaev.cluserizator;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TextFieldTreeCellImpl extends TreeCell<SeoKeywordModel> {

    private TreeCellGraphic graphicImpl;

    public TextFieldTreeCellImpl() {
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (graphicImpl == null) {
            createTextField();
        }
        setText(null);
        setGraphic(graphicImpl);
        graphicImpl.getInput().requestFocus();
//        textField.selectPositionCaret(textField.getText().length());
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getKeyword());
        setGraphic(getTreeItem().getGraphic());
        this.requestFocus();
    }

    @Override
    public void updateItem(SeoKeywordModel item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (graphicImpl != null) {
                    graphicImpl.getInput().setText(getString());
                }
                setText(null);
                setGraphic(graphicImpl);
            } else {
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }

    private void createTextField() {
        graphicImpl = new TreeCellGraphic(getItem().getId(), getItem().getKeyword(), getItem().getOtherMetas()[1]);
        graphicImpl.getInput().setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ENTER) {
                getItem().setKeyword(graphicImpl.getInput().getText());
                commitEdit(getItem());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "<<null>>" : getItem().getKeyword();
    }
}

