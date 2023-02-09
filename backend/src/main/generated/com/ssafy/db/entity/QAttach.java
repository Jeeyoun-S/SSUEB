package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttach is a Querydsl query type for Attach
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttach extends EntityPathBase<Attach> {

    private static final long serialVersionUID = 1207275655L;

    public static final QAttach attach = new QAttach("attach");

    public final StringPath attachFile = createString("attachFile");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> reservationNo = createNumber("reservationNo", Integer.class);

    public QAttach(String variable) {
        super(Attach.class, forVariable(variable));
    }

    public QAttach(Path<? extends Attach> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttach(PathMetadata metadata) {
        super(Attach.class, metadata);
    }

}

