package tw.com.fcb.fp.core.fp.service.cmd;

import lombok.Data;
import tw.com.fcb.fp.core.fp.common.enums.BookType;
import tw.com.fcb.fp.core.fp.common.enums.CrcyCode;

@Data
public class FPAccountCreateCmd {

	private String accountNo;
	
	private String customerIdno;

	private BookType bookType;

	private CrcyCode crcyCode;
}
