package cn.acyou.iblog.utility;

public class RandomNum {


	/**
	 * 生成随机数字
	 * @param length 长度
	 * @return result
	 */
	public static String createRandom(int length) {
		return RandomNum.createRandom(true, length);
	}
	/**
	 * 
	 * @param numberFlag 是否数字
	 * @param length 长度
	 * @return result
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890"
				: "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}
	
	public static void main(String[] args){
		String num = createRandom(true,4);
		System.out.println(num);
	}
}
