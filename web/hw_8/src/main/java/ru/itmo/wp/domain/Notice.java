package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(
        indexes = @Index(columnList = "creationTime")
)
public class Notice {
    @Id
    @GeneratedValue
    private long id;

    @Lob
    @NotNull
    @NotEmpty(message = "must not be empty")
    @NotBlank(message = "must have 1 letter or more")
    @Size(min = 1, max = 1024, message = "size must be between 1..1024")
    private String content;

    @CreationTimestamp
    private Date creationTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
