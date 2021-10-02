package Players;

public abstract class Player {

    private boolean blackSide;
    private boolean human;

    public Player(boolean blackSide, boolean human) {
        this.blackSide = blackSide;
        this.human = human;
    }

    public String isColorSide() {
        if(blackSide)
            return "Black";
        return "White";
    }

    public void setColorSide(boolean colorSide) {
        this.blackSide = colorSide;
    }

    public boolean isHuman() {
        return human;
    }

    public void setHuman(boolean human) {
        this.human = human;
    }
}
