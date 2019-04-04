package com.bbva.dataflow.coders;

import com.bbva.dataflow.entities.QualityPrinciple;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.CoderException;
import org.apache.beam.sdk.coders.StructuredCoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static org.apache.beam.sdk.coders.Coder.Context.NESTED;

public class QualityPrincipleCoder extends StructuredCoder<QualityPrinciple> {
    private final Coder<Integer> idCoder;
    private final Coder<String> sourceCoder;
    private final Coder<String> classificationCoder;
    private final Coder<String> nameEsCoder;
    private final Coder<String> descEsCoder;
    private final Coder<String> nameEnCoder;
    private final Coder<String> descEnCoder;

    private QualityPrincipleCoder(Coder<Integer> idCoder,Coder<String> sourceCoder,Coder<String> classificationCoder,
                                  Coder<String> nameEsCoder,Coder<String> descEsCoder,Coder<String> nameEnCoder,
                                  Coder<String> descEnCoder) {
        this.idCoder = idCoder;
        this.sourceCoder = sourceCoder;
        this.classificationCoder = classificationCoder;
        this.nameEsCoder = nameEsCoder;
        this.descEsCoder = descEsCoder;
        this.nameEnCoder = nameEnCoder;
        this.descEnCoder = descEnCoder;
    }

    public Coder<Integer> getIdCoder() { return this.idCoder;}

    public Coder<String> getSourceCoder() {return this.sourceCoder;}

    public Coder<String> getClassificationCoder() {return this.classificationCoder;}

    public Coder<String> getNameEsCoder() {return this.nameEsCoder;}

    public Coder<String> getDescEsCoder() {return this.descEsCoder;}

    public Coder<String> getNameEnCoder() {return this.nameEnCoder;}

    public Coder<String> getDescEnCoder() {return this.descEnCoder;}


    public static QualityPrincipleCoder of(Coder<Integer> idCoder,Coder<String> sourceCoder,Coder<String> classificationCoder,
                                    Coder<String> nameEsCoder,Coder<String> descEsCoder,Coder<String> nameEnCoder,
                                    Coder<String> descEnCoder){
        return new QualityPrincipleCoder(idCoder, sourceCoder,classificationCoder, nameEsCoder, descEsCoder,nameEnCoder,descEnCoder);
    }

    public void encode(QualityPrinciple value, OutputStream outStream) throws CoderException, IOException {
        this.encode(value,outStream, NESTED);
    }

    public void encode(QualityPrinciple qp, OutputStream outStream, Context context) throws IOException, CoderException {
        if (qp == null) {
            throw new CoderException("cannot encode a null QualityPrinciple");
        } else {
            this.idCoder.encode(qp.getId(), outStream);
            this.sourceCoder.encode(qp.getSource(), outStream);
            this.classificationCoder.encode(qp.getClassification(),outStream);
            this.nameEsCoder.encode(qp.getNameEs(),outStream);
            this.descEsCoder.encode(qp.getDescEs(),outStream);
            this.nameEnCoder.encode(qp.getNameEn(),outStream);
            this.descEnCoder.encode(qp.getDescEn(),outStream);

        }
    }

    public QualityPrinciple decode(InputStream inStream) throws IOException {
        return this.decode(inStream, NESTED);
    }

    public QualityPrinciple decode(InputStream inStream,Context context) throws IOException {
        Integer id = this.idCoder.decode(inStream);
        String source = this.sourceCoder.decode(inStream);
        String classification = this.classificationCoder.decode(inStream);
        String nameEs = this.nameEsCoder.decode(inStream);
        String descEs = this.descEsCoder.decode(inStream);
        String nameEn = this.nameEnCoder.decode(inStream);
        String descEn = this.descEnCoder.decode(inStream);

        return QualityPrinciple.of(id, source,classification,nameEs,descEs,nameEn,descEn);
    }

    public List<? extends Coder<?>> getCoderArguments() {
        return Arrays.asList(this.idCoder,this.sourceCoder,this.classificationCoder,this.nameEsCoder,
                this.descEsCoder,this.nameEnCoder,this.descEnCoder);
    }

    public void verifyDeterministic() throws NonDeterministicException {
        verifyDeterministic(this, "Id coder must be deterministic", this.getIdCoder());
        verifyDeterministic(this, "Source coder must be deterministic", this.getSourceCoder());
        verifyDeterministic(this, "Classification coder must be deterministic", this.getClassificationCoder());
        verifyDeterministic(this, "NameEs coder must be deterministic", this.getNameEsCoder());
        verifyDeterministic(this, "DescEs coder must be deterministic", this.getDescEsCoder());
        verifyDeterministic(this, "NameEn coder must be deterministic", this.getNameEnCoder());
        verifyDeterministic(this, "DescEn coder must be deterministic", this.getDescEnCoder());
    }

}