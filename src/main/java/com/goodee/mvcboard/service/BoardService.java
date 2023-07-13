package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.mapper.BoardfileMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;


@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardfileMapper boardfileMapper;
	
	public List<Map<String, Object>> getLocalNameList(){
		List<Map<String, Object>> localNameList =  boardMapper.selectLocalNameList();
		return localNameList;
	}
	
	public Map<String, Object> getBoardList(int currentPage, int rowPerPage, String localName){
		//service layer 역할1 : controller가 넘겨준 매개값을 dao의 매개값에 맞게 가공
		int beginRow = (currentPage-1) * rowPerPage;
		
		// 반환값 1
		List<Map<String,Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
		paramMap.put("localName", localName);
		
		// 반환값 2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
		int boardCount = boardMapper.selectBoardCount(localName);
		// service layer 역할 2 : dao에서 반환받은 값을 가공하여 controller에 반환
		int lastPage = boardCount / rowPerPage;
		if(boardCount % rowPerPage != 0) {
			lastPage++;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
		
		return resultMap;
	}
	public int addBoard(Board board, String path) {
		int row = boardMapper.insertBoard(board);
		
		// board입력이 성공하고 첨부파일 1개 이상일때
		List<MultipartFile> fileList = board.getMultipartFile();
		if(row == 1 && fileList != null && fileList.size() > 0) {
			int boardNo = board.getBoardNo();
			System.out.println("BoardServiece에서 insertBoard한 후 생긴 boardNo : " + boardNo);
			
			for(MultipartFile mf : fileList) {
				if(mf.getSize() > 0) {
					Boardfile bf = new Boardfile();
					bf.setBoardNo(boardNo);
					bf.setOriginFilename(mf.getOriginalFilename());
					bf.setFilesize(mf.getSize());
					bf.setFiletype(mf.getContentType());
					// 저장될 파일이름
					int lastDot = mf.getOriginalFilename().lastIndexOf(".");
					String ext = mf.getOriginalFilename().substring(lastDot);
					// 새로운 이름 + 확장자
					bf.setSaveFilename(UUID.randomUUID().toString().replace("-", "") + ext);
					
					// 테이블에 저장
					boardfileMapper.insertBoardfile(bf);
					
					// 파일저장 (저장위치 - path)
					File f = new File(path + bf.getSaveFilename());
					
					// 빈파일에 첨부된 파일의 스트림을 주입함
					try {
						mf.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
						// try-catch를 강요하지 않는 예외
						throw new RuntimeException();
					}
				}
			}
		}
		return row;
	}
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	public int deleteBoard(Board board, String path) {
		int row;
		List<Boardfile> boardfile = selectBoardfile(board.getBoardNo());
		if(boardfile != null) {
			for(Boardfile bf : boardfile) {
				File f = new File(path + bf.getSaveFilename());
				if(f.exists()) {
					f.delete();
				}
			}
			row = boardfileMapper.deleteBoardfile(board.getBoardNo());
		}
		row = boardMapper.deleteBoard(board);
		return row;
	}
	public Board boardOne(int boardNo) {
		return boardMapper.selectBoard(boardNo);
	}
	public List<Boardfile> selectBoardfile(int boardNo){
		return boardfileMapper.selectBoardfile(boardNo);
	}
}
