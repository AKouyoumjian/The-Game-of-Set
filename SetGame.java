package cs3500.set;

import java.io.InputStreamReader;

import cs3500.set.controller.SetGameController;
import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.model.hw03.GeneralSetGameModel;
import cs3500.set.view.SetGameTextView;

/**
 * SetGameProgram is the class for running the game through the method Main().
 */
public final class SetGame {

  /**
   * Method, required to be named main, is used to run the game in the console.
   */
  public static void main(String[] args) {
    SetGameModel<Card> model = new GeneralSetGameModel();
    SetGameTextView view = new SetGameTextView(model, System.out);
    SetGameController controller =
            new SetGameControllerImpl(model, view, new InputStreamReader(System.in));

    String input = args[0];
    switch (input) {

      case "three":
        model = new SetThreeGameModel();
        view = new SetGameTextView(model, System.out);
        controller = new SetGameControllerImpl(model, view, new InputStreamReader(System.in));
        break;

      case "general":
        model = new GeneralSetGameModel();
        view = new SetGameTextView(model, System.out);
        controller = new SetGameControllerImpl(model, view, new InputStreamReader(System.in));
        break;

      default: // default game is General Set Game
        model = new GeneralSetGameModel();
        view = new SetGameTextView(model, System.out);
        controller = new SetGameControllerImpl(model, view, new InputStreamReader(System.in));
        break;
    }


    try {
      controller.playGame();
    } catch (IllegalStateException ise) {
      // do nothing if IllegalStateException happens.
    }

  }
}
