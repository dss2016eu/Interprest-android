package coop.biantik.traductor.network.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ErrorStatusEnum {
	OK(0), ERROR(1);
	private int value;

	private ErrorStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ErrorStatusEnum getErrorStatus(int codErrorStatus) {
		return lookup.get(codErrorStatus);
	}

	private static final Map<Integer, ErrorStatusEnum> lookup = new HashMap<Integer, ErrorStatusEnum>();

	static {
		for (ErrorStatusEnum s : EnumSet.allOf(ErrorStatusEnum.class)) {
			lookup.put(s.value, s);
		}
	}

}