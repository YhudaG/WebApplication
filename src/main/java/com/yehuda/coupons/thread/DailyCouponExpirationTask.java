package com.yehuda.coupons.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yehuda.coupons.exceptions.ApplicationException;
import com.yehuda.coupons.logic.CouponController;

@Component
public class DailyCouponExpirationTask extends Thread {
	
	@Autowired
	private CouponController couponController;
	private boolean quit = false;
	private static final int DAY_SLEEP = 1000 * 60 * 60 * 24;

	public DailyCouponExpirationTask() {
		super();
	}

	/**
	 * delete all coupons that there time is over from customer and from the coupons
	 * table.
	 */
	@Override
	public void run() {
		while (!quit) {
			try {
				couponController.deleteExpiredCoupons();
				Thread.sleep(DAY_SLEEP);
			} catch (ApplicationException | InterruptedException e) {
				// in the next version i will log the exception to file/data base
				e.printStackTrace();
			}
		}
	}

	/**
	 * stop the thread of DailyCouponExpirationTask
	 * 
	 * @param thread
	 */
	public void stop(Thread thread) {
		quit = true;
		thread.interrupt();

	}

}
