package com.giovedi.stopwatchlog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchLogActivity extends Activity {
	public long startTime, milliTime = 0;
	private Handler mHandler = new Handler();
	private MyTimerTask myTimerTask = null;
	private TextView mTextView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button start_btn = (Button) findViewById(R.id.start_btn);
		start_btn.setOnClickListener(new StartBtnClickListener());
		findViewById(R.id.reset_btn).setOnClickListener(new ResetBtnClickListener());

		mTextView = (TextView) findViewById(R.id.timeText);

	}

	void viewTime() {
		int seconds = (int) (milliTime / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;

		mTextView.setText(String.format("%d:%02d.%02d", minutes, seconds,
				(milliTime % 1000) / 10));
	}
	
	class MyTimerTask implements Runnable {

		@Override
		public void run() {
			milliTime = System.currentTimeMillis() - startTime;
			viewTime();

			mHandler.postDelayed(this, 10);

		}

	}

	class StartBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (myTimerTask == null) {
				startTime = System.currentTimeMillis() - milliTime;
				myTimerTask = new MyTimerTask();
				mHandler.post(myTimerTask);
			} else {
				mHandler.removeCallbacks(myTimerTask);
				myTimerTask = null;
			}
		}

	}

	class ResetBtnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			milliTime = 0;
			viewTime();
		}
	}
}