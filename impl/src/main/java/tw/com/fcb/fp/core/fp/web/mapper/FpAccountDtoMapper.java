package tw.com.fcb.fp.core.fp.web.mapper;

import org.mapstruct.Mapper;

import tw.com.fcb.fp.core.fp.service.cmd.FPAccountCreateCmd;
import tw.com.fcb.fp.core.fp.service.vo.FPAccountVo;
import tw.com.fcb.fp.core.fp.web.dto.FPAccountDto;
import tw.com.fcb.fp.core.fp.web.request.FPAccountCreateRequest;

@Mapper
public interface FpAccountDtoMapper {

	FPAccountCreateCmd toCreateCmd(FPAccountCreateRequest request);
	
	FPAccountDto fromVo(FPAccountVo vo);
	
}



