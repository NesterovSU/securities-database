package ru.nesterov.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nesterov.app.domain.History;
import ru.nesterov.app.domain.MyInfo;
import ru.nesterov.app.exceptions.MyNotFoundException;
import ru.nesterov.app.repositories.HistRepo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Sergey Nesterov
 */
@RestController
@RequestMapping("/api")
public class HistRestController {

    @Autowired
    HistRepo histRepo;

    @Autowired
    XmlToDataBase xmlToDataBase;


    @GetMapping("histories")
    public Iterable<History> showHistories() {
        Iterable<History> historyIterable = histRepo.findAll();
        if (!historyIterable.iterator().hasNext()){
            throw new MyNotFoundException();
        }
        return historyIterable;
    }

    @PutMapping("histories")
    public void addHistory(@ModelAttribute History history) {
        histRepo.save(history);
    }

    @DeleteMapping("histories")
    public void deleteHistory(@RequestParam(name = "id") String id) {
        histRepo.delete(histRepo.findById(id).get());
    }

    @PostMapping("histories")
    public void fromFile(@RequestParam(name = "file") List<MultipartFile> files) {
        files.forEach(file -> xmlToDataBase.parseHistoryXml(file));
    }

    @GetMapping("info")
    public List<MyInfo> showInfo(
            @RequestParam(name = "secid") String secid,
            @RequestParam(name = "date") String date) {
        Iterable<History> historyIterable;
        if (!secid.isEmpty() && !date.isEmpty()) {
            historyIterable = histRepo.findBySecidAndTradedate(secid, date);
        } else if (!secid.isEmpty()) {
            historyIterable = histRepo.findBySecid(secid);
        } else if (!date.isEmpty()) {
            historyIterable = histRepo.findByTradedate(date);
        } else {
            historyIterable = histRepo.findAll();
        }
        if (!historyIterable.iterator().hasNext()){
            throw new MyNotFoundException();
        }
        List<MyInfo> myInfoList = new ArrayList<MyInfo>();
        historyIterable.forEach(history -> myInfoList.add(new MyInfo(history)));
        return myInfoList;
    }
}
