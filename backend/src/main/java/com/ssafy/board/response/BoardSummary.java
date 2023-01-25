package com.ssafy.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.swagger.annotations.ApiModel;


@ApiModel("BoardSummary")
public interface BoardSummary {
    int getNo();
    String getUserNickname();
    String getBoardTitle();
}
