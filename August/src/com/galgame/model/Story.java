package com.galgame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name="story")
public class Story extends Model 
	implements Serializable, Cloneable{
	@Override
	public Story clone()  {
		try {
			return (Story) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    @Expose
    @Column
    private String title;
    @SerializedName("ga_prefix")
    @Expose
    @Column
    private String gaPrefix;
    @Expose
    @Column
    private List<String> images = new ArrayList<String>();
    @Expose
    @Column
    private Boolean multipic;
    @Expose
    @Column
    private Integer type;
    @Expose
    @Column(name="story_id")
    private Integer id;

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The gaPrefix
     */
    public String getGaPrefix() {
        return gaPrefix;
    }

    /**
     * 
     * @param gaPrefix
     *     The ga_prefix
     */
    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    /**
     * 
     * @return
     *     The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The multipic
     */
    public Boolean getMultipic() {
        return multipic;
    }

    /**
     * 
     * @param multipic
     *     The multipic
     */
    public void setMultipic(Boolean multipic) {
        this.multipic = multipic;
    }

    /**
     * 
     * @return
     *     The type
     */
    public Integer getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getStoryId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
