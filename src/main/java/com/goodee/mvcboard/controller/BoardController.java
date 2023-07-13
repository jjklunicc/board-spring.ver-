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
		List<Boardfile> boardfile = boardService.selectBoardfile(boardNo);
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
			@RequestParam(name="boardNo") int boardNo,
			@RequestParam(name="localName") String localName,
			@RequestParam(name="boardTitle") String boardTitle,
			@RequestParam(name="boardContent") String boardContent,
			@RequestParam(name="memberId") String memberId) {
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setLocalName(localName);
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setMemberId(memberId);
		
		int row = boardService.modifyBoard(board);
		log.debug("\u001B[31m" + "row : " + row + "\u001B[0m");
		return "redirect:/board/boardOne?boardNo=" + boardNo;
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
