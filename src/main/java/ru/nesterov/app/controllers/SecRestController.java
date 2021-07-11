package ru.nesterov.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nesterov.app.domain.Security;
import ru.nesterov.app.exceptions.MyNotFoundException;
import ru.nesterov.app.repositories.SecRepo;

import java.util.List;


/**
 * @author Sergey Nesterov
 */
@RestController
@RequestMapping("/api/securities")
public class SecRestController {

    @Autowired
    SecRepo secRepo;

    @Autowired
    XmlToDataBase xmlToDataBase;

    @GetMapping
    public Iterable<Security> showSecurities(){
        Iterable<Security> securityIterable = secRepo.findAll();
        if(!securityIterable.iterator().hasNext()){
            throw new MyNotFoundException();
        }
        return securityIterable;
    }

    @PutMapping
    public void saveSecurity(@ModelAttribute Security security){
        secRepo.save(security);
    }

    @DeleteMapping
    public void deleteSecurity(@RequestParam(name = "secid") String secid){
        secRepo.delete(secRepo.findBySecid(secid));
    }

    @PostMapping
    public void fromFile(@RequestParam(name = "file") List<MultipartFile> files){
        files.forEach(file -> xmlToDataBase.parseSecurityXml(file));
    }
}
