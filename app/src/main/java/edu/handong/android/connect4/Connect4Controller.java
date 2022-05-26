package edu.handong.android.connect4;

import static android.content.ContentValues.TAG;

import edu.handong.android.connect4.ConnectLogic;
import edu.handong.android.connect4.ConnectLogic.Outcome;
import edu.handong.android.connect4.BuildConfig;
import edu.handong.android.connect4.game_activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Connect4Controller implements View.OnClickListener {

    /**
     * number of columns
     */
    public static final int COLS = 7;
    /**
     * number of rows
     */
    public static final int ROWS = 6;
    /**
     * mGrid, contains 0 for empty cell or player ID
     */
    public static int[][] connGrid = new int[ROWS][COLS];
    /**
     * mFree cells in every column
     */
    private static int[] spFree = new int[COLS];
    /**
     * board mBoardLogic (winning check)
     */
    private final ConnectLogic connBoardLogic = new ConnectLogic(connGrid, spFree);
    /**
     * player turn
     */
    public static int connPlayerTurn;
    /**
     * current status
     */
    private Outcome connOutcome = Outcome.NOTHING;
    /**
     * if the game is mFinished
     */
    private boolean mFinished = true;

    public Connect4Controller(){
        initialize();
    }

    private void initialize() {

        connPlayerTurn = game_activity.firstTurnStatic;
        mFinished = false;
        for (int j = 0; j < COLS; ++j) {
            for (int i = 0; i < ROWS; ++i) {
                connGrid[i][j] = 0;
            }
            spFree[j] = ROWS;
        }
    }


    @Override
    public void onClick(View v) {
        Log.d("aa",v.toString());
        System.out.println("Vgetx"+v.getX());
        int col = game_activity.getInstance().colAtX(v.getX());
        System.out.println(col);
        selectColumn(col);
    }
    private void selectColumn(int column) {
        System.out.println("MFree column"+ spFree[column]);
        if (spFree[column] == 0) {
            System.out.println(spFree[column]);
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "full column or game is mFinished");
            }
            return;
        }
        connBoardLogic.placeMove(column, connPlayerTurn);
        System.out.println(spFree[column]);
        // put disc
        game_activity.getInstance().dropDisc(spFree[column], column, connPlayerTurn);
        game_activity.getInstance().progressBarSwap(connPlayerTurn);

        togglePlayer(connPlayerTurn);
        connBoardLogic.displayBoard();
        checkForWin();
    }

    public void togglePlayer(int playerTurn) {
        if(playerTurn==1)
        {
            connPlayerTurn =2;

        }else {
            connPlayerTurn =1;
        }
    }

    private void checkForWin() {
        connOutcome = connBoardLogic.checkWin(connGrid);
        System.out.println("Hello");
        if (connOutcome != Outcome.NOTHING) {
            mFinished = true;
            ArrayList<ImageView> winDiscs = connBoardLogic.getWinDiscs(game_activity.getInstance().getCells());
            game_activity.getInstance().showWinStatus(connOutcome, winDiscs);

        } else {
//            togglePlayer(mPlayerTurn);
        }
    }
}
