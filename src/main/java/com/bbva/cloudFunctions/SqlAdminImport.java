package com.bbva.cloudFunctions;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sqladmin.SQLAdmin;
import com.google.api.services.sqladmin.model.InstancesImportRequest;
import com.google.api.services.sqladmin.model.Operation;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class SqlAdminImport {
    public static void main(String args[]) throws IOException, GeneralSecurityException {
        // Project ID of the project that contains the instance.
        String project = "datio-dataquality-poc"; // TODO: Update placeholder value.

        // Cloud SQL instance ID. This does not include the project ID.
        String instance = "example";

        // TODO: Assign values to desired fields of `requestBody`:
        InstancesImportRequest requestBody = new InstancesImportRequest();


        SQLAdmin sqlAdminService = createSqlAdminService();
        SQLAdmin.Instances.SQLAdminImport request =
                sqlAdminService.instances().sqladminImport(project, instance, requestBody);

        Operation response = request.execute();

        // TODO: Change code below to process the `response` object:
        System.out.println(response);
    }

    public static SQLAdmin createSqlAdminService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential.getApplicationDefault();
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        return new SQLAdmin.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Google-SQLAdminSample/0.1")
                .build();
    }
}