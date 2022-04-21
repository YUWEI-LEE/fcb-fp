package tw.com.fcb.fp.core.fp.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.fcb.fp.core.fp.respository.entity.FPCuster;



@Repository
public interface FPCustomerRepository extends JpaRepository<FPCuster , Long>{
	FPCuster findByfpcAccount(String acc);

}
