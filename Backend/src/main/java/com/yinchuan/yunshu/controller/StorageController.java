package com.yinchuan.yunshu.controller;

import com.yinchuan.yunshu.service.StorageService;
import com.yinchuan.yunshu.config.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest controller for Storage functions
 * @author Siyuan Zeng
 *
 */
@RestController
public class StorageController {
    /**
     * Service Method for Storage function
     */
    @Autowired
    private StorageService storageService;

    /**
     * Find goods By userId Endpoint
     * @param userId The id of the expected user
     * @return The goods from the StorageService method findByUserId
     */
    @RequestMapping(method = RequestMethod.GET, path = "/storage/getByUserId")
    public List<Storage> findByUserId(@RequestParam int userId) {
        return storageService.findByUserId(userId);
    }

    /**
     * Find a specific good By userId Endpoint
     * @param userId The id of the expected user
     * @return The goods from the StorageService method findByUserId
     */
    @RequestMapping(method = RequestMethod.GET, path = "/storage/getByIdAndUserId")
    public Storage findByIdAndUserId(@RequestParam int id, int userId) {
        return storageService.findByIdAndUserId(id, userId);
    }



    /**
     * Find goods By type Endpoint
     * @param type The type of the expected goods
     * @return The goods from the StorageService method findByType
     */
    @RequestMapping(method = RequestMethod.GET, path = "/storage/getByType")
    public List<Storage> findByType(@RequestParam String type) {
        return storageService.findByType(type);
    }

    /**
     * Find all goods in storage
     */
    @RequestMapping(method = RequestMethod.GET, path = "/storage")
    public List<Storage> findAll(){return storageService.findAll();}

    /**
     * Add goods Endpont
     * @param good The good you want to add to storage, object = Storage
     * @return Message from the StorageService addGood.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/storage/addGood")
    public String addGood(@RequestBody Storage good) {
        return storageService.addGood(good);
    }

    /**
     * Edit good description Endpoint
     * @param id the id of the desired good
     * @param userId the id of the desired user
     * @param description the description you want add to this good
     * @return Message from the storageService editDescription
     */
    @RequestMapping(method = RequestMethod.POST, path = "/storage/editDescription")
    public String editDescription(@RequestParam int id, int userId, String description)
        {return storageService.editDescription(id, userId, description);}


}
