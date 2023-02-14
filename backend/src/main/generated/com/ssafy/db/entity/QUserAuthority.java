package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserAuthority is a Querydsl query type for UserAuthority
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserAuthority extends EntityPathBase<UserAuthority> {

    private static final long serialVersionUID = -1696530634L;

    public static final QUserAuthority userAuthority = new QUserAuthority("userAuthority");

    public final StringPath authorityName = createString("authorityName");

    public final StringPath id = createString("id");

    public QUserAuthority(String variable) {
        super(UserAuthority.class, forVariable(variable));
    }

    public QUserAuthority(Path<? extends UserAuthority> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAuthority(PathMetadata metadata) {
        super(UserAuthority.class, metadata);
    }

}

