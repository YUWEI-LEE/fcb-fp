package tw.com.fcb.fp.core.fp.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.fcb.fp.core.fp.web.dto.FPTxLogDto;

@RestController
@RequestMapping("/fp/txlogs")
public interface FPTxLogApi {

//	@GetMapping("")
//	public List<FPTxLogDto> getByCondition(@RequestBody FPTxLogQueryRequest);
	
}
