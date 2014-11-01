package flash.demo.activities;

import java.lang.reflect.Method;


import flash.demo.R;
import flash.demo.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private WebView wv;
	private ProgressBar progressBar;
	private EditText et;

	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT>=11) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		progressBar.setVisibility(ProgressBar.GONE);
		wv=(WebView) findViewById(R.id.webView1);
		wv.getSettings().setPluginsEnabled(true);
		wv.getSettings().setPluginState(PluginState.ON);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setAllowFileAccess(true);
		wv.setVerticalScrollBarEnabled(true);
		wv.setHorizontalScrollBarEnabled(true);

		//Set Flash Player Enabled
		Boolean[] input={new Boolean(true)};
		try{
			Class cl=Class.forName("android.webkit.WebSettings");
			Class[] par=new Class[1];
			par[0]=Boolean.TYPE;
			@SuppressWarnings("unchecked")
			Method mthd=cl.getMethod("setFlashPlayerEnabled", par);
			mthd.invoke(wv.getSettings(),input);
		}catch(Exception e){
			e.printStackTrace();
		}

		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) 
			{
				if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
					progressBar.setVisibility(ProgressBar.VISIBLE);
				}
				if(progress == 100) {
					progressBar.setVisibility(ProgressBar.GONE);
				}
			}
		});
		et=(EditText) findViewById(R.id.editText1);
		Button go=(Button) findViewById(R.id.button1);
		go.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button1){
			InputMethodManager imm = (InputMethodManager)getSystemService(
					Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
			if(et.getText().toString().length()==0)
				Toast.makeText(this, "Please enter website address.", Toast.LENGTH_SHORT).show();
			else if(!Util.isNetworkAvailable(this))
				Toast.makeText(this, "Internet not available.", Toast.LENGTH_SHORT).show();
			else{
				String url=et.getText().toString();
				if(!url.startsWith("http://"))
					url="http://"+url;
				wv.loadUrl(url);
			}
		}
	}
}
