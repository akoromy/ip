package waddles;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A single row in the chat history: a speech-bubble style {@link Label}
 * paired with a circular emoji avatar, defined in {@code /view/DialogBox.fxml}.
 * User messages are shown as-is; Waddles' messages are produced via
 * {@link #getWaddlesDialog} which mirrors the layout so the avatar appears
 * on the opposite side.
 */
public class DialogBox extends HBox {
    private static final String USER_EMOJI = "👤";
    private static final String WADDLES_EMOJI = "🐷";

    @FXML
    private Label dialog;
    @FXML
    private Label avatar;

    private DialogBox(String text, String emoji) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        avatar.setText(emoji);
    }

    /**
     * Mirrors this dialog box so the avatar is on the left instead of the
     * right, used to visually distinguish Waddles' messages from the
     * user's.
     */
    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String text) {
        DialogBox db = new DialogBox(text, USER_EMOJI);
        db.getStyleClass().add("user-dialog");
        return db;
    }

    public static DialogBox getWaddlesDialog(String text) {
        DialogBox db = new DialogBox(text, WADDLES_EMOJI);
        db.flip();
        db.getStyleClass().add("waddles-dialog");
        return db;
    }
}
