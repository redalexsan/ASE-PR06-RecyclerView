package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    private AvatarActivityViewModel avatarVM;
    private int positionImageSelected;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;

    private ImageView images[];
    private TextView labels[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        avatarVM = ViewModelProviders.of(this).get(AvatarActivityViewModel.class);
        obtainData(getIntent());
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void obtainData(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_AVATAR)) {
            if(avatarVM.getAvatar() == null)
            avatarVM.setAvatar(intent.getParcelableExtra(EXTRA_AVATAR));
        }
    }

    private void initViews() {
        image1 = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
        image2 = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
        image3 = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
        image4 = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
        image5 = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
        image6 = ActivityCompat.requireViewById(this, R.id.imgAvatar6);
        TextView txtCat1 = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
        TextView txtCat2 = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
        TextView txtCat3 = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
        TextView txtCat4 = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
        TextView txtCat5 = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
        TextView txtCat6 = ActivityCompat.requireViewById(this, R.id.lblAvatar6);
        images = new ImageView[]{image1, image2, image3, image4, image5, image6};
        labels = new TextView[]{txtCat1, txtCat2, txtCat3, txtCat4, txtCat5, txtCat6};

        initAvatars();
        showSelectedAvatar();

        image1.setOnClickListener(v -> getSelectedAvatar(image1));
        image2.setOnClickListener(v -> getSelectedAvatar(image2));
        image3.setOnClickListener(v -> getSelectedAvatar(image3));
        image4.setOnClickListener(v -> getSelectedAvatar(image4));
        image5.setOnClickListener(v -> getSelectedAvatar(image5));
        image6.setOnClickListener(v -> getSelectedAvatar(image6));
        txtCat1.setOnClickListener(v -> getSelectedAvatar(image1));
        txtCat2.setOnClickListener(v -> getSelectedAvatar(image2));
        txtCat3.setOnClickListener(v -> getSelectedAvatar(image3));
        txtCat4.setOnClickListener(v -> getSelectedAvatar(image4));
        txtCat5.setOnClickListener(v -> getSelectedAvatar(image5));
        txtCat6.setOnClickListener(v -> getSelectedAvatar(image6));
    }

    private void initAvatars() {
        Avatar avatar;
        for (int i = 0; i < images.length; i++) {
            avatar = Database.getInstance().queryAvatars().get(i);
            images[i].setImageResource(avatar.getImageResId());
            labels[i].setText(avatar.getName());
        }

    }

    private void showSelectedAvatar() {
        Avatar avatarSelected = avatarVM.showSelectedAvatar();
        selectImageView(images[Database.getInstance().queryAvatars().indexOf(avatarSelected)]);
        positionImageSelected = Database.getInstance().queryAvatars().indexOf(avatarSelected);
    }

    private void getSelectedAvatar(ImageView image) {

        for (int i = 0; i < Database.getInstance().queryAvatars().size(); i++) {
            if (image.getId() == images[i].getId()) {
                unselecImage(positionImageSelected);
                selectImageView(image);
                avatarVM.setAvatar(i);
                positionImageSelected = i;
            }
        }
    }

    private void unselecImage(int unselecImagePosition) {
        images[unselecImagePosition].setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
    }

    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_AVATAR, avatarVM.getAvatar());
        this.setResult(RESULT_OK, intent);
        super.finish();
    }

    public static void startForResult(Activity activity, Avatar avatar, int requestCode) {
        Intent intent = new Intent(activity, AvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR, avatar);
        activity.startActivityForResult(intent, requestCode);
    }

    private void selectImageView(ImageView imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
    }

}
