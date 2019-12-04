package com.coutomariel.wallet.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	
	
}
