package com.goodee.mvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardfileMapper {
	int insertBoardfile(Boardfile boardfile);
	List<Boardfile> selectBoardfile(int boardNo);
	int deleteBoardfileByBoard(int boardNo);
	int deleteOneBoardfile(int boardfileNo);
	Boardfile selectOneBoardfile(int boardfileNo);
}
