package coop.biantik.traductor.network.beans;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PostCriteria implements Serializable {

	private static final long serialVersionUID = -1438993741159362621L;

	private Integer page;
	private Integer size;
	private String language;
    private List<Integer> statuses;

	public PostCriteria(){
		page = 1;
		size = 20;
	}

	public PostCriteria(Integer size){
		this.size = size;
        page = 1;
	}


}
