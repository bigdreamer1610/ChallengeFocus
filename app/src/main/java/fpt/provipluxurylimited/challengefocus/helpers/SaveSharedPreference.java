package fpt.provipluxurylimited.challengefocus.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static SharedPreferences getSharedPreference(Context context) {
        return context.getApplicationContext().getSharedPreferences(Constants.pref, Constants.PRIVATE_MODE);
    }

    public static void setName(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Constants.name, name);
        editor.commit();
    }
    public static void setImageUrl(Context context, String url) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Constants.imageUrl, url);
        editor.commit();
    }

    public static void setCaption(Context context, String caption) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Constants.caption, caption);
        editor.commit();
    }

    public static void setUserId(Context context, String id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Constants.userId, id);
        editor.commit();
    }

    public static void removeAll(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(Constants.name);
        editor.remove(Constants.caption);
        editor.remove(Constants.imageUrl);
        editor.remove(Constants.userId);
        editor.commit();
    }

    public static String getName(Context context) {
        return getSharedPreference(context).getString(Constants.name, null);
    }

    public static String getUserId(Context context) {
        return getSharedPreference(context).getString(Constants.userId, null);
    }

    public static String getImageUrl(Context context) {
        return getSharedPreference(context).getString(Constants.imageUrl, null);
    }

    public static String getCaption(Context context) {
        return getSharedPreference(context).getString(Constants.caption, null);
    }
}
