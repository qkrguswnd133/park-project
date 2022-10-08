package com.spring.project.service;

import com.spring.project.entity.Board;
import com.spring.project.entity.BoardFileEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {

    List<Board> selectBoardList() throws Exception;

    void saveBoard(Board board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception;

    Board selectBoardDetail(int boardIdx) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception;

    void deleteBoardFile(int idx, int boardIdx) throws Exception;
}
