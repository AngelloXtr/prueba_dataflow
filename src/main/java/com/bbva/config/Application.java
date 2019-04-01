package com.bbva.config;

import com.bbva.ws.impl.ExampleWS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {

    private Set<Class<?>> classes = new HashSet<>();
    private Set<Object> singletons = Collections.emptySet();

    public Application() {
        classes.add(ExampleWS.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}