package dice_chess.TestSimulations;

public class GameInfo {

    public int total_white_step;

    public int total_black_step;

    public boolean blackWins;

    public GameInfo(int total_white_step, int total_black_step, boolean blackWins) {
        this.total_white_step = total_white_step;
        this.total_black_step = total_black_step;
        this.blackWins = blackWins;
    }
}
