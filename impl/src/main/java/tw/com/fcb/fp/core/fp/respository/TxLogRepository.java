package tw.com.fcb.fp.core.fp.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.fcb.fp.core.fp.respository.entity.TxLog;

@Repository
public interface TxLogRepository extends JpaRepository<TxLog , Long>{
	
	TxLog getByrollbackId(Long rollBackId);

}
