package com.pmovil.nativega.impl;


/**
 *  This is a demo class to help you get started building a library
 * 
 *  @author Your name here
 */
public interface GANativeInterface extends com.codename1.system.NativeInterface {

	public boolean getAppOptOut();

	public boolean isDryRunEnabled();

	public void setAppOptOut(boolean optOut);

	public void setDryRun(boolean dryRun);

	public void setLocalDispatchPeriod(int dispatchPeriodInSeconds);

	public void dispatchLocalHits();
}
