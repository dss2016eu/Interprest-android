package coop.biantik.traductor.network.beans;

import java.io.Serializable;

import coop.biantik.traductor.network.enums.ErrorStatusEnum;

public class JsonResponse implements Serializable {

	private static final long serialVersionUID = 389362879619437180L;

	Object result;

	private ErrorStatusEnum status = ErrorStatusEnum.OK;

	private String errorCode;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public ErrorStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ErrorStatusEnum status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
