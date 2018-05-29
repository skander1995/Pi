package com.pmovil.nativega;


/**
 * 
 *  @author ivan
 */
public class HitBuilders {

	public HitBuilders() {
	}

	public static interface class HitBuilderInterface {


		public void set(String paramName, String paramValue) {
		}

		public void setCampaignParamsFromUrl(String utmParams) {
		}

		public void setCustomDimension(int index, String dimension) {
		}

		public void setCustomMetric(int index, float metric) {
		}

		public void setNewSession() {
		}

		public void setNonInteraction(boolean nonInteraction) {
		}

		public String build() {
		}
	}

	protected static class HitBuilder {


		protected HitBuilders.HitBuilderInterface impl;

		protected HitBuilder() {
		}

		public Object set(String paramName, String paramValue) {
		}

		public Object setCampaignParamsFromUrl(String utmParams) {
		}

		public Object setCustomDimension(int index, String dimension) {
		}

		public Object setCustomMetric(int index, float metric) {
		}

		public Object setNewSession() {
		}

		public void setNonInteraction(boolean nonInteraction) {
		}

		public java.util.Map build() {
		}
	}

	public static class ScreenViewBuilder {


		public ScreenViewBuilder() {
		}
	}
}
