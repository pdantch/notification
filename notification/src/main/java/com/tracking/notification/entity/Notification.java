package com.tracking.notification.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ntf_id")
	private Long id;

	@Column(name = "ntf_cli_id")
	private Long clienteId;

	@Column(name = "ntf_log_id")
	private Long loginId;

	@Column(name = "ntf_tag_id")
	private Long targetId;

	@Column(name = "ntf_dev_id")
	private Long deviceId;

	@Column(name = "ntf_message")
	private String message;

	@Column(name = "ntf_title")
	private String title;

	@Column(name = "ntf_token_device", columnDefinition = "TEXT")
	private String token;

	@Column(name = "ntf_topic")
	private String topic;

	@Column(name = "ntf_date_millis")
	private Long dateMillis;

	@Column(name = "ntf_type_id")
	private Integer typeId;

	@Column(name = "ntf_isread")
	private boolean isRead;

	@Column(name = "ntf_speed_limit")
	private Integer speedLimit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ntf_dt_create", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = true)
	private Date dateCreate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ntf_dt_delete")
	private Date dateDelete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ntf_dt_sended")
	private Date dateSended;

	@Column(name = "ntf_log_id_delete")
	private Long loginIdDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Long getDateMillis() {
		return dateMillis;
	}

	public void setDateMillis(Long dateMillis) {
		this.dateMillis = dateMillis;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Integer getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(Integer speedLimit) {
		this.speedLimit = speedLimit;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateDelete() {
		return dateDelete;
	}

	public void setDateDelete(Date dateDelete) {
		this.dateDelete = dateDelete;
	}

	public Long getLoginIdDelete() {
		return loginIdDelete;
	}

	public void setLoginIdDelete(Long loginIdDelete) {
		this.loginIdDelete = loginIdDelete;
	}

	public Date getDateSended() {
		return dateSended;
	}

	public void setDateSended(Date dateSended) {
		this.dateSended = dateSended;
	}

}
