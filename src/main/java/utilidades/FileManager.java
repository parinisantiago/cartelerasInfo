package utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class FileManager {

	public static final String multimediaDir = "/home/agustin/workspace/cartelerasInfo/src/main/webapp/";
	public static final String ImagenesDir = multimediaDir + "img/";
	public static final String ImagenesAnuncioDir = ImagenesDir + "upload/";
	public static final String ImagenesPerfilDir = ImagenesDir + "perfil/";
	
	public static String getDefaultName(MultipartFile fileInput){
        String name = fileInput.getOriginalFilename(); 
        String ext = name.substring(name.lastIndexOf("."),name.length()); 
        String fileName = System.currentTimeMillis()+ext;
        return fileName; 
	}
	
	public static String getExtension(String name){
		return name.substring(name.lastIndexOf("."),name.length()).replaceAll(".", "");
	}
	
	public File getFile(String path){
		return new File(path);
	}
	
	public File getImageAnuncio(String nombre){
		return this.getFile(FileManager.ImagenesAnuncioDir+nombre);
	}
	
	public String save(MultipartFile fileInput, String dirPath, String fileName) throws IOException{
		byte[] bytes = fileInput.getBytes(); 

        String rpath = dirPath ;
        File file = new File(rpath);
        if(!file.exists()){ 
        	file.mkdir();
        } 

        File temp = new File(file,fileName); 

        FileOutputStream fos = new FileOutputStream(temp); 
        fos.write(bytes); 
        fos.close();
        
        return fileName;
	}
	
	public Boolean delete(String filePath){
			try{
				File file = new File(filePath);
				return file.delete();
			}
			catch (Exception e) {
				return false;
			}
	}
	
	public String saveImagenAnuncio(MultipartFile fileInput) throws IOException{
		return this.save(fileInput, FileManager.ImagenesAnuncioDir , FileManager.getDefaultName(fileInput));
	}

	public String saveImagenAnuncio(MultipartFile fileInput, String fileName) throws IOException{
		return this.save(fileInput, FileManager.ImagenesAnuncioDir , fileName);
	}
	
	public Boolean deleteImagenAnuncio(String fileName){
		return this.delete(FileManager.ImagenesAnuncioDir + fileName);
	}
	
	public String saveImagenPerfil(MultipartFile fileInput) throws IOException{
		return this.save(fileInput, FileManager.ImagenesPerfilDir , FileManager.getDefaultName(fileInput));
	}

	public String saveImagenPerfil(MultipartFile fileInput, String fileName) throws IOException{
		return this.save(fileInput, FileManager.ImagenesPerfilDir , fileName);
	}
	
	public Boolean deleteImagenPerfil(String fileName){
		return this.delete(FileManager.ImagenesPerfilDir + fileName);
	}
}
