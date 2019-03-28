package com.yehuda.coupons.start_system;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yehuda.coupons.data_base.ConnectionPool;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;
import com.yehuda.coupons.thread.DailyCouponExpirationTask;

@Component
public class StartSystem {

	@Autowired
	DailyCouponExpirationTask dailyThread;

	@PostConstruct
	public void start() throws ApplicationException {

		try {
			// load the data base driver and full the connection pool
			ConnectionPool.getInstance();
			// start the daily coupon expiration thread.
			dailyThread.start();
			System.out.println("system started");
		} catch (ApplicationException e) {
			throw new ApplicationException(ErrorType.START_SYSTEM_ERROR);
		}

	}

	@PreDestroy
	public void stop() throws ApplicationException {

		// shut down the System by stopping the DailyCouponExpirationTask and closing
		// all connections of the ConnectionPool.
		try {
			dailyThread.stop(dailyThread);
			dailyThread.join();
			ConnectionPool.getInstance().closeAllConnections();
			System.out.println("system closed");
		} catch (ApplicationException | InterruptedException e) {
			throw new ApplicationException(ErrorType.STOP_SYSTEM_ERROR);
		}
	}
}
