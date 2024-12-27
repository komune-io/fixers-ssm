package io.komune.c2.chaincode.api.fabric.config;

import com.google.common.base.Strings;
import io.komune.c2.chaincode.api.fabric.utils.FileUtils;

import java.net.MalformedURLException;
import java.net.URL;

public interface HasSigncerts {

    String getSigncerts();

    default URL getSigncertsAsUrl(String cryptoBase) throws MalformedURLException {
        return FileUtils.getUrl(cryptoBase, getSigncerts());
    }

}
