package com.zarluk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Piece.
 */
@Entity
@Table(name = "piece")
public class Piece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "regex")
    private String regex;

    @Column(name = "jhi_value")
    private Long value;

    @ManyToMany(mappedBy = "pieces")
    @JsonIgnore
    private Set<Game> games = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Piece name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public Piece regex(String regex) {
        this.regex = regex;
        return this;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Long getValue() {
        return value;
    }

    public Piece value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Set<Game> getGames() {
        return games;
    }

    public Piece games(Set<Game> games) {
        this.games = games;
        return this;
    }

    public Piece addGame(Game game) {
        this.games.add(game);
        game.getPieces().add(this);
        return this;
    }

    public Piece removeGame(Game game) {
        this.games.remove(game);
        game.getPieces().remove(this);
        return this;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        if (piece.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, piece.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Piece{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", regex='" + regex + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
