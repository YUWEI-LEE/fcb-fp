package tw.com.fcb.fp.core.fp.service.cmd;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxLogCreatCmd {

	private String account;
	
	private String crcy;
	
	private LocalDate txDate;
	
	private String txDTime;
	
	private String memo;
	
	private BigDecimal txAmt;
		
	private BigDecimal balance;
	
	private String cdCode;
	
	private String status;
	
	private Long rollbackId;
	
}
