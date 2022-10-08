package com.spring.project.service;

import com.spring.project.entity.Board;
import com.spring.project.entity.BoardFileEntity;
import com.spring.project.repository.BoardRepository;
import com.spring.project.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    FileUtils fileUtils;

    @Override
    public List<Board> selectBoardList() throws Exception {
        return boardRepository.findAllByOrderByBoardIdxDesc();
    }

    @Override
    public void saveBoard(Board board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception {
        board.setCreatorId("admin");
        board.setHitCnt(hitCnt);
        List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(list) == false) {
            board.setFileList(list);
        }
        boardRepository.save(board);
    }

    @Override
    public Board selectBoardDetail(int boardIdx) throws Exception {
        Optional<Board> optional = boardRepository.findById(boardIdx);
        if (optional.isPresent()){
            Board board = optional.get();
            board.setHitCnt(board.getHitCnt() + 1);
            boardRepository.save(board);

            return board;
        }else {
            throw new NullPointerException();
        }
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardRepository.deleteById(boardIdx);
    }

    @Override
    public BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        BoardFileEntity boardFile = boardRepository.findBoardFile(idx, boardIdx);
        return boardFile;
    }

    @Override
    public void deleteBoardFile(int idx, int boardIdx) throws Exception {
        boardRepository.deleteBoardFile(idx, boardIdx);
    }
}
