package info.haxahaxa.astparser.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import info.haxahaxa.astparser.sample.visitor.SampleVisitor1;
import info.haxahaxa.astparser.util.Envs;
import info.haxahaxa.astparser.util.SourceFile;


/**
 * クラス名やフィールド，メソッドの概要を表示するサンプル
 *
 * @author satanabe1
 *
 */
public class Sample1 {
	private static ASTVisitor visitor = new SampleVisitor1();

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		String dir_path = "C:\\pbl";  //検索開始したいフォルダのPath
		String extension = "Test.java";   //検索したいTestファイルの拡張子(今回は"<クラス名+Test>"をテストコードとする)

		String data[];
		data = file_search(dir_path, extension);

		Sample1 s = new Sample1();


		for(int i=0;i<data.length;i++) {

			System.out.println("テストファイルのPath["+i+ "]--> "+ data[i]);

			FileReader f = null;
			f = new FileReader(data[i]);
			s.loadJavaFile(f);

			SourceFile sourceFile = new SourceFile(data[i]);
			CompilationUnit unit;
			ASTParser astParser = ASTParser.newParser(AST.JLS4);
			// 以下の setBindingsRecovery setStatementsRecovery はおまじない．
			// 完成しているソースコードを解析する時は呼ぶ必要ない．
			// 詳しく知りたいならば，IMBのASTParser関連のドキュメントとかを参照すべき．
			astParser.setBindingsRecovery(true);
			astParser.setStatementsRecovery(true);
			// 次の setResolveBindings と setEnvironment が重要！！
			// setResolveBindings(true) をしておかないとまともに解析はできない．
			// setResolveBindings をまともに機能させるために setEnvironment が必要．
			astParser.setResolveBindings(true);
			// setEnvironment の第一引数にはクラスパスの配列．第二引数にはソースコードを検索するパスの配列
			// 第三第四については何も考えず null, true ．納得いかない時はIBMのASTPa...
			astParser.setEnvironment(Envs.getClassPath(), Envs.getSourcePath(),
					null, true);

			// 解析対象のソースコードの入力とか
			astParser.setUnitName(sourceFile.getFilePath());// なんでもいいから名前を設定しておく
			astParser.setSource(sourceFile.getSourceCode().toCharArray());// 解析対象コードを設定する
			unit = (CompilationUnit) astParser.createAST(new NullProgressMonitor());
			unit.recordModifications();// ASTへの操作履歴のようなものを有効に
			// 解析実行
			unit.accept(visitor);

			System.out.println("\n");

		}
	}


	public void loadJavaFile(FileReader file){


		try(BufferedReader in = new BufferedReader(file)){
			String code_s;

			System.out.println("--------↓↓入力ファイルのソースコード↓↓--------------------------------------------------------------"+"\n");
			//最後の一行まで読み込む
			while((code_s = in.readLine()) != null){
				System.out.println(code_s);
			}

			System.out.println("--------↑↑入力ファイルのソースコード↑↑-------------------------------------------------------------"+"\n");


			System.out.println("--------↓↓ここから解析結果↓↓-------------------------------------------------------------");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String[] FilePath = new String[1000];
	static int k=0;

	public static String[] file_search(String path, String extension){
		File dir = new File(path);
		File files[] = dir.listFiles();


		for(int i=0; i<files.length; i++){

			String file_name = files[i].getName();
			if(file_name.endsWith(extension)){  //file_nameの最後尾(拡張子)が指定のものならば出力
				FilePath[k] = path+"/"+file_name;
				k++;
			}else if(files[i].isDirectory()){  //ディレクトリなら再帰を行う
				file_search(path+"/"+file_name, extension);
			}
		}
		return FilePath;
	}
}


