package com.joeanakbar.fp.ui.detail;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joeanakbar.fp.R;
import com.joeanakbar.fp.model.MovieItem;
import com.joeanakbar.fp.model.TvShowItem;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity implements DetailView.View {
    private ImageView imgPoster, imgBanner;
    private TextView tvTitle, tvReleased, tvSynopsis;
    private String pathBanner, pathGambar;
    private ImageButton btnFavo;
    private MovieItem movieItem;
    private TvShowItem tvShowItem;
    private DetailPresenter detailPresenter = new DetailPresenter(this);
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //Buat Full Transparan di StatusBar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        imgBanner = findViewById(R.id.imgBanner);
        imgPoster = findViewById(R.id.imgPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvReleased = findViewById(R.id.tvReleased);
        tvSynopsis = findViewById(R.id.tvSinopsis);
        btnFavo = findViewById(R.id.btnFavorite);

        Bundle bundle = getIntent().getExtras();
        detailPresenter.getDetailMovie(bundle);

        btnFavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    if (tvShowItem != null) {
                        detailPresenter.removeFavTv(DetailActivity.this, tvShowItem);
                    } else {
                        detailPresenter.removeFavMovie(DetailActivity.this, movieItem);
                    }
                } else {
                    if (tvShowItem != null) {
                        detailPresenter.addToFavTv(DetailActivity.this, tvShowItem);
                    } else {
                        detailPresenter.addToFavMovie(DetailActivity.this, movieItem);
                    }
                }
                if (tvShowItem != null) {
                    isFavorite = detailPresenter.checkFavTvShow(DetailActivity.this, tvShowItem);
                } else {
                    isFavorite = detailPresenter.checkFavMovie(DetailActivity.this, movieItem);
                }
                setFavorite();
            }
        });
    }

    @Override
    public void showDetailMovie(MovieItem movieItem) {
        this.movieItem = movieItem;
        pathBanner = movieItem.getBannerPath();
        pathGambar = movieItem.getPosterPath();
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + pathBanner)
                .into(imgBanner);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + pathGambar)
                .into(imgPoster);
        tvTitle.setText(movieItem.getTitle());
        tvReleased.setText(movieItem.getReleaseDate());
        tvSynopsis.setText(movieItem.getOverview());
        isFavorite = detailPresenter.checkFavMovie(this, movieItem);
        setFavorite();
    }

    @Override
    public void showDetailTvShow(TvShowItem tvShowItem) {
        this.tvShowItem = tvShowItem;
        pathBanner = tvShowItem.getBannerPath();
        pathGambar = tvShowItem.getPosterPath();
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + pathBanner)
                .into(imgBanner);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + pathGambar)
                .into(imgPoster);
        tvTitle.setText(tvShowItem.getTitle());
        tvReleased.setText(tvShowItem.getReleaseDate());
        tvSynopsis.setText(tvShowItem.getOverview());
        isFavorite = detailPresenter.checkFavTvShow(this, tvShowItem);
        setFavorite();
    }

    private void setFavorite() {
        if (isFavorite) {
            btnFavo.setBackground((getResources().getDrawable(R.drawable.ic_favorite_red_true)));
        } else {
            btnFavo.setBackground((getResources().getDrawable(R.drawable.ic_favorite_red_false)));
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void btnKembali(View view) {
        finish();
    }
}
