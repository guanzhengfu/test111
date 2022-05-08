//package com.example.test111.file;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.UUID;
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//public class 文件压缩 {
//  /**
//   * @Description:压缩上传
//   * @Author: duyaqiong
//   * @Date: 2020/9/19 14:53
//   * @param imageFile
//   * @param request
//   **/
//  private ResultJson compress(MultipartFile imageFile, HttpServletRequest request, Long size) {
//    ResultJson result = new ResultJson();
//    String rootPath = request.getSession().getServletContext().getRealPath("/");
//    String fileName = UUID.randomUUID().toString().replace("-", "") + "." + FilenameUtils.getExtension(imageFile.getOriginalFilename());
//    String trueFileName = "/" + fileName;
//    String path = rootPath + trueFileName;
//    try {
//      LogUtil.log("********************图片压缩开始******************", LOG_NAME);
//      LogUtil.log("原图片:"+imageFile.getOriginalFilename() + ",大小:" + imageFile.getSize()/1024 + "kb", LOG_NAME);
//      File beforeFile = new File(path);
//      //生成目标图片
//      Thumbnails.of(imageFile.getInputStream()).scale(1f).toFile(beforeFile);
//      //压缩图片至指定大小下
//      commpressPicCycle(path,size,0.8);
//      File afterFile = new File(path);
//      LogUtil.log("目标图片:" + path + ",大小" + afterFile.length()/1024 + "kb", LOG_NAME);
//      LogUtil.log("********************图片压缩完成******************", LOG_NAME);
//      InputStream inputStream = new FileInputStream(afterFile);
//      ossUpload(result, imageFile.getOriginalFilename(), inputStream,type);
//    } catch (Exception e) {
//      e.printStackTrace();
//      result.setMess("上传异常!");
//    }
//    return result;
//  }
//
//  /**
//   * @Description:压缩文件至指定大小
//   * @Author: duyaqiong
//   * @Date: 2020/9/19 16:08
//   * @param desPath
//   * @param desFileSize
//   * @param accuracy
//   * @Return: void
//   **/
//  private static void commpressPicCycle(String desPath, long desFileSize, double accuracy)
//      throws IOException {
//    File srcFileJPG = new File(desPath);
//    //如果小于指定大小不压缩；如果大于等于指定大小压缩
//    if (srcFileJPG.length() <= desFileSize) {
//      return;
//    }
//    // 计算宽高
//    BufferedImage bim = ImageIO.read(srcFileJPG);
//    int desWidth = new BigDecimal(bim.getWidth()).multiply(new BigDecimal(accuracy)).intValue();
//    int desHeight = new BigDecimal(bim.getHeight()).multiply(new BigDecimal(accuracy)).intValue();
//    Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
//    commpressPicCycle(desPath, desFileSize, accuracy);
//  }
//}
