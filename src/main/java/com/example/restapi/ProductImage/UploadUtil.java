package com.example.restapi.ProductImage;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class UploadUtil {

    @Value("${shop.upload.path}")
    private String uploadPath;

    //내 프로젝트에 upload라는 폴더가 없으면 생성하는 메서드
    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        String uploadPath = tempFolder.getAbsolutePath();

        log.info(uploadPath);
    }

    public List<String> upload(MultipartFile[] files) {
        List<String> result = new ArrayList<>();

        for (MultipartFile file : files) {
//            log.info("name: {}", file.getOriginalFilename());

            if (!file.getContentType() //실제 파일헤더가 이미지 파일인지 확인하고 업로드 수행
                     .startsWith("image")) {
                log.error("File type not supported: " + file.getContentType());
                continue;
            }
            String uuid = UUID.randomUUID()
                              .toString();
            String saveFileName = uuid + "_" + file.getOriginalFilename(); //파일 이름 중복 방지

            //try - with - resoure
            try (InputStream in = file.getInputStream();
                 OutputStream out = new FileOutputStream(uploadPath + File.separator + saveFileName)) {
                FileCopyUtils.copy(in, out); //파일복사도구 (in -> out 쓰기)

//                Thumbnails.of(new File(uploadPath + File.separator + saveFileName)) //썸네일 만들기 (이미지 리사이징)
//                          .size(200, 200)
//                          .toFile(uploadPath + File.separator + "s_" + saveFileName);

                result.add(saveFileName); //저장된 사진 이름을 리스트로 반환
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    //파일 삭제
    public void deleteFile(String fileName) {
        File file = new File(uploadPath + File.separator + fileName);
//        File thumbFile = new File(uploadPath + File.separator + "s_" + fileName);

        try {
            if (file.exists()) {
                file.delete();
            }
//            if (thumbFile.exists()) {
//                thumbFile.delete();
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }
}
