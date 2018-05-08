package com.github.alinz.reactNativeShareExtension;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.database.Cursor;
import android.content.Context;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import javax.annotation.Nullable;

public class ShareExModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    public ShareExModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    public String getName() {
        return "ReactNativeShareExtension";
    }

    @ReactMethod
    public void data(Promise promise) {
        promise.resolve(processIntent());
    }

    /*public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }*/

    protected WritableMap processIntent() {

        Activity currentActivity = getCurrentActivity();

        WritableMap map = Arguments.createMap();

        Intent intent = currentActivity.getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        String value = "";

        if (type == null) {
            type = "";
        }


        if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
            value = intent.getStringExtra(Intent.EXTRA_TEXT);
        }

        //type.equals("video/*")

       if (Intent.ACTION_SEND.equals(action)) {

            Uri selectedImageUri = intent.getData();

           String filemanagerstring = selectedImageUri.getPath();
           // String selectedImagePath = getPath(selectedImageUri);

            if (filemanagerstring != null) {
                value = filemanagerstring;
            }
        }

        map.putString("type", type);
        map.putString("value", value);

        return map;
    }

    @ReactMethod
    public void close() {
        Activity currentActivity = getCurrentActivity();

        currentActivity.finish();
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) { }
  
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) { }

    public void onNewIntent(Intent intent) {  }