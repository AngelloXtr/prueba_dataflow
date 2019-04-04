package com.bbva.dataflow;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation;

/**
 * This interface is needed to custom our own options for the pipelines
 */
public interface CustomOptions extends PipelineOptions {

    /**
     * By default, this example reads from the quality principle csv allocated in a private GCS bucket from the project
     * datio-dataquality-poc.
     * Set this option to choose a different input file or glob.
     */
    @Description("Path of the file to read from")
    @Default.String("gs://datio-dataquality-poc/example-dataflow/sophia-in/quality_principle.csv")
    @Validation.Required
    String getInputFile();

    void setInputFile(String value);

    /** Set this required option to specify where to write the output. */
    @Description("Path of the file to write to")
    @Validation.Required
    String getOutput();
    void setOutput(String value);



}



