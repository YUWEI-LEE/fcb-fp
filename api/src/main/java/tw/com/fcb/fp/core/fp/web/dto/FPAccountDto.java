package tw.com.fcb.fp.core.fp.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.com.fcb.fp.core.fp.common.enums.BookType;
import tw.com.fcb.fp.core.fp.common.enums.CrcyCode;
import tw.com.fcb.fp.core.fp.common.enums.Status;

@Data
@Schema(description = "外活帳號")
// DTO Data Transfer Object
public class FPAccountDto {

	@Schema(description = "id")
	private Long id;
	
	@Schema(description = "外活帳號")
	private String accountNo;

	@Schema(description = "客戶統編")
	private String customerIdno;

	@Schema(description = "外活狀態")
	private String status;

	@Schema(description = "有效幣別數")
	private Integer validCrcyCnt;
	
	@Schema(description = "存摺種類")
	private String bookType;

	@Schema(description = "幣別")
	private String crcyCode;
}
