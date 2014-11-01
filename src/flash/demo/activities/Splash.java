package flash.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import flash.demo.R;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.splash);
		new SplashTask().execute();	
	}

	private class SplashTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {			
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;

		}
		@Override
		protected void onPostExecute(Void result) {
			Intent intent;
			intent = new Intent(Splash.this, MainActivity.class);
			startActivity(intent);
			finish();
		}				 
	}

}
