package com.rose.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final int FPS = 60;
    private static final int SPEED = 30;

    private GameView mGameView;

    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameView = findViewById(R.id.game_view);
        mGameView.init();

        findViewById(R.id.up_btn).setOnClickListener(v ->
                mGameView.setDirection(Direction.UP));
        findViewById(R.id.down_btn).setOnClickListener(v ->
                mGameView.setDirection(Direction.DOWN));
        findViewById(R.id.left_btn).setOnClickListener(v ->
                mGameView.setDirection(Direction.LEFT));
        findViewById(R.id.right_btn).setOnClickListener(v ->
                mGameView.setDirection(Direction.RIGHT));

        startGame();
    }

    private void startGame() {
        final int delay = 1000 / FPS;
        new Thread(() -> {
            int count = 0;
            while (!mGameView.isGameOver()) {
                try {
                    Thread.sleep(delay);
                    if (count % SPEED == 0) {
                        mGameView.next();
                        mHandler.post(mGameView::invalidate);
                    }
                    count++;
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }
}