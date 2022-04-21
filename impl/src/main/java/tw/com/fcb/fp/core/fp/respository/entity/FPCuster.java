package tw.com.fcb.fp.core.fp.respository.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.com.fcb.fp.core.fp.common.enums.BookType;
import tw.com.fcb.fp.core.fp.common.enums.CrcyCode;

/**
 * 
 * FPC 帳號層資訊
 *
 */
@Entity
@Table(name = "FPCUSTOMER")
@Data
public class FPCuster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FPC_ACCOUNT")
	private String fpcAccount;
	
	@Column(name = "FPC_CUSTOMER_ID")
	private String fpcCustomerId;
	
	@Column(name = "FPC_STATUS")
	private String fpcStatus;

	@Column(name = "FPC_VALID_CRCY_CNT")
	private Integer validCrcyCnt;
	
	@Column(name = "FPC_BOOK_TYPE")
	private String bookType;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "FPC_LINK_ID",nullable = false)
	List<FPMaster> fpmasters;
	


}
