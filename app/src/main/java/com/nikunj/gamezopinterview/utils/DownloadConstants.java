package com.nikunj.gamezopinterview.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by nikunj on 3/27/18.
 */

final public class DownloadConstants {
    public static final String FILE_PATH = "file:///" + Environment.getExternalStorageDirectory().toString() + File.separator;
    public static final String HOST_ADDRESS = "ben.gamezop.io";
    public static final String PORT = "50051";
    public static final String HTML_FILE_NAME = "index.html";
    public static final String SLASH = "/";
    public static final String BASE_URL = HOST_ADDRESS + ":" + PORT;

}
