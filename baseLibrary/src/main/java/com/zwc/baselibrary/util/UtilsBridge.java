package com.zwc.baselibrary.util;

import java.io.File;
import java.io.InputStream;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
class UtilsBridge {

    static String bytes2HexString(final byte[] bytes) {
        return bytes2HexString(bytes, true);
    }

    static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        return ConvertUtils.bytes2HexString(bytes, isUpperCase);
    }


    static String byte2FitMemorySize(final long byteSize) {
        return ConvertUtils.byte2FitMemorySize(byteSize);
    }


    static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return FileIOUtils.writeFileFromIS(filePath, is);
    }

    static boolean isFileExists(final File file) {
        return FileUtils.isFileExists(file);
    }

    static File getFileByPath(final String filePath) {
        return FileUtils.getFileByPath(filePath);
    }

    static boolean createOrExistsFile(final File file) {
        return FileUtils.createOrExistsFile(file);
    }

    static boolean isSpace(final String s) {
        return StringUtils.isSpace(s);
    }

    static boolean createOrExistsDir(final File file) {
        return FileUtils.createOrExistsDir(file);
    }
}
