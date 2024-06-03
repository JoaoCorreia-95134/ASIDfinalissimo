package Asid.G1.gatewaydemo;

import Asid.G1.gatewaydemo.model.DTOs.*;
import Asid.G1.gatewaydemo.model.entity.*;
import Asid.G1.gatewaydemo.model.entity.base.BasePersonEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;

import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/Servicos")
public class listaServicos {

    @Autowired
    private RestTemplate restTemplate;
    private final ModelMapper mapper;

    public listaServicos(ModelMapper mapper){
        this.mapper = mapper;
        this.restTemplate= new RestTemplate();
    }

    @GetMapping("/getStudentsParent/{id}")
    public ResponseEntity<ParentDTO> getStudentsParent(@PathVariable("id") Long studentId) {

        ResponseEntity<Long> parentIDResponse = restTemplate.getForEntity(
                "http://student-service/students/getParent/{id}",
                Long.class,
                studentId
        );

        // Check if parent ID was successfully retrieved
        if (parentIDResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.notFound().build();
        }

        Long parentId = parentIDResponse.getBody();

        // Now, use the parent ID to fetch parent details
        ResponseEntity<ParentDTO> parentResponse = restTemplate.getForEntity(
                "http://parent-service/parents/{parentId}",
                ParentDTO.class,
                parentId
        );

        // Check if parent details were successfully retrieved
        if (parentResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.notFound().build();
        }

        // Return the parent details in the response
        return ResponseEntity.ok(parentResponse.getBody());
    }

    @GetMapping("/searchPersonsByTown/{town}")
    public ResponseEntity<List<ResponseEntity<?>>> searchPersonsByTown(@PathVariable("town") String town) {

        ResponseEntity<Long> idTownResponse = restTemplate.getForEntity(
                "http://town-service/Town/getByName/{cidade}",
                Long.class,
                town
        );

        // Check if parent ID was successfully retrieved
        if (idTownResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.notFound().build();
        }

        Long idTown = idTownResponse.getBody();

        ResponseEntity<ParentDTO[]> responseParent= listaByTown(idTown, "parent-service", "parents", ParentDTO.class);
        ResponseEntity<EmployeeDTO[]> responseEmployee= listaByTown(idTown, "employee-service", "employees",EmployeeDTO.class);

        ResponseEntity<TeacherDTO[]> responseTeacher= listaByTown(idTown, "teacher-service", "teachers",TeacherDTO.class);
        ResponseEntity<StudentDTO[]> responseStudent= listaByTown(idTown, "student-service", "students",StudentDTO.class);

        List<ResponseEntity<?>> nonNullResponses = new ArrayList<>();

        // Add parents to the allPersons list
        if (responseParent.getStatusCode() == HttpStatus.OK) {
            nonNullResponses.add(responseParent);
        }

        // Add employees to the allPersons list
        if (responseEmployee.getStatusCode() == HttpStatus.OK) {
            nonNullResponses.add(responseParent);
        }

        // Add students to the allPersons list
        if (responseStudent.getStatusCode() == HttpStatus.OK) {
            nonNullResponses.add(responseParent);
        }

        // Add teachers to the allPersons list
        if (responseTeacher.getStatusCode() == HttpStatus.OK) {
            nonNullResponses.add(responseParent);
        }

        if (nonNullResponses.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(nonNullResponses);
        }
    }



    @GetMapping("/searchPersonsBigCity")
    public ResponseEntity<List<ResponseEntity<?>>> searchPersonUrbana() {


        ResponseEntity<Long[]> responseIds = restTemplate.getForEntity(
                "http://town-service/Town/getBigCity",
                Long[].class);


        // Check if parent ID was successfully retrieved
        if (responseIds.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.notFound().build();
        }

        Long[] arrayIdsTown = responseIds.getBody();
        List<ResponseEntity<?>> nonNullResponses = new ArrayList<>();

        for (Long id : arrayIdsTown) {
            ResponseEntity<TeacherDTO[]> responseTeacher= listaByTown(id, "teacher-service", "teachers",TeacherDTO.class);

            if (responseTeacher.getStatusCode() == HttpStatus.OK) {
                nonNullResponses.add(responseTeacher);
            }

        }

        if (nonNullResponses.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(nonNullResponses);
        }
    }

    @PostMapping("/addClubToStudent/{clubId}/{studentId}")
    public ResponseEntity<List<ResponseEntity<?>>> addClubToStudent(@PathVariable("clubId") Long clubId, @PathVariable("studentId") Long studentId) {


        ResponseEntity<Student> responseStudent = restTemplate.getForEntity(
                "http://student-service/students/{studentId}",
                Student.class,
                studentId);

        System.out.println(responseStudent.getBody());

        // Check if parent ID was successfully retrieved
        if (responseStudent.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.notFound().build();
        }
        Student student = responseStudent.getBody();

        String clubUrl = "http://club-service/clubs/" + clubId + "/student/"+studentId;

        ResponseEntity<Club> responseClub = restTemplate.postForEntity(
                clubUrl,
                student,
                Club.class);

//        ResponseEntity<Student> responseFinal = restTemplate.postForEntity(
//                "http://localhost:8082/students/updateClub",
//                responseClub.getBody(),
//                Student.class);



        List<ResponseEntity<?>> nonNullResponses = new ArrayList<>();
        nonNullResponses.add(responseClub);

        if (nonNullResponses.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(nonNullResponses);
        }
    }

    @PostMapping("/addTeacher")
    public ResponseEntity<TeacherDTO> addTeacher(@Valid @RequestBody TeacherDTO teacherDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        UsersWithLargeTown user= mapper.map (teacherDTO, UsersWithLargeTown.class);

        ResponseEntity<Boolean> responseView = restTemplate.getForEntity(
                "http://town-service/Town/getPopulation/"+teacherDTO.getTown(),
                Boolean.class,
                user
        );


        if(responseView.getBody()){

            ResponseEntity<TeacherDTO> responseTeacher = restTemplate.postForEntity(
                    "http://teacher-service/teachers",
                    teacherDTO,  // Pass the received TeacherDTO object
                    TeacherDTO.class);

            if (!responseTeacher.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.notFound().build();
            }

            ResponseEntity<UsersWithLargeTown> responseFinal = restTemplate.postForEntity(
                    "http://view/View",
                    user,
                    UsersWithLargeTown.class);

            if (responseFinal.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(teacherDTO);
            }
        }else{
            ResponseEntity<TeacherDTO> responseTeacher = restTemplate.postForEntity(
                    "http://teacher-service/teachers",
                    teacherDTO,  // Pass the received TeacherDTO object
                    TeacherDTO.class);

            if (!responseTeacher.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(teacherDTO);
        }

    }


    @GetMapping ("/searchPersonsBigCityView")
    public ResponseEntity<List<UsersWithLargeTown>> searchPerson() {


        ResponseEntity<UsersWithLargeTown[]> responseVIEW = restTemplate.getForEntity(
                "http://view/View",
                UsersWithLargeTown[].class
        );

        if (responseVIEW.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.notFound().build();
        }else{
            List<UsersWithLargeTown> userList = Arrays.asList(responseVIEW.getBody());
            return ResponseEntity.ok(userList);
        }
    }

    @PostMapping("/addClubStudentParent/createStudent")
    public ResponseEntity<?> createStudent(@RequestBody Map<String, Object> payload) {

        String url = "http://saga/saga/createStudent";

        ResponseEntity<?> response = restTemplate.postForEntity(url, payload, String.class);

            return response;
    }




    public <T> ResponseEntity<T[]> listaByTown(Long idTown, String name, String local, Class<T> responseType){

        ResponseEntity<T[]> response = restTemplate.getForEntity(
                "http://"+name+"/"+local+"/getListByTown/{idTown}",
                (Class<T[]>) Array.newInstance(responseType, 0).getClass(),
                idTown);

        T[] lista = response.getBody();


        if (lista != null) {
            return ResponseEntity.ok(lista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
