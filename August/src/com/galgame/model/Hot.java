package com.galgame.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Hot {

    @Expose
    private List<Recent> recent = new ArrayList<Recent>();

    /**
     * 
     * @return
     *     The recent
     */
    public List<Recent> getRecent() {
        return recent;
    }

    /**
     * 
     * @param recent
     *     The recent
     */
    public void setRecent(List<Recent> recent) {
        this.recent = recent;
    }

}
