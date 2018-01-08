package de.markuskfrank.cryptocur.main.bussineslogic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BackgroundUpdate extends Thread{

	private final boolean requestStop = false;
	private final MainControler controler;
	private static final int UPDATE_INTERVALL = 60*5*1000;
	private final Logger logger = Logger.getLogger("BackroundUpdate");
	
	public BackgroundUpdate(MainControler controler) {
		this.controler = controler;
	}
	
	@Override
	public void run() {
		super.run();
		while(!requestStop){
			try {
				logger.log(Level.INFO, "Sleep");
				Thread.sleep(UPDATE_INTERVALL);
				logger.log(Level.INFO, "Start update");
				controler.updateCurrencys();
				logger.log(Level.INFO, "Update finished");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
