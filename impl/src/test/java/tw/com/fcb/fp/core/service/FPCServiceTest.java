package tw.com.fcb.fp.core.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.FetchType;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import tw.com.fcb.fp.core.fp.respository.FPCustomerRepository;
import tw.com.fcb.fp.core.fp.respository.entity.FPCuster;
import tw.com.fcb.fp.core.fp.respository.entity.FPMaster;
import tw.com.fcb.fp.core.fp.service.FPCService;
import tw.com.fcb.fp.core.fp.service.vo.FPAccountVo;


@SpringBootTest
class FPCServiceTest {

	@Autowired
	FPCService fpCService;
	
	@Autowired
	FPCustomerRepository fPCustomerRepository;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	//FPCuster 需改成 FetchType.EAGER才可以執行
	@Disabled
	@Test
	void updfpmBalTest() {
		//Arrange
		String acc="09340123456";
		String crcy="USD";
		BigDecimal beforeBalance= fpCService.getByfpmCurrencyBal(acc,crcy);
		BigDecimal addAmt = new BigDecimal(1000);
		BigDecimal subAmt = new BigDecimal(100);
		//Act
		FPAccountVo fpAccountVo = fpCService.updfpmBal(acc, crcy, addAmt, subAmt);
		FPCuster fpCuster = fPCustomerRepository.findByfpcAccount(acc);
		System.out.println(fpCuster);

		BigDecimal expectValue = beforeBalance.add(addAmt).subtract(subAmt);
		
		//Assert
		for( FPMaster fpMaster : fpCuster.getFpmasters()) {
			if(fpMaster.getFpmCurrency().equals("USD")) {
				BigDecimal actualValue = fpMaster.getFpmBalance();
				assertEquals(expectValue, actualValue);
				log.info("actualValue: {}",actualValue);
				log.info("expectValue: {}",expectValue);
			}
		}
			
		
	}

}
