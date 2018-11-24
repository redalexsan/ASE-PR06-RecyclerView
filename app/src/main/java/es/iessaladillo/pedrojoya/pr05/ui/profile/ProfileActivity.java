package es.iessaladillo.pedrojoya.pr05.ui.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.pr05.utils.IntentsUtils;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.NetworkUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

@SuppressWarnings("WeakerAccess")
public class ProfileActivity extends AppCompatActivity {

    private static final int RC_OTRA = 1;
    public static final String EXTRA_EDITPROFILE = "EXTRA_EDITPROFILE", EXTRA_NEWPROFILE = "EXTRA_NEWPROFILE";
    public static final int NEW_PROFILE = -1;

    ProfileViewModel mViewModel;
    private ImageView profileImage;
    private TextView profileName;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextAdress;
    private EditText editTextWeb;
    private ImageView emailImage;
    private ImageView phoneImage;
    private ImageView adressImage;
    private ImageView webImage;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewAdress;
    private TextView textViewWeb;
    private User profile;
    private boolean isNewProfile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        obtainData(getIntent());
        initViews();
        setProfileAvatar(mViewModel.getAvatar());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        profileImage = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        profileName = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        textViewName = ActivityCompat.requireViewById(this, R.id.lblName);
        editTextName = ActivityCompat.requireViewById(this, R.id.txtName);
        textViewEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        editTextEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        textViewPhone = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        editTextPhone = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        textViewAdress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        editTextAdress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        textViewWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        editTextWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        emailImage = ActivityCompat.requireViewById(this, R.id.imgEmail);
        phoneImage = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        adressImage = ActivityCompat.requireViewById(this, R.id.imgAddress);
        webImage = ActivityCompat.requireViewById(this, R.id.imgWeb);
        editTextName.requestFocus();
       if(!isNewProfile)
           fillData();

        profileName.setOnClickListener(v -> AvatarActivity.startForResult(ProfileActivity.this,mViewModel.getAvatar(),RC_OTRA));
        profileImage.setOnClickListener(v -> AvatarActivity.startForResult(ProfileActivity.this,mViewModel.getAvatar(),RC_OTRA));

        emailImage.setOnClickListener(v -> startIntents(IntentsUtils.newMessage(editTextEmail.getText().toString()),true));
        phoneImage.setOnClickListener(v -> startIntents(IntentsUtils.newDialIntent(editTextPhone.getText().toString()),false));
        adressImage.setOnClickListener(v -> startIntents(IntentsUtils.newSearchInMap(editTextAdress.getText().toString()),true));
        webImage.setOnClickListener(v -> startIntents(IntentsUtils.newSearchInWeb(editTextWeb.getText().toString()),true));

        editTextName.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(textViewName,hasFocus));

        editTextEmail.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(textViewEmail,hasFocus));

        editTextPhone.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(textViewPhone,hasFocus));

        editTextAdress.setOnFocusChangeListener((v, hasFocus) -> setTypeFaceTxt(textViewAdress,hasFocus));

        editTextWeb.setOnFocusChangeListener((v, hasFocus) ->  setTypeFaceTxt(textViewWeb,hasFocus));



        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { request(editTextName,textViewName);}

            @Override
            public void afterTextChanged(Editable s) { }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { request(editTextEmail,textViewEmail,emailImage);}

            @Override
            public void afterTextChanged(Editable s) { }
        });

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { request(editTextPhone,textViewPhone,phoneImage);}

            @Override
            public void afterTextChanged(Editable s) { }
        });

        editTextAdress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { request(editTextAdress,textViewAdress,adressImage);}

            @Override
            public void afterTextChanged(Editable s) { }
        });

        editTextWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { request(editTextWeb,textViewWeb,webImage);}

            @Override
            public void afterTextChanged(Editable s) { }
        });

        editTextWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return true;
        });
    }

    private void fillData() {
        mViewModel.setAvatar(profile.getAvatar());
        profileName.setText(profile.getAvatar().getName());
        profileImage.setImageResource(profile.getAvatar().getImageResId());
        editTextName.setText(profile.getName());
        editTextAdress.setText(profile.getAdress());
        editTextEmail.setText(profile.getMail());
        editTextPhone.setText(String.valueOf(profile.getPhoneNumer()));
        editTextWeb.setText(profile.getWeb());
    }


    private void setTypeFaceTxt(TextView text, boolean hasfocus){
        if(hasfocus)
            text.setTypeface(Typeface.DEFAULT_BOLD);
        else
            text.setTypeface(Typeface.DEFAULT);
    }

    private void startIntents(Intent intent,boolean connection){
        if(connection) {
            if (NetworkUtils.isConnectionAvailable(getApplicationContext()))
                startActivity(intent);
        }
        else startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == RC_OTRA){
            obtainResponseData(data);
        }
    }

    private void obtainResponseData(Intent data) {
        if(data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)){
            mViewModel.setAvatar(data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR));
        }
        setProfileAvatar(mViewModel.getAvatar());
    }

    private void setProfileAvatar(Avatar avatar) {
        profileImage.setImageResource(avatar.getImageResId());
        profileName.setText(avatar.getName());
        profileImage.setTag(avatar.getImageResId());
    }

    private boolean request(EditText text, TextView textView, ImageView image){
        boolean resultado = true;

        if(text.getId() == editTextEmail.getId()) {
            if (!ValidationUtils.isValidEmail(String.valueOf(text.getText())))
                resultado = false;
        }
        else if(text.getId() == editTextPhone.getId()) {
            if (!ValidationUtils.isValidPhone(String.valueOf(text.getText())))
                resultado = false;
        }
        else if(text.getId() == editTextWeb.getId()) {
            if (!ValidationUtils.isValidUrl(String.valueOf(text.getText())))
                resultado = false;
        }
        else
        if(String.valueOf(text.getText()).equals(""))
            resultado = false;


        if(!resultado) {
            text.setError(getString(R.string.main_invalid_data));
            textView.setEnabled(false);
            image.setEnabled(false);
        }
        else {
            text.setError(null);
            textView.setEnabled(true);
            image.setEnabled(true);
        }

        return resultado;
    }

    private boolean request(EditText text, TextView textView){

        if(String.valueOf(text.getText()).equals("")) {
            text.setError(getString(R.string.main_invalid_data));
            textView.setEnabled(false);
            return false;
        }
        else {
            text.setError(null);
            textView.setEnabled(true);
            return true;
        }

    }

    private void save() {
        KeyboardUtils.hideSoftKeyboard(this);
        if(validar()) {
            changeData();
            finish();
        }
        else
            Snackbar.make(getCurrentFocus(),getString(R.string.main_error_saving),Snackbar.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceType")
    public boolean validar(){
        boolean resultado1, resultado2,resultado3,resultado4, resultado5;

        resultado1 = request(editTextName,textViewName);
        resultado2 = request(editTextEmail,textViewEmail,emailImage);
        resultado3 = request(editTextPhone,textViewPhone,phoneImage);
        resultado4 = request(editTextAdress,textViewAdress,adressImage);
        resultado5 = request(editTextWeb,textViewAdress,webImage);

        return resultado1 && resultado2 && resultado3 && resultado4 && resultado5;

    }

    public static void startForResult(Activity activity, User profile, int requestCode) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        if(profile != null)
            intent.putExtra(EXTRA_EDITPROFILE, profile);
        else
            intent.putExtra(EXTRA_NEWPROFILE,profile);
        activity.startActivityForResult(intent, requestCode);
    }

    private void obtainData(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_EDITPROFILE))
            profile = intent.getParcelableExtra(EXTRA_EDITPROFILE);
        else if(intent != null && intent.hasExtra(EXTRA_NEWPROFILE)) {
            profile = new User(mViewModel.getAvatar(), "", "", "", "", NEW_PROFILE);
            isNewProfile = true;
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        if(!isNewProfile)
            intent.putExtra(EXTRA_EDITPROFILE,profile);
        else
            intent.putExtra(EXTRA_NEWPROFILE,profile);
        this.setResult(RESULT_OK,intent);
        super.finish();
    }

    private void changeData() {
        profile.setName(editTextName.getText().toString());
        profile.setAdress(editTextAdress.getText().toString());
        profile.setPhoneNumer(Integer.parseInt(editTextPhone.getText().toString()));
        profile.setWeb(editTextWeb.getText().toString());
        profile.setMail(editTextEmail.getText().toString());
        profile.setAvatar(mViewModel.getAvatar());
    }
}
