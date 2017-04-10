package com.example.android.mahjongcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] playerName = {"Player 1", "Player 2", "Player 3", "Player 4"};
    Integer[] chiNumber = {0,0,0,0};
    Integer[] chiPoints = {0,0,0,0};
    Integer[] pongMinorExposedNumber = {0,0,0,0};
    Integer[] pongMajorExposedNumber = {0,0,0,0};
    Integer[] pongMinorHiddenNumber = {0,0,0,0};
    Integer[] pongMajorHiddenNumber = {0,0,0,0};
    Integer[] pongNumber = {0,0,0,0};
    Integer[] pongPoints = {0,0,0,0};
    Integer[] kongMinorExposedNumber = {0,0,0,0};
    Integer[] kongMajorExposedNumber = {0,0,0,0};
    Integer[] kongMinorHiddenNumber = {0,0,0,0};
    Integer[] kongMajorHiddenNumber = {0,0,0,0};
    Integer[] kongNumber = {0,0,0,0};
    Integer[] kongPoints = {0,0,0,0};
    Integer[] flowerNumber = {0,0,0,0};
    Integer[] flowerPoints = {0,0,0,0};
    Integer[] pairNumber = {0,0,0,0};
    Integer[] pairPoints = {0,0,0,0};
    Integer[] tilesUsed = {0,0,0,0};
    int winnerPlayerNumber=-1;
    Integer[] winnerPoints = {0,0,0,0};
    Integer[] subtotalPoints = {0,0,0,0};
    Integer[] faNumber = {0,0,0,0};
    Integer[] faMultiple = {0,0,0,0};
    Integer[] totalPoints = {0,0,0,0};
    int mCurrentPlayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * adds 1 to the build the user wants to add, calculates the points of the player then updates the layout
     */
    public void buildClick(View view){
        switch (view.getId()) {
            case R.id.chi_button:
                chiNumber[mCurrentPlayer]++;
                break;
            case R.id.minor_exposed_pong_button:
                pongMinorExposedNumber[mCurrentPlayer]++;
                break;
            case R.id.major_exposed_pong_button:
                pongMajorExposedNumber[mCurrentPlayer]++;
                break;
            case R.id.minor_hidden_pong_button:
                pongMinorHiddenNumber[mCurrentPlayer]++;
                break;
            case R.id.major_hidden_pong_button:
                pongMajorHiddenNumber[mCurrentPlayer]++;
                break;
            case R.id.minor_exposed_kong_button:
                kongMinorExposedNumber[mCurrentPlayer]++;
                break;
            case R.id.major_exposed_kong_button:
                kongMajorExposedNumber[mCurrentPlayer]++;
                break;
            case R.id.minor_hidden_kong_button:
                kongMinorHiddenNumber[mCurrentPlayer]++;
                break;
            case R.id.major_hidden_kong_button:
                kongMajorHiddenNumber[mCurrentPlayer]++;
                break;
            case R.id.flower_button:
                flowerNumber[mCurrentPlayer]++;
                break;
            case R.id.pair_button:
                pairNumber[mCurrentPlayer]++;
                break;
            case R.id.winner_checkbox:
                if (((CheckBox) view).isChecked()) {
                    winnerPlayerNumber = mCurrentPlayer;
                } else {
                    winnerPlayerNumber = -1;
                }
                break;
            case R.id.fa_button: faNumber[mCurrentPlayer]++;
                break;
        }
        calculatePoints(mCurrentPlayer);
        refreshView();
    }

    /**
     * calculates the points of the player and all the variables in the process
     * @param currentPlayer is the number of the player whose points we have to calculate
     */
    public void calculatePoints(int currentPlayer){
        //chi: 0 points
        //pong: hidden minor: 4, hidden major: 8, exposed minor:2, exposed major: 4
        pongNumber[currentPlayer]=pongMinorHiddenNumber[currentPlayer]+
                pongMajorHiddenNumber[currentPlayer]+
                pongMinorExposedNumber[currentPlayer]+
                pongMajorExposedNumber[currentPlayer];
        pongPoints[currentPlayer]=pongMinorHiddenNumber[currentPlayer]*4+
                pongMajorHiddenNumber[currentPlayer]*8+
                pongMinorExposedNumber[currentPlayer]*2+
                pongMajorExposedNumber[currentPlayer]*4;
        //kong: hidden minor: 16, hidden major: 32, exposed minor:8, exposed major: 16
        kongNumber[currentPlayer]=kongMinorHiddenNumber[currentPlayer]+
                kongMajorHiddenNumber[currentPlayer]+
                kongMinorExposedNumber[currentPlayer]+
                kongMajorExposedNumber[currentPlayer];
        kongPoints[currentPlayer]=kongMinorHiddenNumber[currentPlayer]*16+
                kongMajorHiddenNumber[currentPlayer]*32+
                kongMinorExposedNumber[currentPlayer]*8+
                kongMajorExposedNumber[currentPlayer]*16;
        //flowers: 4 points
        flowerPoints[currentPlayer] = flowerNumber[currentPlayer] * 4;
        //major pairs: 2 points
        pairPoints[currentPlayer] = pairNumber[currentPlayer] * 2;
        //winner: 30 points
        winnerPoints[currentPlayer] = (winnerPlayerNumber == currentPlayer)? 30 : 0;
        // subtotal: round to superior 10
        subtotalPoints[currentPlayer] = pongPoints[currentPlayer]+kongPoints[currentPlayer]
                +flowerPoints[currentPlayer]+pairPoints[currentPlayer]+winnerPoints[currentPlayer];
        subtotalPoints[currentPlayer] = (int) Math.ceil((double)subtotalPoints[currentPlayer] / 10) * 10;
        //fa multiple: power of 2
        faMultiple[currentPlayer] = (int) Math.pow(2,faNumber[currentPlayer]);
        //Total: subtotal * faMultiple, maxed at 800
        totalPoints[currentPlayer] = Math.min(subtotalPoints[currentPlayer]*faMultiple[currentPlayer],800);
        if (totalPoints[currentPlayer] == 800) {
            Toast.makeText(this,R.string.limit_800_points_reached,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * refreshes all the views in the layout that can be changed
     */
    public void refreshView(){
        ((TextView) findViewById(R.id.playername_textview)).setText(playerName[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.number_chi_textview)).setText(chiNumber[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_chi_textview)).setText(chiPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.number_pong_textview)).setText(pongNumber[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_pong_textview)).setText(pongPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.number_kong_textview)).setText(kongNumber[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_kong_textview)).setText(kongPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.number_flowers_textview)).setText(flowerNumber[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_flowers_textview)).setText(flowerPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.number_pair_textview)).setText(pairNumber[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_pair_textview)).setText(pairPoints[mCurrentPlayer].toString());
        ((CheckBox) findViewById(R.id.winner_checkbox)).setChecked(winnerPlayerNumber==mCurrentPlayer);
        ((TextView) findViewById(R.id.pts_winner_texview)).setText(winnerPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_subtotal_textview)).setText(subtotalPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.number_fa_textview)).setText(faNumber[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.multiply_fa_textview)).setText("x"+faMultiple[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_total_textview1)).setText(totalPoints[mCurrentPlayer].toString());
        ((TextView) findViewById(R.id.pts_total_textview2)).setText(totalPoints[mCurrentPlayer].toString());
    }

    /**
     * resets all the parameters for all players
     * @param view is the reset button
     */
    public void resetAll(View view){
        winnerPlayerNumber = -1;
        for (int i = 0;  i<4 ; i++) {
            chiNumber[i] = 0;
            pongMinorExposedNumber[i] = 0;
            pongMajorExposedNumber[i] = 0;
            pongMinorHiddenNumber[i] = 0;
            pongMajorHiddenNumber[i] = 0;
            kongMinorExposedNumber[i] = 0;
            kongMajorExposedNumber[i] = 0;
            kongMinorHiddenNumber[i] = 0;
            kongMajorHiddenNumber[i] = 0;
            tilesUsed[i] = 0;
            flowerNumber[i] = 0;
            pairNumber[i] = 0;
            faNumber[i] = 0;
            calculatePoints(i);
        }
        refreshView();
    }

    /**
     * changes the shown player to the lower player. If the current player is player 1, gets back to player 4
     */
    public void displayPlayerLeft(View view){
        mCurrentPlayer = (mCurrentPlayer ==0 ) ? 3: (mCurrentPlayer - 1) % 4;
        refreshView();
    }

    /**
     * changes the shown player to the higher player. If the current player is player 4, gets back to player 1
     */
    public void displayPlayerRight(View view){
        mCurrentPlayer = (mCurrentPlayer + 1) % 4;
        refreshView();
    }
}
