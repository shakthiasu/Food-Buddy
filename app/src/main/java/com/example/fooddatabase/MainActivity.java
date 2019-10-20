package com.example.fooddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.fooddatabase.adapter.MyRecyclerAdapter;
import com.example.fooddatabase.model.Food;

import net.gotev.speech.GoogleVoiceTypingDisabledException;
import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;
import net.gotev.speech.SpeechRecognitionNotAvailable;
import net.gotev.speech.SpeechUtil;
import net.gotev.speech.ui.SpeechProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements SpeechDelegate{
    private static final String TAG = "MainActivity";
    public RequestQueue mRequestQueue;
    public StringRequest mStringRequest;
    RecyclerView recyclerView;
    EditText editText;
    MyRecyclerAdapter mAdapter;
    private List<Food> foodList = new ArrayList<>();
    private List<Food> foodList2 = new ArrayList<>();
    private SpeechProgressView progress;
    private ImageView button;
    private final int PERMISSIONS_REQUEST = 1;
    android.app.AlertDialog spotsDialog;
    EditText optional;
    ImageView search;
    String Tfat = "0.0";
    ArrayList<String> ingredientsArray  = new ArrayList<>();
    boolean global = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Speech.init(this, getPackageName());
        ImagePopup imagePopup = new ImagePopup(this);
        spotsDialog = new SpotsDialog.Builder().setContext(this).build();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        optional = findViewById(R.id.optional);
        mAdapter = new MyRecyclerAdapter(foodList,this,imagePopup,optional.getText().toString());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progress = findViewById(R.id.progress);
        int[] colors = {
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.darker_gray),
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.holo_orange_dark),
                ContextCompat.getColor(this, android.R.color.holo_red_dark)
        };
        progress.setColors(colors);
        button = findViewById(R.id.voice);
        button.setOnClickListener(view -> onButtonClick());
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText()!=null) {

                    retrofitRequest(optional.getText().toString());
                }
                else {
                    Toast.makeText(MainActivity.this, "Field Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    retrofitRequest(optional.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void retrofitRequest(String optp){
        spotsDialog.show();
        foodList.clear();
        foodList2.clear();


        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, "https://api.edamam.com/search?q="+editText.getText()+"&app_id=ae8fff85&app_key=524f2fae42dfc76a691fd57fbe3e5893&from=0&to=10", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array  = jsonObject.getJSONArray("hits");
//                    Toast.makeText(MainActivity.this, "great"+array.length(), Toast.LENGTH_SHORT).show();
                    for (int i=0;i<array.length();i++){
                        ingredientsArray.clear();
                        String CusineText ="";
                        String DishText ="";
                        ArrayList<String> dietArray  = new ArrayList<>();
                        JSONObject recipe = array.getJSONObject(i);
                        JSONObject receipe2 = recipe.getJSONObject("recipe");
                        JSONObject totalNutrients = receipe2.getJSONObject("totalNutrients");
                        JSONArray ingredientLines = receipe2.getJSONArray("ingredientLines");
                        if (receipe2.has("cuisineType")) {
                            JSONArray cuisineType = receipe2.getJSONArray("cuisineType");
                            for (int l=0;l<cuisineType.length();l++){
                                if (l ==cuisineType.length()-1){
                                    CusineText = CusineText+cuisineType.get(l)+"";
                                    CusineText = CusineText.substring(0, 1).toUpperCase() + CusineText.substring(1);
                                }
                                else {
                                    CusineText = CusineText+cuisineType.get(l)+",";
                                    CusineText = CusineText.substring(0, 1).toUpperCase() + CusineText.substring(1);
                                }

                            }
                        }
                        else {
                            CusineText = "None";
                        }
                        if (receipe2.has("dishType")) {
                            JSONArray dishType = receipe2.getJSONArray("dishType");
                            for (int m=0;m<dishType.length();m++){
                                if (m ==dishType.length()-1){
                                    DishText = DishText+dishType.get(m)+"";
                                    DishText = DishText.substring(0, 1).toUpperCase() + DishText.substring(1);
                                }
                                else {
                                    DishText = DishText+dishType.get(m)+",";
                                    DishText = DishText.substring(0, 1).toUpperCase() + DishText.substring(1);
                                }

                            }
                        }
                        else {
                            DishText = "None";
                        }
                        JSONArray dietLabels = receipe2.getJSONArray("dietLabels");
                        for (int j=0;j<ingredientLines.length();j++){
                            ingredientsArray.add(ingredientLines.get(j).toString());
                        }
                        for (int k=0;k<dietLabels.length();k++){
                            dietArray.add(dietLabels.get(k).toString());
                        }


                        JSONObject ENERC_KCAL = totalNutrients.getJSONObject("ENERC_KCAL");
                        JSONObject FAT = totalNutrients.getJSONObject("FAT");
                        JSONObject PROCNT = totalNutrients.getJSONObject("PROCNT");
                        if (totalNutrients.has("FATRN")){
                            JSONObject FATRN = totalNutrients.getJSONObject("FATRN");
                            Tfat = FATRN.getString("quantity");
                        }
                        else {
                            Tfat = "0.0";
                        }

//                        JSONObject CHOCDF = totalNutrients.getJSONObject("CHOCDF");
//                        String carbs = CHOCDF.getString("quantity");
                        String protien = PROCNT.getString("quantity");
                        String fat = FAT.getString("quantity");

                        String energy = ENERC_KCAL.getString("quantity");
                        String label = receipe2.getString("label");
                        String url = receipe2.getString("image");
                        String ingrCancatinate ="";
                        for (int k=0;k<ingredientsArray.size();k++){
                            if (k ==ingredientsArray.size()-1){
                                ingrCancatinate= ingrCancatinate+ingredientsArray.get(k)+".";
                            }
                            else {
                                ingrCancatinate= ingrCancatinate+ingredientsArray.get(k)+",";
                            }


                        }

                        String names = optional.getText().toString();
                        List<String> namesList = (List<String>) Arrays.asList(names.split(","));
                        Log.v("increment",namesList.size()+"::"+namesList);
                        if (namesList.size()==1){
                            if (ingrCancatinate.contains(namesList.get(0))) {
                                Food food1 = new Food(label, energy, protien, fat, ingrCancatinate, url, dietArray, Tfat,CusineText,DishText);
                                foodList.add(food1);
                            }
                        }
                        else if (namesList.size()==2){
                            if (ingrCancatinate.contains(namesList.get(0))&& ingrCancatinate.contains(namesList.get(1))) {
                                Food food1 = new Food(label, energy, protien, fat, ingrCancatinate, url, dietArray, Tfat,CusineText,DishText);
                                foodList.add(food1);
                            }
                        }
                        else if (namesList.size()==3){
                            if (ingrCancatinate.contains(namesList.get(0))&&ingrCancatinate.contains(namesList.get(1))&&ingrCancatinate.contains(namesList.get(2))) {
                                Food food1 = new Food(label, energy, protien, fat, ingrCancatinate, url, dietArray, Tfat,CusineText,DishText);
                                foodList.add(food1);
                            }
                        }
                        else if (namesList.size()==4){
                            if (ingrCancatinate.contains(namesList.get(0))&&ingrCancatinate.contains(namesList.get(1))&&ingrCancatinate.contains(namesList.get(2))&&ingrCancatinate.contains(namesList.get(3))) {
                                Food food1 = new Food(label, energy, protien, fat, ingrCancatinate, url, dietArray, Tfat,CusineText,DishText);
                                foodList.add(food1);
                            }
                        }
                        else if (namesList.size()==5){
                            if (ingrCancatinate.contains(namesList.get(0))&&ingrCancatinate.contains(namesList.get(1))&&ingrCancatinate.contains(namesList.get(2))&&ingrCancatinate.contains(namesList.get(3))&&ingrCancatinate.contains(namesList.get(4))) {
                                Food food1 = new Food(label, energy, protien, fat, ingrCancatinate, url, dietArray, Tfat,CusineText,DishText);
                                foodList.add(food1);
                            }
                        }
//                        else {
//                            Food food1 = new Food(label, energy, protien, fat, ingrCancatinate, url, dietArray, Tfat,CusineText,DishText);
//                            foodList.add(food1);
//                        }


                        mAdapter.notifyDataSetChanged();
                        spotsDialog.dismiss();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void onButtonClick() {
        if (Speech.getInstance().isListening()) {
            Speech.getInstance().stopListening();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                onRecordAudioPermissionGranted();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                onRecordAudioPermissionGranted();
            } else {

                Toast.makeText(MainActivity.this, R.string.permission_required, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onRecordAudioPermissionGranted() {
//        button.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        try {
            Speech.getInstance().stopTextToSpeech();
            Speech.getInstance().startListening(progress, (SpeechDelegate) MainActivity.this);

        } catch (SpeechRecognitionNotAvailable exc) {
            showSpeechNotSupportedDialog();

        } catch (GoogleVoiceTypingDisabledException exc) {
            showEnableGoogleVoiceTyping();
        }
    }

    private void showSpeechNotSupportedDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        SpeechUtil.redirectUserToGoogleAppOnPlayStore(MainActivity.this);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }
    private void showEnableGoogleVoiceTyping() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.enable_google_voice_typing)
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                })
                .show();
    }


    @Override
    public void onStartOfSpeech() {

    }

    @Override
    public void onSpeechRmsChanged(float value) {

    }

    @Override
    public void onSpeechPartialResults(List<String> results) {
        editText.setText("");
        for (String partial : results) {
            editText.append(partial + " ");
        }
    }

    @Override
    public void onSpeechResult(String result) {
//        button.setVisibility(View.VISIBLE);
//        linearLayout.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        editText.setText(result);

        if (result.isEmpty()) {
            Speech.getInstance().say(getString(R.string.repeat));

        } else {
            search.performClick();
//            Speech.getInstance().say(result);
        }
    }
}
