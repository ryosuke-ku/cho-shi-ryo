package filenamelistsample;

import java.io.File;

public class File_search {

	static String[] FilePath = new String[1000];
	static int k=0;

	public static String[] file_search(String path, String extension){
		File dir = new File(path);
		File files[] = dir.listFiles();


		for(int i=0; i<files.length; i++){

			String file_name = files[i].getName();
			if(file_name.endsWith(extension)){  //file_nameの最後尾(拡張子)が指定のものならば出力
				// System.out.println(path+"/"+file_name);

				FilePath[k] = path+"/"+file_name;
				//System.out.println(FilePath[k]);
				k++;
			}else if(files[i].isDirectory()){  //ディレクトリなら再帰を行う
				file_search(path+"/"+file_name, extension);
			}
		}
		return FilePath;
	}
}
