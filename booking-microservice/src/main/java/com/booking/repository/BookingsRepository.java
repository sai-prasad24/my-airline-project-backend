package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.models.Bookings;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long>
{
	

	List<Bookings> findAllByUserName(String userName);

}
