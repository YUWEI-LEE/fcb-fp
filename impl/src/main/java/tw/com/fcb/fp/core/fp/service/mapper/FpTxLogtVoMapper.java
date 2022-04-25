package tw.com.fcb.fp.core.fp.service.mapper;

import org.mapstruct.Mapper;

import tw.com.fcb.fp.core.fp.respository.entity.TxLog;
import tw.com.fcb.fp.core.fp.service.cmd.TxLogCreatCmd;

@Mapper
public interface FpTxLogtVoMapper {
	TxLog toEntity(TxLogCreatCmd cmd);
	
//	TxLogVo toVo (TxLog txLog);

}
