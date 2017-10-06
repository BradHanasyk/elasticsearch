package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//new imports
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				saveInFile(text, new Date(System.currentTimeMillis()));
				finish();

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String[] tweets = loadFromFile();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	private String[] loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				tweets.add(line);
				line = in.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tweets.toArray(new String[tweets.size()]);
	}
	
	private void saveInFile(String text, Date date) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND);
			fos.write(new String(date.toString() + " | " + text)
					.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void GetTweetTask(String text){
		//find tweets containing text
		//add data then retrieve data
		//Get info: ADD then GET
		//display results somehow
	}

	private void ElasticRestClient(String text  /*tweet body*/) {
		//borrowed from https://stackoverflow.com/questions/42388275/how-to-use-elasticsearch-api-in-android

		private static final String BASE_URL = "http://cmput301.softwareprocess.es.:8080/testing/tweet"; //http://localhost:9200/
		private static final String CLASS_NAME = ElasticRestClient.class.getSimpleName();

		private static AsyncHttpClient client = new AsyncHttpClient();

		public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
			client.get(getAbsoluteUrl(url), params, responseHandler);
		}

		public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
			client.post(getAbsoluteUrl(url), params, responseHandler);
		}

		private static String getAbsoluteUrl(String relativeUrl) {
			return BASE_URL + relativeUrl;
		}

		public void getHttpRequest() {
			try {


				ElasticRestClient.get("get", null, new JsonHttpResponseHandler() { // instead of 'get' use twitter/tweet/1
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						// If the response is JSONObject instead of expected JSONArray
						Log.i(CLASS_NAME, "onSuccess: " + response.toString());
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
						Log.i(CLASS_NAME, "onSuccess: " + response.toString());
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e(CLASS_NAME, "onFailure");
						// called when response HTTP status is "4XX" (eg. 401, 403, 404)
					}

					@Override,
					public void onRetry(int retryNo) {
						Log.i(CLASS_NAME, "onRetry " + retryNo);
						// called when request is retried
					}
				});
			}
			catch (Exception e){
				Log.e(CLASS_NAME, e.getLocalizedMessage());
			}
		}


	//add then get
	List results = new Arraylist();


	result = post(BASE_URL, text, response handler)[2];
	final = get(BASE_URL+result, '', response handler);
	results.add(final);

	}

}