package com.classconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class CourseRequest {
    
    @NotBlank
    @Size(max = 200)
    private String name;
    
    @NotBlank
    @Size(max = 20)
    private String subjectCode;
    
    @NotNull
    private UUID teacherId;
    
    private List<UUID> studentIds;
    
    public CourseRequest() {}
    
    public CourseRequest(String name, String subjectCode, UUID teacherId, List<UUID> studentIds) {
        this.name = name;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.studentIds = studentIds;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public UUID getTeacherId() { return teacherId; }
    public void setTeacherId(UUID teacherId) { this.teacherId = teacherId; }
    
    public List<UUID> getStudentIds() { return studentIds; }
    public void setStudentIds(List<UUID> studentIds) { this.studentIds = studentIds; }
}

