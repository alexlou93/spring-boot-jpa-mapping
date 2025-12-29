package com.test01.jpa_uni.repository;

import com.test01.jpa_uni.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    /****** Meaning of findByInstructorInstructorId :
    findBy    	    SELECT query
    Instructor	    field name in Course entity
    InstructorId	field inside Instructor entity ******/
    List<Course> findByInstructorInstructorId(int instructorId);


    /** or you can use the method below // alternate approach **/
        @Query("SELECT c FROM Course c WHERE c.instructor.instructorId = :id")
        List<Course> findCourses(@Param("id") int id);

   /* @Modifying → tells Spring this is not a SELECT
      @Transactional → required for delete/update  */
    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.title = :title")
    void deleteCourse(@Param("title") String title);
}
