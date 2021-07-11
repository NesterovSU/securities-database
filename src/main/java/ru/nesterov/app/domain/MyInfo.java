package ru.nesterov.app.domain;

import lombok.Getter;

/**
 * @author Sergey Nesterov
 */
@Getter
public class MyInfo {
    private String secid;
    private String regnumber;
    private String name;
    private String emitentTitle;
    private String tradedate;
    private int numtrades;
    private double open;
    private double close;

    public MyInfo(History history){
        this.secid = history.getSecid();
        this.regnumber = history.getSecurity().getRegnumber();
        this.name = history.getSecurity().getName();
        this.emitentTitle = history.getSecurity().getEmitentTitle();
        this.tradedate = history.getTradedate();
        this.numtrades = history.getNumtrades();
        this.open = history.getOpen();
        this.close = history.getClose();
    }
}
