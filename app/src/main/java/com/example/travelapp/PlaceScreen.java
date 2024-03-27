package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PlaceScreen extends AppCompatActivity {
    private WebView web;
    private ImageView imageView;
    private Button openBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_screen);

        web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.setVisibility(View.GONE); // Скрываем WebView при запуске активности

        imageView = findViewById(R.id.imgPlace);
        openBookingButton = findViewById(R.id.openBookingButton);

        Intent intent = getIntent();
        String placeName = intent.getStringExtra("PLACE_NAME");
        String placeReview = "Рейтинг:" + intent.getStringExtra("PLACE_REVIEWS");
        String placeDesc = intent.getStringExtra("PLACE_DESC");
        String placeLocation = intent.getStringExtra("PLACE_LOCATION");
        String placePrice = intent.getStringExtra("PLACE_PRICE");

        // Загрузка изображения с использованием Picasso
        Picasso.get()
                .load(intent.getStringExtra("PLACE_IMG"))
                .error(R.drawable.without)
                .into(imageView);

        Log.d("PetActivity", "Loading image from: " + intent.getStringExtra("PLACE_IMG"));

        TextView namePlace = findViewById(R.id.titlePlace);
        TextView reviewPlace = findViewById(R.id.reviews);
        TextView descPlace = findViewById(R.id.descPlace);
        TextView locationPlace = findViewById(R.id.location);
        TextView pricePlace = findViewById(R.id.pricePlace);

        // Установка текста в соответствующие TextView
        namePlace.setText(placeName);
        reviewPlace.setText(placeReview);
        descPlace.setText(placeDesc);
        locationPlace.setText(placeLocation);
        pricePlace.setText(placePrice);

        // Настройка обработчика нажатия кнопки
        openBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // При нажатии кнопки делаем WebView видимым и загружаем Booking.com
                web.setVisibility(View.VISIBLE);
                web.loadUrl("https://www.booking.com/");
            }
        });
    }

    // Переопределение метода onBackPressed для навигации назад внутри WebView
    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
