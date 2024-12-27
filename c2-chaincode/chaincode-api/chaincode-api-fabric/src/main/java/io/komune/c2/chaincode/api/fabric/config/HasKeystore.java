package io.komune.c2.chaincode.api.fabric.config;

import com.google.common.base.Strings;
import io.komune.c2.chaincode.api.fabric.utils.FileUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;



public interface HasKeystore {

    String getKeystore();

    default URL getKeystoreAsUrl(String cryptoBase) throws MalformedURLException {
        return FileUtils.getUrl(cryptoBase, getKeystore());
    }

}
