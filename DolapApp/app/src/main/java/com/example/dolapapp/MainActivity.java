package com.example.dolapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity  {

    TextView likecount,productprice,productmark,productname,commentcount,starscount;
    ImageView productimage,heartimage,spinnercount,starsimage;
    private RequestQueue mQueue;

String url ;
int toplam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        likecount = (TextView) findViewById(R.id.likecount);
        productprice = (TextView) findViewById(R.id.productprice);

        productmark = (TextView) findViewById(R.id.productmark);
        productname = (TextView) findViewById(R.id.productname);
        commentcount = (TextView) findViewById(R.id.commentcount);
        starscount = (TextView) findViewById(R.id.starscount);

        productimage = (ImageView) findViewById(R.id.productimage);
        heartimage = (ImageView) findViewById(R.id.heartimage);
        starsimage = (ImageView) findViewById(R.id.starsimage);





        mQueue = Volley.newRequestQueue(this);

        String url = "http://aliakgun.com/json/product.json";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("product");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject product = jsonArray.getJSONObject(i);





                                String productmarkk = product.getString("name");




                                String imagebackground = product.getString("image");

                                String productnamee = new String (product.getString("desc").getBytes("ISO-8859-1"), "UTF-8");

                                Picasso.get()
                                        .load(imagebackground)
                                        .into(productimage);


                                productmark.setText(productmarkk);
                                productname.setText(productnamee);

                                JSONObject price = product.getJSONObject("price");
                                String value = price.getString("value");
                                String currency = price.getString("currency");

                                productprice.setText(value+",00  "+currency);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);















        final Handler handler = new Handler();
        mQueue = Volley.newRequestQueue(this);

        Runnable refresh = new Runnable() {
            @Override
            public void run() {


                String url2 = "http://aliakgun.com/json/social.json";


                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArray = response.getJSONArray("social");


                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject product = jsonArray.getJSONObject(i);


                                        String likeCountt = product.getString("likeCount");
                                        likecount.setText(likeCountt);


                                        JSONObject commentCounts = product.getJSONObject("commentCounts");
                                        String averageRating = commentCounts.getString("averageRating");


                                        starscount.setText(averageRating );


                                        String anonymousCommentsCountt = new String(commentCounts.getString("anonymousCommentsCount").getBytes("ISO-8859-1"), "UTF-8");
                                        String memberCommentsCount = new String(commentCounts.getString("memberCommentsCount").getBytes("ISO-8859-1"), "UTF-8");

                                        int anonymousComment = Integer.parseInt(anonymousCommentsCountt);
                                        int memberComment = Integer.parseInt(memberCommentsCount);

                                        commentcount.setText(anonymousComment + memberComment + " Yorum");


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });






                handler.postDelayed(this, 1200000);
                mQueue.add(request2);

            }
        };
        handler.postDelayed(refresh,   000);




    }


}
