package com.zarluk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Game entity.
 */
public class GameDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long ndlvalue;

    private Long strategicvalue;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    private String board;

    @NotNull
    private String goals;

    private Set<PieceDTO> pieces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getNdlvalue() {
        return ndlvalue;
    }

    public void setNdlvalue(Long ndlvalue) {
        this.ndlvalue = ndlvalue;
    }
    public Long getStrategicvalue() {
        return strategicvalue;
    }

    public void setStrategicvalue(Long strategicvalue) {
        this.strategicvalue = strategicvalue;
    }
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public Set<PieceDTO> getPieces() {
        return pieces;
    }

    public void setPieces(Set<PieceDTO> pieces) {
        this.pieces = pieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDTO gameDTO = (GameDTO) o;

        if ( ! Objects.equals(id, gameDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GameDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", ndlvalue='" + ndlvalue + "'" +
            ", strategicvalue='" + strategicvalue + "'" +
            ", width='" + width + "'" +
            ", height='" + height + "'" +
            ", board='" + board + "'" +
            ", goals='" + goals + "'" +
            '}';
    }
}
