package cs3500.set.controller;

/**
 * Interface to represent a controller for this game.
 */
public interface SetGameController {


  /**
   * This method plays a new game of Set.
   * @throws IllegalStateException if controller isn't able to read input or transmit output.
   */
  void playGame() throws IllegalStateException;


}
