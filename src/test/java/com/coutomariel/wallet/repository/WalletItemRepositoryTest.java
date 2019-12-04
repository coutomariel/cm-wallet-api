package com.coutomariel.wallet.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coutomariel.wallet.entity.Wallet;
import com.coutomariel.wallet.entity.WalletItem;
import com.coutomariel.wallet.util.enums.TypeEnum;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletItemRepositoryTest {

	private static final Date DATE = new Date();
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	private Long savedWalletItemId = null;
	private Long savedWalletId = null;
	
	@Autowired
	private WalletItemRepository walletItemRespository;
	
	@Autowired
	private WalletRepository walletRepository;

	@Before
	public void setUp() {
		Wallet wallet = new Wallet();
		wallet.setName("Carteira teste");
		wallet.setValue(BigDecimal.valueOf(250));
		walletRepository.save(wallet);
		
		WalletItem wi = new WalletItem(null, wallet, DATE, DESCRIPTION, TYPE, VALUE);
		walletItemRespository.save(wi);
		
		savedWalletId = wallet.getId();
		savedWalletItemId = wi.getId();
	}
	
	@After
	public void tearDown() {
		walletItemRespository.deleteAll();
		walletRepository.deleteAll();
	}
	
	@Test
	public void testSave() {
		Wallet wallet = new Wallet();
		wallet.setName("Carteira 1");
		wallet.setValue(BigDecimal.valueOf(1500));
		walletRepository.save(wallet);
		WalletItem wi = new WalletItem(1L, wallet, DATE, DESCRIPTION, TYPE, VALUE);
		
		WalletItem response = walletItemRespository.save(wi);
		
		assertNotNull(response);
		assertEquals(response.getDescription(),DESCRIPTION);
		assertEquals(response.getType(),TYPE);
		assertEquals(response.getValue(),VALUE);
		assertEquals(response.getWallet().getId(), wallet.getId());
		
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveInvalidWalletItem() {
		WalletItem wi = new WalletItem(null, null, DATE, null, TYPE, null);
		walletItemRespository.save(wi);
	}
	
	@Test
	public void testUpdate() {
		Optional<WalletItem> wi = walletItemRespository.findById(savedWalletItemId);
		
		String description = "Descrição alterada";
		
		WalletItem changed = wi.get();
		changed.setDescription(description);
		
		walletItemRespository.save(changed);
		Optional<WalletItem> newWalletItem = walletItemRespository.findById(savedWalletItemId);
		assertEquals(description, newWalletItem.get().getDescription());
	}

	@Test
	public void testDeleteWalletItem() {
		Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
		WalletItem wi = new WalletItem(null, wallet.get(), DATE, DESCRIPTION, TYPE, VALUE);
		
		walletItemRespository.save(wi);
		
		walletItemRespository.deleteById(wi.getId());
		
		Optional<WalletItem> response = walletItemRespository.findById(wi.getId());
		assertFalse(response.isPresent());
	}
	
	@Test
	public void testFindBetweenDates() {
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
		
		LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        
		walletItemRespository.save(new WalletItem(null, w.get(), currentDatePlusFiveDays,  DESCRIPTION, TYPE, VALUE));
		walletItemRespository.save(new WalletItem(null, w.get(), currentDatePlusSevenDays, DESCRIPTION, TYPE,  VALUE));
		
		PageRequest pg = PageRequest.of(0, 10);
		Page<WalletItem> response = walletItemRespository
				.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(
							savedWalletId, DATE, currentDatePlusFiveDays, pg);
		
		assertEquals(response.getContent().size(), 2);
		assertEquals(response.getTotalElements(), 2);
		assertEquals(response.getContent().get(0).getWallet().getId(), savedWalletId);
	}
	
	@Test
	public void testFindByType() {
		List<WalletItem> response = walletItemRespository.findByWalletIdAndType(savedWalletId, TYPE);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getType(), TYPE);
	}
	
	@Test
	public void testFindByTypeSd() {
		
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
		
		walletItemRespository.save(new WalletItem(null,w.get(),DATE,DESCRIPTION,TypeEnum.SD,VALUE));
		
		List<WalletItem> response = walletItemRespository.findByWalletIdAndType(savedWalletId, TypeEnum.SD);
		
		assertEquals(response.size(), 1);
		assertEquals(response.get(0).getType(), TypeEnum.SD);
	}
	
	@Test
	public void testSumByWallet() {
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
		
		walletItemRespository.save(new WalletItem(null, w.get(), DATE, DESCRIPTION, TYPE,  BigDecimal.valueOf(150.80)));
		
		BigDecimal response = walletItemRespository.sumByWalletId(savedWalletId);
		
		assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
	}
	
	
}
