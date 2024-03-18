package HibernateCRUD;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = null;

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Insert");
            System.out.println("2. Update");
            System.out.println("3. Delete");
            System.out.println("4. Select");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    insertStudent(session, scanner);
                    break;
                case 2:
                    updateStudent(session, scanner);
                    break;
                case 3:
                    deleteStudent(session, scanner);
                    break;
                case 4:
                    selectStudents(session);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        session.close();
        sf.close();
    }

    private static void insertStudent(Session session, Scanner scanner) {
        Transaction tx = session.beginTransaction();
        student student = new student();
        System.out.println("Enter student Id :");
        student.setId(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Enter student name:");
        student.setName(scanner.nextLine());
        System.out.println("Enter student age:");
        student.setAge(scanner.nextInt());
        System.out.println("Enter student marks:");
        student.setMarks(scanner.nextDouble());
        scanner.nextLine(); // Consume newline
        System.out.println("Enter student gender:");
        student.setGender(scanner.nextLine());

        session.save(student);

        tx.commit();
        System.out.println("Student inserted successfully.");
    }

    private static void updateStudent(Session session, Scanner scanner) {
        Transaction tx = session.beginTransaction();

        System.out.println("Enter student ID to update:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        student student = session.get(student.class, studentId);
        if (student != null) {
            System.out.println("Enter new name:");
            student.setName(scanner.nextLine());
            System.out.println("Enter new age:");
            student.setAge(scanner.nextInt());
            System.out.println("Enter new marks:");
            student.setMarks(scanner.nextDouble());
            scanner.nextLine(); // Consume newline
            System.out.println("Enter new gender:");
            student.setGender(scanner.nextLine());

            session.update(student);
            tx.commit();
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    private static void deleteStudent(Session session, Scanner scanner) {
        Transaction tx = session.beginTransaction();

        System.out.println("Enter student ID to delete:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        student student = session.get(student.class, studentId);
        if (student != null) {
            session.delete(student);
            tx.commit();
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    private static void selectStudents(Session session) {
        Transaction tx = session.beginTransaction();

        String hql = "from student";
        Query query = session.createQuery(hql);
        List<student> list = query.getResultList();

        for (student student : list) {
            System.out.println(student);
        }

        tx.commit();
    }
}

//package HibernateCRUD;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;
//
//import servletHib.Student;
//
//import java.util.List;
//
//import javax.persistence.Query;
//
//public class App {
//	public static void main(String[] args) {
//
//		Configuration config = new Configuration();
//		config.configure("hibernate.cfg.xml");
//
//		SessionFactory sf = config.buildSessionFactory();
//		Session session = sf.openSession();
//
//		// Insert/update the table
//		student s1 = new student(); // Transient
//		/*
//		 * s1.setId(04); s1.setName("Golu"); s1.setAge(21); s1.setMarks(69);
//		 * s1.setGender("Male");
//		 */
//
//		Transaction tx = session.beginTransaction();
////		session.save(s1); // persistent
//
//		// Fetch
//
////		student fetchedStudent = session.get(student.class, 1); //
////		System.out.println(fetchedStudent);
//
//		// Create Query
//		
//		/*
//		 * List<student> students = session.createQuery("from student").list(); for
//		 * (student s : students) { System.out.println(s); }
//		 */
//		 
//
//		// Edit or modify the data (transaction commit)
////        student sToUpdate = session.get(student.class, 4);
////        sToUpdate.setName("Golu");
////        sToUpdate.setAge(22);
////        session.saveOrUpdate(sToUpdate);
//
////         Delete (transaction commit)
////        student sToDelete = session.get(student.class, 11);
////        session.delete(sToDelete);
//			
//
//        String hql = "from student where marks > 60";
//        Query query = session.createQuery(hql);
//        List<student> list = query.getResultList();
//
//        for (student s : list) {
//            System.out.println(s);
//        }
//
////        
//		tx.commit();
//		session.close(); // detached
//		sf.close();
//	}
//}