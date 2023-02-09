package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLike is a Querydsl query type for Like
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLike extends EntityPathBase<Heart> {

    private static final long serialVersionUID = 846264921L;

    public static final QLike like = new QLike("like1");

    public final NumberPath<Integer> boardNo = createNumber("boardNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath userId = createString("userId");

    public QLike(String variable) {
        super(Heart.class, forVariable(variable));
    }

    public QLike(Path<? extends Heart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLike(PathMetadata metadata) {
        super(Heart.class, metadata);
    }

}

