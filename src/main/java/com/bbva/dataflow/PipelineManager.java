package com.bbva.dataflow;



import com.bbva.dataflow.entities.QualityPrinciple;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;

import java.util.logging.Logger;

import static com.bbva.dataflow.pipelines.qualityPrinciplePipeline.*;


public class PipelineManager {

    /**
     * Main function needed to deploy jobs into Google Cloud Dataflow
     * @param args
     */
    public static void main(String[] args) {

        CustomOptions options =
                PipelineOptionsFactory.fromArgs(args).withValidation().as(CustomOptions.class);
        Pipeline p = Pipeline.create(options);

        String query = "SELECT id, source, classification, nameEs, descEs, nameEn, descEn FROM quality_principle";
        PCollection<QualityPrinciple> qps = selectQuery(p,query);
        insertQualityPrinciple(qps);
        p.run().waitUntilFinish();
    }
}
