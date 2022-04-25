package tw.com.fcb.fp.core.fp.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class FPTxLogDto {
	
	String account;
	
	String crcy;
	
	LocalDate txDate;
	
	String txDTime;
	
	String memo;
	
	BigDecimal txAmt;
		
	BigDecimal balance;
	

}
