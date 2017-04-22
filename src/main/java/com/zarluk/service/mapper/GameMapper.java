package com.zarluk.service.mapper;

import com.zarluk.domain.*;
import com.zarluk.service.dto.GameDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Game and its DTO GameDTO.
 */
@Mapper(componentModel = "spring", uses = {PieceMapper.class, })
public interface GameMapper {

    GameDTO gameToGameDTO(Game game);

    List<GameDTO> gamesToGameDTOs(List<Game> games);

    Game gameDTOToGame(GameDTO gameDTO);

    List<Game> gameDTOsToGames(List<GameDTO> gameDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Game gameFromId(Long id) {
        if (id == null) {
            return null;
        }
        Game game = new Game();
        game.setId(id);
        return game;
    }
    

}
