package com.example.fooddatabase.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.fooddatabase.R;
import com.example.fooddatabase.model.Food;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.BookViewHolder>{

    private List<Food> foodList;
    Context context;
    ImagePopup imagePopup;
    String optional;
    public MyRecyclerAdapter(List<Food> foodList,Context context,ImagePopup imagePopup, String optional ) {
        this.foodList = foodList;
        this.context = context;
        this.imagePopup = imagePopup;
        this.optional = optional;

    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        String ingrCancatinate = "";
        String labels = "";
        String ingrArray = foodList.get(position).getIngredients();
        ArrayList<String> labelArray = foodList.get(position).getDietLabel();

        for (int i=0;i<labelArray.size();i++){
            if (i==labelArray.size()-1){
                labels= labels+labelArray.get(i)+"";
            }
            else {
                labels= labels+labelArray.get(i)+",";
            }


        }


        Glide.with(context).load(foodList.get(position).getURL()).into(holder.imageView);

        holder.label.setText(foodList.get(position).getLabel());
        holder.ingredients.setText(ingrArray);
        holder.procent.setText(foodList.get(position).getProtien().substring(0, foodList.get(position).getProtien().indexOf("."))+"g");
        holder.carbs.setText(foodList.get(position).getCarbs().substring(0, foodList.get(position).getCarbs().indexOf("."))+"kcal");
        holder.fat.setText(foodList.get(position).getFat().substring(0, foodList.get(position).getFat().indexOf("."))+"g");
        holder.Tfat.setText(foodList.get(position).getTfat().substring(0, foodList.get(position).getTfat().indexOf("."))+"g");
        holder.dietlabel.setText(labels);
        holder.CusineType.setText(foodList.get(position).getCusine());
        holder.DishType.setText(foodList.get(position).getDish());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopupWithGlide(foodList.get(position).getURL());
                imagePopup.viewPopup();
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });

//
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
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
        

        public BookViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.label);
            ingredients = (TextView) view.findViewById(R.id.ingredients);
            carbs = (TextView) view.findViewById(R.id.carbs);
            procent = (TextView) view.findViewById(R.id.procent);
            fat = (TextView) view.findViewById(R.id.fat);
            imageView = (ImageView) view.findViewById(R.id.image);
            dietlabel = (TextView) view.findViewById(R.id.dietlabel);
            Tfat = (TextView) view.findViewById(R.id.TFat);
            CusineType = (TextView) view.findViewById(R.id.CuisineType);
            DishType = (TextView) view.findViewById(R.id.DishType);
           
        }
    }
}