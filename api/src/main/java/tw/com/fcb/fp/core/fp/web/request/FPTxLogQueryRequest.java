package tw.com.fcb.fp.core.fp.web.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FPTxLogQueryRequest {

	private String account;
	
	private LocalDate txDate;
	
	private String crcy;
	
}
