package com.zarluk.service;

import com.zarluk.service.dto.PieceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Piece.
 */
public interface PieceService {

    /**
     * Save a piece.
     *
     * @param pieceDTO the entity to save
     * @return the persisted entity
     */
    PieceDTO save(PieceDTO pieceDTO);

    /**
     *  Get all the pieces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PieceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" piece.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PieceDTO findOne(Long id);

    /**
     *  Delete the "id" piece.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
