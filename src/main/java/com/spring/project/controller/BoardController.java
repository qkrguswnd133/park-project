package com.spring.project.controller;

import com.spring.project.entity.Board;
import com.spring.project.entity.BoardFileEntity;
import com.spring.project.service.BoardService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public ModelAndView openBoardList() throws Exception {
        ModelAndView mv = new ModelAndView("/board/board");

        List<Board> list = boardService.selectBoardList();
        mv.addObject("list", list);

        return mv;
    }

//    @RequestMapping(value = "/boardwrite", method = RequestMethod.GET)
//    public String openBoardWrite() throws Exception {
//        return "/boardwirte";
//    }

    @PostMapping(value = "/boardwrite")
    public String insertBoard(Board board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        int hitCnt = board.getHitCnt();

        boardService.saveBoard(board, multipartHttpServletRequest, hitCnt);
        return "redirect:/board";
    }

    @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        ModelAndView mv = new ModelAndView("/board/BoardDetail");

        Board board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);

        return mv;
    }

    @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.PUT)
    public String updateBoard(Board board) throws Exception {
        int hitCnt = board.getHitCnt();

        boardService.saveBoard(board, null, hitCnt + 1);
        return "redirect:/board";
    }

    @DeleteMapping(value = "/board/{boardIdx}")
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
    }

    @RequestMapping(value = "/board/file", method = RequestMethod.GET)
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
        BoardFileEntity boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
        if(ObjectUtils.isEmpty(boardFile) == false) {
            String fileName = boardFile.getOriginalFillName();

            byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));

            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
            response.setHeader("Content-Trasfer-Encoding", "binary");

            response.getOutputStream().write(files);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    @RequestMapping(value = "/board/file", method = RequestMethod.DELETE)
    public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
        boardService.deleteBoardFile(idx, boardIdx);

        return "redirect:/board/"+boardIdx;
    }

}
