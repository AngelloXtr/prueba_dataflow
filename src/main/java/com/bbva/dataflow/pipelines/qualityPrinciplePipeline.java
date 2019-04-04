package com.bbva.dataflow.pipelines;

import com.bbva.dataflow.coders.QualityPrincipleCoder;
import com.bbva.dataflow.entities.QualityPrinciple;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.*;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static com.bbva.constants.ExampleConstants.*;
import static com.bbva.dataflow.DataflowUtils.insertQuery;

public class qualityPrinciplePipeline {

    public static String query = String.format("INSERT INTO %s VALUES(?,?,?,?,?,?,?) " +
            "ON CONFLICT DO NOTHING",QUALITY_PRINCIPLE);

    /**
     * Class made to override the setParameters function to create our custom statement setter
     */
    public static class QualityPrincipleStatementSetter implements JdbcIO.PreparedStatementSetter<QualityPrinciple>{
        @Override
        public void setParameters(QualityPrinciple qp, PreparedStatement preparedStatement) throws Exception {

            preparedStatement.setInt(1, qp.id);
            preparedStatement.setString(2, qp.source);
            preparedStatement.setString(3, qp.classification);
            preparedStatement.setString(4, qp.nameEs);
            preparedStatement.setString(5, qp.descEs);
            preparedStatement.setString(6, qp.nameEn);
            preparedStatement.setString(7, qp.descEn);
        }
    }


    /**
     * Class made to override the mapRow
     */
    public static class QualityPrincipleRowMapper implements JdbcIO.RowMapper<QualityPrinciple>{
        @Override
        public QualityPrinciple mapRow(ResultSet resultSet) throws Exception {
            return QualityPrinciple.of(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
        }
    }



    /**
     *  Function made to sample a extraction of data
     * @param   p Pipeline
     * @return  A collection of quality principles
     */
    public static PCollection<QualityPrinciple> extractCollection(Pipeline p){

        QualityPrinciple qp = new QualityPrinciple(1,"asdf","sad",
                "asdf","as","asd","asdf");
        QualityPrinciple qp2 = new QualityPrinciple(2,"asdf","sad",
                "asdf","as","asd","asdf");

        QualityPrinciple[] qps = {qp,qp2};
        List<QualityPrinciple> qpsList = Arrays.asList(qps);

        PCollection<QualityPrinciple> qpsC = p.apply("Get Data",Create.of(qpsList));

        return  qpsC;
    }

    /**
     * Function made to insert data from collection into DB
     * @param qps Pipeline
     */
    public static void insertQualityPrinciple(PCollection<QualityPrinciple> qps){
        QualityPrincipleStatementSetter qpStatementSetter = new QualityPrincipleStatementSetter();
        insertQuery(qps,qpStatementSetter,query);
    }


    public static PCollection selectQuery(Pipeline p,String query){

        PCollection<QualityPrinciple> qps = p.apply("Extract Quality Principles",JdbcIO.<QualityPrinciple>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                        DRIVER, JDBC_URL_FINAL)
                        .withUsername(USER_DB)
                        .withPassword(PASS_DB))
                .withQuery(query)
                .withRowMapper(new QualityPrincipleRowMapper())
                .withCoder(QualityPrincipleCoder.of(BigEndianIntegerCoder.of(),StringUtf8Coder.of(),
                        StringUtf8Coder.of(),StringUtf8Coder.of(),StringUtf8Coder.of(),
                        StringUtf8Coder.of(),StringUtf8Coder.of()) )
        );
        return qps;
    }


}
