package tw.com.fcb.fp.core.fp.web;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import tw.com.fcb.fp.core.commons.http.Response;
import tw.com.fcb.fp.core.fp.web.dto.FPAccountDto;
import tw.com.fcb.fp.core.fp.web.request.FPAccountCreateRequest;


public interface FPAccountApi {
	
	@GetMapping("/{account}")
	@Operation(description = "依帳號查詢FPCuster資料", summary="查詢FPCuster資料")
	public Response<FPAccountDto> getByAccount(@Parameter(description = "帳號", example = "09340123456")
													@PathVariable("account") String account);
	
	@GetMapping("/{account}/{crcy}/balance")
	@Operation(description = "依帳號、幣別查詢FPM餘額", summary="查詢FPM餘額")
	public Response<BigDecimal> getByfpmCurrencyBal(@Parameter(description = "帳號", example = "09340123456")
													@PathVariable("account") String acc,@PathVariable("crcy")String crcy);
	
	
	@PutMapping("/{account}/{crcy}/balance")
	@Operation(description = "更新幣別FPM餘額(先加後減)", summary="更新幣別FPM餘額")
	public Response<FPAccountDto> updfpmBal(@PathVariable("account") String acc,@PathVariable("crcy") String crcy,
										@RequestParam BigDecimal addAmt,@RequestParam BigDecimal subAmt);
	
	
	@PutMapping("/{account}/{crcy}/deposit")
	@Operation(description = "存入交易，若幣別不存在則新增FPM", summary="存入交易")
	public Response<FPAccountDto> depositFpm(@Parameter(description = "帳號", example = "09340123456")
										@PathVariable("account") String account,@PathVariable("crcy") String crcy,
										@RequestParam BigDecimal addAmt,@RequestParam String memo);
	
	@PutMapping("/{account}/{crcy}/withdraw")
	@Operation(description = "存入交易，若該幣別不存在則error", summary="支出交易")
	public Response<FPAccountDto> withdrawFpm(@Parameter(description = "帳號", example = "09340123456")
										@PathVariable("account") String account, 
										@Parameter(description = "幣別", example = "USD")
										@PathVariable("crcy") String crcy,
										@RequestParam BigDecimal subAmt,@RequestParam(required = false) String memo );
	
	@PutMapping("/undo")
	@Operation(description = "沖正，請輸入正向交易之回傳txnLogId欄位", summary="更正交易")
	public Response<FPAccountDto> undoFpm(@RequestParam Long id);
	
	@PutMapping("/undo/system")
	@Operation(description = "補償，請輸入正向交易之回傳txnLogId欄位(刪除交易明細)", summary="補償")
	public Response<FPAccountDto> undoSystemFpm(@RequestParam Long id);
	
	@PostMapping("/")
	@Operation(description = "新增帳號、幣別資訊", summary="新增帳號、幣別資訊")
	public Response<FPAccountDto> create(@RequestBody FPAccountCreateRequest createReqeust);
	
	
	
}
