package com.coutomariel.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.coutomariel.wallet.entity.WalletItem;
import com.coutomariel.wallet.repository.WalletItemRepository;
import com.coutomariel.wallet.service.WalletItemService;
import com.coutomariel.wallet.util.enums.TypeEnum;

@Service
public class WalletItemServiceImpl implements WalletItemService{

	@Autowired
	private WalletItemRepository repository;
	
	@Value("${pagination.items_per_page}")
	private int itemsPerPage;
	
	@Override
	@CacheEvict(value = "findByWalletAndType", allEntries = true)
	public WalletItem save(WalletItem walletItem) {
		return repository.save(walletItem);
	}
	
	@Override
	public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page) {
		PageRequest pg = PageRequest.of(page, itemsPerPage);
		return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, pg);
	}
	
	@Override
	public List<WalletItem> findByWalletAndType(Long wallet, TypeEnum type) {
		return repository.findByWalletIdAndType(wallet,type);
	}

	@Override
	public BigDecimal sumByWalletId(Long wallet) {
		return repository.sumByWalletId(wallet);
	}

	@Override
	public Optional<WalletItem> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
