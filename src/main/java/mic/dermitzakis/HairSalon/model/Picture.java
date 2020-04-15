/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mic.dermitzakis.HairSalon.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritablePixelFormat;
import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author mderm
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Component
@Scope("prototype")
//@Table(name = "appointment")

public class Picture implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    long pictureId; 
   
    @Column(columnDefinition = "BLOB")
    byte[] image;
    
    public static Image toImage(byte[] buffer){
        InputStream stream = new ByteArrayInputStream(buffer);
        Image img = new Image(stream);
        return img;
    }
    
    public static byte[] toByteArray(Image img) throws IOException{
        String base64String;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        URI uri = URI.create(img.getUrl());
        BufferedImage bufferedImage = ImageIO.read(new File(uri));
        ImageIO.write(bufferedImage, "jpg", outputStream);
        outputStream.flush();
        base64String = Base64.encodeBase64String(outputStream.toByteArray());
        
        byte[] byteArray = Base64.decodeBase64(base64String);
        return byteArray;
    }
    
}
