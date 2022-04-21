package tw.com.fcb.fp.core.commons.http;

import lombok.Data;
import tw.com.fcb.fp.core.commons.enums.ResponseStatus;

@Data
public class Response<T> {

	ResponseStatus status;
	
	String code;
	
	String message;
	
	T data;
	
	public void of(String code, String message, T data){
		if(code.equals("0000")) {
			this.setStatus(ResponseStatus.SUCCESS);
			this.setCode(code);
			this.setMessage(message);
			this.setData(data);
		}
		else {
			this.setStatus(ResponseStatus.ERROR);
			this.setCode(code);
			this.setMessage(message);
			this.setData(data);
		}
	}
}
