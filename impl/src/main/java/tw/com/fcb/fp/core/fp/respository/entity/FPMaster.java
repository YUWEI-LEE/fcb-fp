package tw.com.fcb.fp.core.fp.respository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FPM 幣別層資訊
 *
 */

@Entity
@Table(name = "FPMASTER")
@Data
public class FPMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FPM_CRCY")
	private String fpmCurrency;
	
	@Column(name = "FPM_STATUS")
	private String fpmStatus;
	
	@Column(name = "FPM_BAL")
	private BigDecimal fpmBalance;
	

}
