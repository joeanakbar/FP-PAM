package com.joeanakbar.fp.ui.tvshow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.joeanakbar.fp.R;
import com.joeanakbar.fp.adapter.TvShowAdapter;
import com.joeanakbar.fp.model.TvShowItem;

import java.util.ArrayList;

import static com.joeanakbar.fp.utils.Helper.KEY_MOVIES;

public class TvShowFragment extends Fragment implements TvShowView {
    private ArrayList<TvShowItem> dataTvShow = new ArrayList<>();
    private TvShowPresenter presenter;
    private TvShowAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnSearch = view.findViewById(R.id.btnSearchTvShow);
        etSearch = view.findViewById(R.id.etSearchTvShow);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshNow);
        RecyclerView recyclerView = view.findViewById(R.id.rvTvShow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TvShowAdapter(getActivity(), dataTvShow);
        recyclerView.setAdapter(adapter);

        presenter = new TvShowPresenter(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListTvShow();
                etSearch.setText("");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String search = etSearch.getText().toString().toUpperCase();
                presenter.getListSearchTv(search);
            }
        });

        if (savedInstanceState == null) {
            presenter.getListTvShow();
        } else {
            dataTvShow = (ArrayList<TvShowItem>) savedInstanceState.getSerializable(KEY_MOVIES);
            adapter.refill(dataTvShow);
        }
    }

    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishLoad() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showList(ArrayList<TvShowItem> listTvShow) {
        dataTvShow = listTvShow;
        adapter.refill(dataTvShow);
    }

    @Override
    public void noData() {
        swipeRefreshLayout.setRefreshing(false);
        dataTvShow.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(KEY_MOVIES, dataTvShow);
        super.onSaveInstanceState(outState);
    }
}
