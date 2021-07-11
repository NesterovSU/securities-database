package ru.nesterov.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Sergey Nesterov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Security {

    @NotNull
    @Column(unique = true)
    private Integer id;

    @Id
    @NotNull
    private String secid;

    @NotNull
    private String regnumber;

    @NotNull
    private String name;

    @NotNull
    private String emitentTitle;

    @JsonIgnore
    @OneToMany(mappedBy="security", cascade = CascadeType.REMOVE)
    private List<History> history;

    public Security(
            @NotNull Integer id,
            @NotNull String secid,
            @NotNull String regnumber,
            @NotNull String name,
            @NotNull String emitentTitle) {
        this.id = id;
        this.secid = secid;
        this.regnumber = regnumber;
        this.name = name;
        this.emitentTitle = emitentTitle;
    }

    @Override
    public String toString(){
        return String.format("id = %s, secid = %s, regnumber = %s, name = %s, emitentTitle = %s",
                id, secid, regnumber, name, emitentTitle);
    }
}
