package com.pie.pieProject.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageUploadController {
	public static String UPLOAD_DIRECTORY2 = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\imgs\\test";

	@PostMapping("/uploadSummernoteImageFile")
	@ResponseBody
	public JSONObject uploadImage(Model model, @RequestParam("file") MultipartFile file, Object msg)
			throws IOException {
		JSONObject obj = new JSONObject();
//		System.out.println(UPLOAD_DIRECTORY_Edit);
		StringBuilder fileNames = null;
		UUID uuidOne = UUID.randomUUID();

		try {
			fileNames = new StringBuilder();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY2, uuidOne + file.getOriginalFilename());
			// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
			fileNames.append(uuidOne + file.getOriginalFilename());
			byte[] fileSize = file.getBytes();

			Files.write(fileNameAndPath, fileSize);
			model.addAttribute("msg", fileNameAndPath);

			System.out.println("파일이 저장되는 경로 : " + fileNameAndPath);
			System.out.println("fileNames에서 얻은 이미지 파일명 : " + fileNames);
			System.out.println("모델에 저장한 메세지 : " + model.getAttribute("msg"));
			System.out.println("==============================================");
			obj.put("success", true);
			obj.put("업로드 결과", "성공");
			obj.put("url", "imgs/test/" + fileNames.toString());
			obj.put("파일 저장명", fileNameAndPath);
			obj.put("파일 용량", fileSize.length + "byte");
			model.addAttribute("fileName", fileNames);
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", false);
			obj.put("업로드 결과", "실패");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 이미지 폴더 새로고침 시간용 딜레이

		return obj;
//		return obj.toJSONString();
	}

	@PostMapping("/imageUploading")
	public ResponseEntity<String> imageuploadAction(@RequestParam(name = "attach_file", required = false) MultipartFile[] files) {

		StringBuilder fileData = new StringBuilder();
		UUID uuidOne = UUID.randomUUID();
		
		System.out.println(UPLOAD_DIRECTORY2);

		try {
			if (files!=null) {
				for (MultipartFile file : files) {
					System.out.println("Uploaded File Name: " + file.getOriginalFilename());
					StringBuilder fileNames = new StringBuilder();
					String newFileName = uuidOne + file.getOriginalFilename();
					Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY2, newFileName);
					// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
					fileNames.append(file.getOriginalFilename());
					byte[] fileSize = file.getBytes();
					Files.write(fileNameAndPath, fileSize);
					
	
					fileData.append(newFileName);
					fileData.append("/");
				}
			}else {
				fileData.append("proxy_title2-01.png/");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("업로드완료");
		return ResponseEntity.ok(fileData.toString());
	}

	@PostMapping("/imageUpdating")
	public ResponseEntity<String> imageupdateAction(
			@RequestParam(name = "attach_file", required = false) MultipartFile[] files,
			@RequestParam("original") String orinalFile) {

		StringBuilder fileData = new StringBuilder();
		UUID uuidOne = UUID.randomUUID();

		try {
			if (files != null) {
				for (MultipartFile file : files) {
					System.out.println("Uploaded File Name: " + file.getOriginalFilename());
					StringBuilder fileNames = new StringBuilder();
					String newFileName = uuidOne + file.getOriginalFilename();
					Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY2, newFileName);
					// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
					fileNames.append(file.getOriginalFilename());
					byte[] fileSize = file.getBytes();
					Files.write(fileNameAndPath, fileSize);
					System.out.println(fileNames + "업로드완료");

					fileData.append(newFileName);
					fileData.append("/");
				}
			}
			fileData.insert(0, orinalFile);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return ResponseEntity.ok(fileData.toString());
	}

	private String[] setArraysData(String key, String wallWord) {
		String[] str_imgs = key.split(wallWord);
		for (String s : str_imgs) {
			s.replace(wallWord, "");
		}
		return str_imgs;
	}

}

/*
 * @PostMapping("/imageUpload") public void
 * uploadTestPOST(@RequestParam(value="boardImage", required=false)
 * MultipartFile[] uploadFile) {
 * 
 * String uploadFolder = "C:\\upload";
 * 
 * 추가된 부분 ......... SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
 * Date date = new Date(); String formatDate = sdt.format(date);
 * 
 * String datePath = formatDate.replace("-", File.separator);
 * 
 * File uploadPath = new File(uploadFolder, datePath);
 * 
 * if (uploadPath.exists() == false) { uploadPath.mkdirs(); } ..........
 * 
 * for (MultipartFile multipartFile : uploadFile) {
 * 
 * String uploadFileName = multipartFile.getOriginalFilename();
 * 
 * 변경 위치 ............. String uuid = UUID.randomUUID().toString();
 * uploadFileName = uuid + "_" + uploadFileName;
 * 
 * File saveFile = new File(uploadPath, uploadFileName); .................
 * 
 * try { multipartFile.transferTo(saveFile); } catch (Exception e) {
 * e.printStackTrace(); } } }
 * 
 * @ResponseBody
 * 
 * @GetMapping("/display") public ResponseEntity<byte[]> showImageGET(
 * 
 * @RequestParam("fileName") String fileName ) { File file = new
 * File("C:\\upload\\" + fileName);
 * 
 * ResponseEntity<byte[]> result = null;
 * 
 * try {
 * 
 * HttpHeaders header = new HttpHeaders();
 * 
 * 
 * Files.probeContentType() 해당 파일의 Content 타입을 인식(image, text/plain ...) 없으면
 * null 반환
 * 
 * file.toPath() -> file 객체를 Path객체로 변환
 * 
 * 
 * header.add("Content-type", Files.probeContentType(file.toPath()));
 * 
 * result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header,
 * HttpStatus.OK);
 * 
 * } catch (IOException e) { e.printStackTrace(); }
 * 
 * return result; }
 */
