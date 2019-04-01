package com.bbva.dataflow;

import com.bbva.dataflow.entities.DummyClass;
import com.bbva.dataflow.entities.QualityPrinciple;
import com.fga.utils.PropertiesUtil;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.BigEndianIntegerCoder;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;

import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExampleUtils {
    public static final String TOKENIZER_PATTERN = "[^\\p{L}]+";

    public static PCollection<String> getLines(Pipeline p, DummyOptions options){
        PCollection<String> lines = p.apply("getLines",TextIO.read().from(options.getInputFile()));

        return lines;
    }


    private static class myPreparedStatementSetter implements JdbcIO.PreparedStatementSetter<KV<Integer, Integer>>{
        @Override
        public void setParameters(KV<Integer, Integer> element, PreparedStatement preparedStatement) throws Exception {
            preparedStatement.setInt(1, element.getKey());
            preparedStatement.setInt(2, element.getValue());
        }
    }


    static class FilterCSVHeaderFn extends DoFn<String, String> {
        String headerFilter;

        public FilterCSVHeaderFn(String headerFilter) {
            this.headerFilter = headerFilter;
        }

        @ProcessElement
        public void processElement(ProcessContext c) {
            String row = c.element();
            // Filter out elements that match the header
            if (!row.equals(this.headerFilter)) {
                c.output(row);
            }
        }
    }

   /*public static PCollection<QualityPrinciple> mapQualityPrinciples(Pipeline p, DummyOptions options){
       PCollection<String> lines = getLines(p,options);


       PCollection<QualityPrinciple> qualityPrinciples;


       return qualityPrinciples;
   }*/

    public static void insertQuery(Pipeline p, DummyOptions options){
        final KV<Integer,Integer> kv = KV.of(2,3);
        String jdbcUrl = String.format(PropertiesUtil.getString("JDBC_SOCKET"),
                PropertiesUtil.getString("DB_NAME"), // database name
                PropertiesUtil.getString("INSTANCE_NAME")); // instance name

        p.apply(Create.of(kv))
                .apply(JdbcIO.<KV<Integer, Integer>>write()
                        .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                PropertiesUtil.getString("DB_DRIVER"), jdbcUrl)
                                .withUsername(PropertiesUtil.getString("USER_NAME"))
                                .withPassword(PropertiesUtil.getString("DB_PASS")))
                .withStatement("INSERT INTO prueba VALUES(?,?)")
                .withPreparedStatementSetter(new myPreparedStatementSetter()));
    }

}
