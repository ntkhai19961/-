package com.example.win7.apphoctiengnhat.Translate.ui.fragment.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.win7.apphoctiengnhat.Translate.App;
import com.example.win7.apphoctiengnhat.R;
import com.example.win7.apphoctiengnhat.Translate.controller.adapter.HistoryRecyclerViewAdapter;
import com.example.win7.apphoctiengnhat.Translate.presentation.contract.history.HistoryContract;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.history.DaggerHistoryComponent;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.history.HistoryModule;
import com.example.win7.apphoctiengnhat.Translate.presentation.implementation.history.HistoryPresenterImpl;
import com.example.win7.apphoctiengnhat.Translate.ui.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NG on 11.04.17.
 */

public class HistoryFragment extends BaseFragment implements HistoryContract.View {

    @BindView(R.id.history_recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.empty_view)
    TextView mEmptyTextView;

    @Inject
    HistoryPresenterImpl mPresenter;
    @Inject
    HistoryRecyclerViewAdapter mHistoryRecyclerViewAdapter;

    public static Fragment newInstance(@Nullable Bundle args) {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_yandex_api_translate, container, false);

        ButterKnife.bind(this, rootView);

        DaggerHistoryComponent.builder()
                .appComponent(App.getAppComponent())
                .historyModule(new HistoryModule(this))
                .build().inject(this);

        initRecyclerView();

        return rootView;
    }

    public void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mHistoryRecyclerViewAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_yandex_api_translate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear_history: {
                mPresenter.clearHistory();
                return true;
            }
        }

        return false;
    }

    @Override
    public void showEmptyView() {
        mRecycler.setVisibility(View.GONE);
        mEmptyTextView.setVisibility(View.VISIBLE);
    }
}
