package com.example.imran.newproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    TextView textView;
    Button button;
    EditText editText;
    StringRequest stringRequest;
    String postid;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textview);
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.edittext);

        requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.4/temp/simple_query.php";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText("Response: \n");

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0 ;i<jsonArray.length();i++){
                        String a = jsonArray.getJSONObject(i).getString("main");
                        String b = jsonArray.getJSONObject(i).getString("info");

                        textView.append("A: "+a+" \n");
                        textView.append("B: "+b+" \n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    textView.append(response);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("postid", "1");
                return params;
            };
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postid = editText.getText().toString();
                requestQueue.add(stringRequest);
            }
        });

    }
}
