package coop.biantik.traductor.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SessionTypeEnum {
	DEFAULT(0), FACEBOOK(1), GOOGLE(2);
	private int value;

	private SessionTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static SessionTypeEnum getSessionType(int value) {
		return lookup.get(value);
	}

	private static final Map<Integer, SessionTypeEnum> lookup = new HashMap<Integer, SessionTypeEnum>();

	static {
		for (SessionTypeEnum s : EnumSet.allOf(SessionTypeEnum.class)) {
			lookup.put(s.value, s);
		}
	}
}
