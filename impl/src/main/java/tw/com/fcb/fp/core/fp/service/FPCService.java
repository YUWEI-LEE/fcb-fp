package tw.com.fcb.fp.core.fp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.fcb.fp.core.fp.respository.FPCustomerRepository;
import tw.com.fcb.fp.core.fp.respository.TxLogRepository;
import tw.com.fcb.fp.core.fp.respository.entity.FPCuster;
import tw.com.fcb.fp.core.fp.respository.entity.FPMaster;
import tw.com.fcb.fp.core.fp.respository.entity.TxLog;
import tw.com.fcb.fp.core.fp.service.cmd.FPAccountCreateCmd;
import tw.com.fcb.fp.core.fp.service.cmd.TxLogCreatCmd;
import tw.com.fcb.fp.core.fp.service.mapper.FpAccountVoMapper;
import tw.com.fcb.fp.core.fp.service.mapper.FpTxLogtVoMapper;
import tw.com.fcb.fp.core.fp.service.vo.FPAccountVo;
import tw.com.fcb.fp.core.fp.service.vo.TxLogVo;
import tw.com.fcb.fp.core.fp.web.request.FPAccountCreateRequest;

@Transactional
@Service
public class FPCService {

	@Autowired
	FPCustomerRepository fPCustomerRepository;
	@Autowired
	TxLogRepository txLogRepository;

	@Autowired
	FpAccountVoMapper fpAccountVoMapper;
	@Autowired
	FpTxLogtVoMapper fpTxLogtVoMapper;
	Logger log = LoggerFactory.getLogger(getClass());

	// 傳入帳號，查詢帳號、幣別資訊
	public FPAccountVo getByfpcAccount(String acc) {
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(acc);
		FPAccountVo fpAccountVo = fpAccountVoMapper.toVo(fpcuster);
		return fpAccountVo;
	}

	// 傳入帳號、幣別，查詢餘額
	public BigDecimal getByfpmCurrencyBal(String acc, String crcy) {
		BigDecimal accfpmBal = null;
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(acc);
		List<FPMaster> fpmArry = fpcuster.getFpmasters();
		for (FPMaster fpMaster : fpmArry) {
			if (fpMaster.getFpmCurrency().equals(crcy)) {
				accfpmBal = fpMaster.getFpmBalance();
			}
		}
		return accfpmBal;
	}

	// 傳入帳號、幣別，查詢幣別資訊
	public FPMaster getByfpmCurrencyData(String acc, String crcy) {
		FPMaster fPMaster = null;
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(acc);
		List<FPMaster> fpmArry = fpcuster.getFpmasters();
		for (FPMaster idx : fpmArry) {
			if (idx.getFpmCurrency().equals(crcy)) {
				fPMaster = idx;
			}
		}
		return fPMaster;
	}

	// 更新幣別餘額
	public FPAccountVo updfpmBal(String acc, String crcy, BigDecimal addAmt, BigDecimal subAmt) {
		FPMaster fPMaster = null;
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(acc);
		List<FPMaster> fpmArry = fpcuster.getFpmasters();
		for (FPMaster idx : fpmArry) {
			if (idx.getFpmCurrency().equals(crcy)) {
				fPMaster = idx;
				BigDecimal afterBal = idx.getFpmBalance().add(addAmt).subtract(subAmt);
				fPMaster.setFpmBalance(afterBal);
			}
		}

		FPAccountVo fpAccountVo = fpAccountVoMapper.toVo(fpcuster);
		return fpAccountVo;
	}

	// 存入：更新幣別餘額
	public FPAccountVo depositFpm(String account, String crcy, BigDecimal addAmt, String memo) {
		FPMaster fPMaster = null;
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(account);
		List<FPMaster> fpmArry = fpcuster.getFpmasters();
		for (FPMaster idx : fpmArry) {
			if (idx.getFpmCurrency().equals(crcy)) {
				fPMaster = idx;
				BigDecimal afterBal = idx.getFpmBalance().add(addAmt);
				fPMaster.setFpmBalance(afterBal);
			}
		}

		// 寫入交易明細
		String txnMemo;
		if (memo == null) {
			txnMemo = "轉帳存入";
		} else {
			txnMemo = memo;
		}
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalDate todaysDate = LocalDate.now();
		BigDecimal aftBal = getByfpmCurrencyBal(account, crcy);
		TxLogCreatCmd txLogCreatCmd = TxLogCreatCmd.builder().account(account).crcy(crcy).txDate(todaysDate)
				.txDTime(time).memo(txnMemo).txAmt(addAmt).balance(aftBal).cdCode("存入").status("A").build();
		TxLogVo txLogVo = writeTxLog(txLogCreatCmd);

		FPAccountVo fpAccountVo = fpAccountVoMapper.toVo(fpcuster);
		fpAccountVo.setCrcyCode(crcy);
		fpAccountVo.setTxnLogId(txLogVo.getId());
		return fpAccountVo;
	}

	// 支出：更新幣別餘額
	public FPAccountVo withdrawFpm(String account, String crcy, BigDecimal subAmt, String memo) {
		FPMaster fPMaster = null;
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(account);
		List<FPMaster> fpmArry = fpcuster.getFpmasters();
		for (FPMaster idx : fpmArry) {
			if (idx.getFpmCurrency().equals(crcy)) {
				fPMaster = idx;
				BigDecimal afterBal = idx.getFpmBalance().subtract(subAmt);
				fPMaster.setFpmBalance(afterBal);
			}
		}

		// 寫入交易明細
		String txnMemo;
		if (memo == null) {
			txnMemo = "轉帳支出";
		} else {
			txnMemo = memo;
		}
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		LocalDate todaysDate = LocalDate.now();
		BigDecimal aftBal = getByfpmCurrencyBal(account, crcy);
		TxLogCreatCmd txLogCreatCmd = TxLogCreatCmd.builder().account(account).crcy(crcy).txDate(todaysDate)
				.txDTime(time).memo(txnMemo).txAmt(subAmt).balance(aftBal).cdCode("支出").status("A").build();
		TxLogVo txLogVo = writeTxLog(txLogCreatCmd);
		
		FPAccountVo fpAccountVo = fpAccountVoMapper.toVo(fpcuster);
		fpAccountVo.setCrcyCode(crcy);
		fpAccountVo.setTxnLogId(txLogVo.getId());
		return fpAccountVo;
	}

	// 沖正：輸入txLog_id更新幣別餘額
	public void undoFpm(Long id) {
		TxLog txLog = txLogRepository.getById(id);
		txLog.setStatus("X");
		FPAccountVo fpAccountVo;
		if (txLog.getCdCode().equals("存入")) {
			fpAccountVo = withdrawFpm(txLog.getAccount(), txLog.getCrcy(), txLog.getTxAmt(), "沖正存入");
		} else {
			fpAccountVo = depositFpm(txLog.getAccount(), txLog.getCrcy(), txLog.getTxAmt(), "沖正支出");	
		}
		TxLog txLogVo = txLogRepository.getById(fpAccountVo.getTxnLogId());
		txLogVo.setStatus("D");
	}

	// 新增帳號資訊
	public FPAccountVo createFpc(FPAccountCreateCmd createCmd) {
		log.info("createCmd: {}", createCmd);

		FPCuster fpcentity = fpAccountVoMapper.toEntity(createCmd);
		fpcentity.setFpcStatus("00");
		fpcentity.setValidCrcyCnt(0);
//		fpAccountVoMapper等同以下註解!!!
// 		FPCuster fpcentity = new FPCuster();
//		fpcentity.setFpcAccount(createCmd.getAccountNo());
//		fpcentity.setFpcCustomerId(createCmd.getCustomerIdno());
//		fpcentity.setBookType(String.valueOf(createCmd.getBookType()));
		fPCustomerRepository.save(fpcentity);
		log.info("fpcentity: {}", fpcentity);

		FPAccountVo vo = fpAccountVoMapper.toVo(fpcentity);
//		fpAccountVoMapper等同以下註解!!!
//		FPAccountVo vo = new FPAccountVo();
//		vo.setId(fpcentity.getId());
//		vo.setAccountNo(fpcentity.getFpcAccount());
//		vo.setCustomerIdno(fpcentity.getFpcCustomerId());
//		vo.setStatus(fpcentity.getFpcStatus());
//		vo.setValidCrcyCnt(fpcentity.getFpcValidCrcyCnt());
		log.info("FPAccountVo: {}", vo);

		return vo;
	}

	// 新增幣別資訊
	public FPAccountVo createFpm(FPAccountCreateCmd createCmd) {
		log.info("createCmd: {}", createCmd);
		FPCuster fpcuster = fPCustomerRepository.findByfpcAccount(createCmd.getAccountNo());
		FPMaster fpMaster = new FPMaster();
		fpMaster.setFpmCurrency(String.valueOf(createCmd.getCrcyCode()));
		fpMaster.setFpmStatus("00");
		fpMaster.setFpmBalance(new BigDecimal(0.00));

		if (fpcuster.getFpmasters() == null) {
			List<FPMaster> fpmArry = new ArrayList<FPMaster>();
			fpmArry.add(fpMaster);
			fpcuster.setFpmasters(fpmArry);
		} else {
			List<FPMaster> fpmArry = fpcuster.getFpmasters();
			fpmArry.add(fpMaster);
			fpcuster.setFpmasters(fpmArry);
		}

		fpcuster.setValidCrcyCnt(fpcuster.getValidCrcyCnt() + 1);
		fPCustomerRepository.save(fpcuster);
		log.info("fpcentity: {}", fpcuster);

		FPAccountVo vo = fpAccountVoMapper.toVo(fpcuster);
		log.info("FPAccountVo: {}", vo);

		return vo;
	}

	// 新增交易明細
	public TxLogVo writeTxLog(TxLogCreatCmd createCmd) {
		log.info("createCmd: {}", createCmd);

		TxLog txLog = fpTxLogtVoMapper.toEntity(createCmd);
		txLogRepository.save(txLog);
		log.info("txLog: {}", createCmd);
		
		TxLogVo vo = fpTxLogtVoMapper.toVo(txLog);
		return vo;

	}

}
