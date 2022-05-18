package AssetManagement.Asset.controller;

import AssetManagement.Asset.entity.Assign;
import AssetManagement.Asset.entity.Staff;
import AssetManagement.Asset.repository.StaffRepo;
import AssetManagement.Asset.services.WebService;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.View;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


@Controller
public class WebController {

    final Logger logger = LoggerFactory.getLogger(WebService.class);

    @Autowired
    WebService webService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user" , new User("sattyda" , "sattyda@gmail.com") );


        List<User> users = new ArrayList<User>();
        users.add( new User("sattyda" , "sattyda@gmail.com") );
        users.add( new User("fattyda" , "fattyda@gmail.com") );
        users.add( new User("rattyda" , "rattyda@gmail.com") );
        users.add( new User("wattyda" , "wattyda@gmail.com") );

        model.addAttribute("users" ,  users );

        List<Staff> staffs =  webService.getAllStaff();

        model.addAttribute("staffs" ,  staffs );

        return "form";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("applicationName" , "Asset Management");
        model.addAttribute("errorMessage" , null);
        model.addAttribute("empty" , new ArrayList() );


        return "register";
    }

    @GetMapping("/form")
    public String upload(Model model) {

        model.addAttribute("applicationName" , "THis is TEst");

        return "form";
    }

    @PostMapping("/submit")
    public String submit(@Valid Staff staff , BindingResult binding , Model model) {
        if(binding.hasErrors())
        {
            logger.info(binding.toString());
            model.addAttribute("errorMessage", binding);
            return "register";
        }else {
            String sdsd = encrypt( "sattyda" , staff.getPassword());
            logger.info(sdsd);
            logger.info(decrypt( sdsd , staff.getPassword() ));

//            staff.setMobile(decrypt( "sattyda" , staff.getPassword() ));

            webService.saveStaff(staff);
            return "submit";
        }
    }


    @PostMapping("/loginverify")
    public String loginverify(Model model, @RequestParam("email") String email, @RequestParam("password") String password ) {


        Staff staff = webService.verifyStaff(email, password);

        model.addAttribute("name" , staff.getName());
        model.addAttribute("mobile" , staff.getMobile());
        model.addAttribute("staffId" , staff.getStaffId());

        return "loginverify";
    }


    @PostMapping("/save")
    public String save(@RequestParam("myfile") MultipartFile file  ) throws IOException {

        String mylocation = System.getProperty("user.dir") + "/src/main/resources/static/";
        String filename = file.getOriginalFilename();

        File mySavedFile = new File( mylocation + filename);

        InputStream inputStream = file.getInputStream();

        OutputStream outputStream = new FileOutputStream(mySavedFile);

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1){
            outputStream.write(bytes , 0 , read);
        }

        String mylink = "http://localhost:9090/" + filename;

        return "redirect:/";
    }

    public String encrypt(String str, String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedText = cipher.doFinal(str.getBytes("UTF-8"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(salt);
            outputStream.write(iv);
            outputStream.write(encryptedText);
            return DatatypeConverter.printBase64Binary(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String str, String password) {
        try {
            byte[] ciphertext = DatatypeConverter.parseBase64Binary(str);
            if (ciphertext.length < 48) {
                return null;
            }
            byte[] salt = Arrays.copyOfRange(ciphertext, 0, 16);
            byte[] iv = Arrays.copyOfRange(ciphertext, 16, 32);
            byte[] ct = Arrays.copyOfRange(ciphertext, 32, ciphertext.length);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(ct);

            return new String(plaintext, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

class User{
    public String name;
    public String email;
    User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

/*
Inversion of Control  ///  IoC Container
 */