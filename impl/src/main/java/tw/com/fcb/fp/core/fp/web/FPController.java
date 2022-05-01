package tw.com.fcb.fp.core.fp.web;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;
import tw.com.fcb.fp.core.commons.http.Response;
import tw.com.fcb.fp.core.fp.common.enums.BookType;
import tw.com.fcb.fp.core.fp.common.enums.CrcyCode;
import tw.com.fcb.fp.core.fp.respository.entity.FPMaster;
import tw.com.fcb.fp.core.fp.service.FPCService;
import tw.com.fcb.fp.core.fp.service.vo.FPAccountVo;
import tw.com.fcb.fp.core.fp.web.dto.FPAccountDto;
import tw.com.fcb.fp.core.fp.web.mapper.FpAccountDtoMapper;
import tw.com.fcb.fp.core.fp.web.request.FPAccountCreateRequest;

@RestController
@RequestMapping("/fp/accounts")
public class FPController implements FPAccountApi {

	@Autowired
	FPCService fpcService;

	@Autowired
	FpAccountDtoMapper fpAccountDtoMapper;

	Logger log = LoggerFactory.getLogger(getClass());

	public Response<FPAccountDto> getByAccount(
			@Parameter(description = "帳號", example = "09340123456") @PathVariable("account") String acc) {
		Response<FPAccountDto> response = new Response<FPAccountDto>();

		try {
			FPAccountDto fpAccountDto = fpAccountDtoMapper.fromVo(fpcService.getByfpcAccount(acc));
			if (fpAccountDto == null) {
				response.of("D001", "查詢" + acc + "無此帳號，請重新輸入", fpAccountDto);
			} else {
				response.of("0000", "交易成功", fpAccountDto);
			}
		} catch (Exception e) {
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}

	public Response<BigDecimal> getByfpmCurrencyBal(
			@Parameter(description = "帳號", example = "09340123456") @PathVariable("account") String acc,
			@PathVariable("crcy") String crcy) {

		Response<BigDecimal> response = new Response<BigDecimal>();
		try {
			FPAccountDto fpAccountDto = fpAccountDtoMapper.fromVo(fpcService.getByfpcAccount(acc));
			FPMaster fPMaster = fpcService.getByfpmCurrencyData(acc, crcy);
			if (fpAccountDto == null) {
				response.of("D001", "查詢" + acc + "無此帳號，請重新輸入", null);
			} else {
				BigDecimal fpmBal = fPMaster.getFpmBalance();
				response.of("0000", "交易成功，帳號" + acc + "之幣別" + crcy + "餘額", fpmBal);
			}
		} catch (Exception e) {
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}

	public Response<FPAccountDto> updfpmBal(@PathVariable("account") String acc, @PathVariable("crcy") String crcy,
			@RequestParam BigDecimal addAmt, @RequestParam BigDecimal subAmt) {

		Response<FPAccountDto> response = new Response<FPAccountDto>();
		try {
			FPAccountDto fpAccountDto = fpAccountDtoMapper.fromVo(fpcService.updfpmBal(acc, crcy, addAmt, subAmt));
			response.of("0000", "交易成功", fpAccountDto);

		} catch (Exception e) {
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}
	
//	存入交易：增加該幣別餘額，若該幣別不存在則insert
	public Response<FPAccountDto> depositFpm(@PathVariable("account") String account, @PathVariable("crcy") String crcy,
			@RequestParam BigDecimal addAmt,@RequestParam(required = false) String memo ) {
		
		Response<FPAccountDto> response = new Response<FPAccountDto>();
		
		// 驗證 Request
		FPAccountVo fpAccountVo = fpcService.getByfpcAccount(account);
		if (fpAccountVo == null) {
			response.of("D123", "此帳號 "+ account +" 不存在，請重新輸入", null); 
			return response;
		}
		
		// 呼叫服務
		FPAccountCreateRequest createRequest = new FPAccountCreateRequest();
		createRequest.setAccountNo(fpAccountVo.getAccountNo());
		createRequest.setBookType(fpAccountVo.getBookType());
		createRequest.setCrcyCode(crcy);
		createRequest.setCustomerIdno(fpAccountVo.getCustomerIdno());
		try {
			CrcyCode.valueOf(crcy);
			//無幣別餘額則新增fpm
			if (fpcService.getByfpmCurrencyBal(account, crcy) == null) {
				fpcService.createFpm(fpAccountDtoMapper.toCreateCmd(createRequest));
			}
			//入帳
			FPAccountDto fpAccountDto = fpAccountDtoMapper.fromVo(fpcService.depositFpm(account, crcy, addAmt,memo));
			response.of("0000", "交易成功", fpAccountDto);
			
		} catch (IllegalArgumentException e) {
			response.of("M502", "幣別"+ crcy+"輸入錯誤", null);
			return response;
		} catch (Exception e) {
			System.out.println("err =" + e);
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}

//	支出交易：減少該幣別餘額，若該幣別不存在則error
	public Response<FPAccountDto> withdrawFpm(@PathVariable("account") String account, @PathVariable("crcy") String crcy,
			@RequestParam BigDecimal subAmt,@RequestParam(required = false) String memo ) {
		
		Response<FPAccountDto> response = new Response<FPAccountDto>();
		
		// 驗證 Request
		FPAccountVo fpAccountVo = fpcService.getByfpcAccount(account);
		if (fpAccountVo == null) {
			response.of("D123", "此帳號 "+ account +" 不存在，請重新輸入", null); 
			return response;
		}
		
		// 呼叫服務
		FPAccountCreateRequest createRequest = new FPAccountCreateRequest();
		createRequest.setAccountNo(fpAccountVo.getAccountNo());
		createRequest.setBookType(fpAccountVo.getBookType());
		createRequest.setCrcyCode(crcy);
		createRequest.setCustomerIdno(fpAccountVo.getCustomerIdno());
		try {
			CrcyCode.valueOf(crcy);
			//無幣別餘額則error
			if (fpcService.getByfpmCurrencyBal(account, crcy) == null) {
				response.of("D50P", "該帳號無此幣別"+ crcy, null);
				return response;
			}else {
				if (fpcService.getByfpmCurrencyBal(account, crcy).compareTo(subAmt) == -1) {
					response.of("M533", "該帳號餘額不足"+ crcy, null);
					return response;
				}
			}
			//支出
			FPAccountDto fpAccountDto = fpAccountDtoMapper.fromVo(fpcService.withdrawFpm(account, crcy, subAmt,memo));
			log.info("fpAccountDto:{}", fpAccountDto);
			response.of("0000", "交易成功", fpAccountDto);
			
		} catch (IllegalArgumentException e) {
			response.of("M502", "幣別"+ crcy+"輸入錯誤", null);
			return response;
		} catch (Exception e) {
			System.out.println("err =" + e);
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}

//	更正交易：沖正
	public Response<FPAccountDto> undoFpm(@RequestParam("id") Long id) {
		
		Response<FPAccountDto> response = new Response<FPAccountDto>();

		// 驗證 Request
		// 呼叫服務
		try {
			fpcService.undoFpm(id);
			response.of("0000", "交易成功", null);
		} catch (Exception e) {
			System.out.println("err =" + e);
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}

//	開戶交易：新增帳號資訊，單幣別存摺增加幣別資訊
	@Override
	public Response<FPAccountDto> create(FPAccountCreateRequest createRequest) {
		Response<FPAccountDto> response = new Response<FPAccountDto>();
		try {
			log.info("createRequest:{}", createRequest);
			// 1. 接值

			// 2. 驗證 Request
			String accountNoStr = createRequest.getAccountNo();
			String bookTypeStr  = createRequest.getBookType();
			String crcyCodeStr  = createRequest.getCrcyCode();
			try {
				Double.parseDouble(accountNoStr);
				BookType.valueOf(bookTypeStr);
				CrcyCode.valueOf(crcyCodeStr);
			} catch (NumberFormatException e) {
				response.of("M501", "帳號=" + accountNoStr + "非數值請重新輸入", null);
				return response;
			} catch (IllegalArgumentException e) {
				response.of("M502", "存摺種類" + bookTypeStr + "或幣別"+ crcyCodeStr+"輸入錯誤", null);
				return response;
			}
			if(bookTypeStr.equals("MULTI") && crcyCodeStr.equals("ALL")){
				response.of("M503", "存摺種類為MULTI【多本存摺(單幣別)】之幣別欄位不得輸入ALL", null);
				return response;
			}
			if(!bookTypeStr.equals("MULTI") && !crcyCodeStr.equals("ALL")){
				System.out.println("@@@"  + bookTypeStr + "!!! " + crcyCodeStr);
				response.of("M504", "存摺種類為ONE【一本存摺(多幣別)】或NONE【無摺】之幣別欄位限輸入ALL", null);
				return response;
			}
			FPAccountDto fpAccountDto = fpAccountDtoMapper.fromVo(fpcService.getByfpcAccount(createRequest.getAccountNo()));
			if (fpAccountDto != null) {
				response.of("D123", "查詢("+ createRequest.getAccountNo() +"此帳號已存在，請重新輸入", null); 
				return response;
			}

			// 3. 呼叫服務Request -> Cmd -> Vo  (新增帳號層資訊)
			FPAccountVo accountVo = fpcService.createFpc(fpAccountDtoMapper.toCreateCmd(createRequest));
			//	  多本存摺(單幣別)須新增幣別資訊
			if (createRequest.getBookType().equals("MULTI")) {
				fpcService.createFpm(fpAccountDtoMapper.toCreateCmd(createRequest));
			}

			// 4. 設值

			// 5. 回傳訊息訊息Vo -> Dto
			FPAccountDto dto = fpAccountDtoMapper.fromVo(accountVo);
			if (createRequest.getBookType().equals("MULTI")) {
				dto.setCrcyCode(String.valueOf(createRequest.getCrcyCode()));
			}
//			response.of("0000", "交易成功", fpAccountDtoMapper.fromVo(vo));
			log.info("FPAccountDto:{}", dto);
			response.of("0000", "交易成功", dto);

		} catch (Exception e) {
			System.out.println("err=" + e);
			response.of("9999", "交易失敗，請重新輸入", null);
		}

		return response;
	}

}
