package es.iessaladillo.pedrojoya.pr05.ui.profile;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class ProfileViewModel extends ViewModel {

    private Avatar avatar;
    private final Database database = Database.getInstance();

    public ProfileViewModel(){
        avatar = database.getDefaultAvatar();
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
