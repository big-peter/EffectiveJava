package com.wj.example.item1;

import java.util.HashMap;
import java.util.Map;

interface Service {    // service interface

    void doSomething();

}

interface Provider{    // service provider API

    Service createService();

}

class ServiceFactory {

    private static Map<String, Provider> providerMap = new HashMap<>();

    static void registerProvider(String name,  Provider provider){    // provider register API

        providerMap.put(name, provider);

    }

    static Service getService(String name) {    // service access API

        Provider provider = providerMap.get(name);

        if (provider == null)

            throw new IllegalArgumentException("Provider not found");

        return provider.createService();

    }

}


public class SPIDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Provider customProvider = (Provider) Class.forName("com.xx.CustomProvider").newInstance();

        ServiceFactory.registerProvider("customProvider", customProvider);

        Service service = ServiceFactory.getService("customProvider");

        service.doSomething();

    }
}
