package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -562840886L;

    public static final QReservation reservation = new QReservation("reservation");

    public final StringPath consultantId = createString("consultantId");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath reservationConsultContent = createString("reservationConsultContent");

    public final NumberPath<Integer> reservationCost = createNumber("reservationCost", Integer.class);

    public final StringPath reservationDate = createString("reservationDate");

    public final StringPath reservationDignosisRecord = createString("reservationDignosisRecord");

    public final NumberPath<Integer> reservationFinish = createNumber("reservationFinish", Integer.class);

    public final NumberPath<Integer> reservationOpen = createNumber("reservationOpen", Integer.class);

    public final NumberPath<Integer> reservationPetNo = createNumber("reservationPetNo", Integer.class);

    public final StringPath reservationRegisterTime = createString("reservationRegisterTime");

    public final StringPath reviewComment = createString("reviewComment");

    public final NumberPath<Integer> reviewGrade = createNumber("reviewGrade", Integer.class);

    public final StringPath userId = createString("userId");

    public QReservation(String variable) {
        super(Reservation.class, forVariable(variable));
    }

    public QReservation(Path<? extends Reservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservation(PathMetadata metadata) {
        super(Reservation.class, metadata);
    }

}

