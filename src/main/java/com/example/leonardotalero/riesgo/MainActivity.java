package com.example.leonardotalero.riesgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    ProgressDialog pvar;
    HttpResponse response;
    String prima = "";
    TextView atributotextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpClient httpclient = new DefaultHttpClient();
       // final TextView atributotextview = (TextView) this.findViewById(R.id.PrimatextView);
          atributotextview = (TextView) this.findViewById(R.id.PrimatextView);

        //final EditText cedula=(EditText)findViewById(R.id.editText1);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pvar = ProgressDialog.show(MainActivity.this, "Verificando Acceso", "Espere por favor...", true);
        //hola mundo nuevo 	 de nuevo
        new RequestTask().execute("http://www.eleconomista.es/prima-riesgo/espana");


        //HttpGet httpget_ = new HttpGet("http://www.eleconomista.es/prima-riesgo/espana");
        //ResponseHandler<String> responseHandler=new BasicResponseHandler();
        //response = httpclient.execute(httpget_);
        prima=  "España: "+ prima;
        atributotextview.setText(prima);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class RequestTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... mURL1) {


            try {
                Thread.sleep(1000);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

///////////////////////////HTTP FUNCTION/////////////////////////////////////
            String response = "";
            String mURL = mURL1[0].replace(" ", "%20");
            Log.i("LocAnd Response HTTP","Ejecutando get 0:" + mURL);
            HttpClient httpclient = new DefaultHttpClient();

            Log.i("LocAnd Response HTTP", "Ejecutando get 1");
            HttpGet httppost = new HttpGet(mURL);
            Log.i("LocAnd Response HTTP", "Ejecutando get 2");
            try {


                Log.i("LocAnd Response HTTP", "Ejecutando get");
                // Execute HTTP Post Request
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);
                Log.i("LocAnd Response HTTP", response);
            } catch (ClientProtocolException e) {
                Log.i("LocAnd Response ERROR 1", e.getMessage());
                // TODO Auto-generated catch block
            } catch (IOException e) {

                Log.i("LocAnd Response ERROR 2", e.getMessage());
                // TODO Auto-generated catch block
            }
            return response;


            // TODO Auto-generated method stub

        }


        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            JSONArray ja = null;

            if (data.length() > 1){


                // ja = new JSONArray(data);
            /*StatusLine statusline = response.getStatusLine();

            if (statusline.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try {
                    response.getEntity().writeTo(out);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //String responsestring = out.toString();


            } else {
                try {
                    response.getEntity().getContent().close();
                    atributotextview.setText("Error");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            */
                String responsestring = data.toString();
            Pattern patern = Pattern.compile("\\| (\\d{3},\\d{2})");
            Matcher matcher = patern.matcher(responsestring);
            while (matcher.find()) {
                prima = matcher.group(1);

            }
                prima= "España: "+ prima;
                atributotextview.setText(prima);

        }

            /*
            try{


                if(ja.equals(null))
                {
                    Toast.makeText(getApplicationContext(), "Error recuperando la informacion del servidor, verifique su usuario y contrase�a de la empresa", 2000).show();
                }else{
                    Intent loginven = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(loginven);

                }


            } catch (Exception e) {

                //pass.setText("");
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error recuperando la informacion del servidor, verifique usuario y contrase�a.", 1000).show();

            }*/


            /////////////////////


            pvar.dismiss();


        }


    }
}
