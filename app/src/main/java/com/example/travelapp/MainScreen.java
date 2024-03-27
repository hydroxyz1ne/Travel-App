package com.example.travelapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainScreen extends AppCompatActivity {

    LinearLayout linearPlaceContainer;
    LinearLayout linearFavoritesContainer;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);


        linearPlaceContainer = findViewById(R.id.linearPlaceContainer);
        linearFavoritesContainer = findViewById(R.id.linearFavoriteContainer);
        databaseReference = FirebaseDatabase.getInstance().getReference("Place");
        loadPlaceFromFirebase();
    }

    private void loadPlaceFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                linearPlaceContainer.removeAllViews();
                linearFavoritesContainer.removeAllViews();

                for (DataSnapshot placeSnapshot : dataSnapshot.getChildren()) {
                    String placeName = placeSnapshot.child("name").getValue(String.class);
                    String placeImage = placeSnapshot.child("imageId").getValue(String.class);
                    String placeReview = placeSnapshot.child("reviews").getValue(String.class);
                    String favoriteString = placeSnapshot.child("favorite").getValue(String.class);

                    Log.d("FirebaseData", "Place name from Firebase: " + placeName);

                    Place currentPlace = placeSnapshot.getValue(Place.class);

                    View placeCard = getLayoutInflater().inflate(R.layout.place_card, null);
                    TextView textViewPlaceName = placeCard.findViewById(R.id.title);
                    TextView textViewPlaceReview = placeCard.findViewById(R.id.reviews);
                    ImageView imageViewPlace = placeCard.findViewById(R.id.cardImage);
                    ImageButton addToFavoritesBtn = placeCard.findViewById(R.id.addToFavoritesBtn);

                    textViewPlaceName.setText(placeName);
                    textViewPlaceReview.setText(placeReview);
                    Picasso.get().load(placeImage).error(R.drawable.without).into(imageViewPlace);

                    // Добавляем карточку места в контейнер linearPlaceContainer
                    linearPlaceContainer.addView(placeCard);

                    boolean isFavorite = Boolean.parseBoolean(favoriteString);
                    if (isFavorite) {
                        // Если место является избранным, добавляем карточку также в linearFavoritesContainer
                        View favoritePlaceCard = getLayoutInflater().inflate(R.layout.place_card, null);
                        TextView textViewFavoritePlaceName = favoritePlaceCard.findViewById(R.id.title);
                        TextView textViewFavoritePlaceReview = favoritePlaceCard.findViewById(R.id.reviews);
                        ImageView imageViewFavoritePlace = favoritePlaceCard.findViewById(R.id.cardImage);
                        ImageButton addToFavoritesBtnFavorite = favoritePlaceCard.findViewById(R.id.addToFavoritesBtn);

                        textViewFavoritePlaceName.setText(placeName);
                        textViewFavoritePlaceReview.setText(placeReview);
                        Picasso.get().load(placeImage).error(R.drawable.without).into(imageViewFavoritePlace);

                        linearFavoritesContainer.addView(favoritePlaceCard);

                        // Обработчик события нажатия кнопки добавления в избранное для избранной карточки
                        addToFavoritesBtnFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference placeRef = placeSnapshot.getRef();
                                placeRef.child("favorite").setValue("false") // Установка "false" вместо "true"
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(MainScreen.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainScreen.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }

                    // Обработчик события нажатия кнопки добавления в избранное для всех карточек
                    addToFavoritesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseReference placeRef = placeSnapshot.getRef();
                            placeRef.child("favorite").setValue("true") // Установка "true"
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MainScreen.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainScreen.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });

                    placeCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Создаем интент для перехода в PlaceScreen
                            Intent intent = new Intent(MainScreen.this, PlaceScreen.class);

                            // Передаем данные о месте в PlaceScreen
                            intent.putExtra("PLACE_NAME", currentPlace.getName());
                            intent.putExtra("PLACE_DESC", currentPlace.getDescription());
                            intent.putExtra("PLACE_LOCATION", currentPlace.getLocation());
                            intent.putExtra("PLACE_REVIEWS", currentPlace.getReviews());
                            intent.putExtra("PLACE_PRICE", currentPlace.getPrice());
                            intent.putExtra("PLACE_IMG", currentPlace.getImageId());

                            startActivity(intent);
                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainScreen.this, "Failed to load pets.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
