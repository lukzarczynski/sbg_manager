package com.zarluk.repository;

import com.zarluk.domain.Piece;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Piece entity.
 */
@SuppressWarnings("unused")
public interface PieceRepository extends JpaRepository<Piece,Long> {

}
