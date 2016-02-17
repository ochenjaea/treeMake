package com.taxholic.core.configuration.web;

import org.springframework.context.ApplicationContext;

public class ApplicationContextProvider {

    static private ApplicationContext applicationContext;

    static public ApplicationContext get() {
        return applicationContext;
    }

    static public void set(ApplicationContext ac) {
        applicationContext = ac;
    }
}
