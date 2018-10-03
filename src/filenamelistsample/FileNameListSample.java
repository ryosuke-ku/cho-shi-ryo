package filenamelistsample;

public class FileNameListSample {

	public static void main(String[] args){
		String dir_path = "C:\\pbl";  //検索開始したいフォルダのPath
		String extension = "Test.java";   //検索したいTestファイルの拡張子(今回は"<クラス名+Test>"をテストコードとする)

		File_search fs = new File_search();
		String data[];

		data = fs.file_search(dir_path, extension) ;
		for(int i=0;i<10;i++){
			if(data[i]!=null) {
				System.out.println(data[i]);

			}
		}

	}
}


