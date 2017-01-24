package coop.biantik.traductor.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum GeneralStatusEnum {
	DISABLED(0), ENABLED(1), BLOCKED(2), PENDING(3);
	private int value;

	private GeneralStatusEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		   return value;
	}
	
	public static GeneralStatusEnum getClientStatus(int codClientStatus){
		return lookup.get(codClientStatus);
	}

	private static final Map<Integer, GeneralStatusEnum> lookup = new HashMap<Integer, GeneralStatusEnum>();

	static {
	    for(GeneralStatusEnum s : EnumSet.allOf(GeneralStatusEnum.class)){
	         lookup.put(s.value, s);
		}
	}

}
