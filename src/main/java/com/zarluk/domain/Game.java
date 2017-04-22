package com.zarluk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ndlvalue")
    private Long ndlvalue;

    @Column(name = "strategicvalue")
    private Long strategicvalue;

    @NotNull
    @Column(name = "width", nullable = false)
    private Integer width;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;

    @NotNull
    @Column(name = "board", nullable = false)
    private String board;

    @NotNull
    @Column(name = "goals", nullable = false)
    private String goals;

    @ManyToMany
    @JoinTable(name = "game_piece",
               joinColumns = @JoinColumn(name="games_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="pieces_id", referencedColumnName="id"))
    private Set<Piece> pieces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNdlvalue() {
        return ndlvalue;
    }

    public Game ndlvalue(Long ndlvalue) {
        this.ndlvalue = ndlvalue;
        return this;
    }

    public void setNdlvalue(Long ndlvalue) {
        this.ndlvalue = ndlvalue;
    }

    public Long getStrategicvalue() {
        return strategicvalue;
    }

    public Game strategicvalue(Long strategicvalue) {
        this.strategicvalue = strategicvalue;
        return this;
    }

    public void setStrategicvalue(Long strategicvalue) {
        this.strategicvalue = strategicvalue;
    }

    public Integer getWidth() {
        return width;
    }

    public Game width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Game height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getBoard() {
        return board;
    }

    public Game board(String board) {
        this.board = board;
        return this;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getGoals() {
        return goals;
    }

    public Game goals(String goals) {
        this.goals = goals;
        return this;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public Game pieces(Set<Piece> pieces) {
        this.pieces = pieces;
        return this;
    }

    public Game addPiece(Piece piece) {
        this.pieces.add(piece);
        piece.getGames().add(this);
        return this;
    }

    public Game removePiece(Piece piece) {
        this.pieces.remove(piece);
        piece.getGames().remove(this);
        return this;
    }

    public void setPieces(Set<Piece> pieces) {
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
        Game game = (Game) o;
        if (game.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Game{" +
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
