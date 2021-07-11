package ru.nesterov.app.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Sergey Nesterov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class History {

    @Id
    @JsonIgnore
    String id;

    @NotNull
    @Pattern(regexp = "\\d{4}-[0,1]\\d-[0-3]\\d", message = "Некорректный формат даты")
    private String tradedate;

    @NotNull
    private int numtrades;

    @NotNull
    private double open;

    @NotNull
    private double close;

    @Column(insertable = false,
            updatable = false)
    private String secid;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "secid")
    private Security security;

    public History(
            @NotNull String tradedate,
            @NotNull int numtrades,
            @NotNull double open,
            @NotNull double close,
            @NotNull Security security) {
        this.tradedate = tradedate;
        this.numtrades = numtrades;
        this.open = open;
        this.close = close;
        this.security = security;
        this.id = security.getSecid().concat(tradedate);
    }


    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
        if(security != null){
            this.id = security.getSecid().concat(tradedate);
        }
    }

    public void setSecid(Security security) {
        this.security = security;
        if(tradedate != null){
            this.id =  security.getSecid().concat(tradedate);
        }
    }
}
