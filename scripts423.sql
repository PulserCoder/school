select student.name, student.age, faculty.name from student
inner join faculty on faculty.id = student.faculty_id;

select student.* from avatar
inner join student on student.id = avatar.student_id;