//package blog.personnel.dm.utile;
//
//import blog.personnel.dm.entity.User;
//import blog.personnel.dm.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class init implements CommandLineRunner {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("#######Init user !!!!!!!!!");
//        User user = new User("Malal Diallo","Elpadrazo02","malal@gmail.com","123345678");
//        userRepository.save(user);
//        System.out.println(" user : " + user );
//    }
//}
