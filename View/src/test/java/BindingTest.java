import com.example.BoardInformation;
import com.example.MainSceneController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class BindingTest {

    @Test
    public void bindingTest() {
        MainSceneController mainSceneController = new MainSceneController();
        BoardInformation boardInformation = new BoardInformation();
        boardInformation.setCol(3);
        boardInformation.setRow(3);
        boardInformation.setFillPercentage(50);
        mainSceneController.initializeBoard(boardInformation);
        boolean preValue = mainSceneController.getGameOfLifeBoard().getBoard()[0][0].isAlive();
        Rectangle rect = (Rectangle) mainSceneController.getMainBoard().getChildren().getFirst();
        if(!preValue) {
            rect.setFill(Color.GREEN);
        }
        else {
            rect.setFill(Color.RED);
        }
        assertFalse(mainSceneController.getGameOfLifeBoard().getBoard()[0][0].isAlive() == preValue);

    }
}
