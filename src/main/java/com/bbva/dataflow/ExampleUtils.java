package com.bbva.dataflow;

import com.bbva.dataflow.entities.DummyClassInput;
import com.bbva.dataflow.entities.QualityPrinciple;
import com.fga.utils.PropertiesUtil;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;

import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.display.DisplayData;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.bbva.constants.ExampleConstants.*;

public class ExampleUtils {

    public static final String TOKENIZER_PATTERN = "[^\\p{L}]+";

    public static PCollection<String> getLines(Pipeline p, DummyOptions options){
        PCollection<String> lines = p.apply("getLines",TextIO.read().from(options.getInputFile()));

        return lines;
    }


    private static class myPreparedStatementSetter implements JdbcIO.PreparedStatementSetter<QualityPrinciple>{
        @Override
        public void setParameters(QualityPrinciple qp, PreparedStatement preparedStatement) throws Exception {

            preparedStatement.setInt(2, qp.id);
            preparedStatement.setString(3, qp.source);
            preparedStatement.setString(4, qp.classification);
            preparedStatement.setString(5, qp.nameEs);
            preparedStatement.setString(6, qp.descEs);
            preparedStatement.setString(7, qp.nameEn);
            preparedStatement.setString(8, qp.descEn);
        }
    }



    public static PCollection<QualityPrinciple> returnCollection(Pipeline p){

        QualityPrinciple qp = new QualityPrinciple(1,"asdf","sad",
                "asdf","as","asd","asdf");
        PCollection<QualityPrinciple> qps = p.apply(Create.of(qp));

        return  qps;
    }


    /**
     * Pipeline to insert data into database
     * @param p     Pipeline
     */
    public static void insertQuery(Pipeline p,PCollection<QualityPrinciple> qualityPrinciples){



        //final KV<String,QualityPrinciple> kv = KV.of("quality_principle",qp);

        String jdbcUrl = String.format(JDBC_URL,
                DATABASE, // database name
                INSTANCE_NAME); // instance name

        //p.apply("CREATE FROM KV",Create.of(kv))
        qualityPrinciples.apply("INSERT INTO DB",JdbcIO.<QualityPrinciple>write()
                        .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                DRIVER, jdbcUrl)
                                .withUsername(USER_DB)
                                .withPassword(PASS_DB))
                .withStatement("INSERT INTO ? VALUES(?,?,?,?,?,?,?)")
                .withPreparedStatementSetter(new myPreparedStatementSetter()));
    }




}
