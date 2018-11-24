package es.iessaladillo.pedrojoya.pr05.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.DatabaseProfiles;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityViewModel extends ViewModel {

    private final DatabaseProfiles data;
    private LiveData<List<User>> users;

    public MainActivityViewModel(DatabaseProfiles data) {
        this.data = data;
        this.users = data.getProfiles();
    }

    public LiveData<List<User>> getProfiles(){
        return users;
    }

    public void deleteUser(User profile){
        data.deleteProflie(profile);
    }

    public void addEditedProfile(User profile, int position){
        data.addEditedProflie(profile,position);
    }

    public void addNewProfile(User profile){
        data.addProflie(profile);
    }
}
