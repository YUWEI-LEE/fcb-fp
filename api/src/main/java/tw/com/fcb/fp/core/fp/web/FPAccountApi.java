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
	
	
	@PostMapping("/")
	@Operation(description = "新增帳號、幣別資訊", summary="新增帳號、幣別資訊")
	public Response<FPAccountDto> create(@RequestBody FPAccountCreateRequest createReqeust);
	
	
	
}
