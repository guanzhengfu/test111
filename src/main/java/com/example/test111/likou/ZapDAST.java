package com.example.test111.likou;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;

public class ZapDAST {
  private static final String ZAP_ADDRESS = "localhost";
  private static final int ZAP_PORT = 8090;
  private static final String ZAP_API_KEY = null; // Change this if you have set the apikey in ZAP via Options / API

  private static final String TARGET = "http://121.4.253.173:3000";

  public static void main(String[] args) {
    ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

    try {
      // Start spidering the target
      System.out.println("Spider : " + TARGET);
      // It's not necessary to pass the ZAP API key again, already set when creating the
      // ClientApi.
      ApiResponse resp = api.spider.scan(TARGET, null, null, null, null);
      String scanid;
      int progress;

      // The scan now returns a scan id to support concurrent scanning
      scanid = ((ApiResponseElement) resp).getValue();

      // Poll the status until it completes
      while (true) {
        Thread.sleep(1000);
        progress =
            Integer.parseInt(
                ((ApiResponseElement) api.spider.status(scanid)).getValue());
        System.out.println("Spider progress : " + progress + "%");
        if (progress >= 100) {
          break;
        }
      }
      System.out.println("Spider complete");

      // Give the passive scanner a chance to complete
      Thread.sleep(2000);

      System.out.println("Active scan : " + TARGET);
      resp = api.ascan.scan(TARGET, "True", "False", null, null, null);

      // The scan now returns a scan id to support concurrent scanning
      scanid = ((ApiResponseElement) resp).getValue();

      // Poll the status until it completes
      while (true) {
        Thread.sleep(5000);
        progress =
            Integer.parseInt(
                ((ApiResponseElement) api.ascan.status(scanid)).getValue());
        System.out.println("Active Scan progress : " + progress + "%");
        if (progress >= 100) {
          break;
        }
      }
      System.out.println("Active Scan complete");

      System.out.println("Alerts:");
      System.out.println(new String(api.core.htmlreport(), StandardCharsets.UTF_8));
      try(FileWriter fileWriter = new FileWriter("report.html")) {
        fileWriter.write(new String(api.core.htmlreport(), StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      System.out.println("Exception : " + e.getMessage());
      e.printStackTrace();
    }
  }
}
