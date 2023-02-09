package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> userAlertFlag = createNumber("userAlertFlag", Integer.class);

    public final NumberPath<Integer> userDeleteFlag = createNumber("userDeleteFlag", Integer.class);

    public final StringPath userJoindate = createString("userJoindate");

    public final StringPath userName = createString("userName");

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userPassword = createString("userPassword");

    public final StringPath userPhone = createString("userPhone");

    public final NumberPath<Integer> userRole = createNumber("userRole", Integer.class);

    public final StringPath userToken = createString("userToken");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

