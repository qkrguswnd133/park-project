package com.spring.project.repository;

import com.spring.project.entity.Board;
import com.spring.project.entity.BoardFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAllByOrderByBoardIdxDesc();

    @Query("SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx =:idx")
    BoardFileEntity findBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);

    @Query("DELETE FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
    void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
}
