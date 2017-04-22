package com.zarluk.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Piece entity.
 */
public class PieceDTO implements Serializable {

    private Long id;

    private String name;

    private String regex;

    private Long value;

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
    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PieceDTO pieceDTO = (PieceDTO) o;

        if ( ! Objects.equals(id, pieceDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PieceDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", regex='" + regex + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
