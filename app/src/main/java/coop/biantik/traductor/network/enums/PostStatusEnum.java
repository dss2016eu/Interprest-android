package coop.biantik.traductor.network.enums;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PostStatusEnum implements Serializable{
	PREPARED(1), PUBLISHED(2);
	private int value;

	private PostStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static PostStatusEnum getErrorStatus(int codErrorStatus) {
		return lookup.get(codErrorStatus);
	}

	private static final Map<Integer, PostStatusEnum> lookup = new HashMap<Integer, PostStatusEnum>();

	static {
		for (PostStatusEnum s : EnumSet.allOf(PostStatusEnum.class)) {
			lookup.put(s.value, s);
		}
	}

}