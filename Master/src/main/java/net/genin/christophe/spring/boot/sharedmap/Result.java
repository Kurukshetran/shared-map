package net.genin.christophe.spring.boot.sharedmap;

import java.io.Serializable;


public class Result implements Serializable {

    private String name;

    private Integer nb;

    public Result() {
    }

    public Result(String name, Integer nb) {
        this.name = name;
        this.nb = nb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNb() {
        return nb;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }
}
