package coop.biantik.traductor.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PushTypeEnum {
	DEFAULT(0);
	private int value;

	private PushTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static PushTypeEnum getPushType(int codPushType) {
		return lookup.get(codPushType);
	}

	private static final Map<Integer, PushTypeEnum> lookup = new HashMap<Integer, PushTypeEnum>();

	static {
		for (PushTypeEnum s : EnumSet.allOf(PushTypeEnum.class)) {
			lookup.put(s.value, s);
		}
	}

}
