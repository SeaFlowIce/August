package com.galgame.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra {

    @SerializedName("long_comments")
    @Expose
    private Integer longComments;
    @Expose
    private Integer popularity;
    @SerializedName("short_comments")
    @Expose
    private Integer shortComments;
    @Expose
    private Integer comments;

    /**
     * 
     * @return
     *     The longComments
     */
    public Integer getLongComments() {
        return longComments;
    }

    /**
     * 
     * @param longComments
     *     The long_comments
     */
    public void setLongComments(Integer longComments) {
        this.longComments = longComments;
    }

    /**
     * 
     * @return
     *     The popularity
     */
    public Integer getPopularity() {
        return popularity;
    }

    /**
     * 
     * @param popularity
     *     The popularity
     */
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    /**
     * 
     * @return
     *     The shortComments
     */
    public Integer getShortComments() {
        return shortComments;
    }

    /**
     * 
     * @param shortComments
     *     The short_comments
     */
    public void setShortComments(Integer shortComments) {
        this.shortComments = shortComments;
    }

    /**
     * 
     * @return
     *     The comments
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

}
