package com.bogdan.webapp.service;

import com.bogdan.webapp.ErrorsEnum;
import com.bogdan.webapp.dao.StudentDao;
import com.bogdan.webapp.dto.StudentDto;
import com.bogdan.webapp.dto.StudentResponseDto;
import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.exception.CustomException;
import com.bogdan.webapp.exception.RestResponse;
import com.bogdan.webapp.security.AuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentDao studentDao;

    private final AuthorizationService authorizationService;

    @Autowired
    private CustomRequestBean customRequestBean;

    public StudentServiceImpl(StudentDao studentDao, AuthorizationService authorizationService) {
        this.studentDao = studentDao;
        this.authorizationService = authorizationService;
    }

    @Override
    public ResponseEntity<String> findAll() {
        List<Student> students = studentDao.findAll();
        List<StudentResponseDto> studentsResponseList = new ArrayList<>();

        students.forEach(s -> {
            StudentResponseDto responseDto = mapToStudentResponseDTO(s);
            studentsResponseList.add(responseDto);
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("students", studentsResponseList);

        return RestResponse.createSuccessResponse(jsonObject);
    }

    @Override
    public ResponseEntity<String> findById() {
        int userId = customRequestBean.getUserId();
        Student student = studentDao.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));
        StudentResponseDto responseDto = mapToStudentResponseDTO(student);
        return RestResponse.createSuccessResponse(new JSONObject(responseDto));
    }

    @Override
    public ResponseEntity<String> findByUsername(String username) {
        Student student = studentDao.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));
        StudentResponseDto responseDto = mapToStudentResponseDTO(student);
        return RestResponse.createSuccessResponse(new JSONObject(responseDto));
    }

    @Override
    public ResponseEntity<String> register(StudentDto studentDto) {
        Optional<Student> existingStudent = studentDao.findByUsername(studentDto.getUsername());
        if (existingStudent.isPresent()) {
            throw new CustomException(ErrorsEnum.STUDENT_EXISTS);
        }

        Student student = mapToStudent(studentDto);

        return RestResponse.createSuccessResponse(new JSONObject(mapToStudentResponseDTO(studentDao.create(student))));
    }

    @Override
    public ResponseEntity<String> login(StudentDto studentDto) {

        Student student = studentDao.findByUsername(studentDto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));

        if (!StringUtils.equals(studentDto.getPassword(), student.getPassword())) {
            throw new CustomException(ErrorsEnum.LOGIN_WRONG_CREDENTIALS);
        }

        String jwtToken = authorizationService.generateJwtToken(student.getId(), student.getUsername());

        StudentResponseDto responseDto = mapToStudentResponseDTO(student);
        responseDto.setJwtToken(jwtToken);

        logger.info("Student: " + studentDto.getUsername() + " logged in");

        return RestResponse.createSuccessResponse(new JSONObject(responseDto));
    }

    @Override
    public ResponseEntity<String> update(StudentDto studentDto) {

        Student student = studentDao.findById(studentDto.getId())
                .orElseThrow(() -> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));

        mapToStudent(studentDto, student);

        StudentResponseDto responseDto = mapToStudentResponseDTO(studentDao.update(student));

        return RestResponse.createSuccessResponse(new JSONObject(responseDto));
    }

    @Override
    public void deleteById(int id) {
        studentDao.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        studentDao.findByUsername(username).orElseThrow(() -> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));
        studentDao.deleteByUsername(username);
    }

    private Student mapToStudent(StudentDto studentDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(studentDto, Student.class);
    }

    private void mapToStudent(StudentDto studentDto, Student student) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addMappings(new PropertyMap<StudentDto, Student>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
            }
        });
        mapper.map(studentDto, student);
    }

    private StudentResponseDto mapToStudentResponseDTO(Student student) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(student, StudentResponseDto.class);
    }

}
