package com.uber.ugsb.model;

import com.uber.ugsb.schema.Vocabulary;

import java.io.Serializable;

public class EdgeModel implements Serializable {
    private static final long serialVersionUID = Vocabulary.serialVersionUID;

    private final Incidence domainIncidence;
    private final Incidence randeIncidence;

    public EdgeModel(final Incidence domainIncidence,
              final Incidence randeIncidence) {
        this.domainIncidence = domainIncidence;
        this.randeIncidence = randeIncidence;
    }

    public Incidence getDomainIncidence() {
        return domainIncidence;
    }

    public Incidence getRandeIncidence() {
        return randeIncidence;
    }
}
