package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class MainActivityViewModel extends ViewModel {

    private Avatar avatar;
    private final Database database = Database.getInstance();

    public MainActivityViewModel(){
        avatar = database.getDefaultAvatar();
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
