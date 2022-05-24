package com.tracking.notification.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tracking.notification.entity.Notification;
import com.tracking.notification.repository.NotificationRepository;

@Service
public class NotificationService {

	static Logger logger = LoggerFactory.getLogger(NotificationService.class);

	private static final String PROJECT_ID = "";
	private static final String BASE_URL = "https://fcm.googleapis.com";
	private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";

	private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	private static final String[] SCOPES = { MESSAGING_SCOPE };

	public static final String MESSAGE_KEY = "message";

	@Value("${app.firebase-configuration-file}")
	private String firebaseConfigPath;

	@Autowired
	private NotificationRepository repository;

	/**
	 * 
	 * Usado para envio de novas mensagens.
	 * 
	 */
	public void sentNewNotification() {
		repository.findAll().forEach(notification -> {
			validSendedMessage(notification);
		});
	}

	/**
	 * Verifica se há mensagens novas mensagens para enviar.
	 * 
	 * @param notification
	 */
	private void validSendedMessage(Notification notification) {
		if (notification.getDateSended() == null) {
			try {
				sendCommonMessage(notification);
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Formata mensagem para envio e aciona o metodo responsável por enviar
	 * mensagem.
	 * 
	 * @param notificationRequest
	 * @throws IOException
	 */
	private void sendCommonMessage(Notification notificationRequest) throws IOException {
		JsonObject notificationMessage = buildNotificationMessage(notificationRequest);
		logger.info("Corpo da solicitação FCM para mensagem usando objeto de notificação:");
		prettyPrint(notificationMessage);

		HttpURLConnection responseCode = sendMessage(notificationMessage);
		if (responseCode.getResponseCode() == 200) {
			notificationRequest.setDateSended(new Date());
			repository.save(notificationRequest);
			logger.info("Mensagem enviada ao Firebase para entrega, resposta:\n"
					+ inputstreamToString(responseCode.getInputStream()));
		} else {
			logger.error("Não foi possível enviar mensagem ao Firebase:\n"
					+ inputstreamToString(responseCode.getErrorStream()));
		}
	}

	/**
	 * Monta o corpo da mensagem de notificação.
	 * 
	 * @param notificationRequest
	 * @return
	 */
	private static JsonObject buildNotificationMessage(Notification notificationRequest) {
		JsonObject jNotification = new JsonObject();
		jNotification.addProperty("title", notificationRequest.getTitle());
		jNotification.addProperty("body", notificationRequest.getMessage());

		JsonObject jMessage = new JsonObject();
		jMessage.addProperty("token", notificationRequest.getToken());
		jMessage.add("notification", jNotification);

		JsonObject jFcm = new JsonObject();
		jFcm.add(MESSAGE_KEY, jMessage);

		return jFcm;
	}

	/**
	 * Apresenta a mensagem contida no objeto JsonObject formatada.
	 * 
	 * @param jsonObject
	 */
	private static void prettyPrint(JsonObject jsonObject) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.info("\n" + gson.toJson(jsonObject) + "\n");
	}

	/**
	 * Enviar solicitação para mensagem FCM usando HTTP. Encoded with UTF-8 and
	 * support special characters.
	 * 
	 * @param fcmMessage
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection sendMessage(JsonObject fcmMessage) throws IOException {
		HttpURLConnection connection = getConnection();
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(fcmMessage.toString());
		writer.flush();
		writer.close();

		return connection;
	}

	/**
	 * Cria HttpURLConnection que pode ser usado para recuperar e publicar
	 * notificações.
	 * 
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection getConnection() throws IOException {
		URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
		return httpURLConnection;
	}

	/**
	 * Recupera um token de acesso válido que possa ser usado para autorizar
	 * solicitações ao FCM REST API.
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getAccessToken() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(firebaseConfigPath))
				.createScoped(Arrays.asList(SCOPES));
		googleCredentials.refresh();
		return googleCredentials.getAccessToken().getTokenValue();
	}

	/**
	 * Converte o conteúdo do InputStream em String.
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static String inputstreamToString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}

}