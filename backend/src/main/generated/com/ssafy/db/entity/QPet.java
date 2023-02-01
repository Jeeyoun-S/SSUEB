package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPet is a Querydsl query type for Pet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPet extends EntityPathBase<Pet> {

    private static final long serialVersionUID = -1219623395L;

    public static final QPet pet = new QPet("pet");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath petBirth = createString("petBirth");

    public final StringPath petImage = createString("petImage");

    public final StringPath petInfo = createString("petInfo");

    public final StringPath petName = createString("petName");

    public final StringPath petType = createString("petType");

    public final StringPath petVariety = createString("petVariety");

    public final StringPath userId = createString("userId");

    public QPet(String variable) {
        super(Pet.class, forVariable(variable));
    }

    public QPet(Path<? extends Pet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPet(PathMetadata metadata) {
        super(Pet.class, metadata);
    }

}

