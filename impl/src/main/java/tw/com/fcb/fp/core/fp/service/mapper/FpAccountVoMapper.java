package tw.com.fcb.fp.core.fp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tw.com.fcb.fp.core.fp.respository.entity.FPCuster;
import tw.com.fcb.fp.core.fp.service.cmd.FPAccountCreateCmd;
import tw.com.fcb.fp.core.fp.service.vo.FPAccountVo;

@Mapper
public interface FpAccountVoMapper {

	@Mapping(target = "fpcAccount" , source = "accountNo")
	@Mapping(target = "fpcCustomerId" , source = "customerIdno")
	FPCuster toEntity(FPAccountCreateCmd cmd);
	
	@Mapping(target = "accountNo" , source = "fpcAccount")
	@Mapping(target = "customerIdno" , source = "fpcCustomerId")
	@Mapping(target = "status" , source = "fpcStatus")
	FPAccountVo toVo (FPCuster fpCuster);
	

}
