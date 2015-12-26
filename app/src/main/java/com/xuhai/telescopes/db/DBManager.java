package com.xuhai.telescopes.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chudong on 2015/12/6.
 */
public class DBManager {

    String filePath = "data/data/com.xuhai.telescopes/castnet.db";
    String path = "data/data/com.telescopes";
    SQLiteDatabase database;

    public SQLiteDatabase openDatabase(Context context) {
        File file = new File(filePath);
        if (file.exists()) {
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        } else {
            File Dbpath = new File(path);
            Dbpath.mkdir();
        }
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("castnet.db");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("DBManager", "创建数据库文件错误");
            return null;
        } catch (Exception eo) {
            eo.printStackTrace();
            return null;
        }
        return openDatabase(context);
    }

}
