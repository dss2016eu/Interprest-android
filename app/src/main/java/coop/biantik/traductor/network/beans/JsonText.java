package coop.biantik.traductor.network.beans;

import java.io.Serializable;

public class JsonText implements Serializable {

	private static final long serialVersionUID = 2778074826595554989L;
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
