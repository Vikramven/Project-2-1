package dice_chess.Constant;

import dice_chess.Players.Human;
import dice_chess.Players.Player;
import dice_chess.TestSimulations.GameInfo;

import java.util.LinkedList;

public class Constant {
    public static boolean AI_SIMULATIONS = false;

    public static Player PLAYER_WHITE = new Human(false);

    public static Player PLAYER_BLACK = new Human(true);

    public static int AI_WHITE = 1;

    public static int AI_BLACK = 1;

    public static int DEPTH_WHITE = 3;

    public static int DEPTH_BLACK = 3;

    public static boolean DEBUG_GAME_SETTINGS = true;

    public static int EVALUATION_FUNCTION_MINIMAX = 1;

    public static int EVALUATION_FUNCTION_EXPECTI_MAX = 2;

    public static int EVALUATION_FUNCTION_EXPECTI_MINIMAX = 2;

    public static boolean GAME_TRACKER = false;

    public static int TOTAL_STEP_WHITE_IN_THE_GAME = 0;

    public static int TOTAL_STEP_BLACK_IN_THE_GAME = 0;

    public static int TOTAL_WIN_BLACK = 0;

    public static int TOTAL_WIN_WHITE = 0;

    public static boolean BLACK_WINS;

    public static boolean DQN_SIMULATION = false;

    public static int GAME_COUNTER = 0;

    public static LinkedList<GameInfo> LIST_GAMES = new LinkedList<>();

}
