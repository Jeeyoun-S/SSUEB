package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMatching is a Querydsl query type for Matching
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMatching extends EntityPathBase<Matching> {

    private static final long serialVersionUID = 308921791L;

    public static final QMatching matching = new QMatching("matching");

    public final StringPath consultantId = createString("consultantId");

    public final StringPath matchingComment = createString("matchingComment");

    public final NumberPath<Integer> matchingCost = createNumber("matchingCost", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath reservationDate = createString("reservationDate");

    public final NumberPath<Integer> reservationNo = createNumber("reservationNo", Integer.class);

    public QMatching(String variable) {
        super(Matching.class, forVariable(variable));
    }

    public QMatching(Path<? extends Matching> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMatching(PathMetadata metadata) {
        super(Matching.class, metadata);
    }

}

