package com.bbva.biz.impl;

import com.bbva.beans.ExampleBean;

import java.util.logging.Logger;

public class ExampleBiz {

    private static final Logger LOGGER = Logger.getLogger(ExampleBiz.class.getName());

    public ExampleBean get(String name, String surname) throws Exception {
        ExampleBean exampleBean;

        try {
            LOGGER.info("Name: " + name);
            LOGGER.info("Surname: " + surname);

            exampleBean = new ExampleBean();
            exampleBean.setText("GET request for ExampleWS");
            exampleBean.setName(name);
            exampleBean.setSurname(surname);
        } catch (Exception e) {
            throw e;
        }

        return exampleBean;
    }
}
