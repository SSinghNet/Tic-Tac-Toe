package net.ssingh.tictactoe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private static int turnNum = 0;
    private static boolean gameIsGoing = true;
    private static Thread myThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton[] theBoard = {findViewById(R.id.imageButton0), findViewById(R.id.imageButton1), findViewById(R.id.imageButton2), findViewById(R.id.imageButton3),
                                        findViewById(R.id.imageButton4), findViewById(R.id.imageButton5), findViewById(R.id.imageButton6), findViewById(R.id.imageButton7), findViewById(R.id.imageButton8)};
        final TextView TOPTEXT = findViewById(R.id.topText);
        final TextView result = findViewById(R.id.result);
        final Button playAgain = findViewById(R.id.playAgain);

        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while (gameIsGoing) {

                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (turnNum % 2 == 0) {

                        TOPTEXT.post(new Runnable() {
                            @Override
                            public void run() {
                                TOPTEXT.setText(R.string.playerOneTurn);
                            }
                        });

                        for (int i = 0; i < theBoard.length; i++) {
                            final int finalI = i;
                            theBoard[i].setOnClickListener(new View.OnClickListener() {
                                    public void onClick (View v) {
                                        if(theBoard[finalI].getTag().equals("c") || theBoard[finalI].getTag().equals("x")) {

                                        }else{
                                            theBoard[finalI].setImageResource(R.drawable.x);
                                            theBoard[finalI].setTag("x");
                                            turnNum++;
                                        }

                                    }

                            });
                        }
                    } else{
                        TOPTEXT.post(new Runnable() {
                            @Override
                            public void run() {
                                TOPTEXT.setText(R.string.playerTwoTurn);
                            }
                        });

                        for (int i = 0; i < theBoard.length; i++) {
                            final int finalI = i;
                            theBoard[i].setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    if(theBoard[finalI].getTag().equals("c") || theBoard[finalI].getTag().equals("x")) {

                                    }else{
                                        theBoard[finalI].setImageResource(R.drawable.circle);
                                        theBoard[finalI].setTag("c");
                                        turnNum++;
                                    }
                                }
                            });
                        }
                    }

                    if( (theBoard[0].getTag().equals("x") && theBoard[1].getTag().equals("x") && theBoard[2].getTag().equals("x")) ||
                        (theBoard[3].getTag().equals("x") && theBoard[4].getTag().equals("x") && theBoard[5].getTag().equals("x")) ||
                        (theBoard[6].getTag().equals("x") && theBoard[7].getTag().equals("x") && theBoard[8].getTag().equals("x")) ||
                        (theBoard[0].getTag().equals("x") && theBoard[3].getTag().equals("x") && theBoard[6].getTag().equals("x")) ||
                        (theBoard[1].getTag().equals("x") && theBoard[4].getTag().equals("x") && theBoard[7].getTag().equals("x")) ||
                        (theBoard[2].getTag().equals("x") && theBoard[5].getTag().equals("x") && theBoard[8].getTag().equals("x")) ||
                        (theBoard[0].getTag().equals("x") && theBoard[4].getTag().equals("x") && theBoard[8].getTag().equals("x")) ||
                        (theBoard[2].getTag().equals("x") && theBoard[4].getTag().equals("x") && theBoard[6].getTag().equals("x"))     ){

                        result.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(R.string.playerOneResult);
                            }
                        });
                        TOPTEXT.post(new Runnable() {
                            @Override
                            public void run() {
                                TOPTEXT.setText("");
                            }
                        });
                        gameIsGoing = false;
                        break;

                    }else if((theBoard[0].getTag().equals("c") && theBoard[1].getTag().equals("c") && theBoard[2].getTag().equals("c")) ||
                            (theBoard[3].getTag().equals("c") && theBoard[4].getTag().equals("c") && theBoard[5].getTag().equals("c")) ||
                            (theBoard[6].getTag().equals("c") && theBoard[7].getTag().equals("c") && theBoard[8].getTag().equals("c")) ||
                            (theBoard[0].getTag().equals("c") && theBoard[3].getTag().equals("c") && theBoard[6].getTag().equals("c")) ||
                            (theBoard[1].getTag().equals("c") && theBoard[4].getTag().equals("c") && theBoard[7].getTag().equals("c")) ||
                            (theBoard[2].getTag().equals("c") && theBoard[5].getTag().equals("c") && theBoard[8].getTag().equals("c")) ||
                            (theBoard[0].getTag().equals("c") && theBoard[4].getTag().equals("c") && theBoard[8].getTag().equals("c")) ||
                            (theBoard[2].getTag().equals("c") && theBoard[4].getTag().equals("c") && theBoard[6].getTag().equals("c"))     ){

                        result.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(R.string.playerTwoResult);
                            }
                        });
                        TOPTEXT.post(new Runnable() {
                            @Override
                            public void run() {
                                TOPTEXT.setText("");
                            }
                        });
                        gameIsGoing = false;
                        break;

                    }else if(turnNum == 9){
                        result.post(new Runnable() {
                            @Override
                            public void run() {
                                result.setText(R.string.tiedResult);
                            }
                        });
                        TOPTEXT.post(new Runnable() {
                            @Override
                            public void run() {
                                TOPTEXT.setText("");
                            }
                        });
                        gameIsGoing = false;
                        break;
                    }

                }
                for (int i = 0; i < theBoard.length; i++) {
                    final int finalI = i;
                    theBoard[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                        }
                    });
                }
            }
        };

        myThread = new Thread(myRunnable);
        myThread.start();

        resetBoard(theBoard, TOPTEXT, playAgain);

        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                myThread = new Thread(myRunnable);
                myThread.start();
            }
        });
    }

    static void resetBoard(ImageButton[] theBoard, TextView TOPTEXT, Button playAgain){
        //playAgain.setVisibility(View.INVISIBLE);
        for(int i = 0; i < theBoard.length; i++) {
            theBoard[i].setImageResource(R.drawable.blank);
            theBoard[i].setTag("");
        }
        gameIsGoing = true;
        turnNum = 0;

    }
}