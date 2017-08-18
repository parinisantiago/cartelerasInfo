package servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilidades.FileManager;

@WebServlet("/img/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        	String url = request.getRequestURI();
	        String urlImage = url.substring(url.lastIndexOf("img/")+4,url.length());
	        FileManager fm = new FileManager();
	        File file = fm.getFile(FileManager.ImagenesDir + urlImage);
	        response.setContentType("image/"+ FileManager.getExtension(file.getAbsolutePath()));
	        ServletOutputStream out;
	        out = response.getOutputStream();
        try{
	        FileInputStream fin = new FileInputStream(file);
	
	        BufferedInputStream bin = new BufferedInputStream(fin);
	        BufferedOutputStream bout = new BufferedOutputStream(out);
	        int ch = 0;
	        while ((ch = bin.read()) != -1) {
	            bout.write(ch);
	        }
	
	        bin.close();
	        fin.close();
	        bout.close();
	        out.close();
        }catch (IOException e) {
        	System.out.println("Imagen "+urlImage +" no encontrada");
        	out.close();
		}
    }
}
