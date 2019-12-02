package com.coutomariel.wallet.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coutomariel.wallet.entity.Wallet;
import com.coutomariel.wallet.entity.WalletItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletItemRepositoryTest {

	private static final Date DATE = new Date();
	private static final String DESCRIPTION = "Conta de luz";
	private static final String TYPE = "EN";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	
	
	@Autowired
	private WalletItemRepository walletItemrespository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Test
	public void testSave() {
		Wallet wallet = new Wallet();
		wallet.setName("Carteira 1");
		wallet.setValue(BigDecimal.valueOf(1500));
		walletRepository.save(wallet);
		WalletItem wi = new WalletItem(1L, wallet, DATE, DESCRIPTION, TYPE, VALUE);
		
		WalletItem response = walletItemrespository.save(wi);
		
		assertNotNull(response);
		assertEquals(response.getDescription(),DESCRIPTION);
		assertEquals(response.getType(),TYPE);
		assertEquals(response.getValue(),VALUE);
		assertEquals(response.getWallet().getId(), wallet.getId());
		
	}
}
