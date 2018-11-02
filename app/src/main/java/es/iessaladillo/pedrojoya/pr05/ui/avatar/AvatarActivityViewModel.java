package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class AvatarActivityViewModel extends ViewModel {

    private final Database database = Database.getInstance();
    private Avatar avatar;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(int position) {
        avatar = database.queryAvatars().get(position);
    }

    public void setAvatar(Avatar avatar){
        this.avatar = avatar;
    }

    public Avatar showSelectedAvatar(){
        for(Avatar o: database.queryAvatars())
            if(avatar.getId() == o.getId())
               return o;
         return database.getDefaultAvatar();
    }

}
