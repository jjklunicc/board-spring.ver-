package com.goodee.mvcboard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/addBoard")
	public String addBoard(Model model) {
		List<Map<String, Object>> localNameList = boardService.getLocalNameList();
		model.addAttribute("localNameList", localNameList);
		return "/board/addBoard";
	}
	
	@PostMapping("/board/addBoard")
	public String addBoard(HttpServletRequest request, Board board) { // 매개값으로 request받아서 api호출
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.addBoard(board, path);
		log.debug("\u001B[31m" + "row : " + row + "\u001B[0m");
		return "redirect:/board/boardList";
	}
	@GetMapping("/board/boardList")
	public String boardList(Model model, 
			@RequestParam(name = "currentPage", defaultValue="1") int currentPage,
			@RequestParam(name = "rowPerPage", defaultValue="10") int rowPerPage,
			@RequestParam(name = "localName", required=false) String localName) {
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName); 
		
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		model.addAttribute("boardList", resultMap.get("boardList"));
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("localName", localName);
		
		return "/board/boardList";
	}
	
	@GetMapping("/board/boardOne")
	public String boardOne(int boardNo, Model model) {
		Board board = boardService.boardOne(boardNo);
		List<Boardfile> boardfile = boardService.selectBoardfiles(boardNo);
		model.addAttribute("board", board);
		model.addAttribute("boardfile", boardfile);
		return "/board/boardOne";
	}
	
	@GetMapping("/board/modifyBoard")
	public String modifyBoard(Model model, @RequestParam(name="boardNo") int boardNo) {
		List<Map<String, Object>> localNameList = boardService.getLocalNameList();
		model.addAttribute("localNameList", localNameList);
		Board board = boardService.boardOne(boardNo);
		model.addAttribute("board", board);
		List<Boardfile> boardfile = boardService.selectBoardfiles(boardNo);
		model.addAttribute("boardfile", boardfile);
		return "/board/modifyBoard";
	}
	@GetMapping("/board/deleteBoard")
	public String deleteBoard(Model model, @RequestParam(name="boardNo") int boardNo) {
		Board board = boardService.boardOne(boardNo);
		model.addAttribute("board", board);
		return "/board/deleteBoard";
	}
	
	@PostMapping("/board/modifyBoard")
	public String modifyBoard(
			HttpServletRequest request,
			Board board) {
		String path = request.getServletContext().getRealPath("/upload/");
		// 기존 저장된 boardFile이랑 비교해서 없는 boardfileNo가 있으면 deleteOneBoardfile 수행
		List<Boardfile> boardfile = boardService.selectBoardfiles(board.getBoardNo());
		System.out.println(board.getBoardfileNo());
		for(Boardfile bf : boardfile) {
			boolean isDelete = true;
			if(board.getBoardfileNo() == null) {
				boardService.deleteOneBoardfile(bf.getBoardfileNo(), path);
				continue;
			}
			for(int bfNo : board.getBoardfileNo()) {
				if(bf.getBoardfileNo() == bfNo) {
					isDelete = false;
				}
			}
			if(isDelete) {
				boardService.deleteOneBoardfile(bf.getBoardfileNo(), path);
			}
		}

		int row = boardService.modifyBoard(board, path);
		log.debug("\u001B[31m" + "row : " + row + "\u001B[0m");
		return "redirect:/board/boardOne?boardNo=" + board.getBoardNo();
	}
	@PostMapping("/board/deleteBoard")
	public String deleteBoard(
			HttpServletRequest request,
			@RequestParam(name="boardNo") int boardNo,
			@RequestParam(name="memberId") String memberId) {
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setMemberId(memberId);
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.deleteBoard(board, path);
		log.debug("\u001B[31m" + "row : " + row + "\u001B[0m");
		return "redirect:/board/boardList";
	}
}
