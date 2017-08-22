package utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {
	
	public static final String baseDir = Paths.get("").toAbsolutePath() + "/cartelerasInfo/";
	
	public static final String multimediaDir = baseDir + "multimedia/";
	public static final String ImagenesDir = multimediaDir + "img/";
	public static final String ImagenesAnuncioDir = ImagenesDir + "upload/";
	public static final String ImagenesPerfilDir = ImagenesDir + "perfil/";
	
	public static final String ImagenesDirURL = "img/";
	public static final String ImagenesAnuncioDirURL = ImagenesDirURL + "upload/";
	public static final String ImagenesPerfilDirURL = ImagenesDirURL + "perfil/";
	
	public static final String defaultProfilePicURL = ImagenesPerfilDirURL +"default.png";
	private static final String defaultProfilePic = ImagenesPerfilDir+"default.png";
	
	private static boolean directoriosCreados = false;
	
	
	private static void downloadDefaultPic(){
		String urlString = "https://www.youngliving.com/vo/content/app/design/images/default-profile-image.jpg";
		try{
			InputStream in = new URL(urlString).openStream();
		    Files.copy(in, Paths.get(FileManager.defaultProfilePic));
		}
		catch (Exception e) {
			System.out.println("Error al descargar la imagen de perfil default");
		}
	}
	
	private static void createDirIfNotExist(File file){
		if(!file.exists()){ 
        	file.mkdir();
        }
	}
	
	private static void crearDirectorios(){
		if(!directoriosCreados){
			File file;
			file = new File(baseDir);
	        FileManager.createDirIfNotExist(file);
	        
	        file = new File(multimediaDir);
	        FileManager.createDirIfNotExist(file);
	        
	        file = new File(ImagenesDir);
	        FileManager.createDirIfNotExist(file);
			
	        file = new File(ImagenesAnuncioDir);
	        FileManager.createDirIfNotExist(file);
	        
	        file = new File(ImagenesPerfilDir);
	        FileManager.createDirIfNotExist(file);
	        
	        file = new File(defaultProfilePic);
	        if(!file.exists()){
	        	FileManager.downloadDefaultPic();
	        }
	        
			directoriosCreados = true;
		}
	}
	
	public static String getDefaultName(MultipartFile fileInput){
        String name = fileInput.getOriginalFilename(); 
        String ext = name.substring(name.lastIndexOf("."),name.length());
        Integer random = new Integer((int) (Math.random()*100));
        String fileName = System.currentTimeMillis()+"_"+random+ext;
        return fileName; 
	}
	
	public static String getExtension(String name){
		return name.substring(name.lastIndexOf("."),name.length()).replaceAll("\\.", "");
	}
	
	public FileManager(){
		FileManager.crearDirectorios();
	}
	
	public File getFile(String path){
		return new File(path);
	}
	
	public File getImageAnuncio(String nombre){
		return this.getFile(FileManager.ImagenesAnuncioDir+nombre);
	}
	
	public File getImagePerfil(Long id){
		File file = this.getFile(FileManager.ImagenesPerfilDir+id.toString());
		if(file == null){
			file = this.getFile(FileManager.defaultProfilePic);
		}
		return file;
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
	
	public String saveImagenPerfil(MultipartFile fileInput, String fileName) throws IOException{
		return this.save(fileInput, FileManager.ImagenesPerfilDir , fileName);
	}
	
	public Boolean deleteImagenPerfil(String fileName){
		return this.delete(FileManager.ImagenesPerfilDir + fileName);
	}
}
