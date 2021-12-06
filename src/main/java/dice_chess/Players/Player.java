package dice_chess.Players;

public abstract class Player {

    //Color of the player
    private final boolean blackSide;

    //Determine if the player is human or AI
    private final boolean human;

    /**
     * Constructor for the player
     * @param blackSide the color side of the player
     * @param human determine who plays AI or Human
     */
    public Player(boolean blackSide, boolean human) {
        this.blackSide = blackSide;
        this.human = human;
    }

    public boolean isHuman() {
        return human;
    }
}
