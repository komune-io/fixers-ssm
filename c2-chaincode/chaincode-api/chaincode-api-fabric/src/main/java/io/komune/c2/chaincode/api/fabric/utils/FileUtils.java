package io.komune.c2.chaincode.api.fabric.utils;

import com.google.common.base.Strings;
import com.google.common.io.Resources;

import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtils {

    public static final String FILE = "file:";

    public static URL getUrl(String resource) throws MalformedURLException {
        if(resource.startsWith(FILE)) {
            return new URL(resource);
        }
        return Resources.getResource(resource);
    }

    public static URL getUrl(String path, String resource) throws MalformedURLException {
        if (!Strings.isNullOrEmpty(path) && !path.endsWith("/")) {
            path = path + "/";
        }
        String fullPath = path + resource;
        return FileUtils.getUrl(fullPath);
    }

}
