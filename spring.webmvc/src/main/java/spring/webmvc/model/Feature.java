package spring.webmvc.model;

public enum Feature {
	SEEDSTARTER_SPECIFIC_SUBSTRATE("SEEDSTARTER_SPECIFIC_SUBSTRATE"), FERTILIZER("FERTILIZER"),
	PH_CORRECTOR("PH_CORRECTOR");
	public static final Feature[] ALL = { SEEDSTARTER_SPECIFIC_SUBSTRATE, FERTILIZER, PH_CORRECTOR };

	private String name;

	private Feature(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}

	public static Feature forName(final String name) {
		if (name == null) {
			return null;
		}

		if (name.toUpperCase().equals("SEEDSTARTER_SPECIFIC_SUBSTRATE")) {
			return SEEDSTARTER_SPECIFIC_SUBSTRATE;
		} else if (name.toUpperCase().equals("FERTILIZER")) {
			return FERTILIZER;
		} else if (name.toUpperCase().equals("PH_CORRECTOR")) {
			return PH_CORRECTOR;
		}
		return null;
	}
}
