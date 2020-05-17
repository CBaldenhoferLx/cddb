package com.luxoft.cddb.beans.master;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.luxoft.cddb.beans.Identifyable;
import com.luxoft.cddb.beans.JpaConstants;
import com.luxoft.cddb.beans.structure.FeatureSetBean;
import com.luxoft.cddb.beans.user.UserBean;

@Entity
@Table(name="cd_elements")
public class ElementBean extends Identifyable {
	
	enum ElementStatus {
		NEW,
		DELETED,
		CHANGED,
		SYNCED,
		DRAFT,
		TAKEOVER
	}
	
	enum ProjectPhase {
		REFINED_CONCEPT,
		UI_DOCU,
		OUTDATED
	}
	
	enum IssueType {
		SCREEN,
		ELEMENT,
		CONTAINER,
		CONTAINER_1
	}
	
	@ManyToOne(optional = false)
	private FeatureSetBean featureSet;
	
	@Column(length = JpaConstants.TEXT_SMALL)
	private String uniqueId;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private ElementStatus status;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private ProjectPhase projectPhase;
	
	@ManyToMany
	private List<TicketRefBean> ticketRefs = new ArrayList<>();

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private IssueType issueType;
	
	@ManyToOne(optional = false)
	private ScreenBean screen;

	@ManyToOne(optional = true)
	private WidgetClassBean widgetClass;
	
	@Column(length = JpaConstants.TEXT_BIG)
	private String comments;
	
	@ManyToOne(optional = true)
	private UserBean commentsByUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentsTs;
	
	@Column(nullable = true)
	private int tag;
	
	@Column(columnDefinition = "text")
	private String visibility;
	
	@Column(columnDefinition = "text")
	private String buttonState;
	
	@Column(columnDefinition = "text")
	private String actions;
	
	@ManyToOne(optional = true)
	private ScreenBean transitionToScreen;
	
	@ManyToMany
	private List<DeviceTaggingBean> deviceTaggingBeans = new ArrayList<>();
	
	@Column(columnDefinition = "text", nullable = true)
	private String textEngEnglish;
	
	@Column(nullable = true)
	private int mkId;
	
	@Column(length = JpaConstants.TEXT_MID)
	private String semanticMeaning;
	
	@Column(columnDefinition = "text", nullable = true)
	private String if1;	
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	

}
