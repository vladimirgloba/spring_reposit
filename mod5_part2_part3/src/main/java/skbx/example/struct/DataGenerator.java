package skbx.example.struct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import skbx.example.struct.DataService.DataService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DataGenerator implements CommandLineRunner {
    @Autowired
    DataService dataService;


    @Override
    public void run(String... args) throws Exception {
//        dataService.authorGenerator();
//        dataService.bookGenerator();


        dataService.dataForAuthor();
        dataService.dataForBook();
    }
}
