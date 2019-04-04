package com.bbva.dataflow;

import com.bbva.dataflow.entities.QualityPrinciple;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import static com.bbva.constants.ExampleConstants.*;

public class DataflowUtils {

    /**
     * @param p         Pipeline
     * @param options   Custom options with all needed parameters to deploy the job
     * @return
     */
    public static PCollection<String> getLines(Pipeline p, CustomOptions options){
        PCollection<String> lines = p.apply("getLines",TextIO.read().from(options.getInputFile()));

        return lines;
    }


    /**
     * @param objects           The collection to use for inserts
     * @param statementSetter   The custom statement setter
     * @param query             Query to execute
     */
    public static void insertQuery(PCollection objects,JdbcIO.PreparedStatementSetter statementSetter, String query){

        objects.apply("INSERT INTO DB",JdbcIO.<QualityPrinciple>write()
                        .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                DRIVER, JDBC_URL_FINAL)
                                .withUsername(USER_DB)
                                .withPassword(PASS_DB))
                .withStatement(query)
                .withPreparedStatementSetter(statementSetter));
    }




}
