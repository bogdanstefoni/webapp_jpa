package com.bogdan.webapp.service;

import java.util.List;
import java.util.Optional;

import com.bogdan.webapp.dto.StudentDto;
import com.bogdan.webapp.dto.StudentResponseDto;
import com.bogdan.webapp.exception.RestResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bogdan.webapp.ErrorsEnum;
import com.bogdan.webapp.dao.StudentDao;
import com.bogdan.webapp.entity.Student;
import com.bogdan.webapp.exception.CustomException;
import com.bogdan.webapp.exception.NoDataFoundException;

@Service
public class StudentServiceImpl implements StudentService {

	private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	private StudentDao studentDao;



	@Autowired
	public StudentServiceImpl(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public ResponseEntity<String> findAll() {
		List<Student> students = studentDao.findAll();
		JSONArray jsonArray = new JSONArray();

		students.forEach(c -> {
			StudentResponseDto responseDto = mapToStudentResponseDTO(c);
			jsonArray.put(new JSONObject(responseDto));
		});

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("students", jsonArray);

		return RestResponse.createSuccessResponse(jsonObject);
	}

	@Override
	public ResponseEntity<String> findById(int id) {
		Student student = studentDao.findById(id).orElseThrow(()
				-> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));
		 StudentResponseDto responseDto = mapToStudentResponseDTO(student);
		return RestResponse.createSuccessResponse(new JSONObject(responseDto));
	}

	@Override
	public ResponseEntity<String> findByUsername(String username) {
		Student student = studentDao.findByUsername(username).orElseThrow(()
				-> new CustomException(ErrorsEnum.STUDENT_NOT_FOUND));
		return RestResponse.createSuccessResponse(new JSONObject(student));
	}

	@Override
	public ResponseEntity<String>  register(StudentDto studentDto) {
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

		StudentResponseDto responseDto = mapToStudentResponseDTO(student);

		logger.info("Student: " + studentDto.getUsername() + " logged in");

		return RestResponse.createSuccessResponse(new JSONObject(responseDto));
	}

	@Override
	public void update(Student student) {

		studentDao.update(student);
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
		return	mapper.map(studentDto, Student.class);
	}

	private StudentResponseDto mapToStudentResponseDTO(Student student) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper.map(student, StudentResponseDto.class);
	}


}
