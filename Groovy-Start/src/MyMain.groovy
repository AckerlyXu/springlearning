

class Student{
	String name;
	int age;
	String getStudent() {
		name+age;
	}
}
def myStudent=new Student(name:"student name",age:15) as Student;
println("hello groovy")
def  stuMethod = myStudent.&getStudent;
println(stuMethod());