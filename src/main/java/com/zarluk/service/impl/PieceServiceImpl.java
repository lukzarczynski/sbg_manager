package com.zarluk.service.impl;

import com.zarluk.service.PieceService;
import com.zarluk.domain.Piece;
import com.zarluk.repository.PieceRepository;
import com.zarluk.service.dto.PieceDTO;
import com.zarluk.service.mapper.PieceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Piece.
 */
@Service
@Transactional
public class PieceServiceImpl implements PieceService{

    private final Logger log = LoggerFactory.getLogger(PieceServiceImpl.class);
    
    private final PieceRepository pieceRepository;

    private final PieceMapper pieceMapper;

    public PieceServiceImpl(PieceRepository pieceRepository, PieceMapper pieceMapper) {
        this.pieceRepository = pieceRepository;
        this.pieceMapper = pieceMapper;
    }

    /**
     * Save a piece.
     *
     * @param pieceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PieceDTO save(PieceDTO pieceDTO) {
        log.debug("Request to save Piece : {}", pieceDTO);
        Piece piece = pieceMapper.pieceDTOToPiece(pieceDTO);
        piece = pieceRepository.save(piece);
        PieceDTO result = pieceMapper.pieceToPieceDTO(piece);
        return result;
    }

    /**
     *  Get all the pieces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PieceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pieces");
        Page<Piece> result = pieceRepository.findAll(pageable);
        return result.map(piece -> pieceMapper.pieceToPieceDTO(piece));
    }

    /**
     *  Get one piece by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PieceDTO findOne(Long id) {
        log.debug("Request to get Piece : {}", id);
        Piece piece = pieceRepository.findOne(id);
        PieceDTO pieceDTO = pieceMapper.pieceToPieceDTO(piece);
        return pieceDTO;
    }

    /**
     *  Delete the  piece by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Piece : {}", id);
        pieceRepository.delete(id);
    }
}
