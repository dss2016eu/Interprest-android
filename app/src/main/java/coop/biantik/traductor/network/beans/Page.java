
package coop.biantik.traductor.network.beans;

import com.google.gson.annotations.Expose;

public class Page {

    @Expose
    private Integer size;
    @Expose
    private Integer totalElements;
    @Expose
    private Integer totalPages;
    @Expose
    private Integer number;

    /**
     * 
     * @return
     *     The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The totalElements
     */
    public Integer getTotalElements() {
        return totalElements;
    }

    /**
     * 
     * @param totalElements
     *     The totalElements
     */
    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * 
     * @return
     *     The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * 
     * @param totalPages
     *     The totalPages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 
     * @return
     *     The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

}
