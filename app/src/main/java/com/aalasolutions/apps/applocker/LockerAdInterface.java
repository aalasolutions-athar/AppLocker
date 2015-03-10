package com.aalasolutions.apps.applocker;


import com.aalasolutions.apps.ads.AdInterface;

public class LockerAdInterface extends AdInterface {

	@Override
	public String getBannerAdUnitId() {
		return null;
	}

	@Override
	public String getInterstitialAdUnitId() {
		return "ca-app-pub-1709767846664941/9982843304";
	}

	@Override
	public String[] getTestDevices() {
		return new String[] { "355033051433847" };
	}

}
