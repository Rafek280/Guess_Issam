package com.example.guessmynumber;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;





public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    public static int currentrandomnumber = 0;
    public static int guessNumberInt = 0;
    public static int currentscore;


    SeekBar seekBar,seekBar2;
    EditText lowerBound,upperBound,guessNumber;
    Button generate,hint,evaluate;
    TextView score;
    RadioButton productHint,sumHint,primeHint,divisibilityHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the visual Elements
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        lowerBound = (EditText) findViewById(R.id.lowerBound);
        upperBound = (EditText) findViewById(R.id.upperBound);
        guessNumber = (EditText) findViewById(R.id.guessNumber);
        generate = (Button) findViewById(R.id.generate);
        evaluate = (Button) findViewById(R.id.evaluate);

        score = (TextView) findViewById(R.id.score);
        productHint = (RadioButton) findViewById(R.id.productHint);
        sumHint = (RadioButton) findViewById(R.id.sumHint);
        primeHint = (RadioButton) findViewById(R.id.primeHint);
        divisibilityHint = (RadioButton) findViewById(R.id.divisibilityHint);
        hint = (Button) findViewById(R.id.hint);




        if(currentrandomnumber<=0 ){
            hint.setEnabled(false);//Disable both buttons at the beginning
            evaluate.setEnabled(false);

        }
        else
            {
            hint.setEnabled(true);//Enable both buttons at the beginning
            evaluate.setEnabled(true);

           }

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "A secret number has been generated randomly. Go, guess it!", Toast.LENGTH_SHORT).show();
                currentrandomnumber = generateRandomNumber();
                hint.setEnabled(true);
                evaluate.setEnabled(true);
                currentscore=getValueEdittext(upperBound)-getValueEdittext(lowerBound);

            }
        });



        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentscore--;
                openResultActivity();
            }
        });

        seekbarUpperBound();
        seekbarLowerBound();

        getValueEdittext(upperBound);
        getValueEdittext(lowerBound);

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int min = 2 ;
                int max = currentrandomnumber - 1;
                int randomIntMod = (int)Math.floor(Math.random()*(max-min)+min);

                if(divisibilityHint.isChecked()){

                    currentscore--;
                    if((currentrandomnumber % randomIntMod)== 0) {

                        Toast.makeText(MainActivity.this, "It is by " + randomIntMod +" dividable", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(MainActivity.this, "It is NOT by " + randomIntMod +" dividable", Toast.LENGTH_SHORT).show();

                    }
                    }
                else if(primeHint.isChecked()){

                    currentscore=currentscore - 10;
                    int num = currentrandomnumber;

                    boolean flag = false;
                    for (int i = 2; i <= num / 2; ++i) {
                        // condition for nonprime number
                        if (num % i == 0) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        Toast.makeText(MainActivity.this, "It is a prime number.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "It is NOT a prime number.", Toast.LENGTH_SHORT).show();
                }
                }
                else if(sumHint.isChecked()){

                    currentscore=currentscore - 5;
                    int num = currentrandomnumber;
                    int sum = 0;
                    while (num > 0) {
                        sum = sum + num % 10;
                        num = num / 10;
                    }
                    Toast.makeText(MainActivity.this, "The digit sum is "+ sum, Toast.LENGTH_SHORT).show();

                }
                else if(productHint.isChecked()){
                    currentscore = currentscore - 10;
                    int product = 1;
                    int num = currentrandomnumber;
                    while (num != 0) {
                        product = product * (num % 10);
                        num = num / 10;
                    }
                    Toast.makeText(MainActivity.this, "The digit product is "+ product, Toast.LENGTH_SHORT).show();

                }
            }

        });



        new Thread(new Runnable() {//this Thread execute
            @Override
            public void run() {
                //your 1st command
                while (true) {
                    edittexttoseekbar();
                    score.setText(String.valueOf(currentscore));



                    Thread.currentThread().interrupt();


                    //you can use a for here and check if the command was executed or just wait and execute the 2nd command
                    try {
                        Thread.sleep(500); //every 0.5 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

    }

    public void seekbarLowerBound() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                lowerBound.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            seekBar.setMax(getValueEdittext(upperBound));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void seekbarUpperBound() {

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean b) {
                upperBound.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {
                seekBar2.setMin(getValueEdittext(lowerBound));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {

            }
        });
    }

    public void edittexttoseekbar(){

            try {
                if(getValueEdittext(upperBound)>0 && getValueEdittext(upperBound)<=1000 ){
                seekBar2.setProgress(getValueEdittext(upperBound));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
        if(getValueEdittext(lowerBound)>0 && getValueEdittext(lowerBound)<=1000 ) {
            seekBar.setProgress(getValueEdittext(lowerBound));
        }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public int generateRandomNumber() {
        int min = getValueEdittext(lowerBound);
        int max = getValueEdittext(upperBound);;

        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        score.setText(String.valueOf(currentscore));
        return random_int;
    }

    public void openResultActivity(){

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        guessNumberInt = getValueEdittext(guessNumber);//Here will set the given number fopr that we have to convert the String to the type int
        startActivity(intent);
    }

    public int getValueEdittext(EditText editText) {

        String value = editText.getText().toString();
        int finalValue = Integer.parseInt(value);
        return finalValue;
}



    }




