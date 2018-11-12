package tk.onlinesilkstore.tictactoe;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button [][] button=new Button[3][3];
    private Boolean player1turn=true;
    private Button reset_;
    private int roundcount;
    private int player1points;
    private int player2points;
    private TextView player_1_view, player_2_view;
    private MediaPlayer mp1,mp2,mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp1 = MediaPlayer.create(this, R.raw.okay);
        mp2 = MediaPlayer.create(this, R.raw.mmm);
        mp= MediaPlayer.create(this, R.raw.applause);

        player_1_view=findViewById(R.id.player_1_textview);
        player_2_view=findViewById(R.id.player_2_textview);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String button_id="button_"+i+j;
                int res_id=getResources().getIdentifier(button_id,"id",getPackageName());
                button[i][j]=findViewById(res_id);
                button[i][j].setOnClickListener(this);
            }
        }
        reset_=findViewById(R.id.reset);
        reset_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetGame();

            }
        });


    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals(""))
        {
            return;

        }

        if(player1turn)
        {
            ((Button) v).setText("X");
       mp1.start();

        }
        else
        {
            ((Button) v).setText("O");

            mp2.start();
        }
        roundcount++;

        if(CheckForWIn())
        {
            if(player1turn)
            {
                player1win();
                mp.start();

            }
            else
            {
                player2win();
                mp.start();

            }
        }else if(roundcount==9)
        {   draw();

        }else
        {
            player1turn=!player1turn;
        }
    }


    private Boolean CheckForWIn()
    {
       String [][] field=new String [3][3];

        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
            field[i][j]=button[i][j].getText().toString();
            }

        }

        for(int i=0;i<3;i++)
        {
           if( field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
           {
               return true;
           }
        }

        for(int i=0;i<3;i++)
        {
            if( field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
            {
                return true;
            }
        }

        if( field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
        {
            return true;
        }
        if( field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
        {
            return true;
        }

        return false;
    }

    private void player1win()
    {
     player1points++;
        Toast.makeText(this,"Player 1 Wins",Toast.LENGTH_LONG).show();
     updatePlayersText();
     resetBoard();
    }

    private void player2win()
    {
        player2points++;
        Toast.makeText(this,"Player 2 Wins",Toast.LENGTH_LONG).show();
        updatePlayersText();
        resetBoard();
    }
    private void draw()
    {
        Toast.makeText(this,"You Both Draw",Toast.LENGTH_LONG).show();
        resetBoard();
    }

    public void updatePlayersText()
    {
        player_1_view.setText("Player 1 Points--"+player1points);
        player_2_view.setText("Player 2 Points--"+player2points);
    }
    public void resetBoard()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {

                button[i][j].setText("");
            }
        }
        roundcount=0;
        player1turn=true;
    }
    public void resetGame()
    {
        player1points=0;
        player2points=0;
        updatePlayersText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundcount",roundcount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("player1turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundcount=savedInstanceState.getInt("roundcount");
        player1points=savedInstanceState.getInt("player1points");
        player2points=savedInstanceState.getInt("player2points");
        player1turn=savedInstanceState.getBoolean("player1turn");
    }
}
