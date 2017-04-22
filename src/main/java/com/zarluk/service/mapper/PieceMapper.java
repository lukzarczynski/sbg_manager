package com.zarluk.service.mapper;

import com.zarluk.domain.*;
import com.zarluk.service.dto.PieceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Piece and its DTO PieceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PieceMapper {

    PieceDTO pieceToPieceDTO(Piece piece);

    List<PieceDTO> piecesToPieceDTOs(List<Piece> pieces);

    @Mapping(target = "games", ignore = true)
    Piece pieceDTOToPiece(PieceDTO pieceDTO);

    List<Piece> pieceDTOsToPieces(List<PieceDTO> pieceDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Piece pieceFromId(Long id) {
        if (id == null) {
            return null;
        }
        Piece piece = new Piece();
        piece.setId(id);
        return piece;
    }
    

}
