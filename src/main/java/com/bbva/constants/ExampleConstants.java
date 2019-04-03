package com.bbva.constants;

public class ExampleConstants {

    /* ****************** PARAMETERS ****************** */
    public static final String PARAM_NAME       = "name";
    public static final String PARAM_SURNAME    = "surname";

    // DATABASE CCONSTANTS(ALL DB INFO WILL BE IN KMS)
    public static final String JDBC_URL         = "jdbc:postgresql://google/%s?socketFactory=com.google.cloud.sql.postgres.SocketFactory&socketFactoryArg=%s";
    public static final String DRIVER           = "org.postgresql.Driver";
    public static final String INSTANCE_NAME    = "datio-dataquality-poc:europe-west1:micro-model";
    public static final String DATABASE         = "test";
    public static final String USER_DB          = "dq_user";
    public static final String PASS_DB          = "DQMicroModel";
}