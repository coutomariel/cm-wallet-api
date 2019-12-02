package com.coutomariel.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coutomariel.wallet.entity.WalletItem;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, Long>{

}