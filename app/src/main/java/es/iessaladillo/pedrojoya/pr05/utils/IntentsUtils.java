package es.iessaladillo.pedrojoya.pr05.utils;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;

public class IntentsUtils {

    private IntentsUtils(){}

    public static Intent newDialIntent(String phoneNumber){
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.trim()));
    }

    public static Intent newSearchInMap(String text){
        return new Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=" + text.trim()));
    }

    public static Intent newSearchInWeb(String text){
        Intent intent;
        if(text.matches("(http).*"))
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text.trim()));
        else  {
            intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,text.trim());
        }
        return  intent;
    }

    public static Intent newMessage(String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        Uri uri = Uri.parse("mailto:" + email.trim());
        intent.setData(uri);
        return intent;
    }
}
