package tw.com.fcb.fp.core.fp.service.vo;

import lombok.Data;

// Value object 
@Data
public class FPAccountVo {
	private Long id;
	
	private String accountNo;
	
	private String customerIdno;
	
	private String status;

	private Integer validCrcyCnt;
	
	private String bookType;

}
