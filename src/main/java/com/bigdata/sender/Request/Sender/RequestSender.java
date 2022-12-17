package com.bigdata.sender.Request.Sender;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestSender {
	
	@Value("${kmessage.url}")
    private String endpointUrl;
	
	public void sendRequest(int numberOfRequest, int sleepTime) {
		endpointUrl = "http://localhost:8080/kmessage";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(endpointUrl);
		Gson gson = new Gson();
		for (int i = 0; i < numberOfRequest; i++) {
			// Random sınıfını kullanarak rasgele bir nesne
			Random random = new Random();

			// -10 ile 50 arasında bir sayı üretir
			int randomNumber = random.nextInt(61) - 10;
			Message message = new Message(String.valueOf(randomNumber));

			String messageJson = gson.toJson(message);

			try {
				// İstek body'sine veriyi ekleyin
				StringEntity requestEntity = new StringEntity(messageJson);
				request.setEntity(requestEntity);
				request.setHeader("Content-type", "application/json");
				// REST çağrısını yapın ve cevabı alın
				HttpResponse response = httpClient.execute(request);

				// Cevabı loglayın
				log.info("Response Status: {}", response.getStatusLine().getStatusCode());
				log.info("Response Body: {}", EntityUtils.toString(response.getEntity()));
			} catch (IOException e) {
				log.error("Error making REST call", e);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				log.error("An Error Accurd while sleep Error: {}", e.getMessage());
			}
		}
	}
}
