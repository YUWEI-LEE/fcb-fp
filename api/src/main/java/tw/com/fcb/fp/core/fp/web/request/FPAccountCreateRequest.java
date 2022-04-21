package tw.com.fcb.fp.core.fp.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.com.fcb.fp.core.fp.common.enums.BookType;
import tw.com.fcb.fp.core.fp.common.enums.CrcyCode;
import tw.com.fcb.fp.core.fp.common.enums.Status;

@Data
public class FPAccountCreateRequest {

	@Schema(description = "外活帳號account")
	private String accountNo;
	
	@Schema(description = "客戶統編customer's idno")
	private String customerIdno;

	@Schema(description = "存摺種類")
	private String bookType;

	@Schema(description = "幣別")
	private String crcyCode;

}
