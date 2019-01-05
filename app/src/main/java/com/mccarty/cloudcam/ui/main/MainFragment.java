package com.mccarty.cloudcam.ui.main;

import android.app.Application;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.mccarty.cloudcam.R;
import com.mccarty.cloudcam.di.component.ActivityScope;
import com.mccarty.cloudcam.persistence.local.Image.ImageEntity;
import com.mccarty.cloudcam.ui.components.ImageAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DaggerFragment;

@ActivityScope
public class MainFragment extends DaggerFragment implements MainContract.MainView {

    @Inject
    MainPresenterImpl presenter;

    @BindView(R.id.image_grid)
    RecyclerView recyclerView;

    private Unbinder unbinder;

    @Inject
    public MainFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: do something
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {

        AWSMobileClient.getInstance().initialize(getContext(), awsStartupResult ->
                IdentityManager.getDefaultIdentityManager().getUserID(new IdentityHandler() {
            @Override
            public void onIdentityId(String identityId) {

            }

            @Override
            public void handleError(Exception exception) {

            }
        })).execute();

        presenter.registerReceiver();
        presenter.takeView(this);
        presenter.getAllImages();
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.unregisterReceiver();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.dropView();
    }

    @Override
    public void loadImages(List<ImageEntity> images) {
        recyclerView.setAdapter(new ImageAdapter(images));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getActivity().getResources().getConfiguration().orientation == 1 ? 3 : 5));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    @Override
    public void downloadRemoteImages() {

    }

    @Override
    public void imageClicked() {
        presenter.showImage();
    }

    @Override
    public void checkInternetConnection(Application application) {
        // TODO:
    }

}