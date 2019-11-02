package com.example.fooddatabase;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fooddatabase.model.Food;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    Food food;
    public TextView label;
    public TextView ingredients;
    public TextView procent;
    public TextView carbs;
    public TextView fat;
    public ImageView imageView;
    public TextView dietlabel;
    public TextView Tfat;
    public TextView CusineType;
    public TextView DishType;
    public CardView cardView;
    String labels = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        food = (Food) getIntent().getSerializableExtra("arg");
        actionBar.setTitle(food.getLabel());

        initilize();

        ArrayList<String> labelArray = food.getDietLabel();

        for (int i=0;i<labelArray.size();i++){
            if (i==labelArray.size()-1){
                labels= labels+labelArray.get(i)+"";
            }
            else {
                labels= labels+labelArray.get(i)+",";
            }
        }

        label.setText(food.getLabel());
        Glide.with(this).load(food.getURL()).into(imageView);
        ingredients.setText(food.getIngredients());
        procent.setText(food.getProtien().substring(0, food.getProtien().indexOf("."))+"g");
        carbs.setText(food.getCarbs().substring(0, food.getCarbs().indexOf("."))+"kcal");
        fat.setText(food.getFat().substring(0, food.getFat().indexOf("."))+"g");
        Tfat.setText(food.getTfat().substring(0, food.getTfat().indexOf("."))+"g");
        dietlabel.setText(labels);
        CusineType.setText(food.getCusine());
        DishType.setText(food.getDish());
        Toast.makeText(this, ""+food.getLabel(), Toast.LENGTH_SHORT).show();
    }

    private void initilize() {
        label = findViewById(R.id.label);
        ingredients = findViewById(R.id.ingredients);
        carbs = findViewById(R.id.carbs);
        procent = findViewById(R.id.procent);
        fat = findViewById(R.id.fat);
        imageView = findViewById(R.id.image);
        dietlabel = findViewById(R.id.dietlabel);
        Tfat = findViewById(R.id.TFat);
        CusineType = findViewById(R.id.CuisineType);
        DishType = findViewById(R.id.DishType);
        cardView = findViewById(R.id.cardView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
