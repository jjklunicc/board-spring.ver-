package com.goodee.mvcboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Board;

@Mapper
public interface BoardMapper {
	// local_name과 count 반환
	List<Map<String, Object>> selectLocalNameList();
	
	// mybatis메서드는 매개값 하나만 허용
	List<Board> selectBoardListByPage(Map<String, Object> map);
	
	// 전체 행의 수
	int selectBoardCount(String localName);
	
	// 보드 추가
	int insertBoard(Board board);
	
	// 보드 수정
	int updateBoard(Board board);
	
	// 보드 삭제
	int deleteBoard(Board board);
	
	// 보드 하나에 대한 정보
	Board selectBoard(int boardNo);
}
