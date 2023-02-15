package com.ssafy.classroom.interceptor;

import java.sql.SQLException;

public interface RoomValidService {
	boolean checkValid(int roomId) throws SQLException;
}
