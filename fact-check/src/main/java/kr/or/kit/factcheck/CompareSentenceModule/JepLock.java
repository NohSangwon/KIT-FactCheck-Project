package kr.or.kit.factcheck.CompareSentenceModule;


public class JepLock {

	private static class LazyHolder {
		public static final JepLock INSTANCE = new JepLock();
	}

	public static JepLock getInstance() {
		return LazyHolder.INSTANCE;
	}
}
