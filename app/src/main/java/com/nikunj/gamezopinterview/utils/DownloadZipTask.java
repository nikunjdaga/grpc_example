package com.nikunj.gamezopinterview.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nikunj on 3/27/18.
 */

public class DownloadZipTask extends AsyncTask<String, String, String> {

    private static final String TAG ="DOWNLOADFILE";

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private onPostDownload callback;
    private Context context;
    private FileDescriptor fd;
    private File file;
    private String downloadLocation;

    public DownloadZipTask(String downloadLocation, Context context, onPostDownload callback){
        this.context = context;
        this.callback = callback;
        this.downloadLocation = downloadLocation;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;

        try {
            URL url = new URL(aurl[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lenghtOfFile = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/gamezopinterview/");
            myDir.mkdirs();


            file = new File(myDir, downloadLocation);
            if (file.exists ()) file.delete ();
            file.getParentFile().mkdirs();
            FileOutputStream output = new FileOutputStream(file);
            fd = output.getFD();

            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(""+(int)((total*100)/lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {e.printStackTrace();}
        return null;

    }
    protected void onProgressUpdate(String... progress) {

    }

    @Override
    protected void onPostExecute(String unused) {
        if(callback != null) callback.downloadFinish(file);
    }

    public static interface onPostDownload{
        void downloadFinish(File fDownload);
    }
}