package filenamelistsample;

import java.io.File;
import java.util.ArrayList;

public class file_control {

	public static void main(String... args) throws Exception {


		listfiles("C:\\pbl", "Test.java", new ArrayList<File>());

	}


		public static ArrayList<File> listfiles(final String searchDir, final String prefix, final ArrayList<File> arrayList) {

		final File[] files = new File(searchDir).listFiles();

		for (final File file : files) {
		if(file.isDirectory()) {
		// ディレクトリのとき、再帰検索
		listfiles(file.getPath(), prefix, arrayList);

		} else if(file.getName().endsWith(prefix)) {
		// 一致したファイルのとき、追加
		System.out.println(file.getPath());
		//fileList.add(file);
		}
		}
		return arrayList;
		}
}
