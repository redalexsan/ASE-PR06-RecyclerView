package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr05.data.local.DatabaseProfiles;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final DatabaseProfiles databaseProfiles;
    public MainActivityViewModelFactory(DatabaseProfiles databaseProfiles) {
        this.databaseProfiles = databaseProfiles;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(databaseProfiles);
    }
}
