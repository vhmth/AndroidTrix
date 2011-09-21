/**
 * @author Vinay Hiremath
 * vhiremath4@gmail.com
 * Code that easily parses JSON from a remote URL.
 */

package com.androidtrix.androidtrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PainlessJSONParsing extends Activity{
	
	public getJSONData getIt;//the AsyncTask that will parse the JSON data
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.painlessjsonparsing);
        
        //when the button is clicked, get the JSON data
        Button getJSON = (Button)findViewById(R.id.getjson);
        getJSON.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText urlField = (EditText)findViewById(R.id.urlfield);//where we'll get the user URL input
				getIt = new getJSONData(v.getContext(), urlField.getText().toString());
				getIt.execute();//finally get the JSON data
			}
		});
        
        //display the code in the Activity
        TextView code = (TextView)findViewById(R.id.jsonparsingcode);
        code.setText(
        	"CODE (you should thread this code):\n"+
        	"------------------------------------\n"+
        	"JSONObject jObject = null;\n"+
        	"try{\n"+
        	"\tHttpClient httpclient = new DefaultHttpClient();\n"+
        	"\tHttpGet httpget = new HttpGet(YOUR URL AS A STRING HERE);\n"+
        	"\tHttpResponse response;\n"+
        	"\tresponse = httpclient.execute(httpget);\n"+
        	"\tHttpEntity entity = response.getEntity();\n"+
        	"\tInputStream in = entity.getContent();\n"+
        	"\tBufferedReader reader = new BufferedReader(new InputStreamReader(in));\n"+
        	"\tStringBuilder sb = new StringBuilder();\n"+
        	"\tString input = null;\n"+
        	"\ttry{\n"+
        	"\t\twhile ((input = reader.readLine()) != null)\n"+
        	"\t\t\tsb.append(input + \"\n\");\n"+
        	"\t}catch (IOException e) {\n"+
        	"\t\te.printStackTrace();\n"+
        	"\t} finally{\n"+
        	"\t\ttry{\n"+
        	"\t\t\tin.close();\n"+
        	"\t\t} catch (IOException e) {\n"+
        	"\t\t\te.printStackTrace();\n"+
        	"\t\t}\n"+
        	"\t}\n"+
        	"\tString enter = sb.toString();\n"+
        	"\tjObject = new JSONObject(enter);"+
        	"\tin.close();\n"+
        	"} catch(MalformedURLException e){\n"+
        	"\te.printStackTrace();\n"+
        	"} catch (IOException e) {\n"+
        	"\te.printStackTrace();\n"+
        	"} catch (JSONException e){\n"+
        	"\te.printStackTrace();\n"+
        	"}\n"+
        	"//Do what you want with jObject, your JSON data.\n"+
        	"------------------------------------\n"
        );
        
        //back button to go back to the main menu
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();	
			}
		});
        
	}
	
	//THE ACTUAL CODE
	//the AsyncTask class that will handle parsing the JSON String
	//by throwing it onto a background worker thread
	class getJSONData extends AsyncTask<String, Void, JSONObject>{
		
		String URL;//URL location that will be parsed
		Context context;//context needed for Toasting the contents of the JSON Data
		
		//constructor
		public getJSONData(Context cntx, String url){
			context = cntx;
			URL = url;
		}
		
		//This is the actual code that parses the JSON String from a remote URL
		protected JSONObject doInBackground(String... params){
			//safeguard
			if (URL==null || URL.equals(""))
				return null;
			
			JSONObject jObject = null;//where our JSON data will be stored

			//the actual code that parses the URL
			try{
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(URL);
				HttpResponse response;
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();

				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
					
				String input = null;
			    try {
			        while ((input = reader.readLine()) != null) {
			        sb.append(input + "\n");
			        }
			    } catch (IOException e) {
			            e.printStackTrace();
			    } finally {
			        try {
			            in.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
			    String enter = sb.toString();
			    jObject = new JSONObject(enter);
				in.close();
			} catch(MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e){
				e.printStackTrace();
			}
			return jObject;
		}
		
		//the JSONObject from doInBackground gets passed here for
		//handling its data; in this case, we'll simply Toast its
		//contents to the screen
		protected void onPostExecute(JSONObject jObject){
			//if we couldn't get the data
			if (jObject==null){
				Toast.makeText(context, "Could not parse the JSON data. Please check the URL.", 15000).show();
				return;
			}
			
			//otherwise return its String representation
			Toast.makeText(context, jObject.toString(), 15000).show();
		}
	}
	
}
