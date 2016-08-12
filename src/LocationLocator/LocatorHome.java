package LocationLocator;

public class LocatorHome {

	private static ILocator locator = null;

    public synchronized static ILocator getLocator() {
        if (locator == null) {
            locator = new KnnLocator();
        }

        return locator;
    }

    private LocatorHome() {
    }
}
