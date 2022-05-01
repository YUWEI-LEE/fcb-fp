package tw.com.fcb.fp.core.fp.respository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 *  TxLog交易明細資訊
 *
 */

@Entity
@Table(name = "FPTxLog")
@Data
public class TxLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TX_ACCOUNT")
	private String account;
	
	@Column(name = "TX_CRCY")
	private String crcy;
	
	@Column(name = "TX_DATE")
	private LocalDate txDate;
	
	@Column(name = "TX_TIME")
	private String txDTime;
	
	@Column(name = "TX_MEMO")
	private String memo;
	
	@Column(name = "TX_AMT")
	private BigDecimal txAmt;
		
	@Column(name = "TX_AFT_BAL")
	private BigDecimal balance;
	
	@Column(name = "TX_CR_DB_CODE")
	private String cdCode;
	
	@Column(name = "TX_STATUS")
	private String status;

}
