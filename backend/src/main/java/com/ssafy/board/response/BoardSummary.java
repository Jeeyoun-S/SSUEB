package com.ssafy.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.swagger.annotations.ApiModel;


@ApiModel("BoardSummary")
public interface BoardSummary {
	//자세한 내용(전체)가 아닌 보이는 내용만 보이게 하려고
    int getNo();
    String getUserNickname();
    String getBoardTitle();
}
