package com.zarluk.service.impl;

import com.zarluk.service.GameService;
import com.zarluk.domain.Game;
import com.zarluk.repository.GameRepository;
import com.zarluk.service.dto.GameDTO;
import com.zarluk.service.mapper.GameMapper;
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
 * Service Implementation for managing Game.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService{

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);
    
    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    /**
     * Save a game.
     *
     * @param gameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GameDTO save(GameDTO gameDTO) {
        log.debug("Request to save Game : {}", gameDTO);
        Game game = gameMapper.gameDTOToGame(gameDTO);
        game = gameRepository.save(game);
        GameDTO result = gameMapper.gameToGameDTO(game);
        return result;
    }

    /**
     *  Get all the games.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        Page<Game> result = gameRepository.findAll(pageable);
        return result.map(game -> gameMapper.gameToGameDTO(game));
    }

    /**
     *  Get one game by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GameDTO findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        Game game = gameRepository.findOneWithEagerRelationships(id);
        GameDTO gameDTO = gameMapper.gameToGameDTO(game);
        return gameDTO;
    }

    /**
     *  Delete the  game by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.delete(id);
    }
}
