package com.example.arnold.takehome.views.UI;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arnold.takehome.R;
import com.example.arnold.takehome.models.repoModel;
import com.example.arnold.takehome.models.userModel;
import com.example.arnold.takehome.utils.IdlingResource.MessageDelayer;
import com.example.arnold.takehome.utils.IdlingResource.SimpleIdlingResource;
import com.example.arnold.takehome.utils.animationHelper;
import com.example.arnold.takehome.viewModels.listViewModel;
import com.example.arnold.takehome.viewModels.userViewModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.arnold.takehome.utils.IdlingResource.MessageDelayer.processMessage;

public class MainActivity extends AppCompatActivity implements LifecycleOwner, MessageDelayer.DelayerCallback {

    RepoListFragment repoListFragment;
    userViewModel userViewModel;

    private LifecycleRegistry mLifecycleRegistry;

    EditText editUser;
    TextView username;
    ImageView avatar;

    ConstraintLayout constraintLayout;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUser = (EditText) findViewById(R.id.edt_user);
        username = (TextView) findViewById(R.id.lblUsername);
        avatar = (ImageView) findViewById(R.id.userAvatar);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        if (savedInstanceState == null) {
            repoListFragment = new RepoListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, repoListFragment, "RepoListFragment").commit();
        }

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationHelper.animateLayer(1, 0, findViewById(R.id.constraintLayout), 200);
                animationHelper.moveObjectIntoView(0, 200,findViewById(R.id.cv_repo_info), 200);
            }
        });

        userViewModel viewModel = ViewModelProviders.of(this).get(userViewModel.class);
        observeViewModel(viewModel);
    }

    public void search(View view) {
        updateUsernameAndAvatar(editUser.getText().toString());
        repoListFragment.updateRecycler(editUser.getText().toString());

        processMessage(editUser.getText().toString(), this, mIdlingResource);
    }

    private void observeViewModel(userViewModel viewModel) {
        userViewModel = viewModel;
        userViewModel.getUserObservable().observe(this, new Observer<userModel>() {
            @Override
            public void onChanged(@Nullable userModel user) {
                if (user != null) {
                    username.setText(user.name);

                    new Thread(new Runnable() {
                        public void run() {
                            Bitmap bmp = null;
                            URL url = null;
                            try {
                                url = new URL(user.avatar_url);
                                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                Bitmap finalBmp = bmp;
                                avatar.post(new Runnable() {
                                    public void run() {
                                        avatar.setImageBitmap(finalBmp);
                                    }
                                });
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    animationHelper.animateLayer(0, 1, username, 1000);
                    animationHelper.animateLayer(0, 1, avatar, 1000);

                    animationHelper.moveObjectIntoView(100, 0, username, 600);
                    animationHelper.moveObjectIntoView(100, 0, avatar, 600);
                }
            }
        });
    }

    public void updateUsernameAndAvatar(String username) {
        userViewModel.getUser(username);
        final userViewModel viewModel =
                ViewModelProviders.of(this).get(userViewModel.class);

        observeViewModel(viewModel);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onDone(String text) {
        //Nothing, just need a proper delay
    }
}
