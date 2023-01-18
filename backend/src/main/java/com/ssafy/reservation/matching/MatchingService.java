package com.ssafy.reservation.matching;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.db.entity.Matching;

public interface MatchingService {
	Matching createMatching(Matching matching) throws SQLException;
}
