package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.DatabaseProfiles;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr05.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    private static final int RC_PROFILE = 2;
    private ActivityMainBinding b;
    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(DatabaseProfiles.getInstance())).get(MainActivityViewModel.class);

        setUpViews();
        observeProfiles();
    }

    private void observeProfiles() {
        viewModel.getProfiles().observe(this, profiles -> {
            listAdapter.submitList(profiles);
            b.lblEmptyView.setVisibility(profiles.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setUpViews() {

        listAdapter = new MainActivityAdapter(position -> deleteProfile(listAdapter.getItem(position)), position -> editProfile(listAdapter.getItem(position)));


        b.lstProfiles.setHasFixedSize(true);
        b.lstProfiles.setLayoutManager(new GridLayoutManager(this,getResources().getInteger(R.integer.main_lstProfiles_columns)));
        b.lstProfiles.setItemAnimator(new DefaultItemAnimator());
        b.lstProfiles.setAdapter(listAdapter);

        b.btnFloat.setOnClickListener(v -> ProfileActivity.startForResult(MainActivity.this,null,RC_PROFILE));
    }

    private void deleteProfile(User profile){
        viewModel.deleteUser(profile);
    }

    private void editProfile(User profile){
        ProfileActivity.startForResult(MainActivity.this,profile,RC_PROFILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == RC_PROFILE){
            obtainResponseData(data);
        }
    }

    private void obtainResponseData(Intent data) {
        if(data != null && data.hasExtra(ProfileActivity.EXTRA_EDITPROFILE))
            changeProfile(data.getParcelableExtra(ProfileActivity.EXTRA_EDITPROFILE));
        else if(data != null && data.hasExtra(ProfileActivity.EXTRA_NEWPROFILE))
            newProfile(data.getParcelableExtra(ProfileActivity.EXTRA_NEWPROFILE));

        }

    private void newProfile(Parcelable parcelableExtra) {
        User newProfile = (User) parcelableExtra;
        if(newProfile.getPhoneNumer() != ProfileActivity.NEW_PROFILE)
            viewModel.addNewProfile(newProfile);
    }

    private void changeProfile(Parcelable parcelableExtra) {
        User editProfile = (User) parcelableExtra;

        for(int i = 0 ; i < viewModel.getProfiles().getValue().size(); i++)
            if(editProfile.getId() == viewModel.getProfiles().getValue().get(i).getId()) {
                viewModel.addEditedProfile(editProfile, i);
                break;
            }
    }

}

