package com.vaadin.spring.ims.backend.service;

import com.vaadin.spring.ims.backend.entity.Intern;

import com.vaadin.spring.ims.backend.repository.InternRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InternService {
    private static final Logger LOGGER = Logger.getLogger(InternService.class.getName());
    private InternRepository internRepository;


    public InternService(InternRepository internRepository) {
        this.internRepository = internRepository;
    }

    public List<Intern> findAll() {
        return internRepository.findAll();
    }

    public List<Intern> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return internRepository.findAll();
        } else  {
            return  internRepository.search(filterText);
        }
    }

    public long count() {
        return internRepository.count();
    }

    public void delete(Intern intern) {
        internRepository.delete(intern);
    }

    public void save(Intern intern) {
        if (intern == null) {
            LOGGER.log(Level.SEVERE,
                "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        internRepository.save(intern);
    }

    @PostConstruct
   public void populateTestData() {


        if (internRepository.count() == 0) {
            Random r = new Random(0);
            internRepository.saveAll(
                Stream.of("Rajnish Patel VIT Learned-Springboot", "Cb Shivananda VIT learned-mysql","Shibani sahoo SRM explored-AWS","Chintamani sahoo KIIT Learned-docker" )
                    .map(name -> {
                        String[] split = name.split(" ");
                        Intern intern = new Intern();
                        intern.setFirstName(split[0]);
                        intern.setLastName(split[1]);
                        intern.setCollegeName(split[2]);
                        intern.setWorkInProgressDetails(split[3]);
                        String email = (intern.getFirstName() + "." + intern.getLastName() + "@" + "gmail" + ".com").toLowerCase();
                        intern.setEmail(email);
                        return intern;
                    }).collect(Collectors.toList()));
        }
    }
}
