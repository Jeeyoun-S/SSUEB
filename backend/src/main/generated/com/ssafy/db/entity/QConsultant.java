package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConsultant is a Querydsl query type for Consultant
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConsultant extends EntityPathBase<Consultant> {

    private static final long serialVersionUID = -1519937603L;

    public static final QConsultant consultant = new QConsultant("consultant");

    public final StringPath consultantIntro = createString("consultantIntro");

    public final NumberPath<Integer> consultantLicenseCopyImage = createNumber("consultantLicenseCopyImage", Integer.class);

    public final StringPath consultantLicenseNumber = createString("consultantLicenseNumber");

    public final StringPath consultantProfile = createString("consultantProfile");

    public final NumberPath<Double> consultantRate = createNumber("consultantRate", Double.class);

    public final NumberPath<Integer> consultantReservationCount = createNumber("consultantReservationCount", Integer.class);

    public final StringPath consultPetType = createString("consultPetType");

    public final StringPath id = createString("id");

    public QConsultant(String variable) {
        super(Consultant.class, forVariable(variable));
    }

    public QConsultant(Path<? extends Consultant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConsultant(PathMetadata metadata) {
        super(Consultant.class, metadata);
    }

}

