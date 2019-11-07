package com.example.fooddatabase.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.fooddatabase.DetailActivity;
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
//        String ingrCancatinate = "";
//        String labels = "";
//        String ingrArray = foodList.get(position).getIngredients();
//        ArrayList<String> labelArray = foodList.get(position).getDietLabel();
//        for (int i=0;i<ingrArray.size();i++){
//            if (i ==ingrArray.size()-1){
//                ingrCancatinate= ingrCancatinate+ingrArray.get(i)+".";
//            }
//            else {
//                ingrCancatinate= ingrCancatinate+ingrArray.get(i)+",";
//            }
//
//
//        }
//        for (int i=0;i<labelArray.size();i++){
//            if (i==labelArray.size()-1){
//                labels= labels+labelArray.get(i)+"";
//            }
//            else {
//                labels= labels+labelArray.get(i)+",";
//            }
//
//
//        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.label.setText(Html.fromHtml("<h4>Title:</h2><p>"+foodList.get(position).getLabel()+"</p>", Html.FROM_HTML_MODE_COMPACT));
//            holder.ingredients.setText(Html.fromHtml("<h4>Energy:</h2><p>"+foodList.get(position).getCategory().substring(0, foodList.get(position).getCategory().indexOf("."))+"</p>", Html.FROM_HTML_MODE_COMPACT));
//            holder.procent.setText(Html.fromHtml("<h4>Proten:</h2><p>"+foodList.get(position).getPROCNT().substring(0, foodList.get(position).getPROCNT().indexOf("."))+"</p>", Html.FROM_HTML_MODE_COMPACT));
//            holder.carbs.setText(Html.fromHtml("<h4>Enery:</h2><p>"+foodList.get(position).getENERC_KCAL().substring(0, foodList.get(position).getENERC_KCAL().indexOf("."))+"</p>", Html.FROM_HTML_MODE_COMPACT));
//            holder.fat.setText(Html.fromHtml("<h4>Fat:</h2><p>"+foodList.get(position).getFAT().substring(0, foodList.get(position).getPROCNT().indexOf("."))+"</p>", Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            holder.ingredients.setText(foodList.get(position).getCategory());
//            holder.procent.setText(foodList.get(position).getPROCNT());
//            holder.carbs.setText(foodList.get(position).getENERC_KCAL());
//            holder.fat.setText(foodList.get(position).getFAT());
//            holder.label.setText(Html.fromHtml("<h4>Title:</h2><p>"+foodList.get(position).getLabel()+"</p>"));
//        }
        Glide.with(context).load(foodList.get(position).getURL()).into(holder.imageView);

        holder.label.setText(foodList.get(position).getLabel());
//        holder.ingredients.setText(ingrArray);
//        holder.procent.setText(foodList.get(position).getProtien().substring(0, foodList.get(position).getProtien().indexOf("."))+"g");
//        holder.carbs.setText(foodList.get(position).getCarbs().substring(0, foodList.get(position).getCarbs().indexOf("."))+"kcal");
//        holder.fat.setText(foodList.get(position).getFat().substring(0, foodList.get(position).getFat().indexOf("."))+"g");
//        holder.Tfat.setText(foodList.get(position).getTfat().substring(0, foodList.get(position).getTfat().indexOf("."))+"g");
//        holder.dietlabel.setText(labels);
//        holder.CusineType.setText(foodList.get(position).getCusine());
//        holder.DishType.setText(foodList.get(position).getDish());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopupWithGlide(foodList.get(position).getURL());
                imagePopup.viewPopup();
//                Toast.makeText(context, ""+foodList.get(position).getURL(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("arg", foodList.get(position)); // getText() SHOULD NOT be static!!!
                context.startActivity(intent);
            }
        });

//
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView label;
//        public TextView ingredients;
//        public TextView procent;
//        public TextView carbs;
//        public TextView fat;
         ImageView imageView;
//        public TextView dietlabel;
//        public TextView Tfat;
//        public TextView CusineType;
//        public TextView DishType;
        CardView cardView;
        

        BookViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.label);
//            ingredients = (TextView) view.findViewById(R.id.ingredients);
//            carbs = (TextView) view.findViewById(R.id.carbs);
//            procent = (TextView) view.findViewById(R.id.procent);
//            fat = (TextView) view.findViewById(R.id.fat);
            imageView = (ImageView) view.findViewById(R.id.image);
//            dietlabel = (TextView) view.findViewById(R.id.dietlabel);
//            Tfat = (TextView) view.findViewById(R.id.TFat);
//            CusineType = (TextView) view.findViewById(R.id.CuisineType);
//            DishType = (TextView) view.findViewById(R.id.DishType);
            cardView = (CardView) view.findViewById(R.id.cardView);
           
        }
    }
}