package es.iessaladillo.pedrojoya.pr05.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class DatabaseProfiles {

    private Database dataAvatars = Database.getInstance();
    private static DatabaseProfiles instance;

    private List<User> profiles = new ArrayList<>(Arrays.asList(
            new User(dataAvatars.queryAvatars().get(0), "Tom","Direccion de Tom","tom@gato.com","http://www.google.es",123456),
            new User(dataAvatars.queryAvatars().get(1), "Luna","Direccion de Luna","luna@gato.com","http://www.marca.es",654987321),
            new User(dataAvatars.queryAvatars().get(2), "Nicolas Cage","Direccion de Nicolas","nicolas@cage.com","https://es.wikipedia.org/wiki/Nicolas_Cage",951842631)
    ));

    private MutableLiveData<List<User>> profliesLiveData = new MutableLiveData<>();

    private DatabaseProfiles() {
        updateProfilesLiveData();
    }

    public static DatabaseProfiles getInstance(){
        if(instance == null )
            synchronized (DatabaseProfiles.class){
                if(instance == null)
                    instance = new DatabaseProfiles();
            }
        return instance;
    }

    private void updateProfilesLiveData() {
        profliesLiveData.setValue(new ArrayList<>(profiles));
    }

    public LiveData<List<User>> getProfiles(){
        return profliesLiveData;
    }

    public void deleteProflie(User profile){
        profiles.remove(profile);
        updateProfilesLiveData();
    }

    public void addProflie(User profile){
        profiles.add(profile);
        updateProfilesLiveData();
    }

    public void addEditedProflie(User profile, int position) {
        profiles.set(position,profile);
        updateProfilesLiveData();
    }
}
