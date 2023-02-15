package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHeart is a Querydsl query type for Heart
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHeart extends EntityPathBase<Heart> {

    private static final long serialVersionUID = 460586436L;

    public static final QHeart heart = new QHeart("heart");

    public final NumberPath<Integer> boardNo = createNumber("boardNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath userId = createString("userId");

    public QHeart(String variable) {
        super(Heart.class, forVariable(variable));
    }

    public QHeart(Path<? extends Heart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHeart(PathMetadata metadata) {
        super(Heart.class, metadata);
    }

}

