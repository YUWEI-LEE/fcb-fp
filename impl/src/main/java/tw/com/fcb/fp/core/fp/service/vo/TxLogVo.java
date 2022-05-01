package tw.com.fcb.fp.core.fp.service.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

//Value object 
@Data
public class TxLogVo {
	private Long id;

	private String account;

	private String crcy;

	private LocalDate txDate;

	private String txDTime;

	private String memo;

	private BigDecimal txAmt;

	private BigDecimal balance;

	private String cdCode;
	
	private String status;
}
