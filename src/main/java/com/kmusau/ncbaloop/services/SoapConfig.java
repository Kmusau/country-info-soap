package com.kmusau.ncbaloop.services;

import org.springframework.boot.webservices.client.HttpWebServiceMessageSenderBuilder;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.time.Duration;

@Configuration
public class SoapConfig {

    /**
     *  Pointed at the collection of generated domain objects and we
     *  will use them to both serialize and deserialize between XML and POJOs.
     * @return A Jaxb2Marshaller instance.
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.countryinfo.xml");
        return marshaller;
    }

    @Bean
    WebServiceTemplate webServiceTemplate(
            WebServiceTemplateBuilder builder,
            Jaxb2Marshaller marshaller) {
        var messageSender = new HttpWebServiceMessageSenderBuilder()
                .setReadTimeout(Duration.ofMillis(30000))
                .setConnectTimeout(Duration.ofMillis(30000))
                .build();
        return builder
                .setMarshaller(marshaller)
                .setUnmarshaller(marshaller)
                .additionalMessageSenders(messageSender)
                .build();
    }
}
