package model.player;


public interface PlayerMovement extends Player, PlayerObservable {

    /**
     * move character in any direction.
     * @param direction
     */
    void moveDirection(Directions direction);
    
    
    /**
     * 
     */
    void triggerAbility();

}
