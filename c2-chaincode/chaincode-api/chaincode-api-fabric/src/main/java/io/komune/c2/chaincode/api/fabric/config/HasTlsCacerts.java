package io.komune.c2.chaincode.api.fabric.config;

import com.google.common.base.Strings;
import io.komune.c2.chaincode.api.fabric.utils.FileUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public interface HasTlsCacerts {

    String getTlsCacerts();

    default URL getTlsCacertsAsUrl(String cryptoBase) throws MalformedURLException {
        return FileUtils.getUrl(cryptoBase, getTlsCacerts());
    }

    default Properties getPeerTlsProperties(String cryptoBase) throws IOException {
        Properties prop = new Properties();
        prop.setProperty("allowAllHostNames", "true");
        URL path = getTlsCacertsAsUrl(cryptoBase);
        prop.setProperty("pemFile", path.getFile());
        return prop;
    }
}
