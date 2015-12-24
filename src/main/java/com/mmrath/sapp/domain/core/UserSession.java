package com.mmrath.sapp.domain.core;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Murali
 * Date: 23/05/12
 */
@Entity
@Table(name = "t_user_session")
public class UserSession {

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "start_time", nullable = false, updatable = false)
    private Date startTime;

    @Column(name = "end_time", insertable = false)
    private Date endTime;

    @Column(name = "auto_signed_off")
    private Boolean autoSignedOff;

}
